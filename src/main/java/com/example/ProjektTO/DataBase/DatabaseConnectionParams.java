package com.example.ProjektTO.DataBase;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseConnectionParams {

    @JsonProperty("ipAddress")
    private String ip;
    @JsonProperty("port")
    private String port;
    @JsonProperty("login")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("databaseName")
    private String name;

    public DatabaseConnectionParams(String ip, String port, String username, String password, String name) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.name = name;
    }



    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
// Getters and setters
}
