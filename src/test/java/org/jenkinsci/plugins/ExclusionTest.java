package org.jenkinsci.plugins;

import static org.junit.Assert.assertEquals;

import hudson.util.FormValidation;
import org.junit.Test;

public class ExclusionTest {

    private Exclusion.DescriptorImpl descriptor = new Exclusion.DescriptorImpl();

    @Test
    public void testGetFqcn() {
        String fqcn = "hudson.testfqcn";
        String context = "testContext";
        Exclusion exclusion = new Exclusion(fqcn, context);

        String result = exclusion.getFqcn();

        assertEquals(fqcn, result);
    }

    @Test
    public void testGetContext() {
        String fqcn = "hudson.testfqcn";
        String context = "testContext";
        Exclusion exclusion = new Exclusion(fqcn, context);

        String result = exclusion.getContext();

        assertEquals(context, result);
    }

    @Test
    public void testValidDescriptorWithSample() {
        FormValidation validation = descriptor.doCheckFqcn("hudson.tasks.Maven$DescriptorImpl");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    public void testValidExtensionWithSample() {
        FormValidation validation = descriptor.doCheckFqcn("hudson.model.AdministrativeMonitor");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    public void testValidExtensionAndDescriptorWithSample() {
        FormValidation validation = descriptor.doCheckFqcn("jenkins.tasks.filters.EnvVarsFilterLocalRule");
        assertEquals(FormValidation.Kind.OK, validation.kind);
    }

    @Test
    public void testInvalidClassWithSample() {
        FormValidation validation = descriptor.doCheckFqcn("hudson.Util");
        assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }

    @Test
    public void testClassNotFound() {
        FormValidation validation = descriptor.doCheckFqcn("");
        assertEquals(FormValidation.Kind.ERROR, validation.kind);
    }
}
