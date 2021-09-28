package commands;
import core.ConsolerMode;
import misc.VectorCore;

public class RemoveLast extends AbCommand {
    private final VectorCore vector;

    /**
     * @param vector
     */
    public RemoveLast(VectorCore vector) {
        super("remove_last", "Removes the last element of the collection", "remove_last", false, false, new String[0]);
        this.vector = vector;
    }

    /**
     * @param args
     * @param mode
     */
    @Override
    public String execute(String[] args, ConsolerMode mode) {
        if(this.vector.getSize() >= 1) {
            this.vector.removeElement(this.vector.getSize()-1);
            return "The last element of the collection was successfully removed";
        }
        return "Cannot remove the last element of the collection because the collection is empty";
    }
}
