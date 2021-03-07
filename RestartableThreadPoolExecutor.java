package com.xxl.job.core.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RestartableThreadPoolExecutor extends ThreadPoolExecutor {

    public RestartableThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        //when thread stop, restart it
        try {
            if (t != null) {
                Runnable task = r;
                System.out.println("RestartableThreadPoolExecutor: restart task.");
                ThrowableUtil.toString(t);
                execute(task);
            }
        }
        catch (Throwable th)
        {
            System.out.println("RestartableThreadPoolExecutor: restart task in catch.");
            ThrowableUtil.toString(t);
            execute(r);
        }
    }
}
