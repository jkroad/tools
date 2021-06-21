package com.ismayfly.coins.tools.core.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPoolMonitor extends ThreadPoolExecutor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * ActiveCount
     * */
    int ac = 0;

    /**
     * 当前所有线程消耗的时间
     * */
    private AtomicLong totalCostTime = new AtomicLong();

    /**
     * 当前执行的线程总数
     * */
    private AtomicLong totalTasks = new AtomicLong();

    /**
     * 线程池名称
     */
    private String poolName;

    /**
     * 最短 执行时间
     * */
    private long minCostTime;

    /**
     * 最长执行时间
     * */
    private long maxCostTime;


    /**
     * 保存任务开始执行的时间
     */
    private ThreadLocal<Long> startTime = new ThreadLocal<>();



    public ThreadPoolMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    public ThreadPoolMonitor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue,String poolName) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,new EventThreadFactory(poolName),new ThreadPoolExecutor.DiscardOldestPolicy());
    }


    /**
     * 任务执行之前，记录任务开始时间
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        startTime.set(System.currentTimeMillis());
    }

    /**
     * 任务执行之后，计算任务结束时间
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long costTime = System.currentTimeMillis() - startTime.get();
        startTime.remove();  //删除，避免占用太多内存
        //设置最大最小执行时间
        maxCostTime = maxCostTime > costTime ? maxCostTime : costTime;
        if (totalTasks.get() == 0) {
            minCostTime = costTime;
        }
        minCostTime = minCostTime < costTime ? minCostTime : costTime;
        totalCostTime.addAndGet(costTime);
        totalTasks.incrementAndGet();
        logger.info("{}-pool-monitor: " +
                        "Duration: {} ms, PoolSize: {}, CorePoolSize: {}, ActiveCount: {}, " +
                        "Completed: {}, Task: {}, Queue: {}, LargestPoolSize: {}, " +
                        "MaximumPoolSize: {},  KeepAliveTime: {}, isShutdown: {}, isTerminated: {}",
                this.poolName,
                costTime, this.getPoolSize(), this.getCorePoolSize(), super.getActiveCount(),
                this.getCompletedTaskCount(), this.getTaskCount(), this.getQueue().size(), this.getLargestPoolSize(),
                this.getMaximumPoolSize(), this.getKeepAliveTime(TimeUnit.MILLISECONDS), this.isShutdown(), this.isTerminated());
    }

    public int getAc() {
        return ac;
    }

    /**
     * 线程平均耗时
     *
     * @return
     * */
    public float getAverageCostTime() {
        return totalCostTime.get() / totalTasks.get();
    }

    /**
     * 线程最大耗时
     * */
    public long getMaxCostTime() {
        return maxCostTime;
    }

    /**
     * 线程最小耗时
     * */
    public long getMinCostTime() {
        return minCostTime;
    }

    /**
     * 生成线程池所用的线程，改写了线程池默认的线程工厂
     */
    static class EventThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        /**
         * 初始化线程工厂
         *
         * @param poolName 线程池名称
         */
        EventThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = Objects.nonNull(s) ? s.getThreadGroup() :   Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
