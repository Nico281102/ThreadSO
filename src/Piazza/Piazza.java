package Piazza;

/*
 * Compilare con: javac Piazza.java
 */

import java.util.Random;

import javax.lang.model.util.ElementScanner14;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Piazza {
    final static int  N_THREAD=40;
    final static int SLEEPBASE=100;
    final static int N_ITER=20;
    final static Person[] person = new Person[N_THREAD];
    final static String contrade[] =  new String[] {"Civetta","Aquila","Bruco","Chiocciola"};
    private static int flag = 0;
    static Random rand = new Random(93);
    static private HashMap<String,Integer> n_persone = new HashMap<String,Integer>();
    static private HashMap<String,Integer> max_persone = new HashMap<String,Integer>();
    static private HashMap<String,List<String>> conflicts = new HashMap<String,List<String>>();
    static private HashMap<String,List<String>> permitted = new HashMap<String,List<String>>();
    static private List<String> checked = new ArrayList<String>();

    public static void main(String args[]) throws InterruptedException {
        int i,j,k;

        Monitor m = new Monitor();

        for (String kk : contrade) {
            permitted.put(kk,new ArrayList<String>());
        }
        conflicts.put("Civetta",Arrays.asList("Aquila", "Bruco", "Chiocciola"));
        conflicts.put("Aquila",Arrays.asList("Civetta", "Chiocciola"));
        conflicts.put("Bruco",Arrays.asList("Civetta"));
        conflicts.put("Chiocciola",Arrays.asList("Civetta", "Aquila"));

        for (String kk : contrade) {
            for (String p : contrade) {
                if (p != kk && !conflicts.get(kk).contains(p)) {
                    permitted.get(kk).add(p);
                }
            }
        }
        // System.out.println(permitted);

        for(j=0;j<N_ITER;j++) {
            for (String kk : contrade) {
                max_persone.put(kk,0);
                n_persone.put(kk,0);
            }
            checked = new ArrayList<String>();

            for (i=0;i<N_THREAD;i++) {
                k=rand.nextInt(4);
                person[i] = new Person(j,m,k);
                max_persone.put(contrade[k],max_persone.get(contrade[k])+1);
                person[i].start();
            }
            // System.out.println(max_persone);

            for (String kk : contrade) {
                if (max_persone.get(kk)==0) {
                    System.out.println(String.format("[ERRORE] Nessuna persona %s!",kk));
                    System.exit(1);
                }
            }

            for (i=0;i<N_THREAD;i++) {
                person[i].join();
            }
        }
        System.out.println(String.format("Terminato, flag=%06x",flag));
    }

    static class Person extends Thread {
        String contrada;
        Monitor m;
        int j;

        Person(int j,Monitor m, int k) {
            this.contrada = contrade[k];
            this.m = m;
            this.j = j;
            flag = (flag + (k*(rand.nextInt(16777216))))%16777216;
        }

        public void run() {
            try {
                go();
            } catch (InterruptedException e) {
                System.out.println(String.format("Persona %s interrotta!",contrada));
            }
        }

        public void go() throws InterruptedException {
            // System.out.println(String.format("Saluti dal thread di tipo %s", this.contrada));
            // m.test1();


            synchronized(m) {
                m.entra(contrada);
                System.out.println(String.format("[%s] entra in piazza", this.contrada));

                n_persone.put(contrada,n_persone.get(contrada)+1);

                for (String k : conflicts.keySet()) {
                    if (n_persone.get(k)>0) {
                        for (String c : conflicts.get(k)) {
                            if (n_persone.get(c)>0) {
                                System.out.println(String.format("[ERRORE] %s assieme a %s",k,c));
                                System.out.println(String.format("Stato: %s",n_persone));
                                System.out.println(String.format("Iterazione: %d",j));

                                System.exit(1);
                            }
                        }
                    }
                }
            }

            sleep(SLEEPBASE);

            synchronized(m) {
                m.esce(contrada);
                if (!permitted.get(contrada).isEmpty()
                        && !checked.contains(contrada)
                        && n_persone.get(contrada) == max_persone.get(contrada))
                {
                    boolean ok=false;
                    for (String k : permitted.get(contrada)) {
                        if (n_persone.get(k)==max_persone.get(k)) {
                            ok=true;
                            checked.add(k);
                        }
                    }
                    if (!ok) {
                        ok=true;
                        for (String k : permitted.get(contrada)) {
                            if (!checked.contains(k)) {
                                ok=false;
                                break;
                            }
                        }
                    }
                    if (!ok) {
                        System.out.println(String.format("[ERRORE] uno di %s non è entrato insieme a %s!",permitted.get(contrada), contrada));
                        System.out.println(String.format("Stato: %s",n_persone));
                        System.out.println(String.format("Iterazione: %d",j));

                        System.exit(1);
                    }
                    checked.add(contrada);
                }
                n_persone.put(contrada,n_persone.get(contrada)-1);
            }

            System.out.println(String.format("[%s] esce dalla piazza", this.contrada));
        }
    }
}

/*COMMENTO GENERALE
 *
 * Autrice: Rebecca Frisoni
 *
 *  Per la sincronizzazione uso:
 *
 * - Un array 'presenti' che tiene traccia di quante persone di ogni contrada
 *   sono presenti in piazza.
 *
 *  Come funziona la sincronizzazione:
 *
 *  -Su entra:
 *  La persona di una data contrada attende(wait) ad entrare in piazza finchè(while) in essa ci fossero
 *  presenti contrade con cui sia in contrasto.
 *  Una volta che la piazza si sia liberata dalle contrade contrastanti, viene aumentato il numero di
 *  persone della contrada che era in attesa.
 *
 *  -Su esce:
 *  Una volta che una persona esce dalla piazza vengono notificate tutti(notifyAll) le persone e diminuito il numero di
 *  persone delle contrade corriapondenti
 *
 */

