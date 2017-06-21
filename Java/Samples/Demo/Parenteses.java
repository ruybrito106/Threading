class Parenteses {

    public void exibir(String s) {
	System.out.print("(" + s);
	try {
	    Thread.sleep(1000);
	}
	catch(InterruptedException e) {
	    System.out.println("Interrupted");
	}
	System.out.println(")");
    }
}