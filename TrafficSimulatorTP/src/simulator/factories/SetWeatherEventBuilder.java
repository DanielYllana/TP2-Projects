package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class SetWeatherEventBuilder extends Builder<Event> {

    public SetWeatherEventBuilder() {
        super("set_weather");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        if (!data.has("time") || !data.has("info")) {
            throw new IllegalArgumentException("Invalid json data in set weather event builder");
        }

        int time = data.getInt("time");

        JSONArray ja = data.getJSONArray("info");

        List<Pair<String, Weather>> ws = new ArrayList<>();

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);

            if (!jo.has("road") || !jo.has("weather")) {
                throw new IllegalArgumentException("Invalid json data in set weather event builder");
            }
            String r = jo.getString("road");
            Weather w = Weather.valueOf(jo.getString("weather"));

            Pair<String, Weather> p = new Pair<>(r, w);
            ws.add(p);
        }


        return new SetWeatherEvent(time, ws);
    }
}
