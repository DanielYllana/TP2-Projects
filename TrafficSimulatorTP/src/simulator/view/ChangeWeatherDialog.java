package simulator.view;

import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.Vehicle;
import simulator.model.Weather;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static simulator.model.Weather.*;

public class ChangeWeatherDialog extends JDialog{
    private int _status;
    private JComboBox<Weather> _weatherSelection;
    private DefaultComboBoxModel<Weather> _weatherModel;
    private JSpinner _ticksSelection;
    private JComboBox<Road> _roadSelection;
    private DefaultComboBoxModel<Road> _roadsModel;


    public ChangeWeatherDialog() {
        super();

        initGUI();
        super.setSize(new Dimension(500, 200));
        super.setModal(true);
    }


    private void initGUI() {
        this._status = 0;

        setTitle("Change Road Weather");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);


        JTextArea infoText = new JTextArea("Schedule an event to change the" +
                "weather of a road after a given number of simulation ticks from now");
        infoText.setEditable(false);
        infoText.setLineWrap(true);
        mainPanel.add(infoText);



        JPanel viewsPanel = new JPanel(new FlowLayout());
        viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(viewsPanel);


        JLabel roadLabel = new JLabel("Road:");
        viewsPanel.add(roadLabel);
        _roadsModel = new DefaultComboBoxModel<>();
        _roadSelection = new JComboBox<>(_roadsModel);
        viewsPanel.add(_roadSelection);

        JLabel weatherLabel = new JLabel("Weather:");
        viewsPanel.add(weatherLabel);
        _weatherModel = new DefaultComboBoxModel<>();
        _weatherSelection = new JComboBox<Weather>(_weatherModel);
        viewsPanel.add(_weatherSelection);

        JLabel ticksLabel = new JLabel("Ticks:");
        viewsPanel.add(ticksLabel);
        SpinnerNumberModel ticksField = new SpinnerNumberModel();
        ticksField.setValue(10);
        _ticksSelection = new JSpinner(ticksField);
        _ticksSelection.setPreferredSize(new Dimension(10, 15));
        viewsPanel.add(_ticksSelection);


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(buttonsPanel);


        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _status = 0;
                ChangeWeatherDialog.this.setVisible(false);
            }
        });
        buttonsPanel.add(cancelButton);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _status = 1;
                ChangeWeatherDialog.this.setVisible(false);
            }
        });
        buttonsPanel.add(okButton);
    }


    public int open(RoadMap roadMap) {
        _roadsModel.removeAllElements();
        for(Road r: roadMap.getRoads()) {
            _roadsModel.addElement(r);
        }
        _weatherModel.removeAllElements();
        for(Weather w: Weather.values()) {
            _weatherModel.addElement(w);
        }

        setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
        setVisible(true);
        return _status;
    }

    int getTime() { return (int) this._ticksSelection.getValue();}

    Road getRoad() { return (Road) this._roadSelection.getSelectedItem(); }

    Weather getWeather() {return (Weather) this._weatherSelection.getSelectedItem(); }

}
