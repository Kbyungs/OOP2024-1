// Todo list
// 1. J,Q,K -> clear
// 2. improve dealer strategy
// 3. display history
// 4. split, insurance


import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;




class BlackJackServer {
    public static int inPort = 9999;
    public static Vector<Client> clients = new Vector<Client>();


    public static void main(String[] args) throws Exception {
        new BlackJackServer().createServer();
    }

    public void createServer() throws Exception {
        System.out.println("강병수 카지노 작동중....");
        ServerSocket server = new ServerSocket(inPort);
        while (true) {
            Socket socket = server.accept();
            //2번 : 서버 열기
            Client c = new Client(socket);
            clients.add(c);
        }
    }

    public int getCard() {
        Random random = new Random();
        int newcard = random.nextInt(13)+1;
        if (newcard > 1 && newcard < 11) System.out.println("new card is "+newcard);
        if (newcard == 1) System.out.println("new card is A");
        if (newcard == 11) System.out.println("new card is JACK");
        if (newcard == 12) System.out.println("new card is QUEEN");
        if (newcard == 13) System.out.println("new card is KING");
        return newcard;
    }


    class Client extends Thread {
        Socket socket;
        PrintWriter out = null;
        BufferedReader in = null;
        int card;
        boolean hadAce = false;

        public Client(Socket socket) throws Exception {
            System.out.println("\n\n"+socket.getInetAddress()+ "  join "); //3번
            this.socket = socket;
            card = 0;
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            start();

            int newCard = getCard();
            if (newCard > 10) {
                if (newCard == 11) System.out.println("new card is JACK");
                if (newCard == 12) System.out.println("new card is QUEEN");
                if (newCard == 13) System.out.println("new card is KING");
                card += 10;
            } else {
                if (newCard == 1) {
                    System.out.println("new card is A");
                    hadAce = true;
                }
                else System.out.println("new card is "+newCard);
                card += newCard;
                if (hadAce && card + 10 <= 21) card += 10;
            }
//            card += newCard;
            send(""+newCard); //4번 client에게 카드 주는거
            System.out.println(socket.getInetAddress()+ "  has "+ card);

        }

        public void send(String msg) {
            out.println(msg);
        }


        @Override
        public void run() {
            String msg;
            int newcard;

            try {
                while(true) {
                    msg = in.readLine(); //6번

                    if (msg.equalsIgnoreCase("1")) {
                        System.out.println(socket.getInetAddress()+" hit");
                        newcard = getCard();
                        if (newcard > 10) card += 10;
                        else card += newcard;
                        System.out.println(socket.getInetAddress()+" has "+card);
                        send(""+newcard); //7번

                        if (card > 21) {
                            System.out.println(socket.getInetAddress()+" has "+card+", Over 21! \n\n");
                            card = getCard();
                            hadAce = false;
                            send(""+card);
                        }
                        if (card == 21) {
                            System.out.println(socket.getInetAddress()+" has "+card+", BLACKJACK! \n\n");
                            card = getCard();
                            hadAce = false;
                            send(""+card);
                        }

                    }
                    else if (msg.equalsIgnoreCase("2")) {
                        System.out.println(socket.getInetAddress()+" hold");
                        Random random = new Random();
                        int dealer = random.nextInt(11) + 16;
                        System.out.println("Dealer's hand: " + dealer);
                        send(""+dealer);

                        if ((card > dealer) || (dealer > 21)) {
                            System.out.println(socket.getInetAddress()+ " win!\n\n");
                            hadAce = false;
                        }
                        else if (card < dealer) {
                            System.out.println("Dealer win!\n\n");
                            hadAce = false;
                        }
                        else {
                            System.out.println("tie! \n\n");
                            hadAce = false;
                        }

                        card = getCard();
                        send(""+card);

                    }
                    else {
                        System.out.println(socket.getInetAddress()+" Exit");
                        in.close();
                        out.close();
                        socket.close();
                        break;
                    }

                }
            }
            catch (IOException e) { }
        }

    }

} 
