package CodaPrioritaria;

public class CodeMonitor {
    // La persona è la prossima ad essere servita e attende che si liberi
// lo sportello. Se la persona è sulla coda prioritaria (priority è
// true) appena lo sportello si libera la persona può procedere. Se
// la persona è sulla coda normale (priority è false) la persona
// attende che non ci siano altre persone in coda prioritaria già
// in attesa e che lo sportello si liberi. In altre parole, dà
// la precedenza a persone in attesa in coda prioritaria.
    int count = 0;
    boolean sportelloOccupato = false;
    synchronized void attendiSportello(boolean priority)
            throws InterruptedException {
        if (priority) {
            // gestione coda prioritaria
            count ++;
            while (sportelloOccupato){
                wait();
            }
            count--;

        } else {
            // gestione coda non prioritaria
            while (sportelloOccupato || count > 0){
                wait();
            }
        }
        sportelloOccupato = true;
    }
    // La persona ha raggiunto lo sportello, ha fruito del servizio e ora
// lo libera
    public synchronized void liberaSportello() {
        sportelloOccupato = false;
        this.notifyAll();
    }
}
