package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainWindow extends JFrame {

    private Controller _ctrl;
    private JPanel eventsView;

    public MainWindow(Controller ctrl) {
        super("Traffic Simulator");
        this._ctrl = ctrl;
        initGUI();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);

        mainPanel.add(new ControlPanel(this._ctrl), BorderLayout.PAGE_START);
        mainPanel.add(new StatusBar(this._ctrl), BorderLayout.PAGE_END);

        JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(viewsPanel, BorderLayout.CENTER);

        JPanel tablesPanel = new JPanel();
        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(tablesPanel);

        JPanel mapsPanel = new JPanel();
        mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
        viewsPanel.add(mapsPanel);

        // TABLES:

        JPanel eventsView = createViewPanel(new JTable(new EventsTableModel(this._ctrl)), "Events");
        eventsView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(eventsView);

        JPanel vehicleView = createViewPanel(new JTable(new VehiclesTableModel(this._ctrl)), "Vehicle");
        vehicleView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(vehicleView);

        JPanel roadView = createViewPanel(new JTable(new RoadsTableModel(this._ctrl)), "Roads");
        roadView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(roadView);

        JPanel junctionView = createViewPanel(new JTable(new JunctionsTableModel(this._ctrl)), "Junctions");
        junctionView.setPreferredSize(new Dimension(500, 200));
        tablesPanel.add(junctionView);


        // MAPS:

        JPanel mapView = createViewPanel(new MapComponent(this._ctrl), "Map");
        mapView.setPreferredSize(new Dimension(500, 400));
        mapsPanel.add(mapView);

        JPanel mapByRoadView = createViewPanel(new MapByRoadComponent(this._ctrl), "Map by Road");
        mapByRoadView.setPreferredSize(new Dimension(500, 400));
        mapsPanel.add(mapByRoadView);

        // TODO add a map for MapByRoadComponent

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private JPanel createViewPanel(JComponent c, String title) {
        Border blackline = BorderFactory.createTitledBorder(title);
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(blackline);
        p.add(new JScrollPane(c));
        return p;
    }
}
