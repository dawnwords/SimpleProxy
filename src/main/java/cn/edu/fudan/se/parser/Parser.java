package cn.edu.fudan.se.parser;

import cn.edu.fudan.se.bean.Gate;
import cn.edu.fudan.se.bean.Server;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class Parser {
    private String url;

    public Parser(String url) {
        this.url = url;
    }

    public Server parse() {
        Server result = null;
        Element root = getRoot(url);
        if (root != null) {
            NodeList serverNodes = root.getElementsByTagName("server");
            if (serverNodes.getLength() > 1) {
                Node serverNode = serverNodes.item(0);
                result = new Server(attr(serverNode, "name"), attr(serverNode, "status"), parseGates(serverNode));
            }
        }
        return result;
    }

    private Element getRoot(String url) {
        try {
            URLConnection connection = new URL(url).openConnection();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(connection.getInputStream());
            return doc.getDocumentElement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Gate> parseGates(Node serverNode) {
        List<Gate> gates = new ArrayList<Gate>();
        NodeList gateNodes = serverNode.getChildNodes();
        for (int j = 0; j < gateNodes.getLength(); j++) {
            Node gate = gateNodes.item(j);
            gates.add(new Gate(attr(gate, "type"), attr(gate, "addr"), attr(gate, "status")));
        }
        return gates;
    }

    private String attr(Node node, String attr) {
        return node.getAttributes().getNamedItem(attr).getNodeValue();
    }
}
