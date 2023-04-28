package MonitorContatore;

public class Monitor {
    private int count;

    public int getCount() {
        return count;
    }
    public synchronized void increaseCount(){
        ++count;
    }
}
