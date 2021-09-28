package utils;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;

import core.Globals;

public class HostInteractor extends HostModule {
    private Host host;
    private final HashMap<String, Boolean> connected;
    private final Serializer serializer = new Serializer();

    public HostInteractor(Host host) {
        super();
        this.host = host;
        this.connected = new HashMap<String, Boolean>();
    }

    public void activate() throws IOException {
        this.changeActive();
        while(this.isActive()) {
            // Incoming packet
            byte[] buffer = new byte[this.host.getBufferSize()];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            this.host.getSocket().receive(packet);
            packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
            Request clientRequest = serializer.deserialize(packet.getData());
            if(clientRequest == null) {
                System.out.printf("[ERROR]: %s was trying to establish connection, but they have an older version of Request\n", packet.getAddress().toString());
                continue;
            }
            clientRequest.setAuthor(packet.getAddress().toString());
            if(this.connected.get(packet.getAddress().toString()) == null) {
                if(!clientRequest.getCommand().trim().equals("@connect")) continue;
                this.connected.put(packet.getAddress().toString(), true);
                System.out.printf("\n[SERVER]: New connection was established (by %s)\n", packet.getAddress().toString());
                sendInitPacket(packet.getAddress(), packet.getPort());
                continue;
            } else {
                if(clientRequest.getCommand().trim().equals("@connect")) {
                    System.out.printf("\n[SERVER]: %s lost connection and reconnected again\n", packet.getAddress());
                    sendInitPacket(packet.getAddress(), packet.getPort());
                    continue;
                }
                if(clientRequest.getCommand().trim().equals("exit")) {
                    this.connected.remove(packet.getAddress().toString());
                    System.out.printf("\n[SERVER]: %s disconnected\n", packet.getAddress());
                } else {
                    System.out.printf("[%s]: %s\n", packet.getAddress(), clientRequest.getMessage());
                    String requestResult = this.host.getConsoler().doHostRequest(clientRequest);
                    System.out.println(requestResult);
                    clientRequest.reset();
                    clientRequest.changeMessage(requestResult);
                    buffer = serializer.serialize(clientRequest);
                    packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
                    this.host.getSocket().send(packet);
                }
            }
            //Outcoming package
        }
    }

    private boolean sendInitPacket(InetAddress address, int port) {
        try {
            byte[] initBuffer = serializer.serialize(new Request(this.host.getCCM().getCommands(), Globals.getRoutesCreated()));
            DatagramPacket initPacket = new DatagramPacket(initBuffer, initBuffer.length, address, port);
            this.host.getSocket().send(initPacket);
            return true;
        } catch(NullPointerException e) {
            System.out.println("NullPointerException @ sendInitPacket");
            return false; 
        } catch (IOException e) {
            return false;
        }
    }

    public void deactivate() {
        this.changeActive();
    }
}
