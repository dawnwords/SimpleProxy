package cn.edu.fudan.se.proxy;

import cn.edu.fudan.se.bean.Gate;
import cn.edu.fudan.se.bean.Server;
import cn.edu.fudan.se.link.Linker;
import cn.edu.fudan.se.parser.Deparser;
import cn.edu.fudan.se.parser.Parser;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class Main {
    public static final String SERVER_URL = "http://patch.gjol.gamebar.com:8090/data/servers.xml";
    public static final int PROXY_PORT = 8651;

    public static void main(String[] args) {
        Server server = new Parser(SERVER_URL).parse();
        Deparser deparser = new Deparser(server);

        for (Gate gate : server.gates()) {
            Linker linker = gate.getBuilder().build();
            deparser.addLinker(linker);
            linker.start();
        }

        String xml = deparser.getResult();

        new ProxyServer(xml, PROXY_PORT).start();
    }
}
