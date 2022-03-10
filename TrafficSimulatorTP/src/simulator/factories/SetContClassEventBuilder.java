package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;

import java.util.ArrayList;
import java.util.List;

public class SetContClassEventBuilder extends Builder<Event> {

    public SetContClassEventBuilder() {
        super("set_cont_class");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        if (!data.has("time") || !data.has("info")) {
            throw new IllegalArgumentException("Invalid json data in set cont class event builder");
        }

        int time = data.getInt("time");

        JSONArray ja = data.getJSONArray("info");

        List<Pair<String, Integer>> cs = new ArrayList<>();

        for (int i = 0; i < ja.length(); i++) {
            JSONObject jo = ja.getJSONObject(i);

            if (!jo.has("vehicle") || !jo.has("class")) {
                throw new IllegalArgumentException("Invalid json data in set cont class event builder");
            }
            String vehicle = jo.getString("vehicle");
            Integer _class = jo.getInt("class");

            Pair<String, Integer> p = new Pair<>(vehicle, _class);
            cs.add(p);

        }

        return new SetContClassEvent(time, cs);
    }
}
