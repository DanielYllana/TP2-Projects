package simulator.view;

import simulator.model.RoadMap;
import simulator.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeCO2ClassDialog extends JDialog {
    private int _status;
    private JComboBox<Integer>  _contClasses;
    private JSpinner _ticksSelection;
    private JComboBox<Vehicle> _vehicleSelection;
    private DefaultComboBoxModel<Vehicle> _vehiclesModel;


    public ChangeCO2ClassDialog() {
        super();

        initGUI();
        super.setSize(new Dimension(500, 200));
        super.setModal(true);
    }


    private void initGUI() {
        this._status = 0;

        setTitle("Change CO2 Class");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);


        JTextArea infoText = new JTextArea("Schedule an event to change the CO2 class" +
                "of a vhicle after a given number of simulation ticks from now");
        infoText.setEditable(false);
        infoText.setLineWrap(true);
        mainPanel.add(infoText);



        JPanel viewsPanel = new JPanel(new FlowLayout());
        viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(viewsPanel);


        JLabel vehicleLabel = new JLabel("Vehicle:");
        viewsPanel.add(vehicleLabel);
        _vehiclesModel = new DefaultComboBoxModel<>();
        _vehicleSelection = new JComboBox<>(_vehiclesModel);
        viewsPanel.add(_vehicleSelection);

        JLabel contClassLabel = new JLabel("CO2 Class:");
        viewsPanel.add(contClassLabel);
        _contClasses = new JComboBox<Integer>( new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        viewsPanel.add(_contClasses);

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
                ChangeCO2ClassDialog.this.setVisible(false);
            }
        });
        buttonsPanel.add(cancelButton);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                _status = 1;
                ChangeCO2ClassDialog.this.setVisible(false);
            }
        });
        buttonsPanel.add(okButton);
    }


    public int open(RoadMap roadMap) {
        _vehiclesModel.removeAllElements();
        for(Vehicle v: roadMap.getVehicles()) {
            _vehiclesModel.addElement(v);
        }

        setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
        setVisible(true);
        return _status;
    }

    int getTime() { return (int) this._ticksSelection.getValue();}

    Vehicle getVehicle() { return (Vehicle) this._vehicleSelection.getSelectedItem(); }

    int getContClass() {return (int) this._contClasses.getSelectedItem(); }

}
