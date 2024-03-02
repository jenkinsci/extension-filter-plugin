package org.jenkinsci.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import hudson.ExtensionList;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class ConfigurableExtensionFilterTest {

    @Test
    public void nullSafety(JenkinsRule j) {
        ConfigurableExtensionFilter.DescriptorImpl impl =
                ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);
        assertNotNull(impl.getExclusions());
    }

    @Test
    public void cannotSetNull(JenkinsRule j) {
        try {
            ConfigurableExtensionFilter.DescriptorImpl impl =
                    ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);
            impl.setExclusions(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            return;
        }
        fail("Expected IllegalArgumentException");
    }

    @Test
    public void testConfigRoundtrip(JenkinsRule j) throws Exception {
        ConfigurableExtensionFilter.DescriptorImpl impl =
                ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);
        impl.setExclusions(new Exclusion[0]);
        j.configRoundtrip();
        assertEquals(0, impl.getExclusions().length);
    }

    @Test
    public void testExtensionNotVisible(JenkinsRule j) throws Exception {

        // Get our descriptor
        ConfigurableExtensionFilter.DescriptorImpl impl =
                ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);

        // Set exclusion with context
        impl.setExclusions(new Exclusion[] {
            new Exclusion(
                    "jenkins.security.BasicHeaderApiTokenAuthenticator", "jenkins.security.BasicHeaderAuthenticator")
        });

        // This is not visible anymore
        assertEquals(
                0,
                ExtensionList.lookup(jenkins.security.BasicHeaderApiTokenAuthenticator.class)
                        .size());

        // This is still visible
        assertEquals(
                1,
                ExtensionList.lookup(jenkins.security.BasicHeaderRealPasswordAuthenticator.class)
                        .size());
    }
}
