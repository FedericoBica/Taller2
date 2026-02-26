package logica;

public class Monitor {

    private int cantLectores;
    private boolean escribiendo;

    public Monitor() {
        cantLectores = 0;
        escribiendo = false;
    }

    public synchronized void comienzoLectura() {
        try {
            while (escribiendo) {
                wait();
            }
            cantLectores++;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void terminoLectura() {
        cantLectores--;
        if (cantLectores == 0) {
            notifyAll();
        }
    }

    public synchronized void comienzoEscritura() {
        try {
            while (escribiendo || cantLectores > 0) {
                wait();
            }
            escribiendo = true;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void terminoEscritura() {
        escribiendo = false;
        notifyAll();
    }
}