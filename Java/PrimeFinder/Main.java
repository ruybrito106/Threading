package PrimeFinder;
import PrimeFinder.MyRunnable;
import PrimeFinder.Counter;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N, X;
		N = in.nextInt();
		X = in.nextInt();
		
		Thread[] t = new Thread[X];
		
		Counter cnt = new Counter(0);
		
		for(int i = 0; i < X; i++) {
			t[i] = new Thread(new MyRunnable(cnt, N), "T"+i);
		}
		
		for(int i = 0; i < X; i++) {
			t[i].start();
		}
		
		for(int i = 0; i < X; i++) {
			try {
				t[i].join();
			} catch (InterruptedException ie) {
				
			}
		}		
	}
}

