import java.util.HashMap;
import java.util.Random;

public class Map {
    int width, num_mine, find_mine;
    String[][] mineMap, displayMap;
    boolean[] isClicked;
    HashMap<Integer, Integer> minePosition;

    public Map(int width, int num_mine) { //생성자
        find_mine = 0;
        this.width = width;
        this.num_mine = num_mine;
        //사이즈만큼 mineMap, displayMap 선언하고 0으로 채우기
        mineMap = new String[width][width];
        displayMap = new String[width][width];
        isClicked = new boolean[width*width];
        for (int i = 0; i < width*width; i++) {
            mineMap[i/width][i%width] = "X";
            displayMap[i/width][i%width] = "?";
        }

        //create mines
        Random rand = new Random();
        minePosition = new HashMap<>();
        for (int i = 0; i < num_mine; i++) {
            int position = rand.nextInt(width*width);
            while (minePosition.containsValue(position)) //check repetition
                position = rand.nextInt(width*width);
            minePosition.put(i, position);
            isClicked[i] = false;
        }

        //deploy mines
        for (int i = 0; i < num_mine; i++) {
            int r = minePosition.get(i) / width;
            int c = minePosition.get(i) % width;
            System.out.println(r+", "+c);;
            mineMap[r][c] = "O";
        }

        printMap(mineMap);
        printMap(displayMap);
    }

    public int checkMine(int r, int c) { //이 칸에 mine이 있는지?
        int pos = (r * width) + c;
        //찾았으면 찾았다, 없으면 없다 알려주기 + 몇개 남았는지도 알려줌
        // 만약에 상하좌우 힌트주면 여기다가 해야 할듯
        if (minePosition.containsValue(pos) && !isClicked[pos]) {
            isClicked[pos] = true;
            System.out.println("    Find mine at ("+r+", "+c+")");
            find_mine++;
            System.out.println(num_mine-find_mine + " mines left");
            hint(r,c);
            return pos;
        } else if (isClicked[pos]) {
            System.out.println("You already find!!");
            return -1;
        } else {
            System.out.println("    No mine at ("+r+", "+c+")");
            isClicked[pos] = true;
            hint(r,c);
            return -1;
        }
//        return 0;
    }

    public void hint(int r, int c) {
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

    public void updateMap(int r, int c) {
        displayMap[r][c] = "O";
        printMap(displayMap);
    }

    public void updateMapfailed(int r, int c) {
        displayMap[r][c] = "X";
        printMap(displayMap);
    }
}
