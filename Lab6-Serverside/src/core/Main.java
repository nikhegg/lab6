package core;
import commands.*;
import misc.VectorCore;
import utils.Host;

public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        VectorCore collection = new VectorCore();
        Commander commander = new Commander(
                new AddElement(collection),
                new AddIfMin(collection),
                new Clear(collection),
                new CountLessThanDistance(collection),
                new FilterByDistance(collection),
                new Help(),
                new Info(collection),
                new MinByDistance(collection),
                new RemoveByID(collection),
                new RemoveLast(collection),
                new Save(collection),
                new Show(collection),
                new Sort(collection),
                new Update(collection));
        FileOperator fileOperator = new FileOperator();
        fileOperator.loadCSVCollection(collection);
        Host host = new Host(2228, 6128, collection);
        Consoler consoler = new Consoler(commander, host);
        host.start();
        consoler.start();
        fileOperator.saveCSVCollection(collection);
    }
}