package conf;



/**
 * Created by huanganna on 16/4/7.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        Config config = new Config();
        config.readFile("conf.json");
        System.out.println(config.getConf("server").toObj(Configuration.class).getPort());
    }
}
