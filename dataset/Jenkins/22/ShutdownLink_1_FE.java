


janfaracik
on Tue Feb 15 2022

janfaracik
on Thu Jan 27 2022

basil
on Mon Jan 03 2022

basil
on Sat Sep 18 2021

basil
on Wed Apr 28 2021

Austry
on Tue Nov 17 2020

jtnord
on Thu Apr 02 2020

jtnord
on Thu Apr 02 2020

escoem
on Wed Apr 01 2020

jtnord
on Thu Mar 26 2020

daniel-beck
on Fri Mar 06 2020

daniel-beck
on Thu Mar 05 2020

GustavoBezerra
on Thu Jun 27 2019

kohsuke
on Sat Mar 19 2016

bkmeneguello
on Wed Jul 02 2014
Convert the management link to POST and allow other management links to require ...

ndeloof
on Tue Oct 16 2012
/*
 * The MIT License
 *
 * Copyright (c) 2012, CloudBees, Intl., Nicolas De loof
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

package jenkins.management;

import hudson.Extension;
import hudson.model.ManagementLink;
import jenkins.model.Jenkins;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Extension(ordinal = Integer.MIN_VALUE)
public class ShutdownLink extends ManagementLink {

    @Override
    public String getIconFileName() {
        return "system-log-out.png";
    }

    public String getDisplayName() {
        return Jenkins.getInstance().isQuietingDown() ? Messages.ShutdownLink_DisplayName_cancel() : Messages.ShutdownLink_DisplayName_prepare();
    }

    @Override
    public String getDescription() {
        return Jenkins.getInstance().isQuietingDown() ? "" : Messages.ShutdownLink_Description();
    }

    @Override
    public String getUrlName() {
        return Jenkins.getInstance().isQuietingDown() ? "cancelQuietDown" : "quietDown";
    }

    @Override
    public boolean getRequiresPOST() {
        return true;
    }
}