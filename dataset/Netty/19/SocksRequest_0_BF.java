


artem-smotrakov
on Fri Oct 23 2020

carryxyh
on Mon Dec 09 2019

trustin
on Mon Feb 11 2013

trustin
on Thu Jan 17 2013

Norman Maurer
on Tue Dec 04 2012
[#787] Move the socks package to the right place
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
package io.netty.handler.codec.socks;

/**
 * An abstract class that defines a SocksRequest, providing common properties for
 * {@link SocksInitRequest}, {@link SocksAuthRequest}, {@link SocksCmdRequest} and {@link UnknownSocksRequest}.
 *
 * @see SocksInitRequest
 * @see SocksAuthRequest
 * @see SocksCmdRequest
 * @see UnknownSocksRequest
 */
public abstract class SocksRequest extends SocksMessage {
    private final SocksRequestType socksRequestType;

    protected SocksRequest(SocksRequestType socksRequestType) {
        super(MessageType.REQUEST);
        if (socksRequestType == null) {
            throw new NullPointerException("socksRequestType");
        }
        this.socksRequestType = socksRequestType;
    }

    /**
     * Returns socks request type
     *
     * @return socks request type
     */
    public SocksRequestType getSocksRequestType() {
        return socksRequestType;
    }

    /**
     * Type of socks request
     */
    public enum SocksRequestType {
        INIT,
        AUTH,
        CMD,
        UNKNOWN
    }
}