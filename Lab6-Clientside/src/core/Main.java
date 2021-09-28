package core;
import utils.Connector;

public class Main {
    private static final String defaultAddress = "localhost";
    private static final int defaultPort = 2228;
    private static final long maxPing = 999;
    private static final int bufferSize = 8192;

    public static void main(String[] args) {
        Connector connector = new Connector(defaultAddress, defaultPort, maxPing, bufferSize);
        connector.activate();
    }
}