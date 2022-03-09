package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements TrafficSimObserver {

    private Controller ctrl;
    private JLabel currTimeLabel;
    private JLabel newEventLabel;

    public StatusBar(Controller _ctrl) {
        super();
        this.ctrl = _ctrl;
        this.ctrl.addObserver(this);

        super.setLayout(new GridLayout(1, 2));
        super.setBackground(new Color(50, 150, 150));

        // LEFT Panel
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(150, 50, 150));
        super.add(leftPanel);

        // Right Panel
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rightPanel.setBackground(new Color(150, 150, 50));
        super.add(rightPanel);


        this.currTimeLabel = new JLabel();
        this.currTimeLabel.setText("Time: " + this.ctrl.getSimTime());
        leftPanel.add(this.currTimeLabel);


        this.newEventLabel = new JLabel();
        this.newEventLabel.setText("Welcome!");
        rightPanel.add(this.newEventLabel);




    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        this.currTimeLabel.setText("Time: " + this.ctrl.getSimTime());
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        this.newEventLabel.setText("Event Added(" + e.toString() + ")");
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
