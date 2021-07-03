import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class OS_SRTF {
	public static ArrayList<Process> main_SRTF(StringBuilder log1, StringBuilder log2)  {

		ArrayList<Process> temp = new ArrayList<Process>();

		Scanner sc = new Scanner(System.in);
		int procNum = sc.nextInt();

		for (int i = 0; i < procNum; i++) {
			System.out.println("프로세스 이름, 도착시간, cpuburst");
			sc = new Scanner(System.in);
			int processID = sc.nextInt();
			int arriveTime = sc.nextInt();
			int burstTime = sc.nextInt();

			temp.add(new Process(processID, arriveTime, burstTime));
		}

		temp.sort(Comparator.comparing(Process::getAT));

		int cpuTime = 0; // 현재시각
		int cpuDone = 0;
		Process CPU = null;
		double AverageWT = 0;

		ArrayList<Process> ReadyQueue = new ArrayList<Process>();
		ArrayList<Process> Result = new ArrayList<Process>();

		while (cpuDone != procNum) {
			if (!temp.isEmpty()) { // 프로세스 들어옴
				for (Process p : temp) {
					if (p.arriveTime == cpuTime) {
						ReadyQueue.add(p);
						log1.append(cpuTime + "초 " + p.processID + " 프로세스 readyQueue 할당\n");
						System.out.println(cpuTime + "초 " + p.processID + " 프로세스 readyQueue 할당");
					}
				}

				ReadyQueue.sort(Comparator.comparing(Process::getBT));
			}

			if (CPU == null && !ReadyQueue.isEmpty()) {// cpu가 놀고있고 readyqueue에 프로세스가 있으면 할당
				// Burst time 가장 작은 프로세스 할당
				CPU = ReadyQueue.get(0);
				ReadyQueue.remove(0);
				
				CPU.cpuAT = cpuTime;
				
				
				if (CPU.max_bt == CPU.burstTime) {
					CPU.responseTime = cpuTime - CPU.arriveTime;
				}
				
				log1.append(cpuTime + "초 " + CPU.processID + "프로세스 cpu 할당\n");
				System.out.println(cpuTime + "초 " + CPU.processID + "프로세스 cpu 할당");
			}

			if (CPU != null && !ReadyQueue.isEmpty()) {
				if (CPU.compareTo(ReadyQueue.get(0)) > 0) {
					ReadyQueue.add(CPU); // 기존 프로세스 종료
					
					CPU.cpuACT = cpuTime - CPU.cpuAT;
					CPU.cpuDACT = cpuTime;
					Process p = (Process) CPU.clone();
					Result.add(p);
					
					CPU = ReadyQueue.get(0); // 새 프로세스 할당
					ReadyQueue.remove(0);
				
					CPU.cpuAT = cpuTime;
					
					ReadyQueue.sort(Comparator.comparing(Process::getBT));
					
				
					log1.append(cpuTime + "초 " + CPU.processID + "프로세스 cpu 할당\n");
					System.out.println(cpuTime + "초 " + CPU.processID + "프로세스 cpu 할당");
				}
			}

			cpuTime++;

			if (CPU != null) {
				CPU.burstTime--;

				if (CPU.burstTime == 0) {
					CPU.turnarroundTime = cpuTime - CPU.arriveTime;
					AverageWT += CPU.waitingTime;

					log2.append(CPU.processID + "프로세스 WT : " + CPU.waitingTime + "\n");
					log2.append(CPU.processID + "프로세스 TT : " + CPU.turnarroundTime + "\n");
					log2.append(CPU.processID + "프로세스 RT : " + CPU.responseTime + "\n");
					log2.append("\n");
					System.out.println(CPU.processID + "프로세스 WT : " + CPU.waitingTime);
					System.out.println(CPU.processID + "프로세스 TT : " + CPU.turnarroundTime);
					System.out.println(CPU.processID + "프로세스 RT : " + CPU.responseTime);
					
					log1.append(cpuTime + "초 " + CPU.processID + "프로세스 종료\n");
					System.out.println(cpuTime + "초 " + CPU.processID + "프로세스 종료");
					
					CPU.cpuACT = cpuTime - CPU.cpuAT;
					CPU.cpuDACT = cpuTime;
					Process p = (Process) CPU.clone();
					Result.add(p);
					
					CPU = null;
					cpuDone++;

				}
			}

			if (!ReadyQueue.isEmpty()) { // readyqueue WT 증가
				for (Process p : ReadyQueue) {
					p.waitingTime++;
				}
			}
		}
		
		log2.append("-------------------------------------------------\n");
		log2.append("전체시간 : " + cpuTime + "\n");
		log2.append("평균 WT : " + AverageWT / (double) procNum);
		System.out.println("전체시간 : " + cpuTime);
		System.out.println("평균 WT : " + AverageWT / (double) procNum);
		return Result;

	}
}
