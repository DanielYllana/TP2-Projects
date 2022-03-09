package simulator.model;


public class NewInterCityRoadEvent extends Event {
    private String id, srcJun, destJun;
    private int length, co2Limit, maxSpeed;
    private Weather weather;

    public NewInterCityRoadEvent(int time, String id, String srcJun, String destJun, int length, int co2Limit,
                            int maxSpeed, Weather weather) {
        super(time);

        this.id = id;
        this.srcJun = srcJun;
        this.destJun = destJun;
        this.length = length;
        this.co2Limit = co2Limit;
        this.maxSpeed = maxSpeed;
        this.weather = weather;
    }

    @Override
    void execute(RoadMap map) {
        Junction srcJun = map.getJunction(this.srcJun);
        Junction destJun = map.getJunction(this.destJun);

        Road r = new InterCityRoad(this.id, srcJun, destJun, this.maxSpeed, this.co2Limit, this.length, this.weather);
        map.addRoad(r);
    }



    @Override
    public String toString() {
        return "New Inter City Road '" + this.id + "'";
    }
}
