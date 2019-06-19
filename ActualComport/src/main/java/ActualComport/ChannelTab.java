package ActualComport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ChannelTab extends JPanel {

    private JLabel sourceLabel = new JLabel("Источник");
    private JLabel probeLabel = new JLabel("Проба");
    private JLabel couplingLabel = new JLabel("Сцепка ");
    private JLabel voltage1Label = new JLabel("Масштаб");
    private JLabel voltage2Label = new JLabel("Вольт/деление");
    private JLabel position1Label = new JLabel("Позиция по");
    private JLabel position2Label = new JLabel("Вертикали");

    private JCheckBox enableButton = new JCheckBox("Вкл");

    private JComboBox sourceBox = new JComboBox<String>();
    private JComboBox probeBox = new JComboBox<String>();
    private JComboBox couplingBox = new JComboBox<String>();
    private JComboBox voltageBox = new JComboBox<String>();
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
            sourceBox.addItem("Ножка "+ i);
    }

    private void probeFill(){
        probeBox.addItem("1x");
        probeBox.addItem("2x");
        probeBox.addItem("4x");
        probeBox.addItem("8x");
    }

    private void voltageFill(){
        voltageBox.addItem("1 мВ");
        voltageBox.addItem("5 мВ");
        voltageBox.addItem("10 мВ");
        voltageBox.addItem("50 мВ");
        voltageBox.addItem("100 мВ");
        voltageBox.addItem("500 мВ");
        voltageBox.addItem("1 В");
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
        SpinnerModel model = new SpinnerNumberModel(0,0,10,1);
        positionBox = new JSpinner(model);

        enableButton.setSelected(false);

        // Check box
        enableButton.setSelected(false);
        boxFill();
        Dimension boxSize = sourceBox.getPreferredSize();
        boxSize.height = 25;
        boxSize.width = sourceBox.getWidth();
        sourceBox.setPreferredSize(boxSize);
        probeBox.setPreferredSize(boxSize);
        couplingBox.setPreferredSize(boxSize);
        voltageBox.setPreferredSize(boxSize);
        positionBox.setPreferredSize(boxSize);

    }

    private void layoutSetUp(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.1f;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(0,5,0,0);
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(sourceLabel, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets = new Insets(0,5,5,0);
        add(sourceBox, gc);


        gc.gridx = 0;
        gc.gridy = 2;
        gc.insets = new Insets(0,5,0,0);
        add(enableButton, gc);


        gc.weightx = 0.5f;

        gc.gridx = 0;
        gc.gridy = 3;
        add(probeLabel, gc);


        gc.gridx = 1;
        gc.gridy = 3;
        add(couplingLabel, gc);


        gc.gridx = 0;
        gc.gridy = 4;
        gc.insets = new Insets(0,5,5,0);
        add(probeBox, gc);



        gc.gridx = 1;
        gc.gridy = 4;
        gc.insets = new Insets(0,5,5,0);
        add(couplingBox, gc);



        gc.gridx = 0;
        gc.gridy = 5;
        gc.insets = new Insets(0,5,0,5);
        add(voltage1Label, gc);

        gc.gridx = 0;
        gc.gridy = 6;
        add(voltage2Label, gc);


        gc.gridx = 1;
        gc.gridy = 5;
        add(position1Label, gc);

        gc.gridx = 1;
        gc.gridy = 6;
        add(position2Label, gc);


        gc.gridx = 0;
        gc.gridy = 7;
        gc.insets = new Insets(0,5,5,5);
        add(voltageBox, gc);


        gc.gridx = 1;
        gc.gridy = 7;
        add(positionBox, gc);
    }

    public ChannelTab (int channelNum){
        // Tab size
        Dimension dim = getPreferredSize();
        dim.width = 300;
        dim.height = 300;
        setPreferredSize(dim);

        componentInit();

        // Set border for panel
        Border innerBorder = BorderFactory.createTitledBorder("Настройки канала "+ channelNum );
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutSetUp();
    }
}
