package cipher;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Created by huanganna on 16/5/4.
 */
public class FileEncryptAndDecrypt {
    private Key key;

    public FileEncryptAndDecrypt(String str) {
        this.key = generateKey(str);//生成密匙
    }

    public FileEncryptAndDecrypt(){

    }


    public Key generateKey(String strKey) {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            generator.init(new SecureRandom(strKey.getBytes()));
            return generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean encrypt(String file, String destFile,Key key) {
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            InputStream is = new FileInputStream(file);
            OutputStream out = new FileOutputStream(destFile);
            CipherInputStream cis = new CipherInputStream(is, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = cis.read(buffer)) > 0) {
                out.write(buffer, 0, r);
            }
            cis.close();
            is.close();
            out.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean encrypt(String file,String destFile) throws NullPointerException{

        if(key!=null){
            return encrypt(file,destFile,this.key);
        }else{
            throw new NullPointerException("请输入加密密匙");
        }


    }

    public boolean decrypt(String file, String dest,Key key)  {

        try{
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            InputStream is = new FileInputStream(file);
            OutputStream out = new FileOutputStream(dest);
            CipherOutputStream cos = new CipherOutputStream(out, cipher);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = is.read(buffer)) >= 0) {
                System.out.println();
                cos.write(buffer, 0, r);
            }
            cos.close();
            out.close();
            is.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    public boolean decrypt(String file, String dest)  throws NullPointerException{

        if(key!=null){
            return decrypt(file,dest,this.key);
        }else{
            throw new NullPointerException("请输入解密密匙");
        }

    }

}
