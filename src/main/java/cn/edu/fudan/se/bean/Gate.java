package cn.edu.fudan.se.bean;

import cn.edu.fudan.se.link.LinkerBuilder;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class Gate {

    private String addr;
    private Type type;
    private int status;

    public Gate(String type, String addr, String status) {
        this.addr = addr;
        this.type = Type.toType(type);
        this.status = Integer.parseInt(status);
    }

    public LinkerBuilder getBuilder() {
        if (type == null) {
            throw new RuntimeException("unknown linker type");
        }
        String[] addrtokens = addr.split(":");

        LinkerBuilder builder = new LinkerBuilder();
        builder.serverIp(addrtokens[0]);
        builder.serverPort(Integer.parseInt(addrtokens[1]));
        builder.status(status);
        builder.type(type);
        return builder;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "addr='" + addr + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}