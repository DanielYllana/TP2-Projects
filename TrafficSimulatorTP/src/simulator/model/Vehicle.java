package simulator.model;

import org.json.JSONObject;

import java.util.List;
import java.lang.Math;
/*
    PENDING THING IN THIS IMPLEMENTATION:
        - List<Junction>
        - Road to declare
        - Vehicle constructor
        - SetSpeed exception
        - SetContaminationClass exception
        - Put default functions package protected
        - there may be more gets or set than asked -> done for future possible applications
        - Function advance needs: road.length, road.addContamination, contaminationFactor = contaminationClass?, Junction method needed in c)
        - txt annotations needs to be implemented
        - no idea about json function
        - Make sure that the speed of the vehicle is 0 when its status is not Traveling




 */

public class Vehicle extends SimulatedObject{
        private List<Junction> list;  // to complete well //don´t have set or get yet //to define junction
        private int maximumSpeed;
        private int currentSpeed;
        private VehicleStatus  status;
        private Road road; //should be null if is not in any road //to declare road
        private int location;
        private int contaminationClass;
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

    //FUNCTIONS: NOT COMPLETED PACKAGE PROTECTED
        void setSpeed(int s){
            if(s < 0){

            }//throw exception to implement
            else{
                this.currentSpeed = s + (int)(Math.random() * ((this.maximumSpeed - s) + 1));
                // bio: https://stackoverflow.com/questions/363681/how-do-i-generate-random-integers-within-a-specific-range-in-java
                //source code: Min + (int)(Math.random() * ((Max - Min) + 1))
            }

        }

        void setContaminationClass(int c){
            if(c < 0 || c > 10){
                //Throw exception
            }
            else{
                this.contaminationClass = c;
            }
        }

        void advance(int time){
            if(this.status != VehicleStatus.TRAVELING){
                int oldLocation = this.location;
                //a)
                this.location = Math.min(this.location + this.currentSpeed, road.legnth()); //needs of road.length
                //https://www.geeksforgeeks.org/java-math-min-method-examples/

                //b)
                int c = this.contaminationClass *  (this.location - oldLocation);
                this.totalContamination = this.totalContamination + c;
                road.addContamination(c); //needs to be implemented

                //c)
                if(this.location = road.lenght()){
                    //The vehicle enters the queue of the corresponding junction (by calling the corresponding method of class Junction)
                    //Modify the vehicle status
                }
            }
        }

        void moveToNextRoad(){
            if(this.status == VehicleStatus.PENDING || this.status == VehicleStatus.WAITING){
                //needed functions from Road
            }//throw exception
        }

        public JSONObject report(){
            //no idea of this shit
        }






    //setters and getters: PENDING GET ITINERARY / MORE GETTERS THAN ASKED FOR
    //maxSpeed
        void setMaxSpeed(int n){
            this.maximumSpeed = n;
        }
        int getMaxSpeed(){
            return this.maximumSpeed;
        }
    //current speed

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
        int getTotalDistance(){
            return this.totalDistance;
        }


}
