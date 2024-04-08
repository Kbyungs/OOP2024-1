// Todo list
// 1. J,Q,K -> clear
// 2. improve dealer strategy
// 3. display history
// 4. split, insurance

import java.io.*;
import java.net.*;
import java.util.*;


class BlackJackClient {
    static int inPort = 9999;
    static String address ="192.168.0.104";
    static public PrintWriter out;
    static public BufferedReader in;
    static int card=0;
    static String nickName = null;
    static boolean firstDraw = true;


    public static void main(String[] args) {
        String msg;
        int newcard, dealer, choice=0;

        System.out.println("client가 실행되었습니다.");
        try (Socket socket = new Socket(address, inPort)) { //1번 : client 입장
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            Scanner sc = new Scanner(System.in);
//            nickName = sc.nextLine();
//            System.out.print("당신의 이름은? : ");//닉네임 입력하고싶음

            int nc = getCard(in);
            if (nc > 10) card += 10;
            else card += nc; //4.5번 카드 받은거임

            while (choice != 3) {
                choice = options();
                out.println(""+choice); //5번

                switch (choice) {
                    case 1:
                        newcard = getCard(in); //8번
                        if (newcard > 10) {
                            if (newcard == 11) System.out.println("new card is JACK");
                            if (newcard == 12) System.out.println("new card is QUEEN");
                            if (newcard == 13) System.out.println("new card is KING");
                            card += 10;
                        } else {
                            if (newcard == 1) System.out.println("new card is A");
                            else System.out.println("new card is "+newcard);
                            card += newcard;

                        }

                        if (card > 21) {
                            System.out.println(card+", Over 21! \n\n");
                            firstDraw = true;
                            card = getCard(in);
                        }
                        if (card == 21) {
                            System.out.println(card+", BLACKJACK! \n\n");
                            firstDraw = true;
                            card = getCard(in);
                        }

                        break;
                    case 2:
                        msg = in.readLine();
                        System.out.println("dealer has "+msg);
                        dealer = Integer.parseInt(msg);
                        if ((card > dealer) || (dealer > 21)) {
                            System.out.println("You win!\n\n");
                            firstDraw = true;
                        }
                        else if (card < dealer) {
                            System.out.println("Dealer win!\n\n");
                            firstDraw = true;
                        }
                        else {
                            System.out.println("tie! \n\n");
                            firstDraw = true;
                        }

                        card = getCard(in);
                        break;
                    case 3:
                        System.out.println("Exit");
                        in.close();
                        out.close();
                        socket.close();
                        break;
                }
            }

        }
        catch (Exception e) {}
    }


    private static int options() {
        if (card > 10 && firstDraw) {
            if (card == 11) {
                System.out.println("You have JACK");
            }
            if (card == 12) {
                System.out.println("You have QUEEN");
            }
            if (card == 13) {
                System.out.println("You have KING");
            }
            card = 10;
        } else if (card == 1) {
            System.out.println("You have A");
        } else {
            System.out.println("You have " + card);
        }
        firstDraw = false;

        System.out.println("	1. Hit");
        System.out.println("	2. Hold");
        System.out.println("	3. Exit");
        System.out.print("Choose an option: ");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        while ((choice < 1) || (choice > 3)) {
            System.out.println("Invalid input!");
            System.out.println("Please enter an integer value between 1 and 4.");
            System.out.println("	1. Hit");
            System.out.println("	2. Hold");
            System.out.println("	3. Exit");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
        }

        return choice;
    }



    public static int getCard(BufferedReader in) {
        String msg="";
        try {
            msg = in.readLine();
        } catch (IOException e) { }
        int newcard = Integer.parseInt(msg);
        return newcard;
    }
}

