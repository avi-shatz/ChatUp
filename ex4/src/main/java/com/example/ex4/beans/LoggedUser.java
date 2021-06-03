package com.example.ex4.beans;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class LoggedUser{

    private static final int TIME_OUT_MS = 3000;

    private String userName;
    private Date LastConnectAt;

    public LoggedUser(String userName) {
        this.userName = userName;
        this.LastConnectAt = new Date();
    }

    public LoggedUser() {}

    public String getUserName() {
        return userName;
    }

    public boolean isConnected() {
        long currentTime = (new Date()).getTime();

        if(currentTime - LastConnectAt.getTime() >= TIME_OUT_MS) {
            return false;
        }

        return true;
    }

    public void updateConnectionTime() {
        this.LastConnectAt = new Date();
    }
}