package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private List<Event> _events;
    private String[] _colNames = {"Time", "Description" };

    public EventsTableModel(Controller _ctrl) {
        this._events= new ArrayList<Event>();
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
        return _events == null ? 0 : _events.size();
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
                s = _events.get(rowIndex).getTime();
                break;
            case 1:
                s = _events.get(rowIndex).toString();
                break;

        }
        return s;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        this._events.add(e);
        this.update();
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
