package com.dstorage.p2p;

import java.io.*;
import java.net.Socket;

public class Connection extends Thread{

    private final Socket socket;
    private final Transport transport;
    private PrintWriter out;

    public Connection(Socket socket, Transport transport) {
        this.socket = socket;
        this.transport = transport;
    }

    // For the whole story about "how to properly interrupt thread"
    // see http://www.javaspecialists.eu/archive/Issue056.html
    @Override
    public void interrupt() {
        super.interrupt();
        try {
            socket.close();
        } catch(IOException e) {}
    }

    @Override
    public void run() {
        try(
                PrintWriter outRessource = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out = outRessource;
            boolean quit = false;
            while(!quit) {
                String msg = in.readLine();
                quit = parseMessage(msg);
            }
        } catch(InterruptedIOException e) {
            Thread.currentThread().interrupt(); // see interrupt() above.
        } catch (IOException io) {
            System.err.println(io);
        }
    }

    // Returns true if it was the last message of the client.
    public boolean parseMessage(String msg) {
        if(msg == null) {
            return true;
        }
        System.out.println("Message : "+msg);
        transport.broadcastMsg(msg);
        if(msg.equals("\\shutdown")) {
            transport.stop();
            return true;
        }
        else if(msg.equals("\\quit")) {
            transport.clientQuits(this);
            return true;
        }
        return false;
    }

    // Note: there are no troubles if `out` is closed.
    // The data will just not be sent.
    public void send(String msg) {
        if(out != null) {
            out.println(msg);
        }
    }
}
