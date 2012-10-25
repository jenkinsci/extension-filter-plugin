package org.jenkinsci.plugins;

import hudson.Extension;
import hudson.Util;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author <a href="mailto:nicolas.deloof@gmail.com">Nicolas De Loof</a>
 */
public class Exclusion extends AbstractDescribableImpl<Exclusion> {

    private final String fqcn;
    private String context;

    @DataBoundConstructor
    public Exclusion(String fqcn, String context) {
        this.fqcn = fqcn;
        this.context = Util.fixEmpty(context);
    }

    public String getFqcn() {
        return fqcn;
    }

    public String getContext() {
        return context;
    }

    @Extension
    public static final class DescriptorImpl extends Descriptor<Exclusion> {

        @Override
        public String getDisplayName() {
            return "Extension";
        }
    }
}
