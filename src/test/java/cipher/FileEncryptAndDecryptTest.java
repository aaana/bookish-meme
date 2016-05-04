package cipher;

import org.junit.Test;

import java.security.Key;

import static org.junit.Assert.*;

/**
 * Created by huanganna on 16/5/4.
 */
public class FileEncryptAndDecryptTest {

    @Test
    public void test1(){
        FileEncryptAndDecrypt fileEncryptAndDecrypt1 = new FileEncryptAndDecrypt();
//        fileEncryptAndDecrypt1.encrypt("./messageRecords/100.log","./messageRecords/100_加密.log");
        Key key = fileEncryptAndDecrypt1.generateKey("abc");
        fileEncryptAndDecrypt1.encrypt("./messageRecords/100.log","./messageRecords/100_加密.log",key);
        fileEncryptAndDecrypt1.decrypt("./messageRecords/100_加密.log","./messageRecords/100_解密.log",key);
    }


    @Test
    public void test2(){
        FileEncryptAndDecrypt fileEncryptAndDecrypt2 = new FileEncryptAndDecrypt("abc");
        fileEncryptAndDecrypt2.encrypt("./messageRecords/100.log","./messageRecords/100_加密.log");
        fileEncryptAndDecrypt2.decrypt("./messageRecords/100_加密.log","./messageRecords/100_解密.log");
    }

}