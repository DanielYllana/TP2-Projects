package simulator.view;

import jdk.nashorn.internal.scripts.JD;
import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.*;
import simulator.model.Event;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel extends JPanel implements TrafficSimObserver, ActionListener {

    private final String LOAD = "load";
    private final String QUIT = "quit";
    private final String RUN = "run";
    private final String STOP = "stop";
    private final String SETCO2 = "setco2";
    private final String WEATHER = "weather";


    private Controller ctrl;
    private JFileChooser _fc;
    private JSpinner _ticksFieldSpinner;
    private boolean _stopped = false;
    private boolean _loaded = false;

    private RoadMap roadMap;

    private JToolBar toolBarLeft;
    private ChangeCO2ClassDialog contaminationDialog;
    private ChangeWeatherDialog weatherDialog;


    public ControlPanel(Controller _ctrl) {
        super();
        _ctrl.addObserver(this);
        this.ctrl = _ctrl;
        this.roadMap = new RoadMap();

        super.setLayout(new GridLayout(1, 2));
        super.setBackground(new Color(50, 150, 150));


        this.toolBarLeft = new JToolBar();
        super.add(this.toolBarLeft);
        JToolBar toolBarRight = new JToolBar();
        super.add(toolBarRight);

        JButton loadButton = new JButton();
        loadButton.setActionCommand(LOAD);
        loadButton.setToolTipText("Load a file");
        loadButton.setIcon(new ImageIcon("TrafficSimulatorTP/resources/icons/open.png"));
        loadButton.addActionListener(this);
        this.toolBarLeft.add(loadButton);
        this.toolBarLeft.addSeparator();


        JButton co2Button = new JButton();
        co2Button.setActionCommand(SETCO2);
        co2Button.setToolTipText("Set contamination level");
        co2Button.setIcon(new ImageIcon("TrafficSimulatorTP/resources/icons/co2class.png"));
        co2Button.addActionListener(this);
        this.toolBarLeft.add(co2Button);


        JButton weatherButton = new JButton();
        weatherButton.setActionCommand(WEATHER);
        weatherButton.setToolTipText("Set weather");
        weatherButton.setIcon(new ImageIcon("TrafficSimulatorTP/resources/icons/weather.png"));
        weatherButton.addActionListener(this);
        this.toolBarLeft.add(weatherButton);
        this.toolBarLeft.addSeparator();


        JButton runButton = new JButton();
        runButton.setActionCommand(RUN);
        runButton.setToolTipText("Run simulation");
        runButton.setIcon(new ImageIcon("TrafficSimulatorTP/resources/icons/run.png"));
        runButton.addActionListener(this);
        this.toolBarLeft.add(runButton);


        JButton stopButton = new JButton();
        stopButton.setActionCommand(STOP);
        stopButton.setToolTipText("Stop the simulation");
        stopButton.setIcon(new ImageIcon("TrafficSimulatorTP/resources/icons/stop.png"));
        stopButton.addActionListener(this);
        this.toolBarLeft.add(stopButton);



        SpinnerNumberModel ticksField = new SpinnerNumberModel();
        ticksField.setValue(1);
        _ticksFieldSpinner = new JSpinner(ticksField);
        _ticksFieldSpinner.setPreferredSize(new Dimension(10, 15));

        JLabel ticksLabel = new JLabel("Ticks: ");


        this.toolBarLeft.add(ticksLabel);
        this.toolBarLeft.add(_ticksFieldSpinner);
        this.toolBarLeft.addSeparator();



        JButton exitButton = new JButton();
        exitButton.setActionCommand(QUIT);
        exitButton.setToolTipText("Exit the application");
        exitButton.setIcon(new ImageIcon("TrafficSimulatorTP/resources/icons/exit.png"));
        exitButton.addActionListener(this);
        this.toolBarLeft.add(exitButton);

        this._fc = new JFileChooser();
        this._fc.setCurrentDirectory(new File(System.getProperty("user.dir")));


        this.contaminationDialog = new ChangeCO2ClassDialog();
        this.weatherDialog = new ChangeWeatherDialog();


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (LOAD.equals(e.getActionCommand())) {
            this.loadEventsFile();
        } else if(QUIT.equals(e.getActionCommand())) {
            this.exit();
        } else if( RUN.equals(e.getActionCommand())) {
            this.run_sim(this.getTicks()); // TODO
        } else if (STOP.equals(e.getActionCommand())) {
            this.stop();
        } else if (WEATHER.equals(e.getActionCommand())) {
            this.changeWeather();
        } else if (SETCO2.equals(e.getActionCommand())) {
            this.changeContClass();
        }
    }


    private void loadEventsFile() {
        int returnVal = this._fc.showOpenDialog(this.getParent());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = this._fc.getSelectedFile();
            try {
                InputStream is = new FileInputStream(file);
                this.ctrl.reset();
                this.ctrl.loadEvents(is);
                is.close(); // close input stream
                this._loaded = true;
            } catch (Exception e) {
                System.out.println("Something went wrong while loading the file: " + file);
            }
        }
    }


    private void exit() {
        int n = JOptionPane.showOptionDialog(new JFrame(),
                "Are you sure you want to exit?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                null, null);
        if (n == 0) {
            System.exit(0);
        }
    }


    private void run_sim(int n) {
        if (n > 0 && !this._stopped && this._loaded) {

            try {
                this.ctrl.run(1);
            } catch (Exception e) {
                // TODO show error message
                this._stopped = true;
                return;
            }
            SwingUtilities.invokeLater(() -> run_sim(n - 1));
        } else {
            enableToolBar(true);
            this._stopped = false;
        }

    }

    private void changeContClass() {
        int status = this.contaminationDialog.open(this.roadMap);

        if(status == 1) {
            int time = this.ctrl.getSimTime() + this.contaminationDialog.getTime();

            List pairList = new ArrayList<Pair<String, Integer>>();
            pairList.add(new Pair(this.contaminationDialog.getVehicle().getId(),
                    this.contaminationDialog.getContClass()));
            Event e = new SetContClassEvent(time, pairList);
            this.ctrl.addEvent(e);
        }
    }


    private void changeWeather() {
        int status = this.weatherDialog.open(this.roadMap);
        System.out.println("Chaning weather");
        if(status == 1) {
            int time = this.ctrl.getSimTime() + this.weatherDialog.getTime();

            List pairList = new ArrayList<Pair<String, Weather>>();
            pairList.add(new Pair(
                    this.weatherDialog.getRoad().getId(),
                    this.weatherDialog.getWeather()
            ));

            Event e = new SetWeatherEvent(time, pairList);
            this.ctrl.addEvent(e);
        }
    }

    private void stop() {
        this._stopped = false;
    }

    private int getTicks() {
        return (int) _ticksFieldSpinner.getValue();
    }


    private void enableToolBar(boolean value) {
        this.toolBarLeft.setEnabled(value);
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        this.roadMap = map;
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        this.roadMap = map;
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }


}
