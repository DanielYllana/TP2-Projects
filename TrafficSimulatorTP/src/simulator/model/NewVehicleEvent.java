package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
    private String id;
    private int maxSpeed, contClass;
    private List<String> itinerary;

    public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> _itinerary) {
        super(time);
        this.id = id;
        this.maxSpeed = maxSpeed;
        this.contClass = contClass;
        this.itinerary = _itinerary;
    }

    @Override
    void execute(RoadMap map) {

        List<Junction> vehicleItinerary = new ArrayList<>();
        for (String s: this.itinerary) {
            Junction j = map.getJunction(s);
            if (j != null) {
                vehicleItinerary.add(j);
            }
        }

        Vehicle v = new Vehicle(this.id, this.maxSpeed, this.contClass, vehicleItinerary);
        v.moveToNextRoad();
        map.addVehicle(v);

    }


    @Override
    public String toString() {
        return "New Vehicle '" + this.id + "i";
    }
}
