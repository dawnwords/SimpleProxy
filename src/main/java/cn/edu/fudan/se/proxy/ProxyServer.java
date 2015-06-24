package cn.edu.fudan.se.proxy;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class ProxyServer extends Thread {

    private String xml;
    private ServerSocket serverSocket;
    private Date lastModify;

    private static final int TEN_MINUTE = 60 * 10 * 1000;

    public ProxyServer(String xml, int port) {
        this.xml = xml;
        this.lastModify = new Date();
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                new Worker(serverSocket.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class Worker extends Thread {
        private Socket client;

        public Worker(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                Date current = new Date();

                out.write("HTTP/1.0 200 OK\r\n");
                out.write(String.format("Date: %tc\r\n", current));
                out.write("Server: GJOL Proxy/0.2.0\r\n");
                out.write("Content-Type: text/xml\r\n");
                out.write(String.format("Content-Length: %d\r\n", xml.getBytes().length));
                out.write(String.format("Expires: %tc\r\n", new Date(current.getTime() + TEN_MINUTE)));
                out.write(String.format("Last-modified: %tc\r\n", lastModify));
                out.write("\r\n");
                out.write(xml);
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(out);
                close(client);
            }
        }

        private void close(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
