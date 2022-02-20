package simulator.model;
/*
   NOT SPECIALLY ABOUT THIS IMPLEMENTATION
 */
import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

    private int timeSlot;

    public MostCrowdedStrategy(int timeSlot){
        this.timeSlot = timeSlot;
    }


    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
        if(roads.size() == 0){
            return -1;
        }
        else if(currGreen == -1){
            int maxIndex = 0;
            for(List<Vehicle> list: qs) {
                if (list.size() > qs.get(maxIndex).size()) {
                    maxIndex = qs.indexOf(list);
                }
            }
            return maxIndex;


        }
        else if((currTime - lastSwitchingTime) < timeSlot){
            return currGreen;
        }else{
            int maxIndex = (currGreen + 1) % qs.size();
            for (int i = 0; i < qs.size(); i++) {

                int index = (i + currGreen + 1) % qs.size();
                if (qs.get(index).size() > qs.get(maxIndex).size()) {
                    maxIndex = index;
                }
            }
            return maxIndex;
        }
    }
}
