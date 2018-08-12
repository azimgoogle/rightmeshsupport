package com.rightmesh.support;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.apache.http.util.TextUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyGenerator extends DefaultTask {

    @TaskAction
    void generateProperty() throws Exception {

        String localPropertiesFile = "local.properties";
        Properties localProperties = new Properties();
        try {
            localProperties.load(new FileInputStream(getProject().getRootProject().file("local.properties")));
        } catch (IOException e) {
            throw new FileNotFoundException(String.format("%s file not found", localProperties));
        }

        String userName = localProperties.getProperty("USER_NAME");

        if(TextUtils.isEmpty(userName)) {
            throw new Exception(String.format("Specify your user name in %s file", localPropertiesFile) );
        }
    }
}