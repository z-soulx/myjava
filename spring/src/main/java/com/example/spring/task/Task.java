package task;

import org.springframework.scheduling.annotation.Async;

/**
 * 测试Spring Task.
 *
 * @author skywalker
 */
public class Task {

    @Async
    public void print() throws InterruptedException {
        System.out.println("print执行");
//        Thread.sleep(5000);
    }

}
