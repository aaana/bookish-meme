package compressor;

import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class CompressTaskTest {

    @Test
    public void t(){
        CompressTask compressTask = new CompressTask("archive/hh", "logs/");
        compressTask.start();
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }

}