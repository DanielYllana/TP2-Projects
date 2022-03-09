package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {


    private List<Junction> _junctions;
    private String[] _colNames = { "Id", "Green", "Incoming Queue"};

    public JunctionsTableModel(Controller _ctrl) {
        this._junctions= new ArrayList<Junction>();
        _ctrl.addObserver(this);
    }

    public void update() {
        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public String getColumnName(int col) {
        return _colNames[col];
    }

    @Override
    public int getRowCount() {
        return _junctions == null ? 0 : _junctions.size();
    }

    @Override
    public int getColumnCount() {
        return _colNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object s = null;
        switch (columnIndex) {
            case 0:
                s = _junctions.get(rowIndex).getId();
                break;
            case 1:
                int index = _junctions.get(rowIndex).getGreenLightIndex();
                if (index == -1) {
                    s = "None";
                } else {
                    s = _junctions.get(rowIndex).getInRoads().get(index).getId();
                }
                break;
            case 2:
                Map<Road, List<Vehicle>> q = _junctions.get(rowIndex).getQueue();
                s = "";
                for (Road r: q.keySet()) {
                    s += " " + r.getId() + ": [";

                    for (Vehicle v: q.get(r)) {
                        s += " " + v.getId();
                    }
                    s += "]";
                }
                break;


        }
        return s;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        this._junctions = map.getJunctions();
        this.update();
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        this._junctions = map.getJunctions();
        this.update();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
