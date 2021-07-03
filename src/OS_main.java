import java.util.ArrayList;
import java.util.Scanner;

public class OS_main {
	static StringBuilder log1 = new StringBuilder();
	static StringBuilder log2 = new StringBuilder();
	
	
	public static void main(String[] args) throws CloneNotSupportedException {
		Scanner sc = new Scanner(System.in);
		System.out.println("1.FCFS 2.SJF 3.SRTF 4.RR");
		int type = sc.nextInt();
		
		ArrayList<Process> Result;
		
		
		switch(type) {
		case 1:
			Result = OS_FCFS.main_FCFS(log1, log2);
			OS_UI.main_UI(Result, log1, log2);
			System.out.println(log1);
			System.out.println(log2);
			break;
			
		case 2:
			Result = OS_SJF.main_SJF(log1, log2);
			OS_UI.main_UI(Result, log1, log2);
			break;
			
		case 3:
			Result = OS_SRTF.main_SRTF(log1, log2);
			OS_UI.main_UI(Result, log1, log2);
			break;
			
		case 4:
			Result = OS_RR.main_RR(log1, log2);
			OS_UI.main_UI(Result, log1, log2);
			break;
			
		}
		

	}

}
