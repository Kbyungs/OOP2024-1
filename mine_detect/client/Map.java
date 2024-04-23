import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {
    int width;
    int num_mine;
    String[][] mineMap;
    String[][] displayMap;
    boolean[] isClicked;
    HashMap<Integer, Integer> minePosition;

    public Map(int width, int num_mine) {
        this.width = width;
        this.num_mine = num_mine;

        // create map
        System.out.println("Create  "+ width+" X "+ width + "  map");
        mineMap = new String[width][width];
        displayMap = new String[width][width];
        isClicked = new boolean[width*width];
        for (int i=0; i<width*width; i++) {
            mineMap[i/width][i%width] = "X";
            displayMap[i/width][i%width] = "_";
            isClicked[i] = false;
        }

        printMap(mineMap);

    }

//    public int checkMine(int r, int c) {
//        int pos = (r*width) + c;
//
//        if (minePosition.containsValue(pos) && !isClicked[pos]) {
//            isClicked[pos] = true;
//            System.out.println("   Find mine at (" + r + ", " + c + ")");
//            return pos;
//        } else if (isClicked[pos]) {
//            System.out.println("you already find!!");
//            return -1;
//        } else {
//            isClicked[pos] = true;
//            System.out.println("   No mine at ("+r+", "+c+")");
//            return -1;
//        }
//    }

//    public void printMap(int[][] a) {
//        System.out.println();
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(a[i][0]);
//            for (int j = 1; j < a[0].length; j++)
//                System.out.print(" " + a[i][j]);
//            System.out.println();
//        }
//    }
    public void printMap(String[][] map) {
        System.out.println();
        System.out.print("r\\c ");
        for (int i = 0; i < map.length; i++) {
            System.out.print(i+" ");
            if (i == map.length-1) System.out.println();
        }
        for (int i = 0; i < map.length; i++) {
            System.out.print(i +" | "+map[i][0]);
            for (int j = 1; j < map[0].length; j++)
                System.out.print(" " + map[i][j]);
            System.out.println();
        }
    }

    public void updateMap(int x, int y) {
        displayMap[x][y] = "O";
        printMap(displayMap);
    }
    public void updateMapFailed(int x, int y) {
        displayMap[x][y] = "X";
        printMap(displayMap);
    }
}
