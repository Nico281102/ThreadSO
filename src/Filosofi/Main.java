package Filosofi;

import MonitorContatore.MyThread;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int N_THREAD = 10;

        Thread t[] = new Filosofo[N_THREAD];//fa la new dell' array, devo ancora creare l'oggetto e poi spawnarlo
        Monitor m = new Monitor(N_THREAD);
        for(int i = 0; i < N_THREAD; ++i){
            t[i] = new Filosofo(m,i); //creo l'oggetto
            t[i].start();//spawno il thread
        }

        for(int i = 0; i < N_THREAD; ++i){
            t[i].join();
        }
        System.out.println("Hanno tutti terminato!");

    }
}
