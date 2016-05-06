package compressor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tanjingru on 5/5/16.
 */

public class CompressTask {

    private String source;
    private String destination;
    private int status;
    private Timer timer;
    private int delay;
    private int interval;


    public CompressTask(String destination, String source) {
        this.destination = destination;
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void start(){
        status = 1;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                compress();
            }
        }, delay, interval);
    }

    public void compress(){

    }

    public void stop(){
        timer.cancel();
        status = 0;
    }
}
