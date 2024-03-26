import java.io.*;
import java.net.*;
import java.util.*;


class Client {
    public static int inPort = 9999;
    public static String address ="192.168.0.6";
    public static String line = null;
    public static String userID = "unknown";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("이름은? : ");
        userID = sc.nextLine();
        System.out.println("가위 : 0, 바위 : 1, 보 : 2");

        try (Socket socket = new Socket(address, inPort)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            ChatHandler c = new ChatHandler(socket);
            Thread myThread = new Thread(c);
            myThread.start();
            Scanner scn = new Scanner(System.in);

            while (!"gogo".equalsIgnoreCase(line)) {
                line = scn.nextLine();
                out.println(userID + ":" + line);
                out.flush();
            }
            scn.close();
        }
        catch (Exception e) {}
    }

}


class ChatHandler implements Runnable {
    private final Socket socket;;


    public ChatHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
//                System.out.println("("+ socket.getInetAddress()+  ") " + line);
                System.out.println(line);
            }

        }
        catch (IOException e) { }
        finally {
            try {
                if (in != null) {
                    in.close();
                    socket.close();
                }
            }
            catch (IOException e) { }
        }
    }
}
