package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {

    private TrafficSimulator trafficSim;
    private Factory<Event> eventsFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        if (sim == null || eventsFactory == null) {
            throw new IllegalArgumentException("TrafficSimulator and Events Factory cant be null");
        }

        this.trafficSim = sim;
        this.eventsFactory = eventsFactory;
    }


    public void loadEvents(InputStream in) {
        JSONObject jo = new JSONObject(new JSONTokener(in));

        if (!jo.has("events")) {
            throw new IllegalArgumentException("Invalid input stream data");
        }

        JSONArray ja = jo.getJSONArray("events");

        for (int i = 0; i < ja.length(); i++) {
            Event e = this.eventsFactory.createInstance(ja.getJSONObject(i));
            this.trafficSim.addEvent(e);
        }
    }

    public void run(int n, OutputStream out) {
        PrintStream p = new PrintStream(out);

        p.print("{");
        p.print("  \"states\": [");

        for (int i = 0; i < n - 1; i++) {
            this.trafficSim.advance();
            p.println(this.trafficSim.report());

            p.print(",");

        }

        if (n > 0) {
            this.trafficSim.advance();
            p.println(this.trafficSim.report());
        }

        p.println("]");
        p.println("}");
        p.close();
    }

    public void run(int n) {
        for (int i = 0; i < n; i++) {
            this.trafficSim.advance();
        }
    }

    public void reset() {
        this.trafficSim.reset();
    }


    public void addObserver(TrafficSimObserver o) {
        this.trafficSim.addObserver(o);
    }

    public void removeObserver(TrafficSimObserver o) {
        this.trafficSim.removeObserver(o);
    }

    public void addEvent(Event e) {
        this.trafficSim.addEvent(e);
    }


    public int getSimTime() {
        return this.trafficSim.getSimTime();
    }


}
