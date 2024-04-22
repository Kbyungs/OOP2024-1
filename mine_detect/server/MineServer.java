// enjoy mine game by dr.han
// ToDo list
// 1. statistic (#success, #fail)
// 2. prevent same trial 


import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;


class MineServer {
    public static int inPort = 9999;
    public static Vector<Client> clients = new Vector<Client>();


    public static void main(String[] args) throws Exception {
        new MineServer().createServer();
    }

    public void createServer() throws Exception {
        System.out.println("Server start running ..");
        ServerSocket server = new ServerSocket(inPort);
        while (true) {
            Socket socket = server.accept();
            Client c = new Client(socket);
            clients.add(c);
        }
    }


    class Client extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;
        int width=0, num_mine=0;
        Map map;


        public Client(Socket socket) throws Exception {
            System.out.println("\n\n"+socket.getInetAddress()+ "  join ");
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            initial();

            start();
        }


        public void initial() throws IOException {
            String msg = in.readLine();
            String[] arr = msg.split(",");
            width = Integer.parseInt(arr[0]);
            num_mine = Integer.parseInt(arr[1]);

            map = new Map(width, num_mine);
        }


        @Override
        public void run() {
            String msg;

            try {
                while(true) {
                    msg = in.readLine();
                    if(msg.equalsIgnoreCase("Done")) {
                        break;
                    }
                    String[] arr = msg.split(",");
                    int x = Integer.parseInt(arr[0]);
                    int y = Integer.parseInt(arr[1]);

                    int result = map.checkMine(x,y);
                    out.println(""+result);
                    if(result > 0) {
                        map.updateMap(x, y);
                    }
                }

                System.out.println("Success found "+num_mine+" mines !");
                out.close();
                in.close();
                socket.close();
            }
            catch (IOException e) { }
        }






    }

} 
