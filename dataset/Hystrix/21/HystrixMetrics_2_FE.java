


Matt Jacobs
on Thu Oct 15 2015

Matt Jacobs
on Thu Oct 15 2015

Matt Jacobs
on Thu Oct 15 2015

Matt Jacobs
on Wed Jul 29 2015

Matt Jacobs
on Tue Jul 28 2015
Make thread-pool and collapser metrics also able to be varied by plugin

Matt Jacobs
on Tue Jul 28 2015

Matt Jacobs
on Tue Jan 27 2015
/**
 * Copyright 2012 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.hystrix;

import com.netflix.hystrix.util.HystrixRollingNumberEvent;

/**
 * Abstract base class for Hystrix metrics
 */
public abstract class HystrixMetrics {

    /**
     * Get the cumulative count since the start of the application for the given {@link HystrixRollingNumberEvent}.
     * 
     * @param event
     *            {@link HystrixRollingNumberEvent} of the event to retrieve a sum for
     * @return long cumulative count
     */
    public abstract long getCumulativeCount(HystrixRollingNumberEvent event);

    /**
     * Get the rolling count for the given {@link HystrixRollingNumberEvent}.
     * <p>
     * The rolling window is defined by {@link HystrixCommandProperties#metricsRollingStatisticalWindowInMilliseconds()}.
     * 
     * @param event
     *            {@link HystrixRollingNumberEvent} of the event to retrieve a sum for
     * @return long rolling count
     */
    public abstract long getRollingCount(HystrixRollingNumberEvent event);


    protected abstract void addEvent(HystrixRollingNumberEvent event);

    protected abstract void addEventWithValue(HystrixRollingNumberEvent event, long value);

    protected abstract void updateRollingMax(HystrixRollingNumberEvent event, long value);

    protected abstract long getRollingMax(HystrixRollingNumberEvent event);
}