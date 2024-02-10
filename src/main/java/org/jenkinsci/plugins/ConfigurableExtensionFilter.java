package org.jenkinsci.plugins;

import edu.umd.cs.findbugs.annotations.CheckForNull;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import hudson.Extension;
import hudson.ExtensionComponent;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.DescriptorVisibilityFilter;
import javax.annotation.Nonnull;
import jenkins.ExtensionFilter;
import net.sf.json.JSONObject;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.StaplerRequest;

public class ConfigurableExtensionFilter extends AbstractDescribableImpl<ConfigurableExtensionFilter> {

    public static /*almost final*/ DescriptorImpl DESCRIPTOR;

    @Symbol("configurableExtensionFilter")
    @Extension
    public static final class DescriptorImpl extends Descriptor<ConfigurableExtensionFilter> {

        @SuppressFBWarnings(
                value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD",
                justification = "for backward compatibility")
        public DescriptorImpl() {
            DESCRIPTOR = this;
            load();
        }

        private Exclusion[] exclusions = new Exclusion[0];

        public Exclusion[] getExclusions() {
            return exclusions.clone();
        }

        public void setExclusions(@Nonnull Exclusion[] exclusions) {
            if (exclusions == null) {
                throw new IllegalArgumentException("Passed 'exclusions' array must be non null");
            }
            this.exclusions = exclusions.clone();
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            setExclusions(
                    req.bindJSONToList(Exclusion.class, json.get("exclusions")).toArray(new Exclusion[0]));
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

    @Extension
    public static final class Filter extends ExtensionFilter {

        @Override
        public <T> boolean allows(Class<T> tClass, ExtensionComponent<T> tExtensionComponent) {
            if (tExtensionComponent.getInstance() == this) return true;

            return DESCRIPTOR.allows(tClass.getName())
                    && DESCRIPTOR.allows(
                            tExtensionComponent.getInstance().getClass().getName());
        }
    }

    @Extension
    public static class ConfigurableDescriptorVisibilityFilter extends DescriptorVisibilityFilter {

        @Override
        public boolean filter(@CheckForNull Object context, @NonNull Descriptor descriptor) {
            Class<?> contextClass = context == null ? null : context.getClass();
            return DESCRIPTOR.allows(contextClass, descriptor.getClass().getName());
        }
    }
}
