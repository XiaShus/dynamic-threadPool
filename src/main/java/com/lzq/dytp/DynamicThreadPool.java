package com.lzq.dytp;

import com.lzq.dytp.queue.ResizeableCapacityLinkedBlockingDeque;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : LZQ
 * @create 2022/6/23 11:49
 */
public class DynamicThreadPool extends ThreadPoolExecutor {


    public DynamicThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ResizeableCapacityLinkedBlockingDeque<>(100));
    }

    public DynamicThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int capacity) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ResizeableCapacityLinkedBlockingDeque<>(capacity));
    }

    public DynamicThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, int capacity, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new ResizeableCapacityLinkedBlockingDeque<>(capacity), threadFactory);
    }

    public void setCapacity(int capacity) {
        ResizeableCapacityLinkedBlockingDeque queue = (ResizeableCapacityLinkedBlockingDeque) this.getQueue();
        queue.setCapacity(capacity);
    }
}
