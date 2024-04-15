// Enjoy mine game
// dr.seungchul@gmail.com

import java.util.*;

public class DetectionGame {
	public static Map map;
	public static int width;
	public static int num_mine;
	
	public static void main(String []args){
		int find=0;
		initial();
		
		while(find<num_mine) 
			find += guess();
					
		System.out.println("Success found "+find+" mines !");
		
	}
	
	private static void initial() {
		System.out.println("Welcome to the Mine Game !");
		Scanner scan = new Scanner (System.in);
		
		
		System.out.print("Enter the width (1~10, width x width) :");
		width = scan.nextInt();
		while ((width < 1) || (width>10)) {
			System.out.println("Invalid width, enter 1~10 !");
			width = scan.nextInt();
		}
		
		System.out.print("Enter number of mines : ");
		num_mine = scan.nextInt();
		while ((num_mine >= width * width) || (num_mine< 1)) {
			System.out.println("Invalid number of mines, must be 0 ~ " + width * width);
			num_mine = scan.nextInt();
		}	
		
		map = new Map(width, num_mine);		
	}
	
	public static int guess() {
		Scanner scan = new Scanner (System.in);
		System.out.print(" Enter x coordinate(0 ~ "+width+") :");
		int x = scan.nextInt();
		while ((x < 0) || (x >= width)) {
			System.out.println(" Invalid x, enter a new x coordinate");
			x = scan.nextInt();
		}
		System.out.print(" Enter y coordinate(0 ~ "+width+") :");
		int y = scan.nextInt();
		while ((y < 0) || (y >= width)) {
			System.out.println(" Invalid y, enter a new y coordinate");
			y = scan.nextInt();
		}
		
		if (map.checkMine(x,y) >= 0) {			
			map.updateMap(x,y);
			return 1;
		}
		else 
			return 0;
	}
	
		
		
		
}
