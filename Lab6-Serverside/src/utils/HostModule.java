package utils;

import java.io.IOException;

abstract class HostModule {
    private boolean active;

    public HostModule() {
        this.active = false;
    }

    public void activate() throws IOException {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void changeActive() {
        this.active = !this.active;
    }

    public boolean isActive() {
        return this.active;
    }

}
