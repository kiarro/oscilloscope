package ActualComport;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentTab extends JPanel implements ActionListener {

    private JLabel deviceLabel;
    private JLabel modeLabel;

    private JButton scaleButton;
    private JButton rescanButton;
    private JButton runButton;
    private JButton stopButton;

    private JComboBox portList;
    private JComboBox modeBox;

    private SerialPort[] portNames;

    private StringListener textListener;

    public String getComportName(){
        if(portList.getSelectedItem() == null) return null;
        return portList.getSelectedItem().toString();
    }

    public String getAcquisitionMode(){return modeBox.getSelectedItem().toString();}

    private void layoutSetUp(){

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.weightx = 0.5f;
        gc.weighty = 0.5f;
        gc.fill = GridBagConstraints.HORIZONTAL;

        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LAST_LINE_START;
        gc.insets = new Insets(0,5,0,5);
        add(deviceLabel, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        add(modeLabel, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(portList, gc);

        gc.gridx = 2;
        gc.gridy = 1;
        add(modeBox, gc);

        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridy = 2;
        gc.insets = new Insets(5,5,5,5);
        add(scaleButton, gc);

        gc.gridx = 1;
        gc.gridy = 2;
        add(rescanButton, gc);

        gc.gridx = 2;
        gc.gridy = 2;
        add(runButton, gc);

        gc.gridx = 3;
        gc.gridy = 2;
        add(stopButton, gc);
    }

    public void setStringListener (StringListener listener) {
        this.textListener = listener;
    }

    private void componentInit(){
        portList = new JComboBox();
        modeBox = new JComboBox();

        scaleButton = new JButton("Auto-find");
        rescanButton = new JButton("Rescan");
        runButton = new JButton("Run");
        stopButton = new JButton("Stop");

        scaleButton.addActionListener(this);
        rescanButton.addActionListener(this);
        runButton.addActionListener(this);
        stopButton.addActionListener(this);

        scaleButton.setFont(new Font(scaleButton.getFont().getFontName(), Font.PLAIN, 10));
        rescanButton.setFont(new Font(rescanButton.getFont().getFontName(), Font.PLAIN, 10));
        runButton.setFont(new Font(runButton.getFont().getFontName(), Font.PLAIN, 10));
        stopButton.setFont(new Font(stopButton.getFont().getFontName(), Font.PLAIN, 10));

        deviceLabel = new JLabel("Device");
        modeLabel = new JLabel("Acquisition Mode");
    }

    private void portFill(){
        portNames = SerialPort.getCommPorts();
        for(int i = 0; i < portNames.length; i++)
            portList.addItem(portNames[i].getSystemPortName());
    }

    private void modeFill(){
        modeBox.addItem("Continuous mode");
        modeBox.addItem("Record mode");
        modeBox.addItem("Period mode");
    }

    private void boxFill(){
        portFill();
        modeFill();
    }

    private void setSize(){
        Dimension dim = getPreferredSize();
        dim.width = 1000;
        dim.height = 400;
        setPreferredSize(dim);
    }

///////////////////////////////MAIN////////////////////////////////////////////////////
    public InstrumentTab(){

        setSize();

        componentInit();

        Border innerBorder = BorderFactory.createTitledBorder("Instrument Control");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        boxFill();

        layoutSetUp();
    }


    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == scaleButton){
            for(int i = 0; i < portNames.length; i++)
            {
                if (portNames[i].getDescriptivePortName().contains("USB Serial Device"))
                    portList.setSelectedIndex(i);
            }
        }
        else if (clicked == rescanButton) {
            if (!portList.isEnabled()) {
                return;
            }
            portList.removeAllItems();
            portFill();
            repaint();
        }
        else if (clicked == runButton && modeBox.getSelectedItem().toString().equals("Record mode")){
            portList.setEnabled(false);
            modeBox.setEnabled(false);
            runButton.setEnabled(false);
        }
        else {
            portList.setEnabled(true);
            modeBox.setEnabled(true);
            runButton.setEnabled(true);
        }
    }
}
