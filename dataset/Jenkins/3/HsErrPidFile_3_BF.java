


basil
on Mon Jan 03 2022

basil
on Sat Dec 11 2021
Fix `RV_RETURN_VALUE_IGNORED_BAD_PRACTICE` SpotBugs violations (#6025)

basil
on Sat Sep 18 2021

GustavoBezerra
on Thu Jun 27 2019

daniel-beck
on Fri Apr 14 2017

kohsuke
on Fri Nov 08 2013
package jenkins.diagnosis;

import hudson.Util;
import hudson.util.HttpResponses;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.interceptor.RequirePOST;

/**
 * Serves hs_err_pid file.
 *
 * @author Kohsuke Kawaguchi
 */
public class HsErrPidFile {
    private final HsErrPidList owner;
    private final File file;

    public HsErrPidFile(HsErrPidList owner, File file) {
        this.owner = owner;
        this.file = file;
    }

    public String getName() {
        return file.getName();
    }

    public String getPath() {
        return file.getPath();
    }

    public long getLastModified() {
        return file.lastModified();
    }

    public Date getLastModifiedDate() {
        return new Date(file.lastModified());
    }

    public String getTimeSpanString() {
        return Util.getTimeSpanString(System.currentTimeMillis()-getLastModified());
    }

    public HttpResponse doDownload() throws IOException {
        Jenkins.get().checkPermission(Jenkins.ADMINISTER);
        return HttpResponses.staticResource(file);
    }

    @RequirePOST
    public HttpResponse doDelete() throws IOException {
        Jenkins.get().checkPermission(Jenkins.ADMINISTER);
        Files.deleteIfExists(Util.fileToPath(file));
        owner.files.remove(this);
        return HttpResponses.redirectTo("../..");
    }
}