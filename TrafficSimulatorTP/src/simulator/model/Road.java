package simulator.model;

/*
   PENDING IN THIS IMPLEMENTATION:
        - Junction is needed
        - Complete with the notes of the professor
        - don't know about where to put public in getters/setters
        - package protected?
 */

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Road extends SimulatedObject{

    private Junction srcJunction;       // source Junction
    private Junction destJunction;      // destination Junction
    private int length;
    private int maxSpeed;               // Maximum speed
    protected int currentSpeedLimit;      // Current Speed limit ; ITS INITIAL VALUE SHOULD BE EQUAL TO maxSpeed
    private int cntAlarmLimit;          // Contamination alarm limit
    private Weather weatherConditions;  // Weather conditions
    protected int totalContamination;     // total contamination
    private List<Vehicle> vehicles;     //SHOULD ALWAYS BE SORTED BY VEHICLE LOCATION DESC


    Road(String id, Junction srcJunction, Junction destJunction, int maxSpeed, int contLimit, int length, Weather weather){
        super(id);

        if (srcJunction == null || destJunction == null || weather == null) {
            throw new IllegalArgumentException("Source junction, destination junction, and weather can't be null values");
        }
        this.srcJunction = srcJunction;
        this.destJunction = destJunction;
        this.weatherConditions = weather;

        if (maxSpeed <= 0) {
            throw new IllegalArgumentException("Max speed must be a positive value");
        }
        this.maxSpeed = maxSpeed;
        this.currentSpeedLimit = maxSpeed;

        if (contLimit < 0) {
            throw new IllegalArgumentException("cont limit must be a non-negative value");
        }
        this.cntAlarmLimit = contLimit;

        if (length <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }
        this.length = length;

        this.totalContamination = 0;

        this.vehicles = new ArrayList<Vehicle>();

        this.destJunction.addIncomingRoad(this);
        this.srcJunction.addOutGoingRoad(this);

    }

    void enter(Vehicle v){
        if (v.getLocation() != 0 || v.getSpeed() != 0) {
            throw new IllegalArgumentException("vehicle location and speed must be 0");
        }
        vehicles.add(v);
        //this.sortVehicles();
    }

    void exit(Vehicle v){
        vehicles.remove(v);
    }

    void setWeather(Weather w){
        if(w == null){
            throw new IllegalArgumentException("Weather must not be null");
        }
        else{
            this.weatherConditions = w;
        }
    }

    void addContamination(int c){
        if(c < 0){
        }
        else{
            this.totalContamination = this.totalContamination + c;
        }
    }

    abstract void reduceTotalContamination();
    abstract void updateSpeedLimit();
    abstract int calculateVehicleSpeed(Vehicle v);

    void advance(int time){
        this.reduceTotalContamination();
        this.updateSpeedLimit();

        for (Vehicle v: this.vehicles) {
            v.setSpeed(this.calculateVehicleSpeed(v));
            // TODO
            v.advance(time);
        }
        this.sortVehicles();

    }

    public JSONObject report(){
        JSONObject jo = new JSONObject();
        jo.put("id", this._id);
        jo.put("speedlimit", this.currentSpeedLimit);
        jo.put("weather", this.weatherConditions.toString());
        jo.put("co2", this.totalContamination);

        JSONArray ja = new JSONArray();
        for (Vehicle v: this.vehicles) {
            ja.put(v._id);
        }

        jo.put("vehicles", ja);

        return jo;
    }


    private void sortVehicles() {
        // using built in comparator for ints
        this.vehicles.sort((b, a)-> a.getLocation() - b.getLocation());
    }



    /* --------------
        Getters
     */

    public int getLength() {
        return this.length;
    }

    public Junction getDest() {
        return this.destJunction;
    }

    public Junction getSrc() {
        return this.srcJunction;
    }

    public Weather getWeather() {
        return this.weatherConditions;
    }

    public int getContLimit() {
        return this.cntAlarmLimit;
    }

    public int getMaxSpeed() {
        return this.maxSpeed;
    }

    public int getTotalCO2(){
        return this.totalContamination;
    }

    public int getSpeedLimit(){
        return this.currentSpeedLimit;
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    private void setC(int c) {
        this.totalContamination = c;
    }
}
