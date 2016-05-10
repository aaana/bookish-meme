package compressor;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class TZCompressorTest {

    @Test
    public void t(){
        TZCompressor tzCompressor = new TZCompressor();
        tzCompressor.addTask(new CompressTask("archive/qq","logs"),"qq")
                    .addTask(new CompressTask("archive/oo", "logs"),"oo");
        tzCompressor.startAllTask();
    }

}