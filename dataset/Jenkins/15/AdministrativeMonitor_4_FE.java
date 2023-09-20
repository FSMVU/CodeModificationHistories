


basil
on Mon Jan 03 2022

basil
on Sat Dec 18 2021

basil
on Sat Dec 11 2021

basil
on Sat Sep 18 2021

basil
on Wed Apr 28 2021

jglick
on Fri Nov 06 2020

jtnord
on Thu Mar 26 2020

Raihaan Shouhell
on Wed Oct 23 2019

GustavoBezerra
on Thu Jun 27 2019

jsoref
on Thu Feb 21 2019

jglick
on Tue Dec 13 2016

jglick
on Tue Dec 13 2016
Replaced other usages of printStackTrace.

csimons
on Fri Nov 06 2015

stephenc
on Thu Mar 26 2015

oleg-nenashev
on Sun Oct 26 2014

kohsuke
on Tue Jul 30 2013
package jenkins.management;

import hudson.AbortException;
import hudson.Functions;
import hudson.console.AnnotatedLargeText;
import hudson.model.AdministrativeMonitor;
import hudson.model.TaskListener;
import hudson.security.ACL;
import hudson.util.StreamTaskListener;
import jenkins.model.Jenkins;
import jenkins.security.RekeySecretAdminMonitor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nonnull;

/**
 * Convenient partial implementation of {@link AdministrativeMonitor} that involves a background "fixing" action
 * once the user opts in for the execution of it.
 *
 * <p>
 * A subclass defines what that background fixing actually does in {@link #fix(TaskListener)}. The logging output
 * from it gets persisted, and this class provides a "/log" view that allows the administrator to monitor its progress.
 *
 * <p>
 * See {@link RekeySecretAdminMonitor} for an example of how to subtype this class.
 *
 * @author Kohsuke Kawaguchi
 */
public abstract class AsynchronousAdministrativeMonitor extends AdministrativeMonitor {
    /**
     * Set to non-null once the background activity starts running.
     */
    private volatile FixThread fixThread;

    /**
     * Is there an active execution process going on?
     */
    public boolean isFixingActive() {
        return fixThread !=null && fixThread.isAlive();
    }

    /**
     * Used to URL-bind {@link AnnotatedLargeText}.
     */
    public AnnotatedLargeText getLogText() {
        return new AnnotatedLargeText<AsynchronousAdministrativeMonitor>(
                getLogFile(), Charset.defaultCharset(),
                !isFixingActive(), this);
    }

    /**
     * Rewrite log file.
     */
    protected File getLogFile() {
        File base = getBaseDir();
        base.mkdirs();
        return new File(base,"log");
    }

    protected File getBaseDir() {
        return new File(Jenkins.getInstance().getRootDir(),getClass().getName());
    }

    public abstract String getDisplayName();

    /**
     * Starts the background fixing activity.
     *
     * @param forceRestart
     *      If true, any ongoing fixing activity gets interrupted and the new one starts right away.
     */
    protected synchronized Thread start(boolean forceRestart) {
        if (!forceRestart && isFixingActive()) {
            fixThread.interrupt();
        }

        if (forceRestart || !isFixingActive()) {
            fixThread = new FixThread();
            fixThread.start();
        }
        return fixThread;
    }

    /**
     * Run on a separate thread in the background to fix up stuff.
     */
    protected abstract void fix(TaskListener listener) throws Exception;

    protected class FixThread extends Thread {
        FixThread() {
            super(getDisplayName());
        }

        @Override
        public void run() {
            ACL.impersonate(ACL.SYSTEM);
            StreamTaskListener listener = null;
            try {
                listener = new StreamTaskListener(getLogFile());
                try {
                    doRun(listener);
                } finally {
                    listener.close();
                }
            } catch (IOException ex) {
                if (listener == null) {
                    LOGGER.log(Level.SEVERE, "Cannot create listener for " + getName(), ex);
                    //TODO: throw IllegalStateException?
                } else {
                    LOGGER.log(Level.WARNING, "Cannot close listener for " + getName(), ex);
                }
            }
         }

        /**
         * Runs the monitor and encapsulates all errors within.
         * @since 1.590
         */
        private void doRun(@Nonnull TaskListener listener) {
            try {
                fix(listener);
            } catch (AbortException e) {
                listener.error(e.getMessage());
            } catch (Throwable e) {
                listener.error(getName() + " failed").print(Functions.printThrowable(e));
                LOGGER.log(Level.WARNING, getName() + " failed", e);
            }
        }
    }

    private static final Logger LOGGER = Logger.getLogger(AsynchronousAdministrativeMonitor.class.getName());
}