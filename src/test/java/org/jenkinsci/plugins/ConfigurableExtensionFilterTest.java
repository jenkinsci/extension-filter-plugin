package org.jenkinsci.plugins;

import org.jenkinsci.plugins.ConfigurableExtensionFilter.DescriptorImpl;
import org.junit.Rule;
import org.junit.Test;
import org.jvnet.hudson.test.JenkinsRule;

import static org.junit.Assert.assertNotNull;

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
}