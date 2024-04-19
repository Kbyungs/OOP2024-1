// Enjoy mine game
// dr.seungchul@gmail.com
/*
<To Do List>
1. welcome, 입력 message 등 UI 고치기 -> clear
2. 좌표입력값에 -1을 입력하면  statistic (횟수, 성공확률 등) 출력하고 종료 -> clear
3. 한번 찾은 지뢰는 다시 안 찾게 처리 -> clear 그냥 한번 클릭한 칸은 못열어봄
4. 코드내의 모든 variable, method 이름 바꾸기
5. 하나 고르면 상하좌우 주변 지뢰 갯수 알려주기 -> clear
* 본 코드를 기반으로 2가지 온라인 게임제작예정이니, 코드 철저히 분석
*/
import java.util.*;

public class DetectionGame {
    public static Map map;
    public static int width;
    public static int num_mine;
    public static int num_try, num_success;

    public static  void main(String[] args) {
        int find = 0;
//        int num_try, num_success;
        initial();
        //다 찾을 때까지 무한 반복
        while (find < num_mine) find += guess();
        if (find != Integer.MAX_VALUE) System.out.println("All mines you found!");
    }

    private static void initial() { //초기 입력 받고 기본적인 map setting
        System.out.println("I My Me \"Mine\" Game !");
        num_success = 0; num_try = 0;
        Scanner sc = new Scanner(System.in);
        //map 크기 설정
        System.out.print("Enter the width (1 ~ 10, width x width): ");
        width = sc.nextInt();
        while ((width < 1) || (width > 10)) {
            System.out.println("Invalid width, enter 1~10");
            width = sc.nextInt();
        }
        //mine 갯수 설정
        System.out.print("Enter number of mines :");
        num_mine = sc.nextInt();
        while ((num_mine >= width*width) || (num_mine < 1)) {
            System.out.print("Invalid number of mines, must be 0 ~ " +width*width+" : ");
            num_mine = sc.nextInt();
        }
        //Map파일에서 map클래스 새로 선언(설정한대로)
        map = new Map(width, num_mine);
    }

    public static int guess() { //한 칸씩 찍어보기 범위 틀리면 말해주고, checkMine, updateMine함수로 연결
        Scanner sc = new Scanner(System.in);
        System.out.print(" Enter Row coordinate(0 ~ "+width+") :");
        int r = sc.nextInt();
        while ((r < 0) || (r >= width)) {
            if (r == -1) {
                checkStatistics();
                return Integer.MAX_VALUE;
            }
            System.out.print(" Invalid Row, enter a new Row coordinate : ");
            r = sc.nextInt();
        }

        System.out.print(" Enter Column coordinate(0 ~ "+width+") : ");
        int c = sc.nextInt();

        while ((c < 0) || (c >= width)) {
            if (c == -1) {
                checkStatistics();
                return Integer.MAX_VALUE;
            }
            System.out.print(" Invalid Column, enter a new Column coordinate : ");
            c = sc.nextInt();
        }
        if (map.checkMine(r, c) >= 0) { // 성공했다는 뜻
            num_try++; num_success++;
            map.updateMap(r, c);
            return 1;
        } else { // 실패했다
            num_try++;
            map.updateMapfailed(r,c);
            return 0;
        }
    }

    public static void checkStatistics() {
        System.out.println("total try: "+num_try);
        if (num_try == 0) System.out.println("probability of success: 0%");
        else System.out.println("probability of success: "+((double)num_success/num_try*100)+"%");
    }
}
