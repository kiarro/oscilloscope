package ActualComport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ChannelTab extends JPanel {

    private JLabel sourceLabel;
    private JLabel probeLabel;
    private JLabel couplingLabel;
    private JLabel voltage1Label;
    private JLabel position1Label;
    private JLabel voltage2Label;
    private JLabel position2Label;

    private JCheckBox enableButton;

    private JComboBox sourceBox;
    private JComboBox probeBox;
    private JComboBox couplingBox;
    private JComboBox voltageBox;
    private JSpinner positionBox;


    public String getSource(){
        return sourceBox.getSelectedItem().toString();
    }

    public String getProbe(){
        return probeBox.getSelectedItem().toString();
    }

    public String getCoupling(){
        return couplingBox.getSelectedItem().toString();
    }

    public String getScale(){
        return voltageBox.getSelectedItem().toString();
    }

    public String getPosition(){
        return positionBox.getValue().toString();
    }

    public boolean getState(){
        return enableButton.isSelected();
    }

    private void sourceFill(){
        for (int i = 0; i < 13; i++)
            sourceBox.addItem("Pin "+ i);
    }

    private void probeFill(){
        probeBox.addItem("1x");
        probeBox.addItem("2x");
        probeBox.addItem("4x");
        probeBox.addItem("8x");
        probeBox.addItem("10x");
    }

    private void voltageFill(){
        voltageBox.addItem("1 mV");
        voltageBox.addItem("5 mV");
        voltageBox.addItem("10 mV");
        voltageBox.addItem("50 mV");
        voltageBox.addItem("100 mV");
        voltageBox.addItem("500 mV");
        voltageBox.addItem("1 V");
    }

    private void coupFill(){
        couplingBox.addItem("AC");
        couplingBox.addItem("DC");
    }

    private void boxFill() {
        sourceFill();
        probeFill();
        coupFill();
        voltageFill();
    }

    private void componentInit(){
        // Labels
        sourceLabel = new JLabel("Source");
        probeLabel = new JLabel("Probe");
        couplingLabel = new JLabel("Coupling ");
        voltage1Label = new JLabel("Scale");
        voltage2Label = new JLabel("Volts/Div");
        position1Label = new JLabel("Vertical");
        position2Label = new JLabel("Position(Div)");

        // Drop-down boxes
        sourceBox = new JComboBox<String>();
        probeBox = new JComboBox<String>();
        couplingBox = new JComboBox<String>();
        voltageBox = new JComboBox<String>();

        SpinnerModel model = new SpinnerNumberModel(5,0,10,1);
        positionBox = new JSpinner(model);

        // Check box
        enableButton = new JCheckBox("Enable");
        enableButton.setSelected(false);
    }

    private void layoutSetUp(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.1f;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,0);
        add(sourceLabel, gc);

        gc.weightx = 1;
        gc.weighty = 0.2f;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets = new Insets(0,5,5,0);
        add(sourceBox, gc);


        gc.weightx = 1;
        gc.weighty = 0.1f;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.insets = new Insets(0,5,0,0);
        add(enableButton, gc);


        gc.weightx = 0.5f;
        gc.weighty = 0.1f;
        gc.gridx = 0;
        gc.gridy = 3;
        add(probeLabel, gc);

        gc.weightx = 0.5f;
        gc.weighty = 0.1f;
        gc.gridx = 1;
        gc.gridy = 3;
        add(couplingLabel, gc);

        gc.weightx = 0.5f;
        gc.weighty = 0.2f;
        gc.gridx = 0;
        gc.gridy = 4;
        gc.insets = new Insets(0,5,5,0);
        add(probeBox, gc);


        gc.weightx = 0.5f;
        gc.weighty = 0.2f;
        gc.gridx = 1;
        gc.gridy = 4;
        gc.insets = new Insets(0,5,5,0);
        add(couplingBox, gc);


        gc.weightx = 0.5f;
        gc.weighty = 0.1f;
        gc.gridx = 0;
        gc.gridy = 5;
        gc.insets = new Insets(0,5,0,5);
        add(voltage1Label, gc);

        gc.gridx = 0;
        gc.gridy = 6;
        add(voltage2Label, gc);

        gc.weightx = 0.5f;
        gc.weighty = 0.1f;
        gc.gridx = 1;
        gc.gridy = 5;
        add(position1Label, gc);

        gc.gridx = 1;
        gc.gridy = 6;
        add(position2Label, gc);

        gc.weightx = 0.5f;
        gc.weighty = 0.2f;
        gc.gridx = 0;
        gc.gridy = 7;
        gc.insets = new Insets(0,5,5,5);
        add(voltageBox, gc);


        gc.weightx = 0.5f;
        gc.weighty = 0.2f;
        gc.gridx = 1;
        gc.gridy = 7;
        add(positionBox, gc);
    }

    public ChannelTab (String channelNum){
        // Tab size
        Dimension dim = getPreferredSize();
        dim.width = 150;
        dim.height = 200;
        setPreferredSize(dim);

        componentInit();
        boxFill();

        // Set border for panel
        Border innerBorder = BorderFactory.createTitledBorder("Channel " + channelNum + " Settings");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutSetUp();
    }
}
