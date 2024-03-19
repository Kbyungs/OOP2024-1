import java.util.*;

public class P0_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("프로그래밍 과목의 점수를 입력하세요: ");
        int pgScore = sc.nextInt();
        System.out.print("이산수학 과목의 점수를 입력하세요: ");
        int dmScore = sc.nextInt();

        double average = ((double)pgScore + (double)dmScore)/2;

        System.out.println("평균 점수: " + average);
    }
}