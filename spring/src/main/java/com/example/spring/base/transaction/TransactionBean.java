package base.transaction;

import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 测试事务.
 *
 * @author skywalker
 */
@Component
public class TransactionBean {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public NestedBean getNestedBean() {
        return nestedBean;
    }
    public TransactionBean getTransactionBean() {
        return transactionBean;
    }

    public void setNestedBean(NestedBean nestedBean) {
        this.nestedBean = nestedBean;
    }

    public void setTransactionBean(TransactionBean transactionBean) {
        this.transactionBean = transactionBean;
    }

    private TransactionBean transactionBean;
    private NestedBean nestedBean;
    @Transactional(propagation = Propagation.REQUIRED)
    public void process() {
        System.out.println("事务执行");
        nestedBean.nest();
    }

    @Transactional
    public void eventProcess() {
        System.out.println("事务执行");
        applicationEventPublisher.publishEvent(new SaveUserEvent(this,1));
//        throw new RuntimeException();
    }


     //回滚之后，提交之后等等 执行事件
//    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onSaveUserEvent(SaveUserEvent event) {
        Integer id = event.getN();
        System.out.println(id);
    }


}
