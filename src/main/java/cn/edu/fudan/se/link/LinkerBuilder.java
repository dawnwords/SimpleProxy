package cn.edu.fudan.se.link;

import cn.edu.fudan.se.bean.Type;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class LinkerBuilder {
    private static AtomicInteger LISTEN_PORT = new AtomicInteger(1245);

    String serverIp;
    int serverPort;
    Type type;
    int status;
    int listenPort;

    public LinkerBuilder() {
        this.listenPort = LISTEN_PORT.getAndIncrement();
    }

    public void serverIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public void serverPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void type(Type type) {
        this.type = type;
    }

    public void status(int status) {
        this.status = status;
    }

    public Linker build() {
        Linker linker = null;
        try {
            linker = type.linker().getDeclaredConstructor(LinkerBuilder.class).newInstance(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linker;
    }
}
