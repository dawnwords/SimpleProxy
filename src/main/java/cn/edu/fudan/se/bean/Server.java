package cn.edu.fudan.se.bean;

import java.util.List;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class Server {
    private String name;
    private int status;
    private List<Gate> gates;

    public Server(String name, String status, List<Gate> gates) {
        this.name = name;
        this.gates = gates;
        this.status = Integer.parseInt(status);
    }

    public String name() {
        return name;
    }

    public int status() {
        return status;
    }

    public List<Gate> gates() {
        return gates;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", gates=" + gates +
                '}';
    }
}
