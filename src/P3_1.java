import java.util.Scanner;

public class P3_1 {
    static boolean cheating = false;
    static int attend = 0;
    static int totalScore = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("부정행위 여부(true 혹은 false): ");
        cheating = sc.nextBoolean();
        System.out.print("출석률(0~ 100사이의 정수값): ");
        attend = sc.nextInt();
        System.out.print("총점 (0~ 100사이의 정수값): ");
        totalScore = sc.nextInt();
        System.out.print("학점 = " + getGrade(cheating,attend,totalScore));
    }

    public static String getGrade(boolean c, int a, int s) {
        if (c || a < 80 || s < 60) return "F";
        if (s >= 90) return "A";
        if (s >= 80) return "B";
        if (s >= 70) return "C";
        if (s >= 60) return "D";
        return "0";
    }
}
