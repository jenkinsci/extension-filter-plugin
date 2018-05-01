package org.jenkinsci.plugins;
import hudson.ExtensionComponent;
import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.DescriptorVisibilityFilter;
import jenkins.ExtensionFilter;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

public class ConfigurableExtensionFilter extends AbstractDescribableImpl<ConfigurableExtensionFilter> {

    @Extension
    public final static ExtensionFilter EXTENSION_FILTER = new ExtensionFilter() {

        @Override
        public <T> boolean allows(Class<T> tClass, ExtensionComponent<T> tExtensionComponent) {
            // Don't exclude myself
            if (tExtensionComponent.getInstance() == this) return true;

            return DESCRIPTOR.allows(tClass.getName())
                    && DESCRIPTOR.allows(tExtensionComponent.getInstance().getClass().getName());
        }
    };


    @Extension
    public final static DescriptorVisibilityFilter DESCRIPTOR_FILTER = new DescriptorVisibilityFilter() {

        @Override
        public boolean filter(@CheckForNull Object context, @Nonnull Descriptor descriptor) {
            Class contextClass = context == null ? null : context.getClass();
            return DESCRIPTOR.allows(contextClass, descriptor.getClass().getName());
        }
    };

    @Extension
    public final static DescriptorImpl DESCRIPTOR = new DescriptorImpl();

    public static final class DescriptorImpl extends Descriptor<ConfigurableExtensionFilter> {

        public DescriptorImpl() {
            load();
        }

        private Exclusion[] exclusions;

        public Exclusion[] getExclusions() {
            return exclusions.clone();
        }

        public void setExclusions(Exclusion[] exclusions) {
            this.exclusions = exclusions.clone();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            setExclusions(req.bindJSONToList(Exclusion.class, json.get("exclusions"))
                    .toArray(new Exclusion[0]));
            save();
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Extension Filter";
        }

        public boolean allows(String name) {
            if (exclusions == null) return true;
            for (Exclusion exclusion : exclusions) {
                if (exclusion.getFqcn().equals(name)) return false;
            }
            return true;
        }

        public boolean allows(Class context, String name) {
            if (exclusions == null) return true;
            for (Exclusion exclusion : exclusions) {
                if (!exclusion.getFqcn().equals(name)) continue;
                String ctx = exclusion.getContext();
                if (ctx == null) return false;
                do {
                    if (context.getName().equals(ctx)) return false;
                } while (Object.class != (context = context.getSuperclass()));
            }
            return true;
        }
    }
}

