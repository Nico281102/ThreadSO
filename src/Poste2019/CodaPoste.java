package Poste2019;

/*
 * COMMENTO GENERALE:
 *
 * Illustrare la soluzione proposta spiegando accuratamente:
 * - Come si è arrivati a scegliere le strutture dati utilizzate per la sincronizzazione
 * - Come funziona, intuitivamente, la sincronizzazione
 * - Come sono state utilizzate le wait e le notify / notifyAll
 *
 * Commentare, inoltre, accuratamente il codice delle funzioni.
 *
 * Una consegna è insufficiente se:
 * - non funziona (anche se commentata accuratamente)
 * - i commenti sono assenti / scarsi / non accurati
 * - realizza la sincronizzazione in modo non chiaro o inutilmente complesso
 */

import java.sql.Struct;

public class CodaPoste {

    private int X, Y;       // dimensioni dell'ufficio
    private int N_SPORTELLI;  // numero di sportelli

    Boolean pos[][];
    Boolean sportello_libero[];
    public CodaPoste(int x, int y, int n_sportelli) {
        this.X = x;
        this.Y = y;
        this.N_SPORTELLI = n_sportelli;
        this.pos = new Boolean[X][Y];
        for(int i = 0; i < X; i++){
            for(int j = 0; j < Y; j++){
                pos[i][j] = false;
            }
        }
        sportello_libero = new Boolean[n_sportelli];
        for(int i = 0; i < n_sportelli; ++i){
            sportello_libero[i] = true;
        }
    }

    // si muove da x,y a nx,ny. Se nx,ny è occupato attende altrimenti
    // occupa nx,ny e libera x,y.
    public synchronized void move(int x, int y, int nx, int ny) throws InterruptedException {
        while ( pos[nx][ny] ) {
            wait();
        }
        libera(x,y);
        pos[nx][ny] = true;
    }


    // libera la posizione x,y (invocata quando l'utente esce dall'ufficio)
    public synchronized void libera(int x, int y) {
        pos[x][y] = false;
        notifyAll();
    }

    // La persona è la prossima ad essere servita e attende che si liberi uno sportello.
    // Nel caso ci sia almeno uno sportello libero, occupa il primo disponibile e ne
    // ritorna l'indice.
    public synchronized int attendiSportello() throws InterruptedException {
        int id;
        while ( (id = find_sportello() ) == -1){
            wait();
        }
        sportello_libero[id] = false;
        return id; // da modificare opportunamente
    }
    synchronized int find_sportello(){
        for(int i = 0; i < this.N_SPORTELLI; ++i){
            if(sportello_libero[i]){
                return i;
            }
        }
        return -1;
    }

    // La persona ha raggiunto lo sportello, ha fruito del servizio e ora lo libera
    public synchronized void liberaSportello(int id) {
        sportello_libero[id] = true;
        notifyAll();
    }
}
