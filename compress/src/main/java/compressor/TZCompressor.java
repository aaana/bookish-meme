package compressor;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.lingala.zip4j.util.Zip4jUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by tanjingru on 5/5/16.
 */
public class TZCompressor {

    private HashMap<String, CompressTask> tasks;

    public void startAllTask(){
        Set<Map.Entry<String, CompressTask>> tasksSet = tasks.entrySet();
        for(Map.Entry<String, CompressTask> task : tasksSet){
            CompressTask ct = task.getValue();
            if(ct.getStatus() == 0){
                ct.start();
            }
        }
    }

    public TZCompressor addTask(CompressTask compressTask, String taskName){
        tasks.put(taskName, compressTask);
        return this;
    }

    public void startTask(String taskName){
        ((CompressTask)tasks.get(taskName)).start();
    }

    public void stopTask(String taskName){
        ((CompressTask)tasks.get(taskName)).stop();
    }

    public TZCompressor(){
        tasks = new HashMap<String, CompressTask>();
    }

    public static void main(String[] args) throws IOException, ZipException {
        ZipFile zipFile = new ZipFile("test1.zip");
        ArrayList<File> files = new ArrayList<File>();
        files.add(new File("conf.json"));
        ZipParameters parameters = new ZipParameters();
        zipFile.addFiles(files, parameters);
    }
}
