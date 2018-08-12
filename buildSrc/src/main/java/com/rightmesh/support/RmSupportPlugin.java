package com.rightmesh.support;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class RmSupportPlugin implements Plugin<Project> {

    public void apply(Project project) {
        project.getTasks().create("generateProperty", PropertyGenerator.class);
    }

}