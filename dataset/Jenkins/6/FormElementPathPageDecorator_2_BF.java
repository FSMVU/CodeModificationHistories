


basil
on Tue Dec 07 2021
Prevent new SpotBugs violations from being introduced (#6015)

timja
on Sat Dec 04 2021

timja
on Thu Nov 25 2021
package jenkins.formelementpath;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.Extension;
import hudson.model.PageDecorator;
import jenkins.util.SystemProperties;

@Extension
public class FormElementPathPageDecorator extends PageDecorator {

    @SuppressFBWarnings(value = "MS_SHOULD_BE_FINAL", justification = "for script console")
    private static /*almost final */ boolean ENABLED =
            SystemProperties.getBoolean(FormElementPathPageDecorator.class.getName() + ".enabled");

    public boolean isEnabled() {
        return ENABLED;
    }

}