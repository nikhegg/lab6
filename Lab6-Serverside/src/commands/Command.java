package commands;

import core.ConsolerMode;
import exceptions.NotOverriddenException;

public interface Command {
    /**
     * @param args
     * @param mode
     * @throws NotOverriddenException
     */
    String execute(String[] args, ConsolerMode mode) throws NotOverriddenException;
}
