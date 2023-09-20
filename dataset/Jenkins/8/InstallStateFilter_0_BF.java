


basil
on Mon Jan 03 2022

basil
on Sat Sep 18 2021

kzantow
on Fri May 20 2016
[FIX JENKINS-33663] - Upgrade wizard ...
package jenkins.install;

import java.util.List;

import javax.inject.Provider;

import hudson.ExtensionList;
import hudson.ExtensionPoint;

/**
 * Allows plugging in to the lifecycle when determining InstallState
 * from {@link InstallUtil#getNextInstallState(InstallState)}
 */
public abstract class InstallStateFilter implements ExtensionPoint {
    /**
     * Determine the current or next install state, proceed with `return proceed.next()`
     */
    public abstract InstallState getNextInstallState(InstallState current, Provider<InstallState> proceed);
    
    /**
     * Get all the InstallStateFilters, in extension order
     */
    public static List<InstallStateFilter> all() {
        return ExtensionList.lookup(InstallStateFilter.class);
    }
}