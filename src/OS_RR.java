import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class OS_RR {

	public static ArrayList<Process> main_RR(StringBuilder log1, StringBuilder log2) {

		ArrayList<Process> temp = new ArrayList<Process>();

		Scanner sc = new Scanner(System.in);
		int procNum = sc.nextInt();
		int timeslice = sc.nextInt();

		for (int i = 0; i < procNum; i++) {
			System.out.println("���μ��� �̸�, �����ð�, cpuburst");
			sc = new Scanner(System.in);
			int processID = sc.nextInt();
			int arriveTime = sc.nextInt();
			int burstTime = sc.nextInt();

			temp.add(new Process(processID, arriveTime, burstTime));
		}

		temp.sort(Comparator.comparing(Process::getAT));

		int cpuTime = 0; // ����ð�
		int pre_cpuTime = 0;
		int cpuDone = 0;
		Process CPU = null;
		double AverageWT = 0;

		ArrayList<Process> ReadyQueue = new ArrayList<Process>();
		ArrayList<Process> Result = new ArrayList<Process>();

		while (cpuDone != procNum) {
			if (!temp.isEmpty()) { // ���μ��� ����
				for (Process p : temp) {
					if (p.arriveTime == cpuTime) {
						ReadyQueue.add(p);
						log1.append(cpuTime + "�� " + p.processID + " ���μ��� readyQueue �Ҵ�\n");
						System.out.println(cpuTime + "�� " + p.processID + " ���μ��� readyQueue �Ҵ�");
					}
				}
			}

			if (CPU == null && !ReadyQueue.isEmpty()) {// cpu�� ����ְ� readyqueue�� ���μ����� ������ �Ҵ�
				CPU = ReadyQueue.get(0);
				ReadyQueue.remove(0);
				
				CPU.cpuAT = cpuTime;
				
				if (CPU.max_bt == CPU.burstTime) {
					CPU.responseTime = cpuTime - CPU.arriveTime;
				}
				log1.append(cpuTime + "�� " + CPU.processID + "���μ��� cpu �Ҵ�\n");
				System.out.println(cpuTime + "�� " + CPU.processID + "���μ��� cpu �Ҵ�");
			}

			pre_cpuTime = cpuTime;
			cpuTime += timeslice;

			if (CPU != null) {
				CPU.burstTime -= timeslice;

				if (CPU.burstTime < 0) { // ���μ��� ��������
					cpuTime += CPU.burstTime;

					if (!ReadyQueue.isEmpty()) { // readyqueue WT ����
						for (Process p : ReadyQueue) {
							p.waitingTime += (cpuTime - pre_cpuTime);
						}
					}

					CPU.turnarroundTime = cpuTime - CPU.arriveTime;
					AverageWT += CPU.waitingTime;

					log2.append(CPU.processID + "���μ��� WT : " + CPU.waitingTime + "\n");
					log2.append(CPU.processID + "���μ��� TT : " + CPU.turnarroundTime + "\n");
					log2.append(CPU.processID + "���μ��� RT : " + CPU.responseTime + "\n");
					log2.append("\n");
					
					System.out.println(CPU.processID + "���μ��� WT : " + CPU.waitingTime);
					System.out.println(CPU.processID + "���μ��� TT : " + CPU.turnarroundTime);
					System.out.println(CPU.processID + "���μ��� RT : " + CPU.responseTime);
					
					log1.append(cpuTime + "�� " + CPU.processID + "���μ��� ����\n");
					System.out.println(cpuTime + "�� " + CPU.processID + "���μ��� ����");
					
					
					CPU.cpuACT = cpuTime - CPU.cpuAT;
					CPU.cpuDACT = cpuTime;
					Process p = (Process) CPU.clone();
					Result.add(p);
					CPU = null;
					cpuDone++;
				} else { // ���μ��� ������

					if (!ReadyQueue.isEmpty()) { // readyqueue WT ����
						for (Process p : ReadyQueue) {
							p.waitingTime += (cpuTime - pre_cpuTime);
						}
					}
					
					CPU.cpuACT = cpuTime - CPU.cpuAT;
					CPU.cpuDACT = cpuTime;
					Process p = (Process) CPU.clone();
					Result.add(p);
					
					
					ReadyQueue.add(CPU);
					CPU = ReadyQueue.get(0);
					ReadyQueue.remove(0);
					
					if (CPU.max_bt == CPU.burstTime) {
						CPU.responseTime = cpuTime - CPU.arriveTime;
					}
					
					CPU.cpuAT = cpuTime;
					
					log1.append(cpuTime + "�� " + CPU.processID + "���μ��� cpu �Ҵ�\n");
					System.out.println(cpuTime + "�� " + CPU.processID + "���μ��� cpu �Ҵ�");
				}
			}

		}
		log2.append("-------------------------------------------------\n");
		log2.append("��ü�ð� : " + cpuTime + "\n");
		log2.append("��� WT : " + AverageWT / (double) procNum);
		
		System.out.println("��ü�ð� : " + cpuTime);
		System.out.println("��� WT : " + AverageWT / (double) procNum);
		return Result;

	}

}