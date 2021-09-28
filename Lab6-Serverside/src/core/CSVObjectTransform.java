package core;
import misc.Coordinates;
import misc.Location;
import misc.Route;

import java.util.Arrays;

class CSVObjectTransform {
    /**
     *
     */
    CSVObjectTransform() {}

    /**
     * @param csvline
     * @return
     */
    public Route csvLineToRoute(String csvline) {
        String[] line = csvline.split(", ");
        if(line.length != 14) return null; // Routes need 14 parameters to be generated
        long id;
        String name = line[1];
        long cordX;
        Integer cordY;
        java.time.ZonedDateTime creationDate;
        String firstLocationName = line[5], secondLocationName = line[9];
        Double firstLocationX, secondLocationX;
        int firstLocationY, secondLocationY;
        Integer firstLocationZ, secondLocationZ;

        Double dist;

        // Check non-null variables (cordX, cordY, first & second LocationX, LocationY, LocationZ)
        try {
            id = Long.parseLong(line[0]);
            cordX = Long.parseLong(line[2]);
            cordY = Integer.parseInt(line[3]);
            creationDate = java.time.ZonedDateTime.parse(line[4]);
            firstLocationX = Double.parseDouble(line[6]);
            firstLocationY = Integer.parseInt(line[7]);
            firstLocationZ = Integer.parseInt(line[8]);
            secondLocationX = Double.parseDouble(line[10]);
            secondLocationY = Integer.parseInt(line[11]);
            secondLocationZ = Integer.parseInt(line[12]);
            // Check dist
            try {
                dist = Double.parseDouble(line[13]);
                if(dist < 1) throw new NumberFormatException();
            } catch(NumberFormatException e) {
                dist = null; // dist can be null
            }

            Coordinates cords = new Coordinates(cordX, cordY);
            Location locationOne, locationTwo;
            if(firstLocationName.equals("")) locationOne = null;
            else locationOne = new Location(firstLocationName, firstLocationX, firstLocationY, firstLocationZ);
            if(secondLocationName.equals("")) locationTwo = null;
            else locationTwo = new Location(secondLocationName, secondLocationX, secondLocationY, secondLocationZ);
            Route generated = new Route(id, name.trim(), cords, creationDate, locationOne, locationTwo, dist);
            return generated;
        } catch(NumberFormatException e) {
            return null; // cordX cannot be null
        }
    }

    /**
     * @param route
     * @return
     */
    public String routeToCSVLine(Route route) {
        String id, name, rx, ry,
                creationDate, locationOneName,
                lOneX, lOneY, lOneZ,
                locationTwoName, lTwoX, lTwoY,
                lTwoZ, distance;
        id = String.valueOf(route.getID());
        name = route.getName();
        rx = String.valueOf(route.getCoordinates().getX());
        ry = String.valueOf(route.getCoordinates().getY());
        creationDate = route.getCreationDate().toString();

        // Start location
        if(route.getStartLocation() == null) {
            locationOneName = "";
            lOneX = "0";
            lOneY = "0";
            lOneZ = "0";
        } else {
            locationOneName = route.getStartLocation().getName();
            lOneX = String.valueOf(route.getStartLocation().getX());
            lOneY = String.valueOf(route.getStartLocation().getY());
            lOneZ = String.valueOf(route.getStartLocation().getZ());
        }

        // End location
        if(route.getEndLocation() == null) {
            locationTwoName = "";
            lTwoX = "0";
            lTwoY = "0";
            lTwoZ = "0";
        } else {
            locationTwoName = route.getEndLocation().getName();
            lTwoX = String.valueOf(route.getEndLocation().getX());
            lTwoY = String.valueOf(route.getEndLocation().getY());
            lTwoZ = String.valueOf(route.getEndLocation().getZ());
        }

        if(route.getDistance() == null) distance = "ZERO";
        else distance = String.valueOf(route.getDistance());

        String[] csvline = {id, name, rx, ry, creationDate, locationOneName, lOneX, lOneY, lOneZ, locationTwoName, lTwoX, lTwoY, lTwoZ, distance};
        return String.join(", ", Arrays.asList(csvline));
    }
}
