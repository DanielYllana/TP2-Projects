package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {


    private List<Road> _roads;
    private String[] _colNames = { "Id", "Length", "Weather", "Max. Speed", "Curr Speed Limit", "Total CO2", "CO2 Limit"};

    public RoadsTableModel(Controller _ctrl) {
        this._roads= new ArrayList<Road>();
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
        return _roads == null ? 0 : _roads.size();
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
                s = _roads.get(rowIndex).getId();
                break;
            case 1:
                s = _roads.get(rowIndex).getLength();
                break;
            case 2:
                s = _roads.get(rowIndex).getWeather();
                break;
            case 3:
                s = _roads.get(rowIndex).getMaxSpeed();
                break;
            case 4:
                s = _roads.get(rowIndex).getCurrentSpeedLimit();
                break;
            case 5:
                s = _roads.get(rowIndex).getTotalCO2();
                break;
            case 6:
                s = _roads.get(rowIndex).getContLimit();
                break;

        }
        return s;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        this._roads = map.getRoads();
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
        this._roads = map.getRoads();
        this.update();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
