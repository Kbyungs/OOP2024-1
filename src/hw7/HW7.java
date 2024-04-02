import java.util.*;
public class HW7 {
    static Random random = new Random();
    static int first, second, third;
    static boolean isExit = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (!isExit) {
            first = random.nextInt(9) + 1;
            second = random.nextInt(9) + 1;
            third = random.nextInt(9) + 1;
            System.out.println("숫자들: " + first + "  " + second + "  " + third);
            if ((first == second) && (second == third)) System.out.println("1등 당첨");
            else if ((first != second) && (second != third) && (first != third)) System.out.println("꽝");
            else System.out.println("2등 당첨");
            while (true) {
                System.out.print("게임 계속?(예 혹은 아니오): ");
                String temp = sc.nextLine();
                if (temp.equals("아니오")){
                    isExit = true;
                    break;
                } else if (temp.equals("예")) break;
                System.out.println("예 혹은 아니오 로 대답해주세요");
            }
        }
    }
}
