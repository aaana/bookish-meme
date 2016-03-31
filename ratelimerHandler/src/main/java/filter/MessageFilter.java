package filter;

/**
 * Created by tanjingru on 3/31/16.
 */

public abstract class MessageFilter<T> {

    public abstract Boolean shouldFilter(T t);

}
