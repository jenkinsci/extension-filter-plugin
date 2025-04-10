package org.jenkinsci.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hudson.ExtensionList;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class ConfigurableExtensionFilterTest {

    @Test
    void nullSafety(JenkinsRule j) {
        ConfigurableExtensionFilter.DescriptorImpl impl =
                ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);
        assertNotNull(impl.getExclusions());
    }

    @Test
    void cannotSetNull(JenkinsRule j) {
        ConfigurableExtensionFilter.DescriptorImpl impl =
                ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);
        assertThrows(IllegalArgumentException.class, () -> impl.setExclusions(null));
    }

    @Test
    void testConfigRoundtrip(JenkinsRule j) throws Exception {
        ConfigurableExtensionFilter.DescriptorImpl impl =
                ExtensionList.lookupSingleton(ConfigurableExtensionFilter.DescriptorImpl.class);
        impl.setExclusions(new Exclusion[0]);
        j.configRoundtrip();
        assertEquals(0, impl.getExclusions().length);
    }

    @Test
    void testExtensionNotVisible(JenkinsRule j) {
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
