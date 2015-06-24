package cn.edu.fudan.se.link;

import cn.edu.fudan.se.log.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by Dawnwords on 2015/6/24.
 */
public class UDPLinker extends Linker {
    private DatagramSocket toServer, fromClient;
    private int serverPort;
    private InetAddress serverIp;
    private byte[] buffer = new byte[4 * 1024 * 1024];
    private Logger logger;

    public UDPLinker(LinkerBuilder builder) {
        super(builder);
        try {
            this.serverIp = InetAddress.getByName(builder.serverIp);
            this.toServer = new DatagramSocket();
            this.fromClient = new DatagramSocket(builder.listenPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.serverPort = builder.serverPort;
        this.logger = new Logger("UDP:" + serverIp);
    }

    @Override
    public void start() {
        try {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                fromClient.receive(packet);
                String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), "utf-8");
                logger.info(String.format("%s:%d=%s", packet.getAddress(), packet.getPort(), s));
                toServer.send(new DatagramPacket(packet.getData(), packet.getOffset(), packet.getLength(), serverIp, serverPort));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
