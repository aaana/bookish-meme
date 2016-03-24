package message;

import protocol.ACKType;

/**
 * Created by tanjingru on 3/23/16.
 */
public class ACK {
    ACKType type;
    Object Content;

    public Object getContent() {
        return Content;
    }

    public void setContent(Object content) {
        Content = content;
    }

    public ACKType getType() {
        return type;
    }

    public void setType(ACKType type) {
        this.type = type;
    }

    public ACK() {
    }
}
