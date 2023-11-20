package com.dstorage.server;

import com.dstorage.p2p.Transport;

public class FileServer {
    private String id;
    private String storageRoot;
    private Transport transport;

    public FileServer(String storageRoot, Transport transport) throws Exception {
        this.id = Crypto.generateID();
        this.storageRoot = storageRoot;
        this.transport = transport;
    }

    public void start() {
        this.transport.run();
    }
}
