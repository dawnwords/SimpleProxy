package cn.edu.fudan.se.link;

import cn.edu.fudan.se.log.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class TCPLinker extends Linker {
    private ServerSocket ss;
    private Socket cs;
    private Logger logger;

    public TCPLinker(LinkerBuilder builder) {
        super(builder);
        try {
            ss = new ServerSocket(builder.listenPort);
            cs = new Socket(builder.serverIp, builder.serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger = new Logger("TCP:" + builder.serverIp);
    }

    @Override
    public void start() {
        try {
            Socket client = ss.accept();
            logger.info(client.getRemoteSocketAddress().toString() + " linked");
            new Sender(client, cs).start();
            new Receiver(cs, client).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Sender extends Messager {
        public Sender(Socket in, Socket out) {
            super(in, out, "send");
        }
    }

    private class Receiver extends Messager {
        public Receiver(Socket in, Socket out) {
            super(in, out, "recv");
        }
    }

    private class Messager extends Thread {
        private Socket in, out;
        private String tag;

        public Messager(Socket in, Socket out, String tag) {
            this.in = in;
            this.out = out;
            this.tag = tag;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            BufferedWriter writer = null;
            String line;
            try {
                reader = new BufferedReader(new InputStreamReader(in.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(out.getOutputStream()));
                while ((line = reader.readLine()) != null) {
                    logger.info(tag + ":" + line);
                    writer.write(line + "\n");
                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(reader);
                close(writer);
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
