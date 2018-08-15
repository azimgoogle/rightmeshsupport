package com.rightmesh.support;

import com.rightmesh.support.exceptions.CredentialNotFoundException;
import com.rightmesh.support.models.Credentials;
import com.rightmesh.support.utils.Constants;
import com.rightmesh.support.utils.TextUtil;
import org.gradle.api.DefaultTask;
import org.gradle.api.internal.plugins.DefaultExtraPropertiesExtension;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.org.apache.maven.model.Extension;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyGenerator extends DefaultTask {

    private final String LOCAL_PROPERTY_FILE_NAME = "local.properties";
    private final String GLOBAL_PROPERTY_FILE_NAME = "gradle.properties";

    @TaskAction
    void generateProperty() throws Exception {

        Properties localProperties = new Properties();
        Properties gradleProperties = new Properties();
        Credentials credentials;
        try {
            localProperties.load(new FileInputStream(getProject().getRootProject().file("local.properties")));
            gradleProperties.load(new FileInputStream(getProject().getRootProject().file("gradle.properties")));

        } catch (IOException e) {
            throw new FileNotFoundException(String.format("%s file not found", localProperties));
        }


        credentials = validateProperties(localProperties, gradleProperties);
        if(credentials == null) {
            throw new CredentialNotFoundException("Put " + Constants.Credentials.USER_NAME + ", "+
                    Constants.Credentials.PASSWORD + " inside project's " + LOCAL_PROPERTY_FILE_NAME + " file and " +
                    Constants.Credentials.APP_KEY + ", " + Constants.Credentials.PORT + " inside project's " +
                    GLOBAL_PROPERTY_FILE_NAME + " File.");

        } else {//Valid credentials existrm_port_number

            DefaultExtraPropertiesExtension extens = (DefaultExtraPropertiesExtension) getProject().getExtensions().getByName("ext");

            extens.set("port_number", credentials.portNumber);

            extens.set("artifactory_app_username", credentials.userName);
            extens.set("artifactory_app_password", credentials.password);
            extens.set("artifactory_app_key", credentials.appKey);

            System.out.println("Retrieved Property:kotlin_version::"+extens);
            System.out.println("Retrieved Property:kotlin_version::"+extens.getProperties());
            System.out.println("Retrieved Property:kotlin_version::"+extens.getProperties().get("kotlin_version"));

        }
    }

    private Credentials validateProperties(Properties localProperties, Properties gradleProperties) {

        String userName = localProperties.getProperty(Constants.Credentials.USER_NAME);
        String password = localProperties.getProperty(Constants.Credentials.PASSWORD);

        String appKey = gradleProperties.getProperty(Constants.Credentials.APP_KEY);
        int portNumber = Integer.parseInt(gradleProperties.getProperty(Constants.Credentials.PORT));

        if(TextUtil.isEmpty(userName, password, appKey)) {
            return null;
        }

        return new Credentials(userName, password, appKey, portNumber);
    }
}