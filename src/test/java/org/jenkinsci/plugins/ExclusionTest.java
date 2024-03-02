package org.jenkinsci.plugins;

import static org.junit.Assert.assertEquals;

import hudson.util.FormValidation;
import org.junit.jupiter.api.Test;
import org.jvnet.hudson.test.JenkinsRule;
import org.jvnet.hudson.test.junit.jupiter.WithJenkins;

@WithJenkins
public class ExclusionTest {

    @Test
    public void testGetFqcn(JenkinsRule jenkinsRule) {
        String fqcn = "hudson.testfqcn";
        String context = "testContext";
        Exclusion exclusion = new Exclusion(fqcn, context);

        String result = exclusion.getFqcn();

        assertEquals(fqcn, result);
    }

    @Test
    public void testGetContext(JenkinsRule jenkinsRule) {
        String fqcn = "hudson.testfqcn";
        String context = "testContext";
        Exclusion exclusion = new Exclusion(fqcn, context);

        String result = exclusion.getContext();

        assertEquals(context, result);
    }

    @Test
    public void testValidDescriptorWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("hudson.tasks.Maven$DescriptorImpl");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    public void testValidExtensionWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("hudson.model.AdministrativeMonitor");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    public void testValidExtensionAndDescriptorWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("jenkins.tasks.filters.EnvVarsFilterLocalRule");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    public void testInvalidClassWithSample(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("hudson.Util");
        assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }

    @Test
    public void testClassNotFound(JenkinsRule jenkinsRule) {
        Exclusion.DescriptorImpl descriptor = jenkinsRule.jenkins.getDescriptorByType(Exclusion.DescriptorImpl.class);
        FormValidation validation = descriptor.doCheckFqcn("");
        assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
}
