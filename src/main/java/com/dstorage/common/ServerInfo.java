package com.dstorage.common;

public class ServerInfo {
    public int port;
    public String ip;

    public ServerInfo(int port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public String info() {
        return this.ip + ":" + this.port;
    }
}
