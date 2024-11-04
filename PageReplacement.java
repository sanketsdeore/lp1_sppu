package SPOSL;
import java.util.Scanner;
import java.util.Queue;
import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Stack;

class FIFO {
		public void execute() {
			Scanner sc = new Scanner(System.in);
			System.out.print("\nEnter no. of pages: ");
			int nump = sc.nextInt();
			int pages[] = new int[nump];
			
			for(int i = 0; i < nump; i++) {
				pages[i] = sc.nextInt();
			}
			
			System.out.print("\nEnter no. of frames: ");
			int capacity = sc.nextInt();
			
			HashSet<Integer> frames = new HashSet<>(capacity);
			Queue<Integer> index = new LinkedList<>();
			
			int pageFaults = 0, hits = 0;
			
			for(int i = 0; i < nump; i++) {
				int page = pages[i];
				if(frames.size() < capacity) {
					if(!frames.contains(page)) {
						frames.add(page);
						index.add(page);
						pageFaults++;
					} else {
						hits++;
					}
				} else {
					if(!frames.contains(page)) {
						int oldestPage = index.poll();
						frames.remove(oldestPage);
						frames.add(page);
						index.add(page);
						pageFaults++;
					} else {
						hits++;
				}
			}
			for(int j : index) {
				System.out.print(j + "\t");
			}
			System.out.println();
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Hits: " + hits);
		System.out.println("Hit Ratio: " + ((double)hits / (double)nump));
	}
}

class LIFO {
	public void execute() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter no. of pages: ");
		int nump = sc.nextInt();
		
		int pages[] = new int[nump];
		
		for(int i = 0; i < nump; i++) {
			pages[i] = sc.nextInt();
		}
		
		System.out.print("\nEnter no. of frames: ");
		int capacity = sc.nextInt();
		
		HashSet<Integer> frames = new HashSet<>(capacity);
		Stack<Integer> index = new Stack<>();
		
		int pageFaults = 0, hits = 0;
		
		for(int i = 0; i < nump; i++) {
			int page = pages[i];
			
			if(frames.size() < capacity) {
				if(!frames.contains(page)) {
					frames.add(page);
					index.push(page);
					pageFaults++;
				} else {
					hits++;
				}
			} else {
				if(!frames.contains(page)) {
					int lastPage = index.pop();
					frames.remove(lastPage);
					
					frames.add(page);
					index.push(page);
					pageFaults++;
				} else {
					hits++;
				}
			}
			for(int j : index) {
				System.out.print(j + "\t");
			}
			System.out.println();
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Hits: " + hits);
		System.out.println("Hit Ratio: " + ((double)hits / (double)nump));
	}
}

class LRU {
	public void execute() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter no. of pages: ");
		int nump = sc.nextInt();
		int pages[] = new int[nump];
		for(int i = 0; i < nump; i++) {
			pages[i] = sc.nextInt();
		}
		System.out.print("\nEnter no. of frames: ");
		int capacity = sc.nextInt();
		
		HashSet<Integer> frames = new HashSet<>(capacity);
		HashMap<Integer, Integer> index = new HashMap<>();
		
		int pageFaults = 0, hits = 0;
		
		for(int i = 0; i < nump; i++) {
			int page = pages[i];
			if(frames.size() < capacity) {
				if(!frames.contains(page)) {
					frames.add(page);
					index.put(page, i);
					pageFaults++;
				} else {
					hits++;
					index.put(page, i);
				}
			} else {
				if(!frames.contains(page)) {
					int oldestPage = -1;
					int oldestTime = Integer.MAX_VALUE;
					Iterator<Integer> iterator = frames.iterator();
					while(iterator.hasNext()) {
						int p = iterator.next();
						int accessTime = index.get(p);
						if(accessTime < oldestTime) {
							oldestTime = accessTime;
							oldestPage = p;
						}
					}
					
					frames.remove(oldestPage);
					frames.add(page);
					pageFaults++;
					index.put(page, i);
				} else {
					hits++;
					index.put(page, i);
				}
			}
			frames.forEach(j -> System.out.print(j + "\t"));
			System.out.println();
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Hits: " + hits);
		System.out.println("Hit Ratio: " + ((double)hits / (double)nump));
	}
}

class Optimal {
	public void execute() {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter no. of pages: ");
		int nump = sc.nextInt();
		int pages[] = new int[nump];
		for(int i = 0; i < nump; i++) {
			pages[i] = sc.nextInt();
		}
		System.out.print("\nEnter no. of frames: ");
		int capacity = sc.nextInt();
		HashSet<Integer> frames = new HashSet<>(capacity);
		HashMap<Integer, Integer> index = new HashMap<>();
		
		int pageFaults = 0, hits = 0;
		for(int i = 0; i < nump; i++) {
			int page = pages[i];
			if(frames.size() < capacity) {
				if(!frames.contains(page)) {
					frames.add(page);
					int farthest = nextIndex(pages, i);
					index.put(page, farthest);
					pageFaults++;
				} else {
					index.put(page, nextIndex(pages, i));
					hits++;
				}
			} else {
				if(!frames.contains(page)) {
					int farthest = -1;
					int val = 0;
					Iterator<Integer> iterator = frames.iterator();
					while(iterator.hasNext()) {
						int p = iterator.next();
						if(index.get(p) > farthest) {
							farthest = index.get(p);
							val = p;
						}
					}
					frames.remove(val);
					frames.add(page);
					index.put(page, nextIndex(pages, i));
				}
			}
			frames.forEach(j -> System.out.print(j + "\t"));
			System.out.println();
		}
		System.out.println("Page Faults: " + pageFaults);
		System.out.println("Hits: " + hits);
		System.out.println("Hit Ratio: " + ((double)hits / (double)nump));
	}
	public static int nextIndex(int pages[], int curIdx) {
		int farthest = curIdx;
		int curPage = pages[curIdx];
		for(int i = farthest + 1; i < pages.length; i++) {
			if(pages[i] == curPage) {
				farthest = i;
				return farthest;
			}
		}
		return Integer.MAX_VALUE;
	}
}

public class PageReplacement {
	public static void main(String[] args) {
		int ch = 0;
		while (ch != 5) {
			System.out.println("Menu:");
			System.out.println("1. FIFO\n2. LIFO\n3. LRU\n4. Optimal\n5. Exit");
			System.out.print("Enter your choice: ");
			Scanner sc = new Scanner(System.in);
			ch = sc.nextInt();
			
			switch(ch) {
			case 1:
				FIFO pr1 = new FIFO();
				pr1.execute();
				break;
			case 2:
				LIFO pr2 = new LIFO();
				pr2.execute();
				break;
			case 3:
				LRU pr3 = new LRU();
				pr3.execute();
				break;
			case 4:
				Optimal pr4 = new Optimal();
				pr4.execute();
				break;
			}
		}
	}
}
