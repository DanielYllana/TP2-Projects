package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Junction extends SimulatedObject{

    private List<Road> incomingRoad;
    private Map<Junction,Road> outgoingRoad;
    private List<List<Vehicle>> listQueues;
    private Map<Road,List<Vehicle>> roadQueueMap;
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

    }


    void addIncomingRoad(Road r) {
        if (!r.getDestJunction().getId().equals(this._id)) {
            throw new IllegalArgumentException("Road and junction don't match");
        }
        this.incomingRoad.add(r);
        // add to queue
        // add to road-queue map
    }

    void addOutGoingRoad(Road r) {
        if (!r.getSrcJunction().getId().equals(this._id) ||
                this.outgoingRoad.containsKey(r.getSrcJunction())
            ) {
            throw new IllegalArgumentException("Invalid outgoing road");
        }
        this.outgoingRoad.put(r.getDestJunction(), r);
    }

    void enter(Vehicle v) {

    }

    Road roadTo(Junction j) {
        return this.outgoingRoad.get(j);
    }

    void advance(int time) {

    }

    public JSONObject report() {
        JSONObject jo = new JSONObject();
        jo.put("id", this._id);

        if (this.greenLightIdx == -1) {
            jo.put("green", "none");
        } else {
            jo.put("green", this.incomingRoad.get(this.greenLightIdx).getId());
        }
        JSONArray ja = new JSONArray();
        for(List<Vehicle> vehicles: listQueues) {
            JSONObject jo2 = new JSONObject();
            jo2.put("road", vehicles.get(0).getRoad().getId());


            JSONArray ja2 = new JSONArray();
            for (Vehicle v: vehicles) {
                ja2.put(v._id);
            }

            jo.put("vehicles", ja2);
        }
        jo.put("queues", ja);

        return jo;
    }




}
