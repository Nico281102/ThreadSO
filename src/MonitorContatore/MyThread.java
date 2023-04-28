package MonitorContatore;

public class MyThread extends Thread{
    Monitor m;
    MyThread(Monitor m){
        this.m = m;
    }
    public void run(){
        System.out.println("Sono il thread " + this.getName());
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(int i = 0; i < 100; i++){
            m.increaseCount();
        }
    }
}
