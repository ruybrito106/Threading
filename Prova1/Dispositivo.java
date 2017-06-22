
public class Dispositivo {

    public Dispositivo() {

    }

    public void iniciar() {
        System.out.println("Iniciando!");
    }

    public void desligar() {
        System.out.println("Desligando!");
    }
}

public class Sensor extends Thread{

    private Dispositivo dispositivo;
    private int valor;
    private boolean alive;

    public Sensor(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
        this.alive = true;
        this.update = false;
    }

    public synchronized int getValor() {
        return this.valor;
    }

    public void kill() {
        this.alive = false;
    }

    private synchronized void atualizarValor() {
        Random rand = new Random();
        int v = rand.nextInt(50);
        int aux = rand.nextInt(100);
        if (aux <= 1) v += 50;
        this.valor = v;
    }

    @Override
    public void run() {
        int prev;
        while (alive) {
            prev = this.valor;
            atualizarValor();
            if(prev != this.getValor()){
                synchronized (dispositivo) {
                    dispositivo.notifyAll();
                }
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                 break;
            }
        }
    }
}

public class Controlador extends Thread{
    private Sensor t;
    private Sensor p;
    private Dispositivo d;
    private boolean alive;

    public Controlador(Sensor t, Sensor p, Dispositivo d){
        this.t = t;
        this.p = p;
        this.d = d;
        this.alive = true;
    }

    public void kill(){
        this.alive = false;
        t.kill();
        p.kill();
    }

    @Override
    public void run(){
        d.iniciar();
        p.start();
        t.start();
        while (alive) {
            try {
                synchronized(d){
                    d.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t.getValor()+" : "+p.getValor());
            if(p.getValor() > 70 || t.getValor() > 100){
                d.desligar();
                kill();
            }
        }
        d.interrupt();
        p.interrupt();
    }
}
