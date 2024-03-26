import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;
import java.util.Vector;
import java.util.*;

class Server {
    public static int inPort = 9999;
    public static Vector<Client> clients = new Vector<Client>();
    public static Map<Client, Integer> choices = new HashMap<>();

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
        for(Client c : clients)
            c.send(msg);
    }
    public synchronized void rockScissorPaper(Client c, int choice) {
        choices.put(c, choice);
        if (choices.size() == clients.size()) {
            // 선택을 저장하는 배열을 준비합니다.
            int[] counts = new int[3]; // 가위(0), 바위(1), 보(2)의 갯수를 저장합니다.
            for (int ch : choices.values()) {
                counts[ch]++;
            }

            // 승리 조건을 검사합니다.
            String result;
            if (counts[0] > 0 && counts[1] > 0 && counts[2] > 0) {
                result = "비김"; // 모든 선택이 포함된 경우 비김 처리
            } else if (counts[0] > 0 && counts[1] > 0) {
                result = "바위가 승리"; // 바위(1)가 이김
            } else if (counts[1] > 0 && counts[2] > 0) {
                result = "보가 승리"; // 보(2)가 이김
            } else if (counts[0] > 0 && counts[2] > 0) {
                result = "가위가 승리"; // 가위(0)가 이김
            } else {
                result = "비김"; // 나머지 경우(모두 같은 선택을 한 경우)
            }

            sendtoall(result);
            choices.clear(); // 다음 라운드를 위해 선택을 지웁니다.
        }
    }


    class Client extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;

        public Client(Socket socket) throws Exception {
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
                while (true) {
                    line = in.readLine();
//            		System.out.println("("+ socket.getInetAddress()+ ") " + line);
                    System.out.println(line);
                    String[] parts = line.split(":");
                    int hand = Integer.parseInt(parts[1]);
                    rockScissorPaper(this,hand);
//                	sendtoall(line);
                }
            }
            catch (IOException e) { }
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
