import java.util.Scanner;

public class P3_2 {
    static String studentId;
    static int admissionYear, admissionType, admissionNum;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("학번은? ");
        studentId = sc.next();

        checkingID(studentId);
    }
    public static void checkingID(String i) {
        boolean isValid = true;
        int year = Integer.parseInt(i.substring(2,4));
        int type = Integer.parseInt(i.substring(4,5));
        int turn = Integer.parseInt(i.substring(5));
        System.out.println("입학 연도: " + year);
        System.out.println("입학 유형: " + type);
        System.out.println("입학 순번: " + turn);

        if (i.length() != 8) {
            System.out.println("틀린 이유: 길이가 8이 아니다.");
            isValid = false;
        }
        if (i.charAt(0) != '6' || i.charAt(1) != '0') {
            System.out.println("틀린 이유: 60으로 시작하지 않는다.");
            isValid = false;
        }
        if (type != 2 && type != 5) {
            System.out.println("틀린 이유: 입학 유형");
            isValid = false;
        }
        if (turn <= 0 || turn > 999) {
            System.out.println("틀린 이유: 입학 순번");
            isValid = false;
        }
        if (isValid) System.out.println("학번이 맞다.");
    }
}
