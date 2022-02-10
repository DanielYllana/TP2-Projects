package simulator.model;

public class NewJunctionEvent extends Event{

    Junction junction;

    public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(time);
        this.junction = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
    }

    @Override
    void execute(RoadMap map) {
        map.addJunction(this.junction);
    }
}
