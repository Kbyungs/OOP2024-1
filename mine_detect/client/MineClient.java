// enoy mine game by dr.han
// ToDo list
// 1. statistic (#success, #fail)
// 2. prevent same trial

import java.io.*;
import java.net.*;
import java.util.*;


class MineClient {
    static int inPort = 9999;
    static String address ="10.10.193.49";
    static public PrintWriter out;
    static public BufferedReader in;
    static int width = 0;
    static int num_mine = 0;
    static int num_try, num_success;
    static Map map;


    public static void main(String[] args) {
        int find = 0;
        int guessNum = 0;

        try (Socket socket = new Socket(address, inPort)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            initial();


            while(find < num_mine) {
                guessNum = guess();
                find += guessNum;
                if (guessNum != Integer.MAX_VALUE) System.out.println((num_mine-find)+" mines left");
                else break;
            }

            if (guessNum != Integer.MAX_VALUE) {
                System.out.println("Success found " + find + " mines !");
                out.println("Done");
            } else {
                out.println("check");
            }
            in.close();
            out.close();
            socket.close();

        }
        catch (Exception e) {}
    }


    public static void initial() {
        System.out.println("I My Me \"Mine\" Game !");
        num_success = 0; num_try = 0;
        Scanner scan = new Scanner (System.in);

        System.out.print("Enter the width (1~10, width x width) :");
        width = scan.nextInt();
        while ((width < 1) || (width>10)) {
            System.out.println("Invalid width, enter 1~10 !");
            width = scan.nextInt();
        }

        System.out.print("Enter number of mines : ");
        num_mine = scan.nextInt();
        while ((num_mine >= width * width) || (num_mine< 1)) {
            System.out.println("Invalid number of mines, must be 0 ~ " + width * width);
            num_mine = scan.nextInt();
        }

        out.println(width+","+num_mine);
        map = new Map(width, num_mine);
    }


    public static int guess() throws IOException {
        Scanner scan = new Scanner (System.in);

        System.out.print("\n Enter Row coordinate(0 ~ "+width+") :");
        int r = scan.nextInt();
        while ((r < 0) || (r >= width)) {
            if (r == -1) {
                checkStatistics();
                return Integer.MAX_VALUE;
            }
            System.out.println(" Invalid Row, enter a new Row coordinate");
            r = scan.nextInt();
        }
        System.out.print(" Enter Column coordinate(0 ~ "+width+") :");
        int c = scan.nextInt();
        while ((c < 0) || (c >= width)) {
            if (c == - 1) {
                checkStatistics();
                return Integer.MAX_VALUE;
            }
            System.out.println(" Invalid Column, enter a new Column coordinate");
            c = scan.nextInt();
        }
        out.println(r+","+c);

        String msg = in.readLine();
        int result = Integer.parseInt(msg);
        if (result>0) {
            System.out.println("   Find mine at ("+r+", "+c+")");
            num_try++; num_success++;
            map.updateMap(r,c);
            return 1;
        } else if (result == 0) {
            System.out.println("you already find!!");
            return 0;
        } else {
            System.out.println("   No mine at ("+r+", "+c+")");
            num_try++;
            map.updateMapFailed(r,c);
            return 0;
        }
    }

    public static void checkStatistics() {
        System.out.println("total try: "+num_try);
        if (num_try == 0) System.out.println("probability of success: 0%");
        else System.out.print("probability of success: "+((double)num_success/num_try*100)+"%");
    }

}

