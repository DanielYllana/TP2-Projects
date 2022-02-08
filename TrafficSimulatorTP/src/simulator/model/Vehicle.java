package simulator.model;

public class Vehicle extends SimulatedObject{
        private List<Junction> list;  // to complete well //don´t have set or get yet
        private int maximumSpeed;
        private int currentSpeed;
        private VehicleStatus  status;
        private Road road; //should be null if is not in any road //to declare road
        private int location;
        private int contaminationClass; // number between 0 and 1 -> Specification not defined
        private int totalContamination;
        private int totalDistance;
    //maxSpeed
        void setMaximumSpeed(int n){
            this.maximumSpeed = n;
        }
        int getMaximumSpeed(){
            return this.maximumSpeed;
        }
    //current speed
        void setCurrentSpeed(int n){
            this.currentSpeed = n;
        }
        int getCurrentSpeed(){
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
        void setTotalContamination(int n){
            this.totalContamination = n;
        }
        int getTotalContamination(){
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
