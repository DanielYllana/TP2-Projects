package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RoadMap {

    private List<Junction> junctionsList;
    private List<Road> roadsList;
    private List<Vehicle> vehiclesList;
    private Map<String, Junction> junctionsMap;
    private Map<String, Road> roadsMap;
    private Map<String, Vehicle> vehiclesMap;

    public RoadMap() {
        this.reset();
    }

    void addJunction(Junction j) {
        if (this.junctionsMap.containsKey(j.getId())) {
            throw new IllegalArgumentException("Junction is already in map");
        }
        this.junctionsList.add(j);
        this.junctionsMap.put(j.getId(), j);
    }

    void addRoad(Road r) {
        if (this.roadsMap.containsKey(r.getId()) ||
            !this.junctionsMap.containsKey(r.getSrcJunction().getId()) ||
            !this.junctionsMap.containsKey(r.getDestJunction().getId())
        ) {
            throw new IllegalArgumentException("Road is already in map");
        }
        this.roadsList.add(r);
        this.roadsMap.put(r.getId(), r);
    }

    void addVehicle(Vehicle v) {
        if (this.vehiclesMap.containsKey(v.getId())) {
            throw new IllegalArgumentException("Junction is already in map");
        }

        List<Junction> itinerary = v.getItinerary();
        for (int i = 0; i < itinerary.size() - 1; i++) {
            if (itinerary.get(i).roadTo(itinerary.get(i+1)) == null) {
                throw new IllegalArgumentException("Invalid vehicle itinerary");
            }
        }

        this.vehiclesList.add(v);
        this.vehiclesMap.put(v.getId(), v);
    }



    public Junction getJunction(String id) {
        return this.junctionsMap.get(id);
    }

    public Road getRoad(String id) {
        return this.roadsMap.get(id);
    }

    public Vehicle getVehicle(String id) {
        return this.vehiclesMap.get(id);
    }


    public List<Junction> getJunctions() {
        return Collections.unmodifiableList(this.junctionsList);
    }

    public List<Road> getRoads() {
        return Collections.unmodifiableList(this.roadsList);
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(this.vehiclesList);
    }


    void reset() {
        this.junctionsList = new ArrayList<>();
        this.roadsList = new ArrayList<>();
        this.vehiclesList = new ArrayList<>();

        this.junctionsMap = new HashMap<>();
        this.roadsMap = new HashMap<>();
        this.vehiclesMap = new HashMap<>();
    }

    public JSONObject report() {
        JSONObject jo = new JSONObject();

        JSONArray jaJunctions = new JSONArray();
        for (Junction j: this.junctionsList) {
            jaJunctions.put(j.report());
        }
        jo.put("junctions", jaJunctions);


        JSONArray jaRoads = new JSONArray();
        for (Junction j: this.junctionsList) {
            jaRoads.put(j.report());
        }
        jo.put("road", jaRoads);


        JSONArray jaVehicles = new JSONArray();
        for (Junction j: this.junctionsList) {
            jaVehicles.put(j.report());
        }
        jo.put("road", jaVehicles);

        return jo;
    }
}
