import java.util.Scanner;

public class MemoryPlacement {
	public static void main(String[] args) {
		int blockSize[] = {100, 550, 300, 250, 600};
		int processSize[] = {312, 227, 412, 126, 525};
		int m = blockSize.length;
		int n = processSize.length;
		int ch;
		
		System.out.println("Menu:\n1. First Fit\n2. Best Fit\n3.Worst Fit\n4. Next Fit");
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter your choice: ");
		ch = sc.nextInt();
		switch(ch) {
		case 1:
			System.out.println("\nFirst Fit:");
			firstFit(blockSize, m, processSize, n);
		case 2:
			System.out.println("\nBest Fit:");
			bestFit(blockSize, m, processSize, n);
		case 3:
			System.out.println("\nWorst Fit:");
			worstFit(blockSize, m, processSize, n);
		case 4:
			System.out.println("\nNext Fit:");
			nextFit(blockSize, m, processSize, n);
		}
	}
	
	static void firstFit(int blockSize[], int blocks, int processSize[], int processes) {
		int allocate[] = new int[processes];
		int occupied[] = new int [blocks];
		
		for(int i = 0; i < allocate.length; i++) {
			allocate[i] = -1;
		}
		
		for(int i = 0; i < occupied.length; i++) {
			occupied[i] = 0;
		}
		
		for(int i = 0; i < processes; i++) {
			for(int j = 0; j < blocks; j++) {
				if(blockSize[j] >= processSize[i] && occupied[j] != 1) {
					allocate[i] = j;
					blockSize[j] -= processSize[i];
					occupied[j] = 1;
					break;
				}
			}
		}
		
		System.out.println("\nProcess No.\tProcess Size\tBlock No.\n");
		for(int i = 0; i < processes; i++) {
			System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");
			if(allocate[i] != -1) {
				System.out.println(allocate[i] + 1);
			}
			else {
				System.out.println("Not Allocated");
			}
		}
	}
	
	static void bestFit(int blockSize[], int blocks, int processSize[], int processes) {
		int allocate[] = new int[processes];
		int occupied[] = new int[blocks];
		
		for(int i = 0; i < allocate.length; i++) {
			allocate[i] = -1;
		}
		
		for(int i = 0; i < processes; i++) {
			int bestIdx = -1;
			for(int j = 0; j < blocks; j++) {
				if(processSize[i] <= blockSize[j] && occupied[j] == 0){
					if(bestIdx == -1) {
						bestIdx = j;
					}
					else if(blockSize[bestIdx] > blockSize[j]) {
						bestIdx = j;
					}
				}
			}
	
			if(bestIdx != -1) {
				allocate[i] = bestIdx;
				blockSize[i] -= processSize[i];
			}
		}
		
		System.out.println("\nProcess No.\tProcess Size\tBlock No.");
		for(int i = 0; i < processes; i ++) {
			System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");
			if(allocate[i] != -1) {
				System.out.println(allocate[i] + 1);
			}
			else {
				System.out.println("Not Allocated");
			}
		}
	}
	
	static void worstFit(int blockSize[], int blocks, int processSize[], int processes) {
		int allocate[] = new int[processes];
		int occupied[] = new int[blocks];
		
		for(int i = 0; i < allocate.length; i++) {
			allocate[i] = -1;
		}
		
		for(int i = 0; i < occupied.length; i++) {
			occupied[i] = 0;
		}
		
		for(int i = 0; i < processes; i++) {
			int worstIdx = -1;
			for(int j = 0; j < blocks; j++) {
				if(blockSize[j] >= processSize[i] && occupied[j] == 0) {
					if(worstIdx == -1) {
						worstIdx = j;
					}
					else if(blockSize[worstIdx] < blockSize[j]) {
						worstIdx = j;
					}
				}
			}
			if(worstIdx != -1) {
				allocate[i] = worstIdx;
				blockSize[worstIdx] -= processSize[i];
			}
		}
		
		System.out.println("\nProcess No.\tProcess Size\tBlock No.\n");
		for(int i = 0; i < processes; i++) {
			System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");
			if(allocate[i] != -1) {
				System.out.println(allocate[i] + 1);
			}
			else {
				System.out.println("Not Allocated");
			}
		}
	}
	
	static void nextFit(int blockSize[], int blocks, int processSize[], int processes) {
		int allocate[] = new int[processes];
		
		for(int i = 0; i < allocate.length; i++) {
			allocate[i] = -1;
		}
		int j = 0;
		
		for(int i = 0; i < processes; i++) {
			int startIdx = j;
			while(true) {
				if(blockSize[j] >= processSize[i]) {
					allocate[i] = j;
					blockSize[j] -= processSize[i];
					j = (j + 1) % blocks;
					break;
				}
				j = (j + 1) % blocks;
				if(j == startIdx) {
					break;
				}
			}
		}
		
		System.out.println("\nProcess No.\tProcess Size\tBlock No.\n");
		for(int i = 0; i < processes; i++) {
			System.out.print(i + 1 + "\t\t" + processSize[i] + "\t\t");
			if(allocate[i] != -1) {
				System.out.println(allocate[i]);
			}
			else {
				System.out.println("Not Allocated");
			}
		}
	}
}
