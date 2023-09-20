


mostroverkhov
on Thu Dec 23 2021

comchi
on Thu Dec 16 2021

violetagg
on Fri May 14 2021

artem-smotrakov
on Fri Oct 23 2020

normanmaurer
on Wed Jan 15 2020

normanmaurer
on Fri Aug 24 2018

slandelle
on Wed Feb 08 2017

radai-rosenblatt
on Thu Sep 29 2016

alexlehm
on Fri Apr 29 2016
Change hosts file resolver to be case-insensitive ...

slandelle
on Mon Dec 14 2015
/*
 * Copyright 2015 The Netty Project
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

import java.net.InetAddress;
import java.util.Locale;
import java.util.Map;

/**
 * Default {@link HostsFileEntriesResolver} that resolves hosts file entries only once.
 */
public final class DefaultHostsFileEntriesResolver implements HostsFileEntriesResolver {

    private final Map<String, InetAddress> entries = HostsFileParser.parseSilently();

    @Override
    public InetAddress address(String inetHost) {
        return entries.get(inetHost.toLowerCase(Locale.ENGLISH));
    }

}