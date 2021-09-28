package misc;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Stream;

public class VectorCore implements Serializable {
    private final Vector<Route> vector;
    private final Date creationDate;

    /**
     *
     */
    public VectorCore() {
        this.vector = new Vector<>();
        this.creationDate = new Date();
    }

    /**
     * @return
     */
    public int getSize() {
        return this.vector.size();
    }

    /**
     * @return
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * @param route
     * @return
     */
    public String getElementInfo(Route route) {
        String message = "";
        message += "\nCID: " + getVector().indexOf(route) + " | Content:\n\t• Route name: " + route.getName() +
                "\n\t• ID: " + route.getID() +
                "\n\t• CollectionID: " + getVector().indexOf(route) +
                "\n\t• Coordinates: (" + route.getCoordinates().getX() + ", " + route.getCoordinates().getY() + ")" +
                "\n\t• Creation date: " + route.getCreationDate().getDayOfMonth() + "/" + route.getCreationDate().getMonthValue() + "/" + route.getCreationDate().getYear() + " " + route.getCreationDate().getHour() + ":" + route.getCreationDate().getMinute();
        if(route.getStartLocation() == null) message += "\n\t• Start location: No start location";
        else message += "\n\t• Start location: " + route.getStartLocation().getName() + " (" + route.getStartLocation().getX() + ", " + route.getStartLocation().getY() + ", " + route.getStartLocation().getZ() + ")";
        if(route.getEndLocation() == null) message += "\n\t• End location: No end location";
        else message += "\n\t• End location: " + route.getEndLocation().getName() + " (" + route.getEndLocation().getX() + ", " + route.getEndLocation().getY() + ", " + route.getEndLocation().getZ() + ")";
        if(route.getDistance() == null) message += "\n\t• Distance: Unknown\n\n";
        else message += "\n\t• Distance: " + route.getDistance() + "\n";
        return message;
    }

    /**
     * @param route
     */
    public void addElement(Route route) {
        this.vector.add(route);
        System.out.println("\nAdded new route to the collection with ID " + route.getID());
    }

    /**
     * @param k
     * @param v
     */
    public void updateID(int k, Route v) {
        this.vector.set(k ,v);
    }

    /**
     *
     */
    public void clear() {
        this.vector.clear();
    }

    /**
     * @param id
     */
    public void removeElement(int id) {
        this.vector.remove(id);
    }

    /**
     * @return
     */
    public Vector<Route> getVector() {
        return this.vector;
    }

    public Stream<Route> getSortedVector() {
        return this.vector.stream().sorted((obj1, obj2) -> obj1.getName().compareTo(obj2.getName()));
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VectorCore that = (VectorCore) o;
        return Objects.equals(vector, that.vector) && Objects.equals(creationDate, that.creationDate);
    }

    /**
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(vector, creationDate);
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "VectorCore{" +
                "vector=" + vector +
                ", creationDate=" + creationDate +
                '}';
    }
}