package ActualComport;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener {

    private JComboBox portList = new JComboBox();
    private JButton findButton = new JButton("Авто поиск");
    private JButton rescanButton = new JButton("Обновить");

    private JLabel freqLabel = new JLabel("        Интервал считывания:");
    private JComboBox freqList = new JComboBox();

    private SerialPort[] portNames = SerialPort.getCommPorts();

    public int getFreq(){
        String string = freqList.getSelectedItem().toString();
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && Character.isDigit(string.charAt(j))) j++;
        return Integer.parseInt(string.substring(i, j));
    }



    public String getPort() {
        int start = portList.getSelectedItem().toString().lastIndexOf("(");
        int end = portList.getSelectedItem().toString().lastIndexOf(")");
        return portList.getSelectedItem().toString().substring(start+1, end);
    }

    private void portFill(){
        portNames = SerialPort.getCommPorts();
        for(int i = 0; i < portNames.length; i++)
            portList.addItem(portNames[i].getDescriptivePortName());
    }

    private void freqFill(){
        freqList.addItem("1 мс");
        freqList.addItem("5 мс");
        freqList.addItem("10 мс");
        freqList.addItem("25 мс");
        freqList.addItem("50 мс");
        freqList.addItem("100 мс");
    }

    private void setUpComponents() {
        Dimension btnSize = getPreferredSize();
        btnSize.height = 25;
        btnSize.width = 100;
        Dimension boxSize = portList.getPreferredSize();
        boxSize.height = 25;
        boxSize.width = portList.getWidth();
        portList.setMaximumSize(boxSize);
        findButton.setPreferredSize(btnSize);
        rescanButton.setPreferredSize(btnSize);

        findButton.addActionListener(this);
        rescanButton.addActionListener(this);
    }

    private void setUpLayout() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(portList);
        add(findButton);
        add(rescanButton);
        add(freqLabel);
        add(freqList);
    }

    private void setUpWindowSize() {
        Dimension fullSize = getPreferredSize();
        fullSize.width = 1300;
        fullSize.height = 30;
        setPreferredSize(fullSize);
    }

    public Toolbar(){

        setUpWindowSize();

        freqFill();
        portFill();

        setUpComponents();

        setUpLayout();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (clicked == findButton){
            for(int i = 0; i < portList.getItemCount(); i++)
            {
                if (portList.getItemAt(i).toString().contains("USB Serial Device"))
                    portList.setSelectedIndex(i);
            }
        }
        else if (clicked == rescanButton) {
            if (!portList.isEnabled()) {
                return;
            }
            portList.removeAllItems();
            portFill();
        }
    }
}
