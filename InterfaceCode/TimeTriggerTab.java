package ActualComport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TimeTriggerTab extends JPanel{

    private JLabel sourceLabel;
    private JLabel timeLabel;

    private JComboBox sourceBox;
    private JComboBox timeBox;

    public String getTimebase(){
        return timeBox.getSelectedItem().toString();
    }

    public String getMainSrc(){
        return sourceBox.getSelectedItem().toString();
    }

    private void sourceFill(){
        sourceBox.addItem("Channel 0 Source");
        sourceBox.addItem("Channel 1 Source");
        sourceBox.addItem("Channel 2 Source");
    }

    private void timeFill(){
        timeBox.addItem("1 ms");
        timeBox.addItem("5 ms");
        timeBox.addItem("10 ms");
        timeBox.addItem("50 ms");
        timeBox.addItem("100 ms");
    }

    private void boxFill() {
        sourceFill();
        timeFill();
    }

    private void componentInit(){
        timeBox = new JComboBox();
        sourceBox = new JComboBox();
        timeLabel = new JLabel("Time/Div");
        sourceLabel = new JLabel("Source");
    }

    public TimeTriggerTab(){

        Dimension dim = getPreferredSize();
        dim.width = 100;
        dim.height = 200;
        setPreferredSize(dim);

        componentInit();

        boxFill();

        // Set border for panel
        Border innerBorder = BorderFactory.createTitledBorder("Timebase and Trigger");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.5f;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,5,0,5);
        add(timeLabel, gc);

        gc.weightx = 1;
        gc.weighty = 0.5f;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,0,5);
        add(timeBox, gc);


        gc.weightx = 1;
        gc.weighty = 0.5f;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(5,5,0,5);
        add(sourceLabel, gc);

        gc.weightx = 1;
        gc.weighty = 0.5f;
        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,5,5);
        add(sourceBox, gc);
    }
}
