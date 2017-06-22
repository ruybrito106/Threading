import java.util.Random;

class Dispositivo {

    public Dispositivo() {

    }

    public void iniciar() {
        System.out.println("Iniciando!");
    }

    public void desligar(){
        System.out.println("Desligando!");
    }
}

class Sensor extends Thread{

    Dispositivo dispositivo;
    private int valor;

    public Sensor(Dispositivo dispositivo, int valor) {
        this.dispositivo = dispositivo;
        this.valor = valor;
    }

    public synchronized int getValor() {
        return this.valor;
    }

    private synchronized void atualizarValor(){
        Random rand = new Random();
        int v = rand.nextInt(50);
        int aux = rand.nextInt(100);
        if (aux <= 1) v += 50;
        this.valor = v;
    }

    public void run() {
        while (true) {
            this.atualizarValor();
            synchronized (this.dispositivo) {
                this.dispositivo.notifyAll();
            }
        }
    }
}

public class Q2 extends Thread {

    private Sensor temperatura;
    private Sensor pressao;
    private Dispositivo dispositivo;

    public Q2(Dispositivo d, Sensor t, Sensor p) {
        this.temperatura = t;
        this.pressao = p;
        this.dispositivo = d;
    }

    public void run() {
        this.dispositivo.iniciar();
        this.pressao.start();
        this.temperatura.start();

        while (true) {
            try {
                synchronized (this.dispositivo) {
                    this.dispositivo.wait();
                }
            } catch (InterruptedException ie) {
                // Print stack trace
            }

            System.out.println("Pressão: " + this.pressao.getValor());
            System.out.println("Temperatura: " + this.temperatura.getValor());

            if (this.pressao.getValor() > 70 || this.temperatura.getValor() > 100) {
                this.dispositivo.desligar();
                break;
            }
        }

        this.pressao.interrupt();
        this.temperatura.interrupt();
        this.interrupt();
    }

    public static void main(String[] args) {
        Dispositivo d = new Dispositivo();
        Q2 controlador = new Q2(d, new Sensor(d, 0), new Sensor(d, 0));
        controlador.start();
    }

}
