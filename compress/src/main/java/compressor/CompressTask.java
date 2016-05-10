package compressor;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private int delay = 0;
    private int interval = 60 * 60;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    private ZipFile destinationZipFile;
    private ZipParameters parameters;


    public CompressTask(String destination, String source) {
        this.destination = destination;
        this.source = source;
        parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
        parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
        parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
        parameters.setPassword("123");
        timer = new Timer();
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String suffix =  df.format(new Date());
        try {
            destinationZipFile = new ZipFile(destination + suffix + ".zip");
            destinationZipFile.addFolder(source, parameters);
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    public void stop(){
        timer.cancel();
        status = 0;
    }
}
