import java.io.*;
import java.net.*;
import java.util.*;


class VoteClient {
    public static int inPort = 9999;
    public static String address ="192.168.0.6";
    public static String line = null;
    public static String userID = "unknown";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("투표 시 !vote 후 후보 이름 작성");
        System.out.println("후보자가 궁금하면 !cand 입력");
        System.out.print("이름은? : ");
        userID = sc.nextLine();

        try (Socket socket = new Socket(address, inPort)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("userID:" + userID);
            out.flush();


            ChatHandler c = new ChatHandler(socket);
            Thread myThread = new Thread(c);
            myThread.start();
            Scanner scn = new Scanner(System.in);

            while (!"gogo".equalsIgnoreCase(line)) {
                line = scn.nextLine();
                out.println(userID + " : " +line);
                out.flush();
            }
            scn.close();
        }
        catch (Exception e) {}
    }

}


