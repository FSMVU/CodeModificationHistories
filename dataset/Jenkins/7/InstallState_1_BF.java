


basil
on Mon Jan 03 2022

basil
on Mon Nov 29 2021

basil
on Sat Sep 18 2021

basil
on Mon Jun 14 2021

basil
on Wed Mar 03 2021

timja
on Tue Feb 16 2021

timja
on Tue Feb 16 2021

timja
on Tue Feb 16 2021

jtnord
on Thu Mar 26 2020

jtnord
on Thu Mar 26 2020

basil
on Sat Oct 19 2019

daniel-beck
on Wed Nov 21 2018

daniel-beck
on Wed Nov 21 2018

jglick
on Thu May 03 2018

Wadeck
on Thu Apr 26 2018

jglick
on Tue Apr 24 2018

daniel-beck
on Sat Mar 31 2018

jglick
on Mon Mar 05 2018

Vlatombe
on Sun Dec 10 2017

jsoref
on Sun Feb 12 2017

jtnord
on Sun Aug 07 2016

oleg-nenashev
on Thu Jun 02 2016

kzantow
on Thu Jun 02 2016

kzantow
on Fri May 20 2016

kzantow
on Fri Apr 01 2016

kzantow
on Sun Mar 06 2016

tfennelly
on Fri Jan 22 2016
Typo fix in InstallState

tfennelly
on Tue Jan 19 2016
/*
 * The MIT License
 *
 * Copyright (c) 2015, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jenkins.install;

import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;

/**
 * Jenkins install state.
 *
 * @author <a href="mailto:tom.fennelly@gmail.com">tom.fennelly@gmail.com</a>
 */
@Restricted(NoExternalUse.class)
public enum InstallState {
    /**
     * New Jenkins install.
     */
    NEW,
    /**
     * New Jenkins install. The user has kicked off the process of installing an
     * initial set of plugins (via the install wizard).
     */
    INITIAL_PLUGINS_INSTALLING,
    /**
     * New Jenkins install. The initial set of plugins are now installed.
     */
    INITIAL_PLUGINS_INSTALLED,
    /**
     * Restart of an existing Jenkins install.
     */
    RESTART,
    /**
     * Upgrade of an existing Jenkins install.
     */
    UPGRADE,
    /**
     * Downgrade of an existing Jenkins install.
     */
    DOWNGRADE,
    /**
     * Jenkins started in test mode (JenkinsRule).
     */



    TEST
}