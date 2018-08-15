package com.rightmesh.support.models;

public class Credentials {

    public Credentials(String userName, String password, String appKey, int portNumber) {
        this.userName = userName;
        this.password = password;
        this.appKey = appKey;
        this.portNumber = portNumber;
    }

    public String userName, password, appKey;
    public int portNumber;

}
