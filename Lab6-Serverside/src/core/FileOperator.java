package core;
import misc.Route;
import misc.VectorCore;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

public class FileOperator {
    private String path;
    private CSVObjectTransform csvog;

    /**
     *
     */
    public FileOperator() {
        this.path = Globals.getEnvPath();
        this.csvog = new CSVObjectTransform();
    }

    /**
     * @return
     */
    public Vector<String> readCSVFile() {
        Vector<String> lines = new Vector<>();
        if(this.path == null) {
            System.out.println("Can't find an environment variable to load the collection from file\nExiting the program...");
            System.exit(1);
        }
        File file = new File(this.path);
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()) {
                String element = scanner.nextLine();
                lines.add(element);
            }
            scanner.close();
            return lines;
        } catch(FileNotFoundException e) {
            try {
                boolean created = file.createNewFile();
                if(created) System.out.println(this.path + " was created in order to save collection");
            } catch(IOException exception) {
                System.out.println("There is a problem with your file system access");
                return null;
            }
            return null;
        }
    }

    /**
     * @param collection
     */
    public void loadCSVCollection(VectorCore collection) {
        Vector<String> routesInFile = readCSVFile();
        if(routesInFile != null) for (String fileRoute : routesInFile) {
            Route routeFromFile = this.csvog.csvLineToRoute(fileRoute);
            if (routeFromFile != null) collection.addElement(routeFromFile);
        }
    }

    /**
     * @param collection
     */
    public String saveCSVCollection(VectorCore collection) {
        try {
            String content = "";
            int count = 0;
            for (Route route : collection.getVector()) {
                if(count != 0) content += "\n";
                content += this.csvog.routeToCSVLine(route);
                count += 1;
            }
            File file = new File(this.path);
            PrintWriter pw = new PrintWriter(file);
            pw.write(content);
            pw.flush();
            pw.close();
            return "Saved " + count + " elements of collection to " + this.path;
        } catch(FileNotFoundException e) {
            return "Cannot save collection because file " + this.path + " does not exist";
        }
    }
}