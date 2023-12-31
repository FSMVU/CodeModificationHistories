


Riliane
on Fri Oct 28 2022

basil
on Fri Jan 07 2022

basil
on Mon Jan 03 2022

basil
on Mon Sep 20 2021

basil
on Sat Sep 18 2021

basil
on Tue Sep 07 2021

GustavoBezerra
on Thu Jun 27 2019

jsoref
on Thu Feb 21 2019

jsoref
on Thu Feb 21 2019

daniel-beck
on Wed Nov 21 2018

stephenc
on Wed Apr 19 2017

jglick
on Sat Apr 08 2017

stephenc
on Mon Mar 20 2017

ndeloof
on Fri Sep 30 2016

kohsuke
on Sat Mar 19 2016

oleg-nenashev
on Sun Oct 26 2014

jglick
on Wed Aug 27 2014

jglick
on Fri Feb 28 2014

kohsuke
on Fri Nov 08 2013
Added an ability to collect hs_err_pid*.log files
package jenkins.diagnosis;

import com.sun.akuma.JavaVMArguments;
import hudson.Extension;
import hudson.Functions;
import hudson.Util;
import hudson.model.AdministrativeMonitor;
import hudson.util.IOUtils;
import hudson.util.jna.Kernel32Utils;
import jenkins.model.Jenkins;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Finds crash dump reports and show them in the UI.
 *
 * @author Kohsuke Kawaguchi
 */
@Extension(optional=true)
public class HsErrPidList extends AdministrativeMonitor {
    /**
     * hs_err_pid files that we think belong to us.
     */
    /*package*/ final List<HsErrPidFile> files = new ArrayList<HsErrPidFile>();

    /**
     * Used to keep a marker file memory-mapped, so that we can find hs_err_pid files that belong to us.
     */
    private MappedByteBuffer map;

    public HsErrPidList() {
        try {
            FileChannel ch = new FileInputStream(getSecretKeyFile()).getChannel();
            map = ch.map(MapMode.READ_ONLY,0,1);

            scan("./hs_err_pid%p.log");
            if (Functions.isWindows()) {
                File dir = Kernel32Utils.getTempDir();
                if (dir!=null) {
                    scan(dir.getPath() + "\\hs_err_pid%p.log");
                }
            } else {
                scan("/tmp/hs_err_pid%p.log");
            }
            // on different platforms, rules about the default locations are a lot more subtle.

            // check our arguments in the very end since this might fail on some platforms
            JavaVMArguments args = JavaVMArguments.current();
            for (String a : args) {
                // see http://www.oracle.com/technetwork/java/javase/felog-138657.html
                if (a.startsWith(ERROR_FILE_OPTION)) {
                    scan(a.substring(ERROR_FILE_OPTION.length()));
                }
            }
        } catch (UnsupportedOperationException e) {
            // ignore
        } catch (Throwable e) {
            LOGGER.log(Level.WARNING, "Failed to list up hs_err_pid files", e);
        }
    }

    @Override
    public String getDisplayName() {
        return "JVM Crash Reports";
    }

    /**
     * Expose files to the URL.
     */
    public List<HsErrPidFile> getFiles() {
        return files;
    }


    private void scan(String pattern) {
        LOGGER.fine("Scanning "+pattern+" for hs_err_pid files");

        pattern = pattern.replace("%p","*").replace("%%","%");
        File f = new File(pattern).getAbsoluteFile();
        if (!pattern.contains("*"))
            scanFile(f);
        else {// GLOB
            File commonParent = f;
            while (commonParent!=null && commonParent.getPath().contains("*")) {
                commonParent = commonParent.getParentFile();
            }
            if (commonParent==null) {
                LOGGER.warning("Failed to process "+f);
                return; // huh?
            }

            FileSet fs = Util.createFileSet(commonParent, f.getPath().substring(commonParent.getPath().length()+1), null);
            DirectoryScanner ds = fs.getDirectoryScanner(new Project());
            for (String child : ds.getIncludedFiles()) {
                scanFile(new File(commonParent,child));
            }
        }
    }

    private void scanFile(File log) {
        LOGGER.fine("Scanning "+log);

        BufferedReader r=null;
        try {
            r = new BufferedReader(new FileReader(log));

            if (!findHeader(r))
                return;

            // we should find a memory mapped file for secret.key
            String secretKey = getSecretKeyFile().getAbsolutePath();


            String line;
            while ((line=r.readLine())!=null) {
                if (line.contains(secretKey)) {
                    files.add(new HsErrPidFile(this,log));
                    return;
                }
            }
        } catch (IOException e) {
            // not a big enough deal.
            LOGGER.log(Level.FINE, "Failed to parse hs_err_pid file: " + log, e);
        } finally {
            IOUtils.closeQuietly(r);
        }
    }

    private File getSecretKeyFile() {
        return new File(Jenkins.getInstance().getRootDir(),"secret.key");
    }

    private boolean findHeader(BufferedReader r) throws IOException {
        for (int i=0; i<5; i++) {
            String line = r.readLine();
            if (line==null)
                return false;
            if (line.startsWith("# A fatal error has been detected by the Java Runtime Environment:"))
                return true;
        }
        return false;
    }

    @Override
    public boolean isActivated() {
        return !files.isEmpty();
    }

    private static final String ERROR_FILE_OPTION = "-XX:ErrorFile=";
    private static final Logger LOGGER = Logger.getLogger(HsErrPidList.class.getName());
}