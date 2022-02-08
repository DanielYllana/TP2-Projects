package simulator.model;

/*
   PENDING IN THIS IMPLEMENTATION:
        - Junction is needed
        - Constructor to be completed
        - to complete enter()
        - setWeather() needs to complete exception
        - addContamination() needs to complete exception
        - error in abstract reduceTotalContamination(), updateSpeedLimit(), calculateVehicleSpeed()
        - advance 3) to complete
        - no idea about Json
        - don't know about where to put public in getters/setters
        - not sure setTotalC02
        - not completed getVehicle()
 */

import java.util.List;

public class Road extends SimulatedObject{

    private Junction srcJunction; // source Junction
    private Junction destJunction; // destination Junction
    private int length;
    private int maxSpeed; // Maximum speed
    private int crrSpeedLimit; // Current Speed limit ; ITS INITIAL VALUE SHOULD BE EQUAL TO maxSpeed
    private int cntAlarmLimit; // Contamination alarm limit
    private Weather weatherCondt; // Weather conditions
    private int totalCont; // total contamination
    private List<Vehicle> vehicles; //SHOULD ALWAYS BE SORTED BY VEHICLE LOCATION DESC


    Road(String id, Junction srcJunction, Junction destJunction, int maxSpeed, int contLimit, int length, Weather weather){
        super(id);
        /*
        The constructor should add the road as an incoming road to its destination junction, and
        as an outgoing road of its source junction. In this constructor, you should check that
        arguments have valid values and throw corresponding exceptions otherwise: maxSpeed is
        positive; contLimit is non-negative; length is positive; srcJunc, destJunc and weather is not
        null.
         */
    }

    void enter(Vehicle v){
        // add the to the corresponding list ( at the end)
        //check that following hold and throw a corresponding exception otherwise: the vehicle location is 0, the vehicle speed is 0
    }

    void exit(Vehicle v){
        //remove the vehicle from the vehicle list
    }

    void setWeather(Weather w){
        if(this.weatherCondt == null){
            //throw exception
        }
        else{
            this.weatherCondt = w;
        }
    }

    void addContamination(int c){
        if(c < 0){
            //Throw exception
        }
        else{
            this.totalCont = this.totalCont + c;
        }
    }

    abstract void reduceTotalContamination();
    abstract void updateSpeedLimit();
    abstract int calculateVehicleSpeed(Vehicle v);

    void advance(int time){
        reduceTotalContamination();
        updateSpeedLimit();
        //3) to complete
    }

    public JSONObject report(){
        //don´t know shit about json
    }

    //getters and setters
//length
    public int getLength() {
        return this.length;
    }

    private void setLength(int l){
        this.length = l;
    }
//destJunction
    public Junction getDestJunction() {
        return this.destJunction;
    }

    private void setDestJunction(Junction destJunction) {
        this.destJunction = destJunction;
    }
//srcDest

    public Junction getSrcJunction() {
        return this.srcJunction;
    }

    private void setSrcJunction(Junction srcJunction) {
        this.srcJunction = srcJunction;
    }
//weather

    public Weather getWeatherCondt() {
        return this.weatherCondt;
    }

    private void setWeatherCondt(Weather weatherCondt) {
        this.weatherCondt = weatherCondt;
    }
//contamination limit


    private void setCntAlarmLimit(int cntAlarmLimit) {
        this.cntAlarmLimit = cntAlarmLimit;
    }

    public int getCntAlarmLimit() {
        return this.cntAlarmLimit;
    }
//max speed

    public int getMaxSpeed() {
        return this.maxSpeed;
    }
    private void setMaxSpeed(int s){
        this.maxSpeed = s;
    }
//total C02
    public int getTotalCO2(){
        return this.totalCont;
    }
    private void setTotalCO2(int c){
        this.totalCont = c;
        //also could be thought as this.totalcont = this.totalcont + c, depending on the implementation
    }
//speed limit
    public int getCrrSpeedLimit(){
        return this.crrSpeedLimit;
    }
    private void setCrrSpeedLimit(int s){
        this.crrSpeedLimit = s;
    }
//vehicles
    public List<Vehicle> getVehicles(){
        //Not completely sure about that
    }
}
