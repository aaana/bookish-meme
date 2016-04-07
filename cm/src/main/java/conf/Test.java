package conf;



/**
 * Created by huanganna on 16/4/7.
 */
public class Test {
    public static void main(String[] args) throws Exception{
        Conf conf = new Conf();
        conf.readFile("conf.json");
        System.out.println(conf.getConf("server").toObj(Configuration.class).getPORT());
    }
}
