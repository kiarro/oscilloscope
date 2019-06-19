package ActualComport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TimeTriggerTab extends JPanel{

    private JLabel sourceLabel = new JLabel("Главный источник");
    private JLabel timeLabel = new JLabel("Время на деление");

    private JComboBox sourceBox = new JComboBox();
    private JComboBox timeBox = new JComboBox();

    public String getTimebase(){
        return timeBox.getSelectedItem().toString();
    }

    public String getMainSrc(){
        return sourceBox.getSelectedItem().toString();
    }

    private void sourceFill(){
        for(int i = 0; i < 4; i++)
        sourceBox.addItem("Канал " + i);
    }

    private void timeFill(){
        timeBox.addItem("1 мс");
        timeBox.addItem("5 мс");
        timeBox.addItem("10 мс");
        timeBox.addItem("50 мс");
        timeBox.addItem("100 мс");
        timeBox.addItem("500 мс");
        timeBox.addItem("1000 мс");
    }

    private void boxFill() {
        sourceFill();
        timeFill();
    }


    public TimeTriggerTab(){

        Dimension dim = getPreferredSize();
        dim.width = 100;
        dim.height = 200;
        setPreferredSize(dim);

        boxFill();

        // Set border for panel
        Border innerBorder = BorderFactory.createTitledBorder("Timebase and Trigger");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutSetUp();
    }

    private void layoutSetUp(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.weightx = 1;
        gc.weighty = 0.5f;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,5,0,5);
        add(timeLabel, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(timeBox, gc);

        gc.gridx = 0;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(5,5,0,5);
        add(sourceLabel, gc);

        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,5,5,5);
        add(sourceBox, gc);
    }
}
