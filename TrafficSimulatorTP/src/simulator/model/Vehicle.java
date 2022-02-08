package simulator.model;

import java.util.List;
import java.lang.Math;

public class Vehicle extends SimulatedObject{
        private List<Junction> list;  // to complete well //don´t have set or get yet //to define junction
        private int maximumSpeed;
        private int currentSpeed;
        private VehicleStatus  status;
        private Road road; //should be null if is not in any road //to declare road
        private int location;
        private int contaminationClass; // number between 0 and 1 -> Specification not defined
        private int totalContamination;
        private int totalDistance;

        Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary){
            super(id);
            //a) id non-empty string
            //b) maxSpeed should be positive
            //c) contClass between 0 and 10, both inclusive
            //d) the length of the itinerary should be at least 2
            //Besides, do not store list itinerary as it is received by
            //the construction, but rather copy it into a new read-only list
            //Collections.unmodifiableList(new ArrayList<>(itinerary));
        }

    //FUNCTIONS







    //setters and getters: PENDING GET ITINERARY / MORE GETTERS THAN ASKED FOR
    //maxSpeed
        void setMaxSpeed(int n){
            this.maximumSpeed = n;
        }
        int getMaxSpeed(){
            return this.maximumSpeed;
        }
    //current speed
        void setSpeed(int s){
            if(s < 0){

            }//throw exception to implement
            else{
                this.currentSpeed = s + (int)(Math.random() * ((this.maximumSpeed - s) + 1));
            }

        }
        int getSpeed(){
            return this.currentSpeed;
        }
    //status
        void setStatus(VehicleStatus  status){
            this.status = status;
        }
        VehicleStatus getStatus(){
            return this.status;
        }
    //road
        void setRoad(Road  road){
            this.road = road;
        }
        Road getRoad(){
            return this.road;
        }
    //location
        void setLocation(int  n){
            this.location = n;
        }
        int getLocation(){
            return this.location;
        }
     //contaminationClass
        void setContaminationClass(int n){
            this.contaminationClass = n;
        }
        int getContaminationClass(){
            return this.contaminationClass;
        }
    //totalContamination
        void setTotalCO2(int n){
            this.totalContamination = n;
        }
        int getTotalCO2(){
            return this.totalContamination;
        }
    //totalDistance
        void setTotalDistance(int n){
            this.totalDistance = n;
        }
        int getTotalDistance(int n){
            return this.totalDistance;
        }


}
