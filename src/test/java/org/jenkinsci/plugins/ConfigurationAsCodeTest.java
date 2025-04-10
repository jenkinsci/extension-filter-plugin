package org.jenkinsci.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.jenkins.plugins.casc.misc.ConfiguredWithCode;
import io.jenkins.plugins.casc.misc.JenkinsConfiguredWithCodeRule;
import io.jenkins.plugins.casc.misc.junit.jupiter.WithJenkinsConfiguredWithCode;
import jenkins.model.Jenkins;
import org.junit.jupiter.api.Test;

@WithJenkinsConfiguredWithCode
class ConfigurationAsCodeTest {

    @Test
    @ConfiguredWithCode("configuration-as-code.yml")
    void shouldSupportConfigurationAsCode(JenkinsConfiguredWithCodeRule j) {
        ConfigurableExtensionFilter.DescriptorImpl extension = Jenkins.get()
                .getExtensionList(ConfigurableExtensionFilter.DescriptorImpl.class)
                .get(0);
        assertNotNull(extension.getExclusions());
        assertEquals(2, extension.getExclusions().length);
    }
}
