class MinhaThread implements Runnable {

    String s1;
    Parenteses p1;
    public MinhaThread (Parenteses p2, String s2) {
    	p1 = p2;
    	s1 = s2;
    }
    public void run() {
    	synchronized(p1) {
    	    p1.exibir(s1);
    	}
    }
}
