package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

import java.util.List;

public class NewJunctionEventBuilder extends Builder<Event> {

    private Factory<LightSwitchingStrategy> lssFactory;
    private Factory<DequeuingStrategy> dqsFactory;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction");

        this.lssFactory = lssFactory;
        this.dqsFactory = dqsFactory;

    }

    @Override
    protected Event createTheInstance(JSONObject data) {

        if (!data.has("time") && !data.has("id") ||
                !data.has("coor") && !data.has("ls_strategy") &&
                !data.has("dq_strategy") ) {
            throw new IllegalArgumentException("Invalid json data for new junction event builder");
        }
        int time = data.getInt("time");
        String id = data.getString("id");


        LightSwitchingStrategy lss = this.lssFactory.createInstance(data.getJSONObject("ls_strategy"));

        DequeuingStrategy dqs = this.dqsFactory.createInstance(data.getJSONObject("dq_strategy"));

        JSONArray ja = data.getJSONArray("coor");

        int x = ja.getInt(0);
        int y = ja.getInt(1);

        Event event = new NewJunctionEvent(time, id, lss, dqs, x, y);

        return event;
    }
}
