package org.jenkinsci.plugins;

import static org.junit.jupiter.api.Assertions.assertEquals;

import hudson.util.FormValidation;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
class ExclusionTest {

    @Test
    void testGetFqcn(JenkinsRule jenkinsRule) {
        String fqcn = "hudson.testfqcn";
        String context = "testContext";
        Exclusion exclusion = new Exclusion(fqcn, context);

        String result = exclusion.getFqcn();

        assertEquals(fqcn, result);
    }

    @Test
    void testGetContext(JenkinsRule jenkinsRule) {
        String fqcn = "hudson.testfqcn";
        String context = "testContext";
        Exclusion exclusion = new Exclusion(fqcn, context);

        String result = exclusion.getContext();

        assertEquals(context, result);
    }

    @Test
    void testValidDescriptorWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("hudson.tasks.Maven$DescriptorImpl");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    void testValidExtensionWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("hudson.model.AdministrativeMonitor");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    void testValidExtensionAndDescriptorWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("jenkins.tasks.filters.EnvVarsFilterLocalRule");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    void testInvalidClassWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("hudson.Util");
        assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }

    @Test
    void testClassNotFound(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("");
        assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
}
