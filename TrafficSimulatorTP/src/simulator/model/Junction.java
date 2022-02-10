package simulator.model;

import java.util.List;

public class Junction extends SimulatedObject{

    private List<Road> incomingRoad;
    private Map<Junction,Road> outgoingRoad;
    private List<List<Vehicle>> listQueues;
    private Map<Road,List<Vehicle>> roadQueueMap;
    private int greenLightIdx;
    private int lastSwitching;
    private LightSwitchingStrategy lightSwitchingStrat;
    private DequeingStrategy decueingStrat;
    private int x,y;

    Junction(String id, LightSwitchStrategy lsStrategy, DequeingStrategy
            dqStrategy, int xCoor, int yCoor) {
        super(id);
    }

}
