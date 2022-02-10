package simulator.model;
/*
   NOT SPECIALLY ABOUT THIS IMPLEMENTATION
 */
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

    private int timeSlot;

    MostCrowdedStrategy(int timeSlot){
        this.timeSlot = timeSlot;
    }


    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        if(roads.size() <= 0){
            return -1;
        }
        else if(currGreen == -1){

        }
        else if((currTime - lastSwitchingTime) < timeSlot){
            return currGreen;
        }else{

        }
    }
}
