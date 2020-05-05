package reactive.tutorial.example;

import io.reactivex.Observable;

public class Launcher2 {
    public static void main(String[] args) {
        DroneBot droneBot = null; //create DroneBot

        droneBot.getLocation()
                .subscribe(loc ->
                        System.out.println("Drone moved to " + loc.x + "," + loc.y));
    }

    interface DroneBot {
        int getId();
        String getModel();
        Observable<Location> getLocation();
    }

    static final class Location {
        private final double x;
        private final double y;
        Location(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}