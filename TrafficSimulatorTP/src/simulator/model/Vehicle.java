package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;


public class Vehicle extends SimulatedObject{
    private List<Junction> itinerary;  // to complete well //don´t have set or get yet //to define junction
    private int maximumSpeed;
    private int currentSpeed;
    private VehicleStatus  status = VehicleStatus.PENDING;
    private Road road;              // current road
    private int location;           // dist to begining of road
    private int contaminationClass;     // [0, 10]
    private int totalContamination;
    private int totalDistance;

    // Not given by the teacher
    private int itineraryIndex = 0;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary){
        super(id);

        if (maxSpeed <= 0) {
            throw new IllegalArgumentException("max speed should be positive");
        } else {
            this.maximumSpeed = maxSpeed;
        }

        if (contClass > 10 || contClass < 0) {
            throw new IllegalArgumentException("contamination class should be in [1, 10]");
        } else {
            this.contaminationClass = contClass;
        }

        if (itinerary.size() < 2) {
            throw new IllegalArgumentException("itinerary length should be at least 2");
        } else {
            this.itinerary = Collections.unmodifiableList(new ArrayList<Junction>(itinerary));
        }



    }

    void setSpeed(int s){
        if(s < 0){
            throw new IllegalArgumentException("cant set speed to a negative value");
        }
        else{
            if (this.status == VehicleStatus.TRAVELING) {
                // make sure not to go over the max speed
                this.currentSpeed = Math.min(s, this.maximumSpeed);
            }
        }
    }

    void setContClass(int c){
        if(c < 0 || c > 10){
            throw new IllegalArgumentException("Contamination class has to be in [1, 10]");
        }
        else{
            this.contaminationClass = c;
        }
    }

    void advance(int time){
        if(this.status == VehicleStatus.TRAVELING){
            int oldLocation = this.location;

            this.location = Math.min(this.location + this.currentSpeed, this.road.getLength());

            int deltDist = this.location - oldLocation;
            this.totalDistance += deltDist;

            int c = this.contaminationClass *  deltDist;
            this.totalContamination += c;
            road.addContamination(c);

            if(this.location == road.getLength()){

                this.itinerary.get(this.itineraryIndex + 1).enter(this);
                this.setStatus(VehicleStatus.WAITING);

            }
        }
    }

    void moveToNextRoad(){


        if(this.status == VehicleStatus.PENDING){
            this.road = this.itinerary.get(0).roadTo(this.itinerary.get(1)); // get first road
            this.road.enter(this);
            this.status = VehicleStatus.TRAVELING;
            this.itineraryIndex = 0;
        }
        else if (this.status == VehicleStatus.WAITING){

            this.exitCurrentRoad();
            if (this.itineraryIndex + 1 == this.itinerary.size() - 1) {
                this.setStatus(VehicleStatus.ARRIVED);
            } else {
                // get new road to next junction
                this.road = this.itinerary.get(this.itineraryIndex + 1).roadTo(this.itinerary.get(this.itineraryIndex + 2));
                this.road.enter(this); // enter new road
                this.setStatus(VehicleStatus.TRAVELING);

            }
            this.itineraryIndex++;
        } else {
            assert(false);
        }

    }


    private void exitCurrentRoad() {
        this.road.exit(this);
        this.setSpeed(0);
        this.location = 0;
    }

    public JSONObject report(){
        JSONObject jo = new JSONObject();
        jo.put("id", this._id);
        jo.put("speed", this.currentSpeed);
        jo.put("distance", this.totalDistance);
        jo.put("co2", this.totalContamination);
        jo.put("class", this.contaminationClass);
        jo.put("status", this.status.toString());
        if (this.status != VehicleStatus.PENDING && this.status != VehicleStatus.ARRIVED) {
            jo.put("road", this.road._id);
            jo.put("location", this.location);
        }


        return jo;
    }


    /*  -----------------------
        Additional setters (must be private)
    */

    private void setStatus(VehicleStatus _status) {
        this.status = _status;
        if (this.status != VehicleStatus.TRAVELING) {
            this.currentSpeed = 0;
        }
    }


    /*  -----------------------
        getters: Only the ones asked by the teacher
    */

    public int getLocation() {
        return this.location;
    }

    public int getSpeed(){
        return this.currentSpeed;
    }

    public int getMaxSpeed(){
        return this.maximumSpeed;
    }

    public int getContClass(){
        return this.contaminationClass;
    }

    public VehicleStatus getStatus(){
        return this.status;
    }

    public int getTotalCO2(){
        return this.totalContamination;
    }

    public List<Junction> getItinerary() {
        return this.itinerary;
    }

    public Road getRoad(){
        return this.road;
    }

    public int getTotalDistance() { return this.totalDistance; }


    public String toString() {
        return this._id;
    }
}
