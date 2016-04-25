package log;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by 马二爷 on 2016/4/5.
 */
public class Log {
    private Map<String, Object> sParam = new HashMap<String, Object>();
    public static ArrayList<String> recordFileName=new ArrayList<String>();

    private long delay=60000;
    private long interval=60000;
    private Timer timer;
    private String pmdir="log\\";
    private static Timer compressTimer = new Timer();
    private static String compressPath = "";

    public void setParam(String key, Object x) {
        sParam.put(key,x);
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public void setPMDir(String dir){
        File file=new File(dir);
        if (dir.endsWith(File.separator))
        pmdir=dir;
        else
        {
            System.out.println("这不是一个目录！日志将保存在默认目录下。");
        }

    }
    public static boolean createFile(String destFileName) {
        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(file.getParentFile()!=null&&!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if(!file.getParentFile().mkdirs()) {
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
    public static boolean writeFile (String filename,String content)throws Exception
    {
        File file =new File(filename);

        if(file.exists())
        {
            FileWriter pw=new FileWriter(file,true);

            pw.write(content+"\n");

            pw.close();
            return true;
        }
        else
        {
            if(createFile(filename))
            {


                FileWriter pw=new FileWriter(file,true);
                pw.write(content+"\n");
                pw.close();

                return true;
            }
            else
            {
                System.out.println("创建文件失败");
                return false;
            }

        }

    }
    public void run()
    {

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Date now = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
                String snow = dateFormat.format(now);
                try {
                    Log.writeFile(pmdir+snow+".txt",sParam.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(sParam+snow);
            }
        },delay,interval);

    }
    public void stop()
    {
        timer.cancel();
        System.out.println("log stop..");
    }


    public static boolean compress (String destFileName)throws Exception
    {
        File file = new File(destFileName);
        if(file.exists()) {
            System.out.println("创建文件" + destFileName + "失败，目标文件已存在！");
            return false;
        }
        if (destFileName.endsWith(File.separator)) {
            System.out.println("创建文件" + destFileName + "失败，目标文件不能为目录！");
            return false;
        }
        //判断目标文件所在的目录是否存在
        if(file.getParentFile()!=null&&!file.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            if(!file.getParentFile().mkdirs()) {
                   System.out.println("创建目标文件所在目录失败！");
                return false;
            }
        }
        ZipOutputStream zipOutputStream=new ZipOutputStream(new FileOutputStream(file));
        ZipEntry zipEntry;
        byte [] buffer=new byte[1024];
        FileInputStream fileInputStream=null;

        for(String filename:recordFileName)
        {
            zipEntry=new ZipEntry(filename);
            zipOutputStream.putNextEntry(zipEntry);

            fileInputStream=new FileInputStream(filename);
            while(fileInputStream.available()>0)
            {
                int readNum=fileInputStream.read(buffer);
                zipOutputStream.write(buffer,0,readNum);
            }
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
        zipOutputStream.close();
        return true;
    }

    public static void setCompressPath(String cPath){
        compressPath = cPath;
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


    private static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }




    public static void resetCompress()
    {
        recordFileName.clear();
    }
}