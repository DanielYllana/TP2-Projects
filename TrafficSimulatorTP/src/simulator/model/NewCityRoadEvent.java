package simulator.model;

public class NewCityRoadEvent extends Event {
    Road road;

    public NewCityRoadEvent(int time, String id, String srcJun, String destJun, int length, int co2Limit,
                            int maxSpeed, Weather weather) {
        super(time);

        // TODO
        // Create the src and dest junction from the String
        //this.road = new CityRoad(id, srcJun, destJun, maxSpeed, co2Limit, length, weather);
    }

    @Override
    void execute(RoadMap map) {
        map.addRoad(this.road);
    }
}
