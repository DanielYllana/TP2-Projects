package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.Comparator;
import java.util.List;

public class TrafficSimulator {

    private RoadMap roadMap;
    private List<Event> eventsList; // sorted by time of events (use SortedArrayList)
    private int simulationTime;

    public TrafficSimulator() {
        this.roadMap = new RoadMap();
        this.eventsList = new SortedArrayList<>();
        this.simulationTime = 0;
    }

    public void addEvent(Event e) {

        this.eventsList.add(e); // should still be sorted
        this.eventsList.sort(Comparator.comparingInt(Event::getTime));
    }

    public void advance() {
        this.simulationTime++;


        for(Event e: this.eventsList) {
            if (e.getTime() == this.simulationTime) {
                e.execute(this.roadMap);
            }
        }

        this.eventsList.removeIf(b -> (b.getTime() == this.simulationTime));


        for(Junction j: this.roadMap.getJunctions()) {
            j.advance(this.simulationTime);
        }

        for(Road r: this.roadMap.getRoads()) {
            r.advance(this.simulationTime);
        }
    }


    public void reset() {
        this.roadMap.reset();
        this.eventsList = new SortedArrayList<>();
        this.simulationTime = 0;
    }

    public JSONObject report() {
        JSONObject jo = new JSONObject();

        jo.put("time", this.simulationTime);
        jo.put("state", this.roadMap.report());


        return jo;
    }
}
