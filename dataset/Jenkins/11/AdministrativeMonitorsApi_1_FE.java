


basil
on Sat Sep 18 2021

fqueiruga
on Wed Jan 27 2021
Rename API endpoint to avoid URL collisions (#5207)

fqueiruga
on Thu Nov 26 2020
package jenkins.management;

import hudson.Extension;
import hudson.model.PageDecorator;
import hudson.model.RootAction;
import jenkins.model.Jenkins;
import org.kohsuke.accmod.Restricted;
import org.kohsuke.accmod.restrictions.NoExternalUse;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.GET;

import javax.servlet.ServletException;
import java.io.IOException;

@Extension
@Restricted(NoExternalUse.class)
public class AdministrativeMonitorsApi implements RootAction {
    @GET
    public void doNonSecurityPopupContent(StaplerRequest req, StaplerResponse resp) throws IOException, ServletException {
        AdministrativeMonitorsApiData viewData = new AdministrativeMonitorsApiData(getDecorator().getNonSecurityAdministrativeMonitors());
        req.getView(viewData, "monitorsList.jelly").forward(req, resp);
    }

    @GET
    public void doSecurityPopupContent(StaplerRequest req, StaplerResponse resp) throws IOException, ServletException {
        AdministrativeMonitorsApiData viewData = new AdministrativeMonitorsApiData(getDecorator().getSecurityAdministrativeMonitors());
        req.getView(viewData, "monitorsList.jelly").forward(req, resp);
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return "administrativeMonitorsApi";
    }

    private AdministrativeMonitorsDecorator getDecorator() {
        return Jenkins.get()
                .getExtensionList(PageDecorator.class)
                .get(AdministrativeMonitorsDecorator.class);
    }
}