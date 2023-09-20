


Matt Jacobs
on Tue May 24 2016

Matt Jacobs
on Sat Aug 08 2015

Matt Jacobs
on Wed Apr 29 2015

Matt Jacobs
on Wed Apr 29 2015
Added HystrixCounters to hold global metrics on Hystrix behavior
package com.netflix.hystrix;

import java.util.concurrent.atomic.AtomicInteger;

public class HystrixCounters {
    private static final AtomicInteger concurrentThreadsExecuting = new AtomicInteger(0);

    /* package-private */ static void incrementGlobalConcurrentThreads() {
        concurrentThreadsExecuting.incrementAndGet();
    }

    /* package-private */ static void decrementGlobalConcurrentThreads() {
        concurrentThreadsExecuting.decrementAndGet();
    }

    public static int getGlobalConcurrentThreadsExecuting() {
        return concurrentThreadsExecuting.get();
    }



}