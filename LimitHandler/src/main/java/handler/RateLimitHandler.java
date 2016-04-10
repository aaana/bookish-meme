package handler;

import filter.MessageFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import license.license.TZLicense;

/**
 * Created by tanjingru on 3/31/16.
 */
public abstract class RateLimitHandler<T> extends ChannelInboundMessageHandlerAdapter<T> {

    private TZLicense rateLimiter;

    private MessageFilter<T> filter;

    public RateLimitHandler(MessageFilter<T> filter, TZLicense rateLimiter) {

        this.filter = filter;
        this.rateLimiter = rateLimiter;

    }

    public void reset(){
        rateLimiter.reset();
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, T msg) throws Exception {

        if(filter.shouldFilter(msg)){

            if(rateLimiter.tryAcquire()) messageAgree(msg);
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