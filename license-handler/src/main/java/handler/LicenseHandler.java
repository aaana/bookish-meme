package handler;

import filter.MessageFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import license.TZLicense;

/**
 * Created by tanjingru on 3/31/16.
 */
public abstract class LicenseHandler<T> extends ChannelInboundMessageHandlerAdapter<T> {

    private TZLicense tzLicense;

    private MessageFilter<T> filter;

    public LicenseHandler(MessageFilter<T> filter, TZLicense tzLicense) {

        this.filter = filter;
        this.tzLicense = tzLicense;

    }

    public void reset(){
        tzLicense.reset();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, T msg) throws Exception {

        if(filter.shouldFilter(msg)){

            if(tzLicense.tryAcquire()) messageAgree(msg);
            else messageDisagree(msg);

        }

        else messageIgnore(msg);

        ctx.nextInboundMessageBuffer().add(msg);
        ctx.fireInboundBufferUpdated();

    }

    public abstract void messageAgree(T msg);

    public abstract void messageDisagree(T msg);

    public abstract void messageIgnore(T msg);

}