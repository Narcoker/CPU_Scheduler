public class Process implements Cloneable, Comparable<Process>  {
	public int processID;
	public int arriveTime;
	public int burstTime;
	public int max_bt;
	public int cpuAT;

	public int waitingTime = 0;
	public int turnarroundTime = 0;
	public int responseTime = 0;
	public int cpuACT = 0;
	public int cpuDACT = 0;

	public Process() {
	}

	public Process(int processID, int arriveTime, int burstTime) {
		this.processID = processID;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
		this.max_bt = burstTime;
	}

	public Integer getAT() {
		return this.arriveTime;
	}

	public Integer getBT() {
		return this.burstTime;
	}

	@Override
	public int compareTo(Process p) {
		return this.burstTime - p.burstTime;
	}
	
	@Override
	public Object clone() {
		Object o = null;
		try {
			o = (Object) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return (Process)o;		

	}

}
