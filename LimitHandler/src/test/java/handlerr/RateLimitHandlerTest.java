package handlerr;

import filter.MessageFilter;
import handler.RateLimitHandler;
import io.netty.channel.embedded.EmbeddedMessageChannel;
import license.license.PerSecondCountLicense;
import license.license.TZLicense;
import license.license.SumCountLicense;
import msg.AnyTypeObject;
import msg.ObjFlag;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by tanjingru on 3/31/16.
 */
public class RateLimitHandlerTest {



    // 测试最大计数的limit handler，不考虑消息类型的问题，只考虑最大问题

    @Test
    public void test_maxCount() throws Exception {

        int maxCount = 5;

        TZLicense rateLimiter = new SumCountLicense(maxCount);

        MessageFilter<AnyTypeObject> messageFilter = new MessageFilter<AnyTypeObject>() {
            @Override
            public Boolean shouldFilter(AnyTypeObject msg) {
                if(msg.getType() == 0) return true;
                else return false;
            }
        };

        RateLimitHandler<AnyTypeObject> rateLimitHandler = new RateLimitHandler<AnyTypeObject>(messageFilter, rateLimiter) {
            @Override
            public void messageAgree(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.ACCEPT);
            }

            @Override
            public void messageDisagree(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.REJECT);
            }

            @Override
            public void messageIgnore(AnyTypeObject msg) {
                ;
            }
        };

        EmbeddedMessageChannel limitHandler = new EmbeddedMessageChannel(rateLimitHandler);


        // 传入十次， 前4次消息被接受，flag变成ACCEPT，后6次消息没被接受，flag变成REJECT

        int acquireTime = 10;
        for(int i = 0 ; i < acquireTime; i++){
            AnyTypeObject anyTypeObject = new AnyTypeObject(ObjFlag.NONE,0);
            limitHandler.writeInbound(anyTypeObject);
            AnyTypeObject readObj = (AnyTypeObject)limitHandler.readInbound();
            if(i < maxCount-1) {
                assertEquals(readObj.getFlag(), ObjFlag.ACCEPT);
            }
            else assertEquals(readObj.getFlag(), ObjFlag.REJECT);
        }

        AnyTypeObject readObj;

        // 重新传入一次，消息应该变为REJECT
        limitHandler.writeInbound(new AnyTypeObject(ObjFlag.NONE,0));
        readObj = (AnyTypeObject)limitHandler.readInbound();
        assertEquals(readObj.getFlag(), ObjFlag.REJECT);

        // 重置一次
        rateLimitHandler.reset();

        limitHandler.writeInbound(new AnyTypeObject(ObjFlag.NONE,0));
        readObj = (AnyTypeObject)limitHandler.readInbound();
        assertEquals(readObj.getFlag(), ObjFlag.ACCEPT);

    }

    //测试每秒的

    @Test
    public void testPerCount() throws Exception{

        int perCount = 5;

        TZLicense rateLimiter = new PerSecondCountLicense(perCount);

        MessageFilter<AnyTypeObject> messageFilter = new MessageFilter<AnyTypeObject>() {
            @Override
            public Boolean shouldFilter(AnyTypeObject msg) {
                if(msg.getType() == 0) return true;
                else return false;
            }
        };

        RateLimitHandler<AnyTypeObject> rateLimitHandler = new RateLimitHandler<AnyTypeObject>(messageFilter, rateLimiter) {
            @Override
            public void messageAgree(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.ACCEPT);
            }

            @Override
            public void messageDisagree(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.REJECT);
            }

            @Override
            public void messageIgnore(AnyTypeObject msg) {
                ;
            }
        };

        EmbeddedMessageChannel limitHandler = new EmbeddedMessageChannel(rateLimitHandler);


        // 传入十次， 第一次次消息被接受，flag变成accept，后9次消息没被接受，flag变成reject

        int acquireTime = 10;
        for(int i = 0 ; i < acquireTime; i++){
            AnyTypeObject anyTypeObject = new AnyTypeObject(ObjFlag.NONE,0);
            limitHandler.writeInbound(anyTypeObject);
            AnyTypeObject readObj = (AnyTypeObject)limitHandler.readInbound();
            if(i == 0) assertEquals(readObj.getFlag(), ObjFlag.ACCEPT);
            else assertEquals(readObj.getFlag(), ObjFlag.REJECT);
        }

        //暂停1s后, 消息应该被接受

        Thread.sleep(1000);

        AnyTypeObject readObj;

        // 重新传入一次，消息应该变为accept
        limitHandler.writeInbound(new AnyTypeObject(ObjFlag.NONE,0));
        readObj = (AnyTypeObject)limitHandler.readInbound();
        assertEquals(readObj.getFlag(), ObjFlag.ACCEPT);

    }


    @Test
    public void test_type() throws Exception {

        int maxCount = 5;

        TZLicense rateLimiter = new SumCountLicense(maxCount);

        MessageFilter<AnyTypeObject> messageFilter = new MessageFilter<AnyTypeObject>() {
            @Override
            public Boolean shouldFilter(AnyTypeObject msg) {
                if(msg.getType() == 0) return true;
                else return false;
            }
        };

        RateLimitHandler<AnyTypeObject> rateLimitHandler = new RateLimitHandler<AnyTypeObject>(messageFilter, rateLimiter) {
            @Override
            public void messageAgree(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.ACCEPT);
            }

            @Override
            public void messageDisagree(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.REJECT);
            }

            @Override
            public void messageIgnore(AnyTypeObject msg) {
                msg.setFlag(ObjFlag.IGNORE);
            }
        };

        EmbeddedMessageChannel limitHandler = new EmbeddedMessageChannel(rateLimitHandler);

        //传入4次类型为0的消息，第五次传入类型为1的消息，前四次变为accept，后一次不会被响应，变为ignore

        AnyTypeObject anyTypeObject;

        AnyTypeObject readObj;

        for(int i = 0 ; i < 4; i++){
            anyTypeObject = new AnyTypeObject(ObjFlag.NONE,0);
            limitHandler.writeInbound(anyTypeObject);
            readObj = (AnyTypeObject)limitHandler.readInbound();
            assertEquals(readObj.getFlag(), ObjFlag.ACCEPT);
        }

        anyTypeObject = new AnyTypeObject(ObjFlag.NONE, 1);
        limitHandler.writeInbound(anyTypeObject);
        readObj = (AnyTypeObject)limitHandler.readInbound();
        assertEquals(readObj.getFlag(), ObjFlag.IGNORE);

        // 再次传入消息为0的消息，被reject


        anyTypeObject = new AnyTypeObject(ObjFlag.NONE,0 );
        limitHandler.writeInbound(anyTypeObject);
        readObj = (AnyTypeObject)limitHandler.readInbound();
        assertEquals(readObj.getFlag(), ObjFlag.REJECT);
    }

}
