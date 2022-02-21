package simulator.factories;

import org.json.JSONObject;
import simulator.model.*;

public class NewCityRoadEventBuilder extends Builder<Event> {


    public NewCityRoadEventBuilder() {
        super("new_city_road");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {

        if (!data.has("time") || !data.has("id") || !data.has("src")
                    || !data.has("dest") || !data.has("length") || !data.has("co2limit")
                    || !data.has("maxspeed") || !data.has("weather")) {
            throw new IllegalArgumentException("Invalid json data for new city road event builder");
        }

        int time = data.getInt("time");
        String id = data.getString("id");
        String src = data.getString("src");
        String dest = data.getString("dest");
        int length = data.getInt("length");
        int co2limit = data.getInt("co2limit");
        int maxspeed = data.getInt("maxspeed");
        Weather weather = Weather.valueOf(data.getString("weather"));

        Event road = new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
        return road;
    }
}
