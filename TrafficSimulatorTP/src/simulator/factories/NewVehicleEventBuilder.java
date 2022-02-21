package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;
import simulator.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEventBuilder extends Builder<Event> {

    public NewVehicleEventBuilder() {
        super("new_vehicle");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        if (!data.has("time") || !data.has("id")
                || !data.has("maxspeed") || !data.has("class")
                || !data.has("itinerary")) {
            throw new IllegalArgumentException("Invalid json data for new vehicle event builder");
        }

        int time = data.getInt("time");
        String id = data.getString("id");


        int maxspeed = data.getInt("maxspeed");
        int _class = data.getInt("class");

        JSONArray ja = data.getJSONArray("itinerary");
        List<String> itinerary = new ArrayList<>();

        for (int i = 0; i < ja.length(); i++) {
            itinerary.add(ja.getString(i));
        }

        Event vehicleEvent = new NewVehicleEvent(time, id, maxspeed, _class, itinerary);
        return vehicleEvent;
    }
}
