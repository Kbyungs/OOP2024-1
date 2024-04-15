import java.util.HashMap;
import java.util.Random;

public class Map {
    int width, num_mine, find_mine;
    int[][] mineMap, displayMap;
    HashMap<Integer, Integer> minePosition;

    public Map(int width, int num_mine) { //생성자
        find_mine = 0;
        this.width = width;
        this.num_mine = num_mine;
        //사이즈만큼 mineMap, displayMap 선언하고 0으로 채우기
        mineMap = new int[width][width];
        displayMap = new int[width][width];
        for (int i = 0; i < width*width; i++) {
            mineMap[i/width][i%width] = 0;
            displayMap[i/width][i%width] = 0;
        }

        //create mines
        Random rand = new Random();
        minePosition = new HashMap<>();
        for (int i = 0; i < num_mine; i++) {
            int position = rand.nextInt(width*width);
            while (minePosition.containsValue(position)) //check repetition
                position = rand.nextInt(width*width);
            minePosition.put(i, position);
        }

        //deploy mines
        for (int i = 0; i < num_mine; i++) {
            int r = minePosition.get(i) / width;
            int c = minePosition.get(i) % width;
            System.out.println(r+", "+c);;
            mineMap[r][c] = 1;
        }

        printMap(mineMap);
        printMap(displayMap);
    }

    public int checkMine(int r, int c) { //이 칸에 mine이 있는지?
        int pos = (r * width) + c;
        //찾았으면 찾았다, 없으면 없다 알려주기 + 몇개 남았는지도 알려줌
        // 만약에 상하좌우 힌트주면 여기다가 해야 할듯
        if (minePosition.containsValue(pos)) {
            System.out.println("    Find mine at ("+r+", "+c+")");
            find_mine++;
            System.out.println(num_mine-find_mine + " mines left");
            return pos;
        } else {
            System.out.println("    No mine at ("+r+", "+c+")");
            return -1;
        }
    }

    public void printMap(int[][] map) {
        System.out.println();
        for (int i = 0; i < map.length; i++) {
            System.out.print(map[i][0]);
            for (int j = 1; j < map[0].length; j++)
                System.out.print(" " + map[i][j]);
            System.out.println();
        }
    }

    public void updateMap(int r, int c) {
        displayMap[r][c] = 1;
        printMap(displayMap);
    }
}
