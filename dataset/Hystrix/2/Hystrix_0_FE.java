


Clarke, Peter
on Tue Jan 31 2017

Matt Jacobs
on Thu Jan 07 2016

Matt Jacobs
on Fri Mar 27 2015

Matt Jacobs
on Tue Feb 10 2015

Matt Jacobs
on Fri Feb 06 2015

Matt Jacobs
on Wed Jan 28 2015

Matt Jacobs
on Wed Jan 28 2015

Matt Jacobs
on Wed Jan 14 2015

Matt Jacobs
on Thu Jan 08 2015

Matt Jacobs
on Wed Jan 07 2015

Matt Jacobs
on Wed Jan 07 2015

cgray
on Thu Jun 05 2014

benjchristensen
on Tue Feb 25 2014

benjchristensen
on Wed Feb 12 2014

neerajrj
on Fri Jan 17 2014

neerajrj
on Fri Nov 15 2013

benjchristensen
on Sun Mar 31 2013

benjchristensen
on Sat Mar 16 2013

benjchristensen
on Fri Feb 15 2013
Hystrix.reset for lifecycle management ...

package com.netflix.hystrix;


import java.util.concurrent.TimeUnit;



/**
 * Lifecycle management of Hystrix.
 */
public class Hystrix {

    /**
     * Reset state and release resources in use (such as thread-pools).
     * <p>
     * NOTE: This can result in race conditions if HystrixCommands are concurrently being executed.
     * </p>
     */
    public static void reset() {
        // shutdown thread-pools
        HystrixThreadPool.Factory.shutdown();
        _reset();
    }

    /**
     * Reset state and release resources in use (such as threadpools) and wait for completion.
     * <p>
     * NOTE: This can result in race conditions if HystrixCommands are concurrently being executed.
     * </p>
     *
     * @param time time to wait for thread-pools to shutdown
     * @param unit {@link TimeUnit} for <pre>time</pre> to wait for thread-pools to shutdown
     */
    public static void reset(long time, TimeUnit unit) {
        // shutdown thread-pools
        HystrixThreadPool.Factory.shutdown(time, unit);
        _reset();
    }

    /**
     * Reset logic that doesn't have time/TimeUnit arguments.
     */
    private static void _reset() {
        // clear metrics
        HystrixCommandMetrics.reset();
        // clear collapsers
        HystrixCollapser.reset();
        // clear circuit breakers
        HystrixCircuitBreaker.Factory.reset();
    }
}