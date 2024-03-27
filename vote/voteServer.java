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
    public static Map<String, Integer> votes = new HashMap<>();

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

    public static void sendtoall(String msg) {
        System.out.println(msg);
        for (Client c : clients)
            c.send(msg);
    }



    public static String maxVoteCandidates(Map<String, Integer> votes) {
        int maxVotes = 0;
        List<String> maxVoteCandidates = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : votes.entrySet()) {
            int voteCount = entry.getValue();
            if (voteCount > maxVotes) {
                maxVotes = voteCount;
                maxVoteCandidates.clear(); // 이전에 저장된 후보자 목록을 지움
                maxVoteCandidates.add(entry.getKey()); // 새로운 최대 득표 후보자 추가
            } else if (voteCount == maxVotes) {
                maxVoteCandidates.add(entry.getKey()); // 동일한 최대 득표 수를 가진 후보자 추가
            }
        }

        return String.join(", ", maxVoteCandidates)+" 당선!!";
    }

    public static void addVote(Map<String, Integer> votes, String candidate) {
        int currentVotes = votes.getOrDefault(candidate, 0);
        votes.put(candidate, currentVotes + 1);
        int totalVotes = 0;
        for (Integer voteCount : votes.values()) {
            totalVotes += voteCount;
        }
        if (totalVotes == clients.size()) {
            sendtoall(maxVoteCandidates(votes));
        }

    }

    class Client extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;
        String userID;

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
            String voteMsg = "!vote ";
            try {
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("userID:")) {
                        // userID 처리
                        this.userID = line.substring("userID:".length()).trim();
                        votes.put(this.userID, 0);
                    } else if (line.contains(voteMsg)) {
                        int index = line.indexOf(voteMsg);
                        if (index != -1) {
                            String v = line.substring(index + voteMsg.length()).trim();
                            addVote(votes, v);
                        }
                    } else if (line.contains("!cand")) {
                        for (Client c: clients) send("후보자: " + c.userID);
                    } else {
                        System.out.println(line);
                        sendtoall(line);
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
