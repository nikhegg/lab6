package utils;
import java.io.IOException;
import java.io.Serializable;
import java.lang.Thread;
import java.net.DatagramSocket;
import core.Consoler;
import misc.VectorCore;

public class Host extends Thread implements Serializable {
    private int port;
    private int bufferSize;
    private DatagramSocket socket;
    private HostInteractor interactor;
    private ClientCommandManager ccm;
    // private HostReciever reciever; // For Lab7 - requires another Thread
    // private HostSender sender; // For Lab7 - requires another Thread
    private VectorCore collection;
    private Consoler consoler;
    
    public Host(int port, int bufferSize, VectorCore collection) {
        this.port = port;
        this.bufferSize = bufferSize;
        this.interactor = new HostInteractor(this);
        //this.reciever = new HostReciever(this); // For Lab7
        //this.sender = new HostSender(this); // For Lab7
        this.collection = collection;
        this.consoler = null;
    }

    public void run() {
        try {
            this.socket = new DatagramSocket(port);
            System.out.printf("Started server on the port %s\n", port);
            this.interactor.activate();
            //this.reciever.activate(); // For Lab7
            //this.sender.activate(); // For Lab7
        } catch (IOException e) {
            if(this.socket.isClosed()) return;
            System.out.printf("Server is already started on this port: %s\n", port);
            return;
        }
    }

    public void disable() {
        this.interactor.deactivate();
        this.socket.close();
        System.out.println("Server is offline now");
    }

    public int getPort() {
        return this.port;
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public DatagramSocket getSocket() {
        return this.socket;
    }

    public void bindConsoler(Consoler consoler) {
        this.consoler = consoler;
        this.ccm = new ClientCommandManager(this.consoler.getCommands());
    }

    public Consoler getConsoler() {
        return this.consoler;
    }

    public VectorCore getCollection() {
        return this.collection;
    }

    public HostInteractor getInteractor() {
        return this.interactor;
    }

    public ClientCommandManager getCCM() {
        return this.ccm;
    }

    /*public HostReciever getReciever() {
        return this.reciever;
    }

    public HostSender getSender() {
        return this.sender;
    }*/ // For Lab7
}
