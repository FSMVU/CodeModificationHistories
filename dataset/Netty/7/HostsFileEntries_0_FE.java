


violetagg
on Fri May 14 2021

artem-smotrakov
on Fri Oct 23 2020

normanmaurer
on Wed Jan 15 2020

slandelle
on Wed Feb 08 2017
HostsFileParser should allow both IPv4 and IPv6 for a given host ...
/*
 * Copyright 2017 The Netty Project
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
package io.netty.resolver;

import io.netty.util.internal.UnstableApi;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A container of hosts file entries
 */
@UnstableApi
public final class HostsFileEntries {

    /**
     * Empty entries
     */
    static final HostsFileEntries EMPTY =
            new HostsFileEntries(
                    Collections.<String, Inet4Address>emptyMap(),
                    Collections.<String, Inet6Address>emptyMap());

    private final Map<String, Inet4Address> inet4Entries;
    private final Map<String, Inet6Address> inet6Entries;

    public HostsFileEntries(Map<String, Inet4Address> inet4Entries, Map<String, Inet6Address> inet6Entries) {
        this.inet4Entries = Collections.unmodifiableMap(new HashMap<String, Inet4Address>(inet4Entries));
        this.inet6Entries = Collections.unmodifiableMap(new HashMap<String, Inet6Address>(inet6Entries));
    }

    /**
     * The IPv4 entries
     * @return the IPv4 entries
     */
    public Map<String, Inet4Address> inet4Entries() {
        return inet4Entries;
    }

    /**
     * The IPv6 entries
     * @return the IPv6 entries
     */
    public Map<String, Inet6Address> inet6Entries() {
        return inet6Entries;
    }
}