package simulator.model;

import simulator.misc.Pair;

import java.util.List;

public class NewSetContClassEvent extends Event {

    private List<Pair<String, Integer>> cs;

    public NewSetContClassEvent(int time, List<Pair<String, Integer>> cs) {
        super(time);

        if (cs == null) {
            throw new IllegalArgumentException("String|Integer pair cant be null");
        }
        this.cs = cs;
    }

    @Override
    void execute(RoadMap map) {
        for (Pair<String, Integer> c: this.cs) {
            Vehicle v = map.getVehicle(c.getFirst());

            if (v == null) {
                throw new IllegalArgumentException("Vehicle does not exist in road-map");
                // Throw exception if vehicle does not exist in road-map
            }
            v.setContClass(c.getSecond());
        }
    }


    @Override
    public String toString() {
        return "Setting Contamination Class";
    }
}
