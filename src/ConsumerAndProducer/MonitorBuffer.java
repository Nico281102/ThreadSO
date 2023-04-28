package ConsumerAndProducer;

import java.util.Queue;

/*
public class MonitorBuffer {
        Queue q;
        boolean scrittore;
        int n_lettori;
        Queue c;
        
        synchronized void ini_leggi(int id) {
                q.add(id); // si accoda
        // attende se non e' il primo della coda o se c'è uno scrittore
        while(q.top()!=id || scrittore)
            c.wait();
        q.remove(); // si toglie dalla coda (e' il primo!)
        n_lettori++; // il lettore entra
        c.notifyAll(); // notifica eventuali altri lettori in attesa
  }

        void end_leggi(int id) {
                n_lettori--; // il lettore esce
        if (n_lettori==0)
            c.notifyAll(); // l'ultimo lettore sblocca tutti i thread
  }

        synchronized void ini_scrivi(int id) {
                q.add(id); // si accoda
        // attende se non e' il primo della coda o se c'è uno scrittore o qualche lettore
        while(q.top()!=id || scrittore || n_lettori > 0)
            c.wait();
        q.remove(); // si toglie dalla coda (e' il primo!)
        scrittore=true; // lo scrittore entra
  }

        synchronized void end_scrivi(int id) {
            scrittore=false; // lo scrittore esce
        c.notifyAll(); // lo scrittore sblocca tutti i thread in attesa
  }
    }
*/