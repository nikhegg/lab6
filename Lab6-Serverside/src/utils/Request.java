package utils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import misc.Route;

public class Request implements Serializable {
    private String message;
    private String command;
    private String[] args;
    private long totalRoutes;
    private HashMap<String, ClientCommand> map;
    private Route route;
    private String author;

    Request(String message, long totalRoutes) {
        changeMessage(message);
        this.totalRoutes = totalRoutes;
        this.map = null;
        this.route = null;
        this.author = null;
    }

    Request(String message, long totalRoutes, Route route) {
        changeMessage(message);
        this.totalRoutes = totalRoutes;
        this.map = null;
        this.route = route;
        this.author = null;
    }

    Request(HashMap<String, ClientCommand> map, long totalRoutes) {
        changeMessage("@commands");
        this.totalRoutes = totalRoutes;
        this.map = map;
        this.author = null;
    }

    public void changeMessage(String message) {
        String[] messageArray = message.split(" ");
        this.message = message;
        this.command = messageArray[0];
        this.args = Arrays.copyOfRange(messageArray, 1, messageArray.length);
    }

    public void setRoutes(long routes) {
        this.totalRoutes = routes;
    } 

    public void setAuthor(String author) {
        this.author = author;
    }

    public void reset() {
        this.message = null;
        this.command = null;
        this.args = null;
        this.map = null;
        this.author = null;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getMessage() {
        if(this.author == null) return "Access denied: Request author is not specified";
        return this.message;
    }

    public String getCommand() {
        if(this.author == null) return "Access denied: Request author is not specified";
        return this.command;
    }

    public String[] getArgs() {
        if(this.author == null) return null;
        return this.args;
    }

    public String getFirstArg() {
        if(this.author == null) return "Access denied: Request author is not specified";
        if(this.args.length != 0) return this.args[0];
        else return null;
    }

    public long getRoutes() {
        return this.totalRoutes;
    }

    public Route getRoute() {
        return this.route;
    }
    
    public HashMap<String, ClientCommand> getMap() {
        return this.map;
    }
    
}
