package base.transaction;

import org.springframework.context.ApplicationEvent;

/**
 * @program: spring
 * @description: 自定义事件
 * @author: soulx
 * @create: 2020-04-03 09:29
 **/
public class SaveUserEvent extends ApplicationEvent {
    private int n ;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SaveUserEvent(Object source,int n) {
        super(source);
        this.n=n;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}