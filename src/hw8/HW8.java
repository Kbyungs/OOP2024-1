import java.util.Scanner;

public class HW8 {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("원금을 입력하세요: ");
        float seedMoney = sc.nextFloat();
        float goal = seedMoney*2;
        System.out.print("연이율을 입력하세요: ");
        float yearRate = sc.nextFloat();

        System.out.println("연도수" + "\t" + "원리금");
        for (int i = 1; ;i++) {
            float futureMoney = seedMoney * (1 + yearRate / 100);
            System.out.println(i + "\t" + futureMoney);
            if (futureMoney > goal) { //over
                System.out.println("필요한 연도수 = " + i);
                break;
            } else {
                seedMoney = futureMoney;
            }
        }
    }
}
