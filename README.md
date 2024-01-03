# Extension Filter Plugin

![Build](https://ci.jenkins.io/job/Plugins/job/extension-filter-plugin/job/main/badge/icon)
[![Coverage](https://ci.jenkins.io/job/Plugins/job/extension-filter-plugin/job/main/badge/icon?status=${instructionCoverage}&subject=coverage&color=${colorInstructionCoverage})](https://ci.jenkins.io/job/Plugins/job/extension-filter-plugin/job/main)
[![LOC](https://ci.jenkins.io/job/Plugins/job/extension-filter-plugin/job/main/badge/icon?job=test&status=${lineOfCode}&subject=line%20of%20code&color=blue)](https://ci.jenkins.io/job/Plugins/job/extension-filter-plugin/job/main)
![Contributors](https://img.shields.io/github/contributors/jenkinsci/extension-filter-plugin.svg?color=blue)
[![GitHub release](https://img.shields.io/github/release/jenkinsci/extension-filter-plugin.svg?label=changelog)](https://github.com/jenkinsci/extension-filter-plugin/releases/latest)
[![Jenkins Plugin Installs](https://img.shields.io/jenkins/plugin/i/extension-filter.svg?color=blue)](https://plugins.jenkins.io/extension-filter)
[![GitHub license](https://img.shields.io/github/license/jenkinsci/extension-filter-plugin)](https://github.com/jenkinsci/extension-filter-plugin/blob/main/LICENSE.md)

This plugin allows to filter features available on a Jenkins instance by
disabling some Extensions/Descriptors.

## About

Jenkins has a large set of plugin to contribute
[extensions](https://wiki.jenkins.io/display/JENKINS/Extension+points).
Sometime such plugin comes with some extra feature you don't want to use
/ have issue with your particular installation, or you consider too
"advanced" for users to let them configure.

This plugin let you filter Extension and Descriptors enabled on your
instance. It can be used for example as a workaround for
[JENKINS-15440](https://issues.jenkins-ci.org/browse/JENKINS-15440) to
disable an extension that has bad performance impact on your Jenkins
master. It can also be used to hide global configuration for some
feature that you don't want users, even with the "administer" privilege,
to edit.

## Usage

Plugin let you define Extensions to be excluded from Jenkins. You just
have to configure the extension class to be excluded. Please note this
won't prevent the extension to load, just won't contribute Jenkins at
runtime. For
[JENKINS-15440](https://issues.jenkins-ci.org/browse/JENKINS-15440) you
would exclude `hudson.scm.SubversionMailAddressResolverImpl` so that
this extension is disabled on your instance and won't impact
performances.

Descriptors can be excluded with some fine-grained contextual filtering
from the UI. A context class can be set and will define when descriptor
should be excluded and then won't render the related web UI. Let's say
for example you like Maven job type so much that you want to deprecate
Maven build step for freestyle jobs (hey, this is just a sample). Then
exclude `hudson.tasks.Maven$DescriptorImpl` with context
`hudson.model.FreeStyleProject`. This will filter Maven from availabel
build step on Freestyle projects.

Let's say you don't want users to manage Maven installations because
there is no reason to use anything but latest maven 3.0.4, isn't it ?
Then exclude `hudson.tasks.Maven$MavenInstallation$DescriptorImpl` with
context `jenkins.model.Jenkins`. This will disable the global
configuration UI for MavenInstallations.

## Changelog

### 1.0 (25 oct. 2012)

-   initial implementation
