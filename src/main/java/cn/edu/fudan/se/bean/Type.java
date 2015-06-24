package cn.edu.fudan.se.bean;

import cn.edu.fudan.se.link.Linker;
import cn.edu.fudan.se.link.TCPLinker;
import cn.edu.fudan.se.link.UDPLinker;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public enum Type {
    TCP("ct", TCPLinker.class), UDP("cu", UDPLinker.class), UNKNOWN("unknown", null);

    String type;
    Class<? extends Linker> linker;

    Type(String val, Class<? extends Linker> linker) {
        this.type = val;
        this.linker = linker;
    }

    public static Type toType(String s) {
        for (Type type : values()) {
            if (type.type.equals(s)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public String type() {
        return type;
    }

    public Class<? extends Linker> linker() {
        return linker;
    }
}