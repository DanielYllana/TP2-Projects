package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class VehicleEvent extends Event {


    public VehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> _itinerary) {
        super(time);
        // TODO
        // FINISH THIS (have to create the vehicle and the itinerary from the ids)
    }

    @Override
    void execute(RoadMap map) {
        // TODO
        // map.addVehicle(vehicle);
    }
}
