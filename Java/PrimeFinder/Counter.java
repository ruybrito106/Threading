package PrimeFinder;

public class Counter {
	
	private int counter;
	
	public Counter(int value) {
		this.counter = value;
	}
	
	public boolean is_prime(int value) {
		if (value <= 1) return false;
		else if (value <= 3) return true;
		else {
			for(int i = 2; i <= Math.sqrt(value); i++) {
				if (value % i == 0) return false;
			}
			return true;
		}
	}
	
	public int move() {
		boolean ans;
		synchronized(this) {
			this.counter++;
			ans = is_prime(counter); 
		}
		if (ans) return counter;
		else return -1;
	}
}

