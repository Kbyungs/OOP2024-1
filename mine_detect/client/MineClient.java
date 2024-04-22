// enoy mine game by dr.han
// ToDo list
// 1. statistic (#success, #fail)
// 2. prevent same trial

import java.io.*;
import java.net.*;
import java.util.*;


class MineClient {
    static int inPort = 9999;
    static String address ="192.168.0.103";
    static public PrintWriter out;
    static public BufferedReader in;
    static int width=0;
    static int num_mine=0;
    static Map map;


    public static void main(String[] args) {
        int find=0;

        try (Socket socket = new Socket(address, inPort)) {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            initial();


            while(find<num_mine) {
                find += guess();
                System.out.println((num_mine-find)+" mines left");
            }

            System.out.println("Success found "+find+" mines !");
            out.println("Done");
            in.close();
            out.close();
            socket.close();

        }
        catch (Exception e) {}
    }


    public static void initial() {
        System.out.println("Welcome to the Mine Game !");
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

        System.out.print("\n Enter x coordinate(0 ~ "+width+") :");
        int x = scan.nextInt();
        while ((x < 0) || (x >= width)) {
            System.out.println(" Invalid x, enter a new x coordinate");
            x = scan.nextInt();
        }
        System.out.print(" Enter y coordinate(0 ~ "+width+") :");
        int y = scan.nextInt();
        while ((y < 0) || (y >= width)) {
            System.out.println(" Invalid y, enter a new y coordinate");
            y = scan.nextInt();
        }
        out.println(x+","+y);

        String msg = in.readLine();
        int result = Integer.parseInt(msg);
        if (result>=0) {
            System.out.println("   Find mine at ("+x+", "+y+")");
            map.updateMap(x,y);
            return 1;
        }
        System.out.println("   No mine at ("+x+", "+y+")");
        return 0;
    }


}

