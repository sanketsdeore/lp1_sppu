import java.util.Scanner;

class Process {
	int pNo = 0, at, bt, rembt = 0, rt = 0, wt = 0, ct = 0, tat = 0, priority = 0;
	
	Process(int pNo, int at, int bt) {
		this.pNo = pNo;
		this.at = at;
		this.bt = bt;
		this.rembt = bt;
	}
	
	Process(int pNo, int at, int bt, int priority) {
		this.pNo = pNo;
		this.at = at;
		this.bt = bt;
		this.priority = priority;
	}
	
	void display() {
		System.out.println("P" + (pNo + 1) + "\t" + at + "\t\t" + bt);
	}
}

public class Schedulers {
	public static void main(String[] args) {
		Schedulers scheduler = new Schedulers();
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\nEnter no. of processes: ");
		int totalProcesses = sc.nextInt();
		
		Process process[] = new Process[totalProcesses];
		
		System.out.println("\nEnter arrival and burst time for each process: ");
		for(int i = 0; i < totalProcesses; i++) {
			System.out.println("For process P" + (i + 1) + ": ");
			int at = sc.nextInt();
			int bt = sc.nextInt();
			process[i] = new Process(i, at, bt);
		}
		
		System.out.println("Process\tArrival Time\tBurst Time");
		for(int i = 0; i < totalProcesses; i++) {
			process[i].display();
		}
		
		System.out.println("\nMenu:");
		System.out.println("1. FCFS\n2. SJF\n3. Round Robin\n4. Priority");
		System.out.print("\nEnter your choice: ");
		int ch = sc.nextInt();
		
		switch(ch) {
		case 1:
			System.out.println("FCFS:");
			scheduler.fcfs(process);
			break;
		case 2:
			System.out.println("SJF:");
			scheduler.sjf(process);
			break;
		}
	}
	
	void fcfs(Process[] process) {
        for (int i = 0; i < process.length - 1; i++) {
            for (int j = 0; j < process.length - i - 1; j++) {
                if (process[j].at > process[j + 1].at) {
                    Process temp = process[j];
                    process[j] = process[j + 1];
                    process[j + 1] = temp;
                }
            }
        }

        int currTime = 0;

        for (int i = 0; i < process.length; i++) {
        	if(currTime < process[i].at) {
        		currTime = process[i].at;
        	}
            process[i].ct = currTime + process[i].bt;
            process[i].tat = process[i].ct - process[i].at;
            process[i].wt = process[i].tat - process[i].bt;
            process[i].rt = process[i].wt;
            currTime = process[i].ct;
        }
		displayProcessDetails(process);
	}
	
	void sjf(Process process[]) {
		int complete = 0, currTime = 0;
		
		for(int i = 0; i < process.length; i++) {
			for(int j = i + 1; j < process.length; j++) {
				if(process[i].at > process[j].at || (process[i].at == process[j].at && process[i].bt > process[j].bt)) {
					Process temp = process[i];
					process[i] = process[j];
					process[j] = temp;
				}
			}
		}
		while(complete < process.length) {
			int minIdx = -1;
			int minBurst = Integer.MAX_VALUE;
			
			for(int i = 0; i < process.length; i++) {
				if(process[i].at <= currTime && process[i].bt < minBurst && process[i].ct == 0) {
					minBurst = process[i].bt;
					minIdx = i;
				}
			}
			if(minIdx == -1) {
				currTime++;
				continue;
			}
			
			Process p = process[minIdx];
			p.ct = currTime + p.bt;
			p.tat = p.ct - p.at;
			p.wt = p.tat - p.bt;
			p.rt = p.wt;
			currTime += p.bt;
			complete++;
		}
		displayProcessDetails(process);
	}
	
	void displayProcessDetails(Process process[]) {
		System.out.println("Process\tArrival Time\tBurst Time\tCompletion Time\tWaiting Time\tTurnaround Time\tResponse Time");
		for(int i = 0; i < process.length; i++) {
			Process p = process[i];
			System.out.println("P" + (i + 1) + "\t" + p.at + "\t\t" + p.bt + "\t\t" + p.ct + "\t\t" + p.wt + "\t\t" + p.tat + "\t\t" + p.rt);
		}
	}
}
