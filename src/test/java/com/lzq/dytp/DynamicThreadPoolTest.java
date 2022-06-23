/*
 * Copyright 2018 Diffblue Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.lzq.dytp;

import com.lzq.dytp.factory.NamedThreadFactory;
import com.lzq.dytp.queue.ResizeableCapacityLinkedBlockingDeque;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DynamicThreadPoolTest {


    @Test
    public void add() throws Throwable {
        DynamicThreadPool executor = new DynamicThreadPool(2, 5, 60, TimeUnit.SECONDS,
                10, new NamedThreadFactory("LZQ", false));
        int n = 15;
        for (int i = 0; i < n; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        ResizeableCapacityLinkedBlockingDeque queue = (ResizeableCapacityLinkedBlockingDeque) executor.getQueue();
        queue.setCapacity(100);

        threadPoolStatus(executor, "改变之后");
        for (int i = 0; i < 90; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.currentThread().join();
    }

    public static void main(String[] args) throws InterruptedException {
        DynamicThreadPool executor = new DynamicThreadPool(2, 5, 60, TimeUnit.SECONDS,
                10, new NamedThreadFactory("LZQ", false));
        int n = 15;
        for (int i = 0; i < n; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "改变之前");
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        ResizeableCapacityLinkedBlockingDeque queue = (ResizeableCapacityLinkedBlockingDeque) executor.getQueue();
        queue.setCapacity(100);

        threadPoolStatus(executor, "改变之后");
        for (int i = 0; i < 90; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor, "创建任务");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.currentThread().join();
    }

    private static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        ResizeableCapacityLinkedBlockingDeque queue = (ResizeableCapacityLinkedBlockingDeque) executor.getQueue();


        System.out.println("name:" + name + " 核心线程数：" + executor.getCorePoolSize() +
                " 活动线程数：" + executor.getActiveCount() +
                " 最大线程数：" + executor.getMaximumPoolSize() +
                " 线程池活跃度：" + divide(executor.getActiveCount(), executor.getMaximumPoolSize()) +
                " 任务完成数：" + executor.getCompletedTaskCount() +
                " 队列大小：" + (queue.size() + queue.remainingCapacity()) +
                " 当前排队线程数：" + queue.size() + " 队列剩余大小：" + queue.remainingCapacity() +
                " 队列使用度：" + divide(queue.size(), queue.size() + queue.remainingCapacity()));
    }

    private static String divide(int activeCount, int maximumPoolSize) {
        return String.format("%1.2f%%", Double.parseDouble(activeCount + "") / Double.parseDouble(maximumPoolSize + ""));
    }

}
