


mebigfatguy
on Wed Jun 29 2016

Matt Jacobs
on Fri Mar 27 2015
Revert back to JDK6

Matt Jacobs
on Tue Mar 03 2015

Matt Jacobs
on Wed Jan 28 2015

benjchristensen
on Mon Nov 19 2012
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

import java.util.concurrent.ConcurrentHashMap;

/**
 * A key to represent a {@link HystrixCollapser} for monitoring, circuit-breakers, metrics publishing, caching and other such uses.
 * <p>
 * This interface is intended to work natively with Enums so that implementing code can be an enum that implements this interface.
 */
public interface HystrixCollapserKey {

    /**
     * The word 'name' is used instead of 'key' so that Enums can implement this interface and it work natively.
     * 
     * @return String
     */
    public String name();

    public static class Factory {

        private Factory() {
        }

        // used to intern instances so we don't keep re-creating them millions of times for the same key
        private static ConcurrentHashMap<String, HystrixCollapserKey> intern = new ConcurrentHashMap<String, HystrixCollapserKey>();

        /**
         * Retrieve (or create) an interned HystrixCollapserKey instance for a given name.
         * 
         * @param name collapser name
         * @return HystrixCollapserKey instance that is interned (cached) so a given name will always retrieve the same instance.
         */
        public static HystrixCollapserKey asKey(String name) {
            HystrixCollapserKey k = intern.get(name);
            if (k == null) {
                intern.putIfAbsent(name, new HystrixCollapserKeyDefault(name));
            }
            return intern.get(name);
        }

        private static class HystrixCollapserKeyDefault implements HystrixCollapserKey {

            private String name;

            private HystrixCollapserKeyDefault(String name) {
                this.name = name;
            }

            @Override
            public String name() {
                return name;
            }

        }
    }
}