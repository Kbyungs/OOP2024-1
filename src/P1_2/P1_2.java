import java.util.*;

public class P1_2 {
    public static void main(String[] args) {
        int MONEY = 1000;
        Scanner sc = new Scanner(System.in);
        System.out.print("물건의 가격(1,000원 이하)을 입력하세요: ");
        int price = sc.nextInt();
        int change = MONEY - price;
        System.out.println(price + "원 짜리 물건을 샀고 1,000원을 내셨습니다.");
        System.out.println("거스름돈은 " + change +"원입니다.");
        System.out.println("거스름돈의 내역은 다음과 같습니다:\n");

        int coins[] = {500, 100, 50, 10, 5, 1};
        for (int i = 0; i < coins.length; i++) {
            int coinNum = change / coins[i];
            change -= coins[i] * coinNum;
            System.out.println(coins[i] + "원 짜리 동전 갯수 = " + coinNum);
        }
    }
}
