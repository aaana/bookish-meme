package msg;

/**
 * Created by tanjingru on 3/31/16.
 */

public class AnyTypeObject {
    private ObjFlag flag;
    private int type;

    public AnyTypeObject(ObjFlag flag, int type) {
        this.flag = flag;
        this.type = type;
    }

    public AnyTypeObject(ObjFlag flag) {
        this.flag = flag;

    }

    public ObjFlag getFlag() {
        return flag;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setFlag(ObjFlag flag) {
        this.flag = flag;

    }
}

