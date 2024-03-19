import java.util.*;
import java.math.*;

public class P1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("구의 반지름을 입력하세요: ");
        double r = sc.nextDouble();
        double volume = (4 * Math.PI * Math.pow(r, 3))/3;
        double area = 4 * Math.PI * Math.pow(r, 2);
        //모범출력과 같으려면 Math.PI -> 3.141592
        System.out.println("부피 = " + volume);
        System.out.println("표면적 = " + area);
    }
}
