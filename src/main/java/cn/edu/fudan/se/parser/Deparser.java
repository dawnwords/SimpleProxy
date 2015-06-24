package cn.edu.fudan.se.parser;

import cn.edu.fudan.se.bean.Server;
import cn.edu.fudan.se.link.Linker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class Deparser {

    private Document document;
    private Element serverNode;

    public Deparser(Server server) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = builder.newDocument();
            Element root = document.createElement("root");
            document.appendChild(root);
            serverNode = document.createElement("server");
            serverNode.setAttribute("name", server.name());
            serverNode.setAttribute("status", String.valueOf(server.status()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLinker(Linker linker) {
        if (document == null || serverNode == null) {
            throw new IllegalStateException("document creation fails or have got the result");
        }
        Element gateNode = document.createElement("gate");
        gateNode.setAttribute("type", linker.type());
        gateNode.setAttribute("addr", linker.address());
        gateNode.setAttribute("status", String.valueOf(linker.status()));
        serverNode.appendChild(gateNode);
    }

    public String getResult() {
        String result = document2String(document);
        document = null;
        serverNode = null;
        return result;
    }

    private String document2String(Document document) {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult sr = new StreamResult(writer);
            transformer.transform(new DOMSource(document), sr);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
