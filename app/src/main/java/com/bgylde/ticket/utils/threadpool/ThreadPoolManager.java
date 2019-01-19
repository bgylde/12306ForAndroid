package com.bgylde.ticket.utils.threadpool;


import com.bgylde.ticket.utils.LogUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangyan on 2019/1/19
 */
public class ThreadPoolManager {

    private static final String TAG = "ThreadPoolManager";

    private static ThreadPoolManager instance = null;

    private ThreadPool appInitPool = null;

    private static final int MAX_QUEUE_SIZE = 30;

    private static final int MAX_APP_INIT_CORE_SIZE = 5;
    private static final long MAX_THREAD_ALIVE_TIME = 5 * 60;

    private ThreadPoolManager() {
        RejectedExecutionHandler rejectHandler = new WaitToAddReject();

        BlockingQueue<Runnable> appInitQueue = new ArrayBlockingQueue<Runnable>(MAX_QUEUE_SIZE);
        ExecutorService appInitExecutor = new ThreadPoolExecutor(MAX_APP_INIT_CORE_SIZE, MAX_APP_INIT_CORE_SIZE, MAX_THREAD_ALIVE_TIME, TimeUnit.SECONDS, appInitQueue, new MyThreadFactory("appInit"), rejectHandler);
        ((ThreadPoolExecutor)appInitExecutor).allowCoreThreadTimeOut(true);
        appInitPool = new ThreadPool(appInitExecutor, "APP_INIT");

    }

    public static ThreadPoolManager getInstance() {
        if (instance == null) {
            synchronized (ThreadPoolManager.class) {
                if (instance == null) {
                    instance = new ThreadPoolManager();
                }
            }
        }

        return instance;
    }

    public void addAppInitTask(Runnable runnable) {
        appInitPool.execute(runnable);
    }

    public static class WaitToAddReject implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            LogUtils.w(TAG, "Thred pool is full, runnable[" + r + "] is rejected.");
        }
    }
}
