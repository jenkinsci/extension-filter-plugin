package org.jenkinsci.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import jenkins.ExtensionFilter;
import org.jenkinsci.plugins.ConfigurableExtensionFilter.DescriptorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

public class ConfigurableExtensionFilterTest {

    @Rule
    public JenkinsRule j = new JenkinsRule();

    @Test
    public void nullSafety() {
        DescriptorImpl impl = new DescriptorImpl();
        assertNotNull(impl.getExclusions());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSetNull() {
        DescriptorImpl impl = new DescriptorImpl();
        impl.setExclusions(null);
    }

    @Test
    public void testConfigRoundtrip() throws Exception {
        DescriptorImpl impl = new DescriptorImpl();
        impl.setExclusions(new Exclusion[0]);

        j.configRoundtrip();

        assertEquals(0, impl.getExclusions().length);
    }

    @Test
    public void testGetExtensionFilter() {
        ExtensionFilter extensionFilter = ConfigurableExtensionFilter.getExtensionFilter();

        assertNotNull(extensionFilter);
    }
}
