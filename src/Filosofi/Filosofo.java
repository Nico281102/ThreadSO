package Filosofi;


public class Filosofo extends Thread{
    Monitor m;
    int id;
    Filosofo(Monitor m, int id){
        this.m= m;
        this.id = id;
    }
    @Override
    public void run() {
        int index = id;
        while(true) {
            // PENSA
            System.out.println("Filosofo " + index +" pensa");
            try {
                Thread.sleep(1000);
                m.raccogli_sx(index);   // raccoglie la bacchetta sinistra
                m.raccogli_dx(index);   // raccoglie la bacchetta destra
                //MANGIA
                System.out.println("Filosofo " + index +" mangia");
                Thread.sleep(1000);
                m.deposita_sx(index);   // deposita la bacchetta sinistra
                m.deposita_dx(index);   // deposita la bacchetta destra
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }
    }
}
