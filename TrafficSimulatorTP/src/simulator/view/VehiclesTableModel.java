package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

    private List<Vehicle> _vehicles;
    private String[] _colNames = { "Id", "Location", "Itinerary", "CO2 Class", "Status", "Max. Speed", "Curr. Speed", "Total CO2", "Total Dist." };

    public VehiclesTableModel(Controller _ctrl) {
        this._vehicles= new ArrayList<Vehicle>();
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
        return _vehicles == null ? 0 : _vehicles.size();
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
                s = _vehicles.get(rowIndex).getId();
                break;
            case 1:
                s = _vehicles.get(rowIndex).getLocation();
                break;
            case 2:
                s = _vehicles.get(rowIndex).getItinerary();
                break;
            case 3:
                s = _vehicles.get(rowIndex).getContClass();
                break;
            case 4:
                s = _vehicles.get(rowIndex).getStatus();
                break;
            case 5:
                s = _vehicles.get(rowIndex).getMaxSpeed();
                break;
            case 6:
                s = _vehicles.get(rowIndex).getSpeed();
                break;
            case 7:
                s = _vehicles.get(rowIndex).getTotalCO2();
                break;
            case 8:
                s = _vehicles.get(rowIndex).getTotalDistance();
                break;
        }
        return s;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        this._vehicles = map.getVehicles();
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
        this._vehicles = map.getVehicles();
        this.update();
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
