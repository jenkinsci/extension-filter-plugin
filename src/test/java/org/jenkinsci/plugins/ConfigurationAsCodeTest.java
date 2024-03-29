package org.jenkinsci.plugins;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import jenkins.model.Jenkins;
import org.junit.Rule;
import org.junit.Test;

public class ConfigurationAsCodeTest {

    @Rule
    public JenkinsConfiguredWithCodeRule j = new JenkinsConfiguredWithCodeRule();

    @Test
    @ConfiguredWithCode("configuration-as-code.yml")
    public void shouldSupportConfigurationAsCode() throws Exception {
        ConfigurableExtensionFilter.DescriptorImpl extension = Jenkins.get()
                .getExtensionList(ConfigurableExtensionFilter.DescriptorImpl.class)
                .get(0);
        assertNotNull(extension.getExclusions());
        assertEquals(2, extension.getExclusions().length);
    }
}
