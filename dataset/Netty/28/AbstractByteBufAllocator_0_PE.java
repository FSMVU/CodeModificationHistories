


franz1981
on Wed Sep 01 2021

artem-smotrakov
on Fri Oct 23 2020

Tim-Brooks
on Wed May 22 2019

thinkerou
on Thu Jan 31 2019

carl-mastrangelo
on Tue Sep 19 2017

fenik17
on Wed Apr 19 2017

normanmaurer
on Thu Jan 26 2017

normanmaurer
on Thu Dec 01 2016

normanmaurer
on Tue Jan 12 2016

Scottmitch
on Fri Jun 05 2015

Norman Maurer
on Mon Jun 23 2014

Norman Maurer
on Mon Jun 16 2014

trustin
on Wed Dec 04 2013

trustin
on Fri Nov 08 2013

He-Pin
on Mon Jul 22 2013

Norman Maurer
on Mon Jul 22 2013

trustin
on Mon Jul 08 2013

trustin
on Fri Mar 08 2013

trustin
on Tue Mar 05 2013

trustin
on Tue Mar 05 2013

Norman Maurer
on Sun Feb 17 2013

trustin
on Wed Dec 19 2012

trustin
on Wed Dec 05 2012

trustin
on Fri Nov 30 2012

trustin
on Thu Nov 15 2012
ByteBufAllocator API w/ ByteBuf perf improvements ...
/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.netty.buffer;

public abstract class AbstractByteBufAllocator implements ByteBufAllocator {

    private final boolean directByDefault;
    private final ByteBuf emptyBuf;

    protected AbstractByteBufAllocator() {
        this(false);
    }

    protected AbstractByteBufAllocator(boolean directByDefault) {
        this.directByDefault = directByDefault;
        emptyBuf = new UnpooledHeapByteBuf(this, 0, 0);
    }

    @Override
    public ByteBuf buffer() {
        if (directByDefault) {
            return directBuffer();
        }
        return heapBuffer();
    }

    @Override
    public ByteBuf buffer(int initialCapacity) {
        if (directByDefault) {
            return directBuffer(initialCapacity);
        }
        return heapBuffer(initialCapacity);
    }

    @Override
    public ByteBuf buffer(int initialCapacity, int maxCapacity) {
        if (directByDefault) {
            return directBuffer(initialCapacity, maxCapacity);
        }
        return heapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf heapBuffer() {
        return heapBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int initialCapacity) {
        return buffer(initialCapacity, Integer.MAX_VALUE);
    }

    public ByteBuf heapBuffer(int initialCapacity, int maxCapacity) {
        if (initialCapacity == 0 && maxCapacity == 0) {
            return emptyBuf;
        }
        return newHeapBuffer(initialCapacity, maxCapacity);
    }

    public ByteBuf directBuffer() {
        return directBuffer(256, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int initialCapacity) {
        return directBuffer(initialCapacity, Integer.MAX_VALUE);
    }

    public ByteBuf directBuffer(int initialCapacity, int maxCapacity) {
        if (initialCapacity == 0 && maxCapacity == 0) {
            return emptyBuf;
        }
        return newDirectBuffer(initialCapacity, maxCapacity);
    }

    protected abstract ByteBuf newHeapBuffer(int initialCapacity, int maxCapacity);
    protected abstract ByteBuf newDirectBuffer(int initialCapacity, int maxCapacity);
}