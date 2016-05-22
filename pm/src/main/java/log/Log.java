package log;

import conf.Config;
import exception.FileNotExistException;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by 马二爷 on 2016/4/5.
 */
public class Log {
    private Map<String, Object> sParam = new HashMap<String, Object>();
    public static ArrayList<String> recordFileName = new ArrayList<String>();
    private static ArrayList<String> zipFileNames = new ArrayList<String>();
    private long delay = 60000;
    private long interval = 60000;
    private Timer timer;
    private String pmdir = "log" + File.separator;
    private static Timer compressTimer = new Timer();
    private static Timer reCompressTimer=new Timer();
    public static String compressPath = "out.zip";
    private static String reCompressPath="total.zip";
    private static ArrayList<String> historyFilenames= new ArrayList<String>();

    public void setParam(String key, Object x) {
        sParam.put(key, x);
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setPMDir(String dir) {
        File file = new File(dir);
        if (dir.endsWith(File.separator))
            pmdir = dir;
        else {
            System.out.println("这不是一个目录！日志将保存在默认目录下。");
        }

    }

    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        //创建目标文件
        try {
            if (file.createNewFile()) {
                System.out.println("创建单个文件" + destFileName + "成功！");

                recordFileName.add(destFileName);

                return true;
            } else {
                System.out.println("创建单个文件" + destFileName + "失败！");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());
            return false;
        }
    }

    public static boolean writeFile(String filename, String content) throws Exception {
        File file = new File(filename);

        if (file.exists()) {
            FileWriter pw = new FileWriter(file, true);

            pw.write(content + "\n");

            pw.close();
            return true;
        } else {
            if (createFile(filename)) {


                FileWriter pw = new FileWriter(file, true);
                pw.write(content + "\n");
                pw.close();

                return true;
            } else {
                System.out.println("创建文件失败");
                return false;
            }

        }

    }
    public static void debug(String content)
    {
        System.out.println(content);
    }
    public static void info(String content) throws Exception {
        writeFile(content,LogLevel.INFO);
    }

    public static void warn(String content) throws Exception {
        writeFile(content,LogLevel.WARN);
    }
    public static void error(String content) throws Exception {
        writeFile(content,LogLevel.ERROR);
    }

