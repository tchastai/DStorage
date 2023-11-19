package com.dstorage;

import com.dstorage.p2p.Transport;

public class Main {
    public static void main(String[] args) {
        Transport transport = new Transport();
        transport.run();
    }
}