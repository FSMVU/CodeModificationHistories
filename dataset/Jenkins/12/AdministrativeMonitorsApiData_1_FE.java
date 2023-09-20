


basil
on Sat Sep 18 2021
Sort imports (#5732)

fqueiruga
on Thu Nov 26 2020
package jenkins.management;

import hudson.model.AdministrativeMonitor;
import java.util.ArrayList;
import java.util.List;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;


@Restricted(NoExternalUse.class)
public class AdministrativeMonitorsApiData {
    private final List<AdministrativeMonitor> monitorsList = new ArrayList<>();

    AdministrativeMonitorsApiData(List<AdministrativeMonitor> monitors) {
        monitorsList.addAll(monitors);
    }

    public List<AdministrativeMonitor> getMonitorsList() {
        return this.monitorsList;
    }

    public boolean hasActiveMonitors() {
        return !this.monitorsList.isEmpty();
    }
}