package simulator.model;

public class NewJunctionEvent extends Event{

    private Junction newJunction;

    public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(time);
        this.newJunction = new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
    }

    @Override
    void execute(RoadMap map) {
        map.addJunction(this.newJunction);
    }


    @Override
    public String toString() {
        return "New Junction '" + this.newJunction.getId() + "'";
    }
}
