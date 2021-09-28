package utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.HashMap;

public class HostReciever extends HostModule {
    private Host host;
    private HashMap<String, Boolean> connected;

    public HostReciever(Host host) {
        super();
        this.host = host;
        this.connected = new HashMap<String, Boolean>();
    }

    public void activate() throws IOException{
        this.changeActive();
        while(this.isActive()) {
            byte[] buffer = new byte[this.host.getBufferSize()];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            this.host.getSocket().receive(packet);
            packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(), packet.getPort());
            if(this.connected.get(packet.getAddress().toString()) == null) {
                this.connected.put(packet.getAddress().toString(), true);
                System.out.printf("\n[SERVER]: New connection was established (by %s)\n", packet.getAddress().toString());
            } else {
                String received = new String(packet.getData(), 0, packet.getLength());
                if(received.startsWith("@connect")) {
                    System.out.printf("\n[SERVER]: %s lost connection and reconnected again\n", packet.getAddress());
                    continue;
                }
                if(received.startsWith("exit")) {
                    this.connected.remove(packet.getAddress().toString());
                    System.out.printf("\n[SERVER]: %s disconnected\n", packet.getAddress());
                } else System.out.printf("[%s]: %s\n", packet.getAddress(), received);
            }
        }
    }

    public void deactivate() {
        this.changeActive();
    }
}
