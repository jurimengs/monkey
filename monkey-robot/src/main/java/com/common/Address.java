package com.common;

public class Address {
    private String host;
    private int port;

    public Address() {
    }
    
    public Address(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public int hashCode() {
        return String.valueOf(host + port).hashCode();
    }

}
