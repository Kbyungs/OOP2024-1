import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Map {
    int width;
    int num_mine;
    int[][] mineMap;
    int[][] displayMap;
    HashMap<Integer, Integer> minePosition;

    public Map(int width, int num_mine) {
        this.width = width;
        this.num_mine = num_mine;

        // create map
        System.out.println("Create  "+ width+" X "+ width + "  map");
        mineMap = new int[width][width];
        displayMap = new int[width][width];
        for (int i=0; i<width*width; i++) {
            mineMap[i/width][i%width] = 0;
            displayMap[i/width][i%width] = 0;
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
            mineMap[x][y] = 1;
        }

        printMap(mineMap);

    }

    public int numMine(int r, int c) {
        if (minePosition.containsValue(r*width+c))
            return -1;
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
        return mine_nearby;
    }

    public int checkMine(int x, int y) {
        int pos = (x*width) + y;

        if (minePosition.containsValue(pos)) {
            //System.out.println("   Find mine at ("+x+", "+y+")");
            return  -1;
        }
        else {
            //System.out.println("   No mine at ("+x+", "+y+")");
            return numMine(x,y);
        }

    }

    public void printMap(int[][] a) {
        System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i][0]);
            for (int j = 1; j < a[0].length; j++)
                System.out.print(" " + a[i][j]);
            System.out.println();
        }
    }

    public void updateMap(int x, int y) {
        displayMap[x][y]=1;
        printMap(displayMap);
    }
}
