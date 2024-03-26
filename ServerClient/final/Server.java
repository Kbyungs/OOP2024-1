import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;
import java.util.Vector;
import java.util.*;

class Server {
    public static int inPort = 9999;
    public static List<Client> clients = new ArrayList<Client>();
    public static int choicesCount = 0;

    public static void main(String[] args) throws Exception {
        new Server().createServer();
    }

    public void createServer() throws Exception {
        ServerSocket server = new ServerSocket(inPort);
        while (true) {
            Socket socket = server.accept();
            Client c = new Client(socket);
            clients.add(c);
        }
    }

    public void sendtoall(String msg) {
        System.out.println(msg);
        for (Client c : clients)
            c.send(msg);
    }

    private void determineWinner() {
        // 선택된 가위, 바위, 보의 수를 카운트
        boolean[] count = new boolean[3]; // 0: 가위, 1: 바위, 2: 보
        String winnerList = "";
        for (Client client : clients) {
            int choice = client.hands;
            if (choice >= 0 && choice < 3) {
                count[choice] = true;
            }
        }

        // 승리 조건 계산
        int winnerChoice = -1;
        if (count[0] && !count[1] && count[2]) { // 가위만 승리
            winnerChoice = 0;
        } else if (count[1] && !count[2] && count[0]) { // 바위만 승리
            winnerChoice = 1;
        } else if (count[2] && !count[0] && count[1]) { // 보만 승리
            winnerChoice = 2;
        }

        // 승리한 클라이언트 찾기
        for (Client client : clients) {
            System.out.println(client.hands == winnerChoice);
            if (client.hands == winnerChoice) {
                System.out.println("승리자 목록 "+winnerList.length());
                if (!winnerList.isEmpty()) {
                    winnerList += ", ";
                }
                System.out.println(client.userID);
                winnerList += client.userID;
            }
        }

        if (winnerChoice == -1) {
            sendtoall("비김"); // 승자가 없는 경우
        } else {
            sendtoall(winnerList.toString() + " 승리!"); // 승리 메시지 전송
        }
    }

    public synchronized void clientMadeChoice() {
        choicesCount++;
        if (choicesCount == clients.size()) { // 모든 클라이언트가 선택을 완료했는지 확인
            determineWinner();
            choicesCount = 0; // 다음 라운드를 위해 카운트를 리셋합니다.
        }
    }



    class Client extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;
        String userID;
        int hands = -1;

        public Client(Socket socket) throws IOException {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            start();
        }

        public void send(String msg) {
            out.println(msg);
        }

        @Override
        public void run() {
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("userID:")) {
                        // userID 처리
                        this.userID = line.substring("userID:".length()).trim();
                    } else {
                        // line에서 실제 선택 추출 (예: "강병수 : 1"에서 숫자만 추출)
                        String[] parts = line.split(" : ");
                        if (parts.length == 2) { // userID와 선택이 모두 있는 경우
                            try {
                                this.hands = Integer.parseInt(parts[1].trim()); // 숫자 부분만 추출하여 정수로 변환
                                Server.this.clientMadeChoice();
                                System.out.println(line);
                            } catch (NumberFormatException e) {
//                                System.out.println(line);
                                sendtoall(line);
                            }
                        }
                    }
                }
            } catch (IOException e) { }
            finally {
                try {
                    if (out != null)
                        out.close();
                    if (in != null) {
                        in.close();
                        socket.close();
                    }
                }
                catch (IOException e) { }
            }
        }

    }
}
