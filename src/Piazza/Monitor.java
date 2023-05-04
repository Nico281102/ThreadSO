package Piazza;

class Monitor {

    // Indica quante persone di una contrada sono in presenti in piazza.
    // E' un vettore di interi poichè ci sono più contrade
    int presenti[];

    //Costruttore
    Monitor() {
        //Posizione:
        //0= indica quante persone della contrada civetta sono in piazza
        //1=indica quante persone della contrada aquila sono in piazza
        //2=indica quante persone della contrada bruco sono in piazza
        //3=indica quante persone della contrada chiocciola sono in piazza
        presenti = new int[4];
    }

    synchronized void entra(String contrada) throws InterruptedException {
        //In attesa sulla coda c'è una persona della contrada civetta
        if (contrada == "Civetta") {
            //Attende se la piazza è occupata da almeno una persona delle altre contrade
            while (presenti[1] > 0 || presenti[2] > 0 || presenti[3] > 0)
                wait();
            //Aumenta nella casella dell'array corrispondente il numero di persone della contrada civetta
            presenti[0]++;
        }
        //In attesa sulla coda c'è una persona della contrada aquila
        else if (contrada == "Aquila") {
            //Attende se la piazza è occupata da almeno una persona delle contrade civetta o chiocciola
            while (presenti[0] > 0 || presenti[3] > 0)
                wait();
            //Aumenta nella casella dell'array corrispondente il numero di persone della contrada aquila
            presenti[1]++;
        }
        //In attesa sulla coda c'è una persona della contrada bruco
        else if (contrada == "Bruco") {
            //Attende se la piazza è occupata da almeno una persona della contrada civetta
            while (presenti[0] > 0)
                wait();
            //Aumenta nella casella dell'array corrispondente il numero di persone della contrada bruco
            presenti[2]++;
        }
        //In attesa sulla coda c'è una persona della contrada chiocciola
        else {
            //Attende se la piazza è occupata da almeno una persona delle contrade civetta o aquila
            while (presenti[0] > 0 || presenti[1] > 0)
                wait();
            //Aumenta nella casella dell'array corrispondente il numero di persone della contrada chiocciola
            presenti[3]++;
        }
    }

    synchronized void esce(String contrada) {
        //Notifica eventuali thread in presenti della piazza
        notifyAll();

        //Se in piazza c'erano persone della contrada civetta
        if (contrada == "Civetta") {
            //Diminuisco di 1 il numero di persone della contrada che era in piazza
            presenti[0]--;
        }
        //Se in piazza c'erano persone della contrada aquila
        else if (contrada == "Aquila") {
            //Diminuisco di 1 il numero di persone della contrada che era in piazza
            presenti[1]--;
        }
        //Se in piazza c'erano persone della contrada bruco
        else if (contrada == "Bruco") {
            //Diminuisco di 1 il numero di persone della contrada che era in piazza
            presenti[2]--;
        }
        //Se in piazza c'erano persone della contrada chiocciola
        else {
            //Diminuisco di 1 il numero di persone della contrada che era in piazza
            presenti[3]--;
        }
    }
}