    public static void fatal(String content) throws Exception {
        writeFile(content,LogLevel.FATAL);
    }
    public static void writeFile(String content,LogLevel level) throws Exception {

        Config config = new Config();
        config.readFile("config/config.json");
        switch (level) {
            case INFO:
                config=config.getConf("INFO");
                break;
            case WARN:
                config=config.getConf("WARN");
                break;
            case ERROR:
                config=config.getConf("ERROR");
                break;
            case FATAL:
                config=config.getConf("FATAL");
                break;
        }
        String folder= config.getString("outputFolder");
        int singleFileSize=config.getInt("singleFileSize");
        int totalFileSize=config.getInt("totalFileSize");
        File foldername=new File(folder);
        if(folderSize(foldername)/(1024*1024)>totalFileSize)
        {
            System.out.println("您的文件夹大小超过限制，请及时清理！");
            return;
        }


        if (historyFilenames.size()==0) {
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
            String snow = dateFormat.format(now);

            String newFile=folder+snow+".txt";
            if(createFile(newFile)) {

                historyFilenames.add(newFile);
                FileWriter pw = new FileWriter(newFile, true);
                pw.write(content + "\n");
                pw.close();

            }

        }else{
            String fileName=historyFilenames.get(historyFilenames.size()-1);
            File file=new File(fileName);
            int writeBytes=content.getBytes().length;

            if((file.length()+writeBytes)/1024>singleFileSize)
            {
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                String snow = dateFormat.format(now);

                String newFile=folder+snow+".txt";
                if(createFile(newFile)) {

                    historyFilenames.add(newFile);
                    FileWriter pw = new FileWriter(newFile, true);
                    pw.write(content + "\n");
                    pw.close();

                }
            }
            else{
                FileWriter pw = new FileWriter(file, true);
                pw.write(content + "\n");
                pw.close();
            }

        }

    }
    public static long folderSize(File directory){
        long length = 0;
        if(directory.exists())
        {
            File [] files=directory.listFiles();
            if(files!=null) {
                for (File file : files) {
                    if (file.isFile())
                        length += file.length();
                    else if(file.isDirectory())
                        length += folderSize(file);

                }
            }
        }

        return length;

    }
    public void run() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
                String snow = dateFormat.format(now);
                try {
                    Log.writeFile(pmdir + snow + ".txt", sParam.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sParam + snow);
            }
        }, delay, interval);

    }

    public void stop() {
        timer.cancel();
        System.out.println("log stop..");
    }


    public static boolean compress(String destFileName) throws Exception {

        File file = new File(destFileName);
        if (file.exists()) {
            System.out.println("创建文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if (!file.getParentFile().mkdirs()) {
                System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
        ZipEntry zipEntry;
        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = null;

        for (String filename : recordFileName) {
            zipEntry = new ZipEntry(filename);
            zipOutputStream.putNextEntry(zipEntry);

            fileInputStream = new FileInputStream(filename);
            while (fileInputStream.available() > 0) {
                int readNum = fileInputStream.read(buffer);
                zipOutputStream.write(buffer, 0, readNum);
            }
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
        zipOutputStream.close();
        return true;
    }

    public static void setCompressPath(String cPath) {
        compressPath = cPath;
        zipFileNames.add(cPath);
    }

    public static void setReCompressPath(String rPath)
    {
        reCompressPath=rPath;
    }

    public static void startCompressSchedule(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1); //凌晨1点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime();
        if (date.before(new Date())) {
            date = addDay(date, 1);
        }
        compressTimer.schedule(new CompressTask(), date,24 * 60 * 60 * 1000);

    }

    public static void endCompressSchedule(){
        compressTimer.cancel();
    }
    public static void startReCompressSchedule() throws  Exception
    {

            reCompressTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        Log.reCompress(reCompressPath);
                        Log.resetReCompress();
                    }catch (Exception e)
                    {e.printStackTrace();}
                    }
            },new Date(),7*24 * 60 * 60 * 1000);

    }
    public static void stopReCompressSchedule()
    {
        reCompressTimer.cancel();
    }

    private static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    public static boolean reCompress (String outPath) throws Exception
    {
        ZipInputStream zin;
        ZipOutputStream zout;
        ZipEntry inEntry;
        ZipEntry outEntry;

        File outFile=new File(outPath);

        if(outFile.exists())
        {
            System.out.println("文件已经存在，无法创建");
            return false;
        }
        if(outPath.endsWith(File.separator))
        {
            System.out.println("目标文件不能是目录");
            return false;
        }
        if(outFile.getParentFile()!=null&&!outFile.getParentFile().exists())
        {
            if(!outFile.getParentFile().mkdirs())
            {
                System.out.println("创建目录失败");
                return false;
            }
        }

        zout=new ZipOutputStream(new FileOutputStream(outFile));
        byte[] buffer=new byte[1024];
        for(String zfile : zipFileNames)
        {
            zin=new ZipInputStream(new FileInputStream(zfile));
            while((inEntry=zin.getNextEntry())!=null)
            {
                outEntry=new ZipEntry(inEntry.getName());
                zout.putNextEntry(outEntry);
                int len;
                while((len=zin.read(buffer))>0)
                {
                    zout.write(buffer,0,len);
                }
                zin.closeEntry();
                zout.closeEntry();

            }
            zin.close();
        }
        zout.close();
        return true;
    }

    public static void resetCompress()
    {
        recordFileName.clear();
    }
    public static void resetReCompress()
    {
        zipFileNames.clear();
    }
}