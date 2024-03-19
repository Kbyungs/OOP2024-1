import java.util.Scanner;

public class P3_3 {
    public static void main(String[] args) {
        float gpa;
        int toeic;
        Scanner sc = new Scanner(System.in);
        System.out.print("평점을 입력하세요: ");
        gpa = sc.nextFloat();
        System.out.print("TOEIC 점수를 입력하세요: ");
        toeic = sc.nextInt();
        test(gpa, toeic);
    }

    public static void test(float g, int t) {
        boolean toeic = false;
        if (t >= 700) toeic = true;
        if (g < 3 || !toeic) {
            System.out.println("지원할 수 없습니다.");
            return;
        }
        if (g < 3.5) {
            System.out.println("필기 시험 대상자입니다.");
            return;
        }
        if (g < 4) {
            System.out.println("서류 전형 대상자입니다.");
            return;
        }
        System.out.println("면접 대상합니다.");
    }
}
