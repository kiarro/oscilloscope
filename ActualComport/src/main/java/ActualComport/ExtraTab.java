package ActualComport;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ExtraTab extends JPanel {

    private boolean state = false;

    private JLabel sourceLabel = new JLabel("Главный канал");
    private JLabel periodLabel = new JLabel("Период(мс)");
    private JLabel edgeLabel = new JLabel("Edge");
    private JLabel priorityLabel = new JLabel("Приоритет");
    private JLabel voltageLabel1 = new JLabel("Предельный");
    private JLabel voltageLabel2 = new JLabel("вольтаж");
    private JLabel midVoltageLabel1 = new JLabel("Средний");
    private JLabel midVoltageLabel2 = new JLabel("вольтаж");

    private JComboBox sourceBox = new JComboBox();
    private JComboBox edgeBox = new JComboBox();
    private JComboBox priorityBox = new JComboBox();

    private JSpinner periodBox;
    private JSpinner voltageBox;
    private JSpinner midVoltageBox;
///////////////////////////////////////////

    private void sourceFill(){
        for(int i = 0; i < 4; i++)
            sourceBox.addItem("Канал " + i);
    }

    private void periodFill(){
        SpinnerModel model = new SpinnerNumberModel(100,10,10000,1);
        periodBox = new JSpinner(model);
    }

    private void voltageFill(){
        SpinnerModel model = new SpinnerNumberModel(1,0,100,0.001);
        voltageBox = new JSpinner(model);
        SpinnerModel model2 = new SpinnerNumberModel(1,0,100,0.001);
        midVoltageBox = new JSpinner(model2);
    }

    private void edgeFill(){
        edgeBox.addItem("+-");
        edgeBox.addItem("-+");
    }

    private void priorityFill(){
        priorityBox.addItem("Период");
        priorityBox.addItem("Край");
    }

    private void boxFill() {
        edgeFill();
        voltageFill();
        priorityFill();
        sourceFill();
        periodFill();
    }
//////////////////////////////////////////////

    public boolean isActive() {
    return state;
}

/////////////////////////////////////////////////

    public int getMainCh(){
        return firstInt(sourceBox.getSelectedItem().toString());
    }

    public int getPeriod(){
        return firstInt(periodBox.getValue().toString());
    }

    public String getEdge(){

        return edgeBox.getSelectedItem().toString();
    }

    public String getPriority(){
        return priorityBox.getSelectedItem().toString();
    }

    public float getMaxVoltage(){
        return firstFloat(voltageBox.getValue().toString());
    }

    public float getMidVoltage(){
        return firstFloat(midVoltageBox.getValue().toString());
    }

    private float firstFloat(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && (Character.isDigit(string.charAt(j)) || string.charAt(j)=='.')) j++;
        return Float.parseFloat(string.substring(i, j));
    }

    private int firstInt(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && Character.isDigit(string.charAt(j))) j++;
        return Integer.parseInt(string.substring(i, j));
    }

//////////////////////

    public void setEnabled(boolean state){
        this.state = state;

        voltageLabel1.setEnabled(state);
        voltageLabel2.setEnabled(state);
        voltageBox.setEnabled(state);

        midVoltageLabel1.setEnabled(state);
        midVoltageLabel2.setEnabled(state);
        midVoltageBox.setEnabled(state);

        periodLabel.setEnabled(state);
        periodBox.setEnabled(state);

        sourceLabel.setEnabled(state);
        sourceBox.setEnabled(state);

        edgeLabel.setEnabled(state);
        edgeBox.setEnabled(state);

        priorityLabel.setEnabled(state);
        priorityBox.setEnabled(state);

    }

    private void layoutSetUp(){

        setLayout(new GridBagLayout());
        // Starting settings
        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 1;
        gc.weighty = 0.3f;
        gc.insets = new Insets(0,5,0,5);
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.fill = GridBagConstraints.HORIZONTAL;

        // First line
        gc.gridx = 0;
        gc.gridy = 0;
        add(sourceLabel, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(edgeLabel, gc);

        // Second line
        gc.insets = new Insets(0,5,5,5);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 0;
        gc.gridy = 1;
        add(sourceBox, gc);

        gc.gridx = 1;
        gc.gridy = 1;
        add(edgeBox, gc);

        // Third line
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,5,0,5);
        gc.gridx = 0;
        gc.gridy = 2;
        add(priorityLabel, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(periodLabel, gc);

        // Fourth line
        gc.insets = new Insets(0,5,5,5);
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        gc.gridx = 0;
        gc.gridy = 3;
        add(priorityBox, gc);

        gc.insets = new Insets(0,5,10,5);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 1;
        gc.gridy = 3;
        add(periodBox, gc);

        // Fifth line
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,5,0,5);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy = 4;
        add(voltageLabel1, gc);

        gc.gridx = 1;
        gc.gridy = 4;
        add(midVoltageLabel1, gc);

        // Sixth line
        gc.gridx = 0;
        gc.gridy = 5;
        add(voltageLabel2, gc);

        gc.gridx = 1;
        gc.gridy = 5;
        add(midVoltageLabel2, gc);

        // Seventh line
        gc.insets = new Insets(0,5,10,5);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 6;
        add(voltageBox, gc);

        gc.insets = new Insets(0,5,10,5);
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 1;
        gc.gridy = 6;
        add(midVoltageBox, gc);

    }

    private void componentInit() {

        boxFill();
        layoutSetUp();
        voltageLabel1.setFont(new Font(voltageLabel1.getFont().getName(), Font.BOLD, 11));
        voltageLabel2.setFont(new Font(voltageLabel2.getFont().getName(), Font.BOLD, 11));
    }

    public ExtraTab(){

        componentInit();

        setEnabled(false);

        Border innerBorder = BorderFactory.createTitledBorder("Настройки Осциллографа");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        layoutSetUp();
    }

}
