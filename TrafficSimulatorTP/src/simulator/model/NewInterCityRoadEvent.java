package simulator.model;


public class NewInterCityRoadEvent extends Event {
    Road road;

    public NewInterCityRoadEvent(int time, String id, String srcJun, String destJun, int length, int co2Limit,
                            int maxSpeed, Weather weather) {
        super(time);
        // TODO
        // Create the src and dest junctions from the String
        // this.road = new InterCityRoad(id, srcJun, destJun, maxSpeed, co2Limit, length, weather);
    }

    @Override
    void execute(RoadMap map) {
        map.addRoad(this.road);
    }
}
