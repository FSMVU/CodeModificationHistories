


hyperxpro
on Fri Aug 06 2021

skyguard1
on Mon Jun 07 2021

artem-smotrakov
on Fri Oct 23 2020

hyperxpro
on Tue Sep 01 2020

carryxyh
on Mon Dec 09 2019

doom369
on Sat Apr 22 2017

Tim-Brooks
on Mon Dec 19 2016

buchgr
on Sun Aug 17 2014

trustin
on Wed Mar 12 2014

buchgr
on Wed Mar 05 2014

trustin
on Sat Jun 02 2012

trustin
on Wed Jan 11 2012
Apply checkstyle to the build ...

trustin
on Wed Dec 28 2011
/*
 * Copyright 2011 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.ipfilter;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Ip V4 and Ip V6 filter rule.<br>
 * <br>
 * Note that mix of IPV4 and IPV6 is allowed but it is not recommended. So it is preferable to not
 * mix IPV4 addresses with IPV6 rules, even if it should work.
 */
public class IpSubnetFilterRule extends IpSubnet implements IpFilterRule {
    /** Is this IpV4Subnet an ALLOW or DENY rule */
    private boolean isAllowRule = true;

    /**
     * Constructor for a ALLOW or DENY ALL
     *
     * @param allow True for ALLOW, False for DENY
     */
    public IpSubnetFilterRule(boolean allow) {
        isAllowRule = allow;
    }

    /** @param allow True for ALLOW, False for DENY */
    public IpSubnetFilterRule(boolean allow, InetAddress inetAddress, int cidrNetMask) throws UnknownHostException {
        super(inetAddress, cidrNetMask);
        isAllowRule = allow;
    }

    /** @param allow True for ALLOW, False for DENY */
    public IpSubnetFilterRule(boolean allow, InetAddress inetAddress, String netMask) throws UnknownHostException {
        super(inetAddress, netMask);
        isAllowRule = allow;
    }

    /** @param allow True for ALLOW, False for DENY */
    public IpSubnetFilterRule(boolean allow, String netAddress) throws UnknownHostException {
        super(netAddress);
        isAllowRule = allow;
    }

    @Override
    public boolean isAllowRule() {
        return isAllowRule;
    }

    @Override
    public boolean isDenyRule() {
        return !isAllowRule;
    }

}