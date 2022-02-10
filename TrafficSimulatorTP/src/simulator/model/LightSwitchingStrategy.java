package simulator.model;

import java.util.List;

public interface LightSwitchingStrategy {
    int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime);
   /*
   The method returns the index of the road (in the list roads) to be switched to green
   – if it is the same as currGreen then, the junction will not consider it as switching. If it returns −1 it means all should be red.
    */
}
