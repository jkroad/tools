package com.ismayfly.coins.tools.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NameThreadFactory implements ThreadFactory {
    private static final AtomicInteger threadNumber = new AtomicInteger(1);
    private final AtomicInteger mThreadNumber = new AtomicInteger(1);
    private String prefix;
    private boolean daemoThead;
    private final ThreadGroup threadGroup;

    public NameThreadFactory(){
        this("mayfly_thread_pool_"+threadNumber.getAndIncrement(),false);
    }

    public NameThreadFactory(String prefix){
        this(prefix,false);
    }

    public NameThreadFactory(String prefix,boolean daemo){
        this.prefix = prefix != null ? prefix + "-thread-" : "";
        daemoThead = daemo;
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }
    @Override
    public Thread newThread(Runnable r) {
        String name = prefix + mThreadNumber.getAndIncrement();
        Thread ret = new Thread(threadGroup, r, name, 0);
        ret.setDaemon(daemoThead);
        return ret;
    }
}
