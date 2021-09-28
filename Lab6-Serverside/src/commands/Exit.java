package commands;
import core.Consoler;
import core.ConsolerMode;

public class Exit extends AbCommand {
    private final Consoler consoler;

    /**
     * @param consoler
     */
    public Exit(Consoler consoler) {
        super("exit", "Stops the program", "exit", false, true, new String[0]);
        this.consoler = consoler;
    }

    /**
     * @param args
     * @param mode
     */
    public String execute(String[] args, ConsolerMode mode) {
        System.out.println("Shutting down the program...");
        this.consoler.getHost().disable();
        this.consoler.stop();
        return "";
    }
}
