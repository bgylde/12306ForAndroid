package com.bgylde.ticket.utils.threadpool;

import java.util.concurrent.ThreadFactory;

/**
 * Created by wangyan on 2019/1/19
 */
public class MyThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private String threadName;

    MyThreadFactory(String threadName) {
        SecurityManager securityManager = System.getSecurityManager();

        group = securityManager != null ?  securityManager.getThreadGroup() :
                Thread.currentThread().getThreadGroup();

        this.threadName = threadName;
    }

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(group, runnable, threadName, 0);
        if (thread.isDaemon()) {
            thread.setDaemon(false);
        }

        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }

        return thread;
    }
}
