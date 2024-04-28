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

        // create mines
        System.out.println("Create  "+num_mine+"  mines");
        Random r = new Random();
        minePosition = new HashMap<>();
        for (int i = 0; i < num_mine; i++) {
            int position = r.nextInt(width * width);
            while (minePosition.containsValue(position))   // check repetition
                position = r.nextInt(width * width);
            minePosition.put(i, position);
        }

        // deploy mines
        System.out.println("mine positions");
        for (int i = 0; i < num_mine; i++) {
            int x = minePosition.get(i) / width;
            int y = minePosition.get(i) % width;
            System.out.println(x+", "+y);
            mineMap[x][y] = "O";
        }

        printMap(mineMap);

    }
    public int hint(int r, int c) {
        int mine_nearby = 0;
        int[] dx = new int[] {0, 0, 1, -1, 1, -1, 1, -1};
        int[] dy = new int[] {1, -1, 0, 0, 1, -1, -1, 1};
        for (int i = 0; i < 8; i++) {
            int newRow = r + dx[i];
            int newCol = c + dy[i];
            if (newRow >= 0 && newRow < width && newCol >= 0 && newCol < width) {
                if (minePosition.containsValue((newRow * width) + newCol)) mine_nearby++;
            }
        }
        System.out.println("there are \""+mine_nearby+"\" mines nearby");
        return mine_nearby;
    }
    public int checkMine(int r, int c) {
        int pos = (r*width) + c;

        if (minePosition.containsValue(pos) && !isClicked[pos]) {
            isClicked[pos] = true;
            System.out.println("   Find mine at (" + r + ", " + c + ")");
            return 1;
        } else if (isClicked[pos]) {
            System.out.println("you already find!!");
            return 0;
        } else {
            isClicked[pos] = true;
            System.out.println("   No mine at ("+r+", "+c+")");
            return -1;
        }
    }

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
