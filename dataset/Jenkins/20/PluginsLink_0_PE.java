


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

daniel-beck
on Sat Aug 01 2020

timja
on Fri Apr 10 2020

jtnord
on Thu Mar 26 2020

daniel-beck
on Fri Mar 06 2020

daniel-beck
on Thu Mar 05 2020

kohsuke
on Sat Mar 19 2016

ndeloof
on Tue Oct 16 2012

ndeloof
on Tue Oct 16 2012

ndeloof
on Tue Oct 16 2012
refactor plugin link as a ManagementLink ...
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

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
@Extension(ordinal = Integer.MAX_VALUE - 4)
public class PluginsLink extends ManagementLink {

    @Override
    public String getIconFileName() {
        return "plugin.png";
    }

    public String getDisplayName() {
        return Messages.PluginsLink_DisplayName();
    }

    @Override
    public String getDescription() {
        return Messages.PluginsLink_Description();
    }

    @Override
    public String getUrlName() {
        return "pluginManager/";
    }
}