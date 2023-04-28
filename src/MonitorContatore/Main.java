package MonitorContatore;

import MonitorContatore.Monitor;
import MonitorContatore.MyThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int N_THREAD = 10;

        MyThread t[] = new MyThread[N_THREAD];//fa la new dell' array, devo ancora creare l'oggetto e poi spawnarlo
        Monitor m = new Monitor();
        for(int i = 0; i < N_THREAD; ++i){
            t[i] = new MyThread(m); //creo l'oggetto
            t[i].start();//spawno il thread
        }

        for(int i = 0; i < N_THREAD; ++i){
            t[i].join();
        }
            System.out.println("Hanno tutti terminato!");
            System.out.println(m.getCount());
    }
}