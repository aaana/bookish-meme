package conf;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


/**
 * Created by Administrator on 2016/4/8.
 */
public class ConfigTest {
    Config config = new Config();

    //基本读功能测试（读int,string,float）以及链式调用测试
    @Test
    public void testGetInt() throws Exception {
        config.readFile("conf.json");

        assertEquals("string2", config.getConf("obj1").getConf("obj2").getString("str2"));
        assertEquals("string1", config.getString("str1"));

        assertEquals(234, config.getConf("obj1").getInt("i2"));
        assertEquals(23,config.getInt("i1"));

        assertEquals(2.5,config.getFloat("f1"),0);
    }

    //读object以及直接转换成相应的类
    @Test
    public void testToObj() throws Exception {
        config.readFile("conf.json");

        Configuration configuration = (Configuration)config.getConf("server").toObj(Configuration.class);
        assertEquals(8080,configuration.getPort());
        assertEquals("localhost",configuration.getHost());
        assertEquals(100,configuration.getMaxMsgNumber());
        assertEquals(5,configuration.getMaxMsgNumberPerSec());
    }

}