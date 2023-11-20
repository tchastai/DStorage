package com.dstorage;

import com.dstorage.p2p.Transport;
import com.dstorage.server.FileServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static void makeServer(Integer listenAddress, List<Integer> bootstrapNodes) throws Exception {
        Transport transport = new Transport(listenAddress, bootstrapNodes);
        FileServer fileServer = new FileServer("test", transport);
        fileServer.start();
    }
    public static void main(String[] args) throws Exception {
        List<Integer> nodeServerOne = new ArrayList<>();
        makeServer(3000, nodeServerOne);
    }
}