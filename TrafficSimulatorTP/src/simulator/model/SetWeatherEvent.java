package simulator.model;

import simulator.misc.Pair;

import java.util.List;

public class SetWeatherEvent extends Event {

    private List<Pair<String, Weather>> ws;

    public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
        super(time);

        if (ws == null) {
            throw new IllegalArgumentException("Weather|String pair cant be null");
        }

        this.ws = ws;

    }

    @Override
    void execute(RoadMap map) {
        for (Pair<String, Weather> pair: this.ws) {
            Road r = map.getRoad(pair.getFirst());

            if (r == null ){
                throw new IllegalArgumentException("Road does not exists in road-map");
            }

            r.setWeather(pair.getSecond());
        }
    }



    @Override
    public String toString() {
        return "Setting Weather";
    }
}
