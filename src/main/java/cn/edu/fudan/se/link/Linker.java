package cn.edu.fudan.se.link;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public abstract class Linker {
    private static final String LOCAL_IP = "10.131.253.211";

    private LinkerBuilder builder;

    public Linker(LinkerBuilder builder) {
        this.builder = builder;
    }

    public abstract void start();

    public final String address() {
        return LOCAL_IP + ":" + builder.listenPort;
    }

    public final int status() {
        return builder.status;
    }

    public final String type() {
        return builder.type.type();
    }
}
