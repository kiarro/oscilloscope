package ActualComport;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import java.lang.String;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.nio.charset.Charset;


public class MainFrame extends JFrame {

    private Toolbar toolbar = new Toolbar();
    private ChartPane chart = new ChartPane();
    private OptionsTab optionsTab = new OptionsTab();
    private JLabel meter = new JLabel("");

    private String portName = " ";
    private String portMode = null;

    private double period = 10;
    private double portFreq = 10;

    private SerialPort comPort;

    private boolean workState = false;
    private boolean portState = false;

    private boolean[] activeChannels = new boolean[4];

///////////// Inner functions ///////////////////////////////////////////////////////
    private void allSettings(int ch, boolean isexport, String filePath, String edge, String priority) {
        System.out.println("Port used: "+ portName);
        System.out.println("Port mode: "+ portMode);
        System.out.println("Is port active: "+ portState);
        System.out.println("Port main channel: "+ ch);
        System.out.println("Is export: "+ isexport);
        System.out.println("Port period: "+ portFreq);
        System.out.println("Edge: "+ edge);
        System.out.println("Priority: "+ priority);
        System.out.println("Export filepath: "+ filePath +"\n");

    }

    private void activateCOMPort() {
        try{
            comPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }
                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    {
                        chart.changeTitle("getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE");
                        return;
                    }
                    while (!portState){
                        chart.changeTitle("Неактивно");
                    }

                    while (comPort.bytesAvailable() == -1){
                        chart.changeTitle("No bytes to read");
                        System.out.println("No bytes");
                    }
                    byte[] newData = new byte[comPort.bytesAvailable()];
                    comPort.readBytes(newData, newData.length);
                    int[] intBuffer = new int[newData.length];
                    for (int i = 0; i != newData.length; i++) {
                        if (newData[i] < 0)
                            intBuffer[i] = newData[i] + 256;
                        else intBuffer[i] = newData[i];
                    }
                    switch (portMode){
                        case ("Дата логгер"):
                            addStraight(calculateReadings(intBuffer));
                            break;
                        case ("Осциллограф"):
                            chart.addPeriodic(calculateReadings(intBuffer));
                            break;
                    }
                    try {
                        Thread.sleep((long)portFreq);
                    } catch (Exception e) {}
                }
            });
        }catch (Exception e) { chart.changeTitle("tried to addDataListener"); e.printStackTrace(); }
    }

    private void addStraight(float[] readings){
        String tempStr = "";
        for (int k = 0; k < readings.length; k++)
            if(activeChannels[k])
            {
                double temp = readings[k];
                tempStr += "    Channel "+ k +" = "+(double)Math.round(temp*100000d)/100000d;
                while(tempStr.length()%23 != 0)
                    tempStr += "0";
            }
        meter.setText(tempStr);
    }

    private void exportGraph(String filePath) {
        Csv.Writer writer = new Csv.Writer(filePath).delimiter(',');
        chart.changeTitle("Exporting");
        ArrayList<ArrayList<String>> table = chart.getGraphs();
        writer.value("Channel 0").value(" ").value(" ").
                value("Channel 1").value(" ").value(" ")
                .value("Channel 2").value(" ").value(" ").
                value("Channel 3").value(" ").value(" ").newLine();
        writer.value("Time").value("Reading").value(" ").
                value("Time").value("Reading").value(" ").
                value("Time").value("Reading").value(" ").
                value("Time").value("Reading").value(" ").newLine();
        int maxLength = maxOf4(table.get(0).size(), table.get(1).size(), table.get(2).size(), table.get(3).size());
        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    String value = table.get(j).get(i);
                    writer.value(firstStringFloat(value)).value(lastStringFloat(value)).value(" ");
                } catch (IndexOutOfBoundsException e) {
                    writer.value("NAN").value("NAN").value(" ");
                }
            }
            writer.newLine();
        }
        writer.value("end").close();
        chart.changeTitle("Finished exporting");
    }

    private String firstStringFloat(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && (Character.isDigit(string.charAt(j))||string.charAt(j) == '.')) j++;
        return string.substring(i, j);
    }

    private String lastStringFloat(String string){
        int i = string.length()-1;
        while (i >= 0 && !Character.isDigit(string.charAt(i))) i--;
        int j = i;
        while (j < string.length() && (Character.isDigit(string.charAt(j))||string.charAt(j) == '.')) j--;
        return string.substring(j, i);
    }

    private void sendChParameters(int[] Source, int[] probe, String[] Coups) {
        byte[] Bytes = new byte[4];
        for (int i = 0; i < 4; i++){
            String SourceString = "";
            switch (Source[i]){
                case (0): SourceString="0000";break;
                case (1): SourceString="0001";break;
                case (2): SourceString="0010";break;
                case (3): SourceString="0011";break;
                case (4): SourceString="0100";break;
                case (5): SourceString="0101";break;
                case (6): SourceString="0110";break;
                case (7): SourceString="0111";break;
                case (8): SourceString="1000";break;
                case (9): SourceString="1001";break;
                case (10): SourceString="1010";break;
                case (11): SourceString="1011";break;
                case (12): SourceString="1100"; break;
            }
            String ProbeString="";
            switch (probe[i]) {
                case (1): ProbeString="00"; break;
                case (2): ProbeString="01"; break;
                case (4): ProbeString="10"; break;
                case (8): ProbeString="11"; break;
            }

            String CoupleString = "";
            //AC = 0 DC = 1
            byte[] stringBytes = Coups[i].getBytes(Charset.forName("UTF-8"));
            for (int j = 0; j < stringBytes.length; j += 3) {
                if (stringBytes[j] == 65) {
                    CoupleString += "0";
                } else if (stringBytes[j] == 68) {
                    CoupleString += "1";
                }
            }

            String string = SourceString + ProbeString + CoupleString + "1";
            Bytes[i] = Byte.parseByte(string, 2);   //conversion from string to decimal
        }

        comPort.writeBytes(Bytes, 4);
    }

    private int maxOf4(int a, int b, int c, int d) {
        int e = a > b ? a : b;
        int f = c > d ? c : d;
        return e > f ? e : f;
    }

    private float[] calculateReadings(int[] buffer) {
        float[] readings = new float[4];
        float[] prevReadings = new float[4];
        float voltCoefficient = 0.0007326f;
        for(int i = 0; i < readings.length * 2; i+=2)
        {
            try {
                prevReadings[i/2] = readings[i/2];
                int temp = buffer[i+1]/128;
                if(temp == 1)
                readings[i/2] = voltCoefficient * (buffer[i] - buffer[i+1]%128 * 256);
                else readings[i/2] = voltCoefficient * (buffer[i] + buffer[i+1]%128 * 256);
            } catch (IndexOutOfBoundsException e) {
                readings[i/2] = prevReadings[i/2];
            }
        }
        return readings;
    }

