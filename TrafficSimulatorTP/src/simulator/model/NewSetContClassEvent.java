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
                // TODO
                // Throw exception if vehicle does not exist in road-map
            }
            v.setContaminationClass(c.getSecond());
        }
    }
}
