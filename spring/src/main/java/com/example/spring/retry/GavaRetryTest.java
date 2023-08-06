package com.example.spring.retry;

import com.github.rholder.retry.*;
import org.junit.Test;

public class GavaRetryTest {

    public static void main(String[] args) {

    }
    private int invokeCount = 0;

    public int realAction(int num) throws InterruptedException {
        invokeCount++;
        System.out.println(String.format("当前执行第 %d 次,num:%d", invokeCount, num));
        if (num <= 0) {
            throw new IllegalArgumentException();
        }
        if (num == 2) {
            throw new InterruptedException();
        }
        return num;
    }

    @Test
    public void guavaRetryTest001() {
        Retryer<Integer> retryer = RetryerBuilder.<Integer>newBuilder()
                // 非正数进行重试
                .retryIfException()
                // 偶数则进行重试
                .retryIfResult(result -> result % 2 == 0)
                // 设置最大执行次数3次
                .withStopStrategy(StopStrategies.stopAfterAttempt(3))
                .withWaitStrategy(new WaitStrategy() {
                    @Override
                    public long computeSleepTime(Attempt failedAttempt) {
                        Throwable cause = failedAttempt.getExceptionCause();
                        if (cause instanceof InterruptedException) {
                            System.out.printf("500");
                            return 500;
                        }
                        if (cause instanceof IllegalArgumentException) {
                            System.out.println("10000");
                            return 10000;
                        }
                        return 0;
                    }
                })
                .build();

        try {
            invokeCount=0;
            retryer.call(() -> realAction(0));
        } catch (Exception e) {
            System.out.println("执行0，异常：" + e.getMessage());
        }

        try {
            invokeCount=0;
            retryer.call(() -> realAction(1));
        } catch (Exception e) {
            System.out.println("执行1，异常：" + e.getMessage());
        }

        try {
            invokeCount=0;
            retryer.call(() -> realAction(2));
        } catch (Exception e) {
            System.out.println("执行2，异常：" + e.getMessage());
        }
    }
}