//////////////////////////////////////////////////////////////////////////////////////
    public MainFrame() {

        super("Осциллограф v0.999");

        setLayout(new BorderLayout());

        meter.setSize(600, 500);
        meter.setFont(new Font(meter.getFont().getName(), Font.PLAIN, 16));
        optionsTab.setOptionsListener(new OptionsListener() {
            @Override
            public void optionsEventOccurred(OptionsEvent e) {

                portState = e.isActive();
                if (!portState){
                    allSettings(e.getMainSrc(), e.isExport(), e.getFile_path(), e.getEdge(), e.getPriority());
                    return;
                }

                // Port name(number)
                if (toolbar.getPort() == null) {
                    chart.changeTitle("COMPORT не доступен");
                    return;
                }
                else if (!portName.equals(toolbar.getPort()))
                {
                    portName = toolbar.getPort();
                    if(workState == true){
                        comPort.closePort();
                        workState = false;
                    }
                }

                portFreq = toolbar.getFreq();

                // Is active?
                if (!workState && portState) {
                    comPort = SerialPort.getCommPort(portName);
                    while(!comPort.openPort()) {
                        chart.changeTitle("Открываем COMPORT");
                    }
                }
                // Export check
                if (e.isExport()) {
                    exportGraph(e.getFile_path());
                    try {
                        Thread.sleep(500);
                    } catch (Exception exc) {}
                }

                // Acquisition/display mode
                if (portMode == null || !(portMode.equals(e.getReceiveMode()))) {
                    boolean tempState = portState;
                    portState = false;
                    try {
                        Thread.sleep(100);
                    } catch (Exception exc) {}
                    portMode = e.getReceiveMode();
                    chart.clearChart();
                    portState = tempState;
                }
                if(!workState)
                {
                    remove(meter);
                    add(chart, BorderLayout.WEST);
                    chart.setOscilloscope(e.getPeriod(), e.getPriority(), e.getEdge(),
                            e.getMidVoltage(),e.getMaxVoltage());
                    chart.chartSetUp(e.getActiveChannels(), portFreq, e.getMainSrc(), e.getChannelPoss(), e.getChannelScales());
                }
                if(portState){
                    activeChannels = e.getActiveChannels();
                    switch (portMode){
                        case ("Осциллограф"):
                            remove(meter);
                            add(chart, BorderLayout.WEST);
                            chart.setOscilloscope(e.getPeriod(), e.getPriority(), e.getEdge(),
                                    e.getMidVoltage(),e.getMaxVoltage());
                            break;
                        default:
                            remove(chart);
                            add(meter, BorderLayout.WEST);
                            chart.clearChart();
                            break;
                    }
                    chart.chartSetUp(e.getActiveChannels(), portFreq, e.getMainSrc(), e.getChannelPoss(), e.getChannelScales());
                    // Micro-controller setup
                    sendChParameters(e.getChannelSrcs(), e.getChannelProbes(), e.getChannelCoups());

                    // Chart setup
                    chart.changeTitle(portMode);
                    chart.restartChart();
                }

                allSettings(e.getMainSrc(), e.isExport(), e.getFile_path(), e.getEdge(), e.getPriority());

                if (!workState && portState) {

                    workState = true;
                    activateCOMPort();
                    return;
                }
            }
        });

        add(chart, BorderLayout.WEST);
        add(optionsTab, BorderLayout.EAST);
        add(toolbar, BorderLayout.NORTH);
        setSize(1300, 500);
        setResizable(false);
        //setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("osc.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
}


