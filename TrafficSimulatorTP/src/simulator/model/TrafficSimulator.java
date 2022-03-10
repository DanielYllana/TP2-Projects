package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrafficSimulator implements Observable<TrafficSimObserver> {

    private RoadMap roadMap;
    private List<Event> eventsList; // sorted by time of events (use SortedArrayList)
    private int simulationTime;
    private List<TrafficSimObserver> observerList;

    public TrafficSimulator() {
        this.roadMap = new RoadMap();
        this.eventsList = new SortedArrayList<>();
        this.simulationTime = 0;

        this.observerList = new ArrayList<>();
    }

    public void addEvent(Event e) {
        if (e.getTime() > this.simulationTime) {
            this.eventsList.add(e); // should still be sorted
        }


        for (TrafficSimObserver ob: this.observerList) {
            ob.onEventAdded(this.roadMap, this.eventsList, e, this.simulationTime);
        }
    }

    public void advance() {
        this.simulationTime++;

        for (TrafficSimObserver ob: this.observerList) {
            ob.onAdvanceStart(this.roadMap, this.eventsList, this.simulationTime);
        }

        Event e;
        for(int i = 0; i < this.eventsList.size(); i++) {
            e = this.eventsList.get(i);
            if (e.getTime() == this.simulationTime) {
                e.execute(this.roadMap);
            } else if(e.getTime() < this.simulationTime) {
                break;
            }
        }

        this.eventsList.removeIf(b -> (b.getTime() == this.simulationTime));


        for(Junction j: this.roadMap.getJunctions()) {
            j.advance(this.simulationTime);
        }

        for(Road r: this.roadMap.getRoads()) {
            r.advance(this.simulationTime);
        }

        for (TrafficSimObserver ob: this.observerList) {
            ob.onAdvanceEnd(this.roadMap, this.eventsList, this.simulationTime);
        }
    }


    public void reset() {
        this.roadMap.reset();
        this.eventsList = new SortedArrayList<>();
        this.simulationTime = 0;

        for (TrafficSimObserver ob: this.observerList) {
            ob.onReset(this.roadMap, this.eventsList, this.simulationTime);
        }
    }

    public JSONObject report() {
        JSONObject jo = new JSONObject();

        jo.put("time", this.simulationTime);
        jo.put("state", this.roadMap.report());


        return jo;
    }

    @Override
    public void addObserver(TrafficSimObserver o) {
        o.onRegister(this.roadMap, this.eventsList, this.simulationTime);
        this.observerList.add(o);
    }

    @Override
    public void removeObserver(TrafficSimObserver o) {
        this.observerList.remove(o);
    }



    public int getSimTime() {
        return this.simulationTime;
    }

}
