package log;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogTest {

    private Log log;

    @Before
    public void setUp() {
        log = new Log();

    }

    /*    @Test
        public void testRun() throws Exception
        {
            log.setDelay(1000);
            log.setInterval(10000);
            log.setPMDir("..\\hello\\how\\");
            log.run();
            for(int i=0;i<10;i++)
            {
                log.setParam("hello",i);
                log.setParam("thankyou",i+1);
                Thread.sleep(1000);
            }
            log.stop();
        }*/
   /* @Test
    public void testWriteFile() throws Exception {
        Log.writeFile("record","haha");
        Log.writeFile("record","nihao");
        Log.writeFile("rsd/jilu.log","houhou");
        Log.writeFile("rsd/jilu.log","yes");
        Log.writeFile("rsd/hhd.log","oyea");

        Log.compress("zip/record2");
    }*/
 /*   @Test
    public void testCompress() throws Exception {
        Log.recordFileName.add("rsd/jilu.log");
        Log.recordFileName.add("rsd/hhd.log");
        Log.compress("zip/record.zip");
    }*/
    @Test
    public void testReCompress() throws Exception{
        Log.writeFile("record","haha");
        Log.writeFile("record","nihao");
        Log.writeFile("rsd/jilu.log","houhou");
        Log.writeFile("rsd/jilu.log","yes");
        Log.writeFile("rsd/hhd.log","oyea");
        Log.setCompressPath("zip/record.zip");
        Log.compress("zip/record.zip");
        Log.resetCompress();

        Log.writeFile("hellw","nihao");
        Log.setCompressPath("zip/nihao.zip");
        Log.compress("zip/nihao.zip");
        Log.resetCompress();

        Log.reCompress("total/total.zip");

    }

}