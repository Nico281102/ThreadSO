package Filosofi;
/*
Questa soluzione mi fa stallare, per garantire l'atomicità devo raccogliere tutte e due o nessuna bacchetta.
Tenendo presente che l'implementazione potrebbe causare starvation.
 */
public class Monitor {
    Boolean bacchetta[];
    final private int N_THREAD;
    Monitor(int N_THREAD){
        bacchetta = new Boolean[N_THREAD];
        this.N_THREAD = N_THREAD;
        for(int i = 0; i< N_THREAD; ++i){
            bacchetta[i] = true;
        }
    }
    public synchronized void raccogli_sx(int index) throws InterruptedException {
        while(!bacchetta[index])
            wait(); // attende se una delle bacchette non e' disponibile
        // raccoglie le bacchette in modo atomico
        bacchetta[index] = false;
    }

    public synchronized void raccogli_dx(int index) throws InterruptedException{
        while(!bacchetta[(index+1)%N_THREAD])
            wait(); // attende se una delle bacchette non e' disponibile
        // raccoglie le bacchette in modo atomico
        bacchetta[(index+1)%N_THREAD] = false;
    }

    public synchronized void deposita_sx(int index){
        // deposita le bacchette
        bacchetta[index] = true;
        // notifica il filosofo a sx e quello a dx
        this.notifyAll();
    }
    public synchronized void deposita_dx(int index){
        // deposita le bacchette
        bacchetta[(index+1)%N_THREAD] = true;
        // notifica il filosofo a sx e quello a dx
        this.notifyAll();
    }
}
