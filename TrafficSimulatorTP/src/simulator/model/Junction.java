package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Junction extends SimulatedObject{

    private List<Road> incomingRoad;                                // list of road enterin this junction
    private Map<Junction,Road> outgoingRoad;                        // list of road out of this junction
    private List<List<Vehicle>> listQueues;                         // list of queues for incoming roads
    private Map<Road,List<Vehicle>> roadQueueMap;                   // map of queues for incoming roads
    private int greenLightIdx;
    private int lastSwitching;
    private LightSwitchingStrategy lightSwitchingStrat;
    private DequeuingStrategy dequeingStrat;
    private int x,y;

    Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy
            dqStrategy, int xCoord, int yCoord) {
        super(id);

        if (lsStrategy == null || dqStrategy == null) {
            throw new IllegalArgumentException("Strategies cant be null");
        }
        this.lightSwitchingStrat = lsStrategy;
        this.dequeingStrat = dqStrategy;

        if (xCoord < 0 || yCoord < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
        this.x = xCoord;
        this.y = yCoord;

        this.incomingRoad = new ArrayList<>();
        this.outgoingRoad = new HashMap<>();
        this.listQueues = new ArrayList<>();
        this.roadQueueMap = new HashMap<>();
        this.greenLightIdx = -1;
    }


    void addIncomingRoad(Road r) {
        if (!r.getDest().getId().equals(this._id)) {
            throw new IllegalArgumentException("Road and junction don't match");
        }
        this.incomingRoad.add(r);

        this.listQueues.add(new ArrayList<>());
        this.roadQueueMap.put(r, new ArrayList());

    }

    void addOutGoingRoad(Road r) {
        if (!r.getSrc().getId().equals(this._id) ||
                this.outgoingRoad.containsKey(r.getDest())
            ) {
            throw new IllegalArgumentException("Invalid outgoing road");
        }
        this.outgoingRoad.put(r.getDest(), r);
    }

    void enter(Vehicle v, Road nextRoad) {
        //Road newRoad = v.getItinerary().
        int roadIndex = this.incomingRoad.indexOf(nextRoad); // get index of road in queues

        this.listQueues.get(roadIndex).add(v);  // add vehicle to list queues

        this.roadQueueMap.replace(v.getRoad(), this.listQueues.get(roadIndex)); // add list with new vehicle to map
    }

    Road roadTo(Junction j) {
        return this.outgoingRoad.get(j);
    }

    void advance(int time) {
        // TODO CHECK
        if (this.greenLightIdx != -1) {

            for (Vehicle v : dequeingStrat.dequeue(this.listQueues.get(this.greenLightIdx))) {
                v.advance(time);
                v.moveToNextRoad();
                this.listQueues.get(this.greenLightIdx).remove(v);
            }
            this.roadQueueMap.replace(this.incomingRoad.get(this.greenLightIdx), this.listQueues.get(this.greenLightIdx));

        }

        int newGreenLightIndex = this.lightSwitchingStrat.chooseNextGreen(this.incomingRoad,
                                    this.listQueues, this.greenLightIdx, this.lastSwitching, time);

        if (newGreenLightIndex != this.greenLightIdx) {
            this.greenLightIdx = newGreenLightIndex;
            this.lastSwitching = time;
        }

    }

    public JSONObject report() {
        JSONObject jo = new JSONObject();
        jo.put("id", this._id);

        if (this.greenLightIdx == -1 || this.incomingRoad.size() == 0) {
            jo.put("green", "none");
        } else {
            jo.put("green", this.incomingRoad.get(this.greenLightIdx).getId());
        }

        JSONArray ja = new JSONArray();



        for (int i = 0; i < this.incomingRoad.size(); i++) {
            JSONObject jo2 = new JSONObject();
            jo2.put("road", this.incomingRoad.get(i).getId());

            JSONArray ja2 = new JSONArray();
            for (Vehicle v: this.listQueues.get(i)) {
                ja2.put(v._id);
            }
            jo2.put("vehicles", ja2);
            ja.put(jo2);
        }

        jo.put("queues", ja);

        return jo;
    }


}
