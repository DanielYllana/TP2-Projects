package simulator.model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.lang.Math;
/*
    PENDING THING IN THIS IMPLEMENTATION:
        - List<Junction>
        - Put default functions package protected
        - Function advance needs: road.length, road.addContamination, contaminationFactor = contaminationClass?, Junction method needed in c)
        - txt annotations needs to be implemented
        - don´t know where to put public in getters and setters



 */

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

        if (maxSpeed < 0) {
            throw new IllegalArgumentException("max speed should be positive");
        } else {
            this.maximumSpeed = maxSpeed;
        }

        if (contClass > 10 || contClass < 0) {
            throw new IllegalArgumentException("contamination class should be in [1, 10]");
        } else {
            this.contaminationClass = contClass;
        }

        if (itinerary.getLength() < 2) {
            throw new IllegalArgumentException("itinerary length should be at least 2");
        } else {
            this.itinerary = Collections.unmodifiableList(new ArrayList<Junction>(itinerary)); // da error porque Junction no esta definido
        }

    }

    //FUNCTIONS: NOT COMPLETED PACKAGE PROTECTED
    void setSpeed(int s){
        if(s < 0){
            throw new IllegalArgumentException("cant set speed to a negative value");
        }
        else{
            // make sure not to go over the max speed
            this.currentSpeed = Math.min(s, this.maximumSpeed);
        }
    }

    void setContaminationClass(int c){
        if(c < 0 || c > 10){
            throw new IllegalArgumentException("Contamination class has to be in [1, 10]");
        }
        else{
            this.contaminationClass = c;
        }
    }

    void advance(int time){
        if(this.status != VehicleStatus.TRAVELING){
            int oldLocation = this.location;
            //a)
            this.location = Math.min(this.location + this.currentSpeed, this.road.getLength());

            //b)
            int c = this.contaminationClass *  (this.location - oldLocation);
            this.totalContamination += c;
            road.addContamination(c); //needs to be implemented
            // may be for above road.setContamination(road.get() + c); instead of new functionx

            //c)
            if(this.location == road.getLength()){
                //this.itinerary(this.itineraryIndex). call function to enter queue
                //The vehicle enters the queue of the corresponding junction (by calling the corresponding method of class Junction)
                this.setStatus(VehicleStatus.WAITING);
            }
        }
    }

    void moveToNextRoad(){
        if(this.status == VehicleStatus.PENDING){
            // enter new road
            this.status = VehicleStatus.TRAVELING;
        }
        else if (this.status == VehicleStatus.WAITING){
            this.road.exit(this);
            //this.road = this.itinerary(this.itineraryIndex) enter new road
            this.setStatus(VehicleStatus.TRAVELING);

        } else {
            //throw new
            // throw exception; no se que tipo de excepcion poner ???
        }
    }

    public JSONObject report(){
        JSONObject jo = new JSONObject();
        jo.put("id", this._id);
        jo.put("speed", this.currentSpeed);
        jo.put("distance", this.totalDistance);
        jo.put("co2", this.totalContamination);
        jo.put("class", this.contaminationClass);
        jo.put("status", this.status);
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

    int getLocation() {
        return this.location;
    }

    int getSpeed(){
        return this.currentSpeed;
    }

    int getMaxSpeed(){
        return this.maximumSpeed;
    }

    int getContClass(){
        return this.contaminationClass;
    }

    VehicleStatus getStatus(){
        return this.status;
    }

    int getTotalCO2(){
        return this.totalContamination;
    }

    List<Junction> getItinerary() {
        return this.itinerary;
    }

    Road getRoad(){
        return this.road;
    }



}
