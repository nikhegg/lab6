package utils;

import java.io.IOException;

public class HostSender extends HostModule {
    private Host host;

    public HostSender(Host host) {
        super();
        this.host = host;
    }

    public void activate() throws IOException {

    }

    public void deactivate() {
        
    }

    public Host getHost() {
        return this.host;
    }
}
