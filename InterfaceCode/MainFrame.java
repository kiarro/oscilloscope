package ActualComport;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import org.jfree.chart.ChartPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private ChartPane chart;
    private OptionsTab optionsTab;

    private String portName;
    private String portMode;
    private double portPeriod;
    private boolean portState;

    private SerialPort comPort;
    private boolean workState;

    private boolean[] activeCh;

    private int[] channelPos;

    private int mainCh;

    private int[] chScales;

    public MainFrame() {

        super("Oscilloscope v0.1");

        setLayout(new BorderLayout());

        chart = new ChartPane();

        optionsTab = new OptionsTab();

        workState = false;

        activeCh = new boolean[3];
        channelPos = new int[3];
        chScales = new int[3];
        mainCh = -1;

        optionsTab.setOptionsListener(new OptionsListener() {
            @Override
            public void optionsEventOccurred(OptionsEvent e) {
                portName = e.getComportName();
                if (portName == null)
                {
                    chart.changeTitle("COMPORT is unavailable");
                    return;
                }
                System.out.println(e.getActiveChannels()[0]);
                if(!(e.getActiveChannels()[0] || e.getActiveChannels()[1] || e.getActiveChannels()[2]))
                {
                    chart.clearChart();
                    chart.changeTitle("No channels selected");
                    System.out.println("Something wrong2");
                    return;
                }
                chart.changeTitle("Oscilloscope");
                activeCh = e.getActiveChannels();
                portMode = e.getAcquisitionMode();
                channelPos = e.getChannelPoss();
                mainCh = e.getMainSrc();
                chScales = e.getChannelScales();
                portState = true;
                portPeriod = e.getTimebase();

                if(!workState && portState)
                {
                    comPort = SerialPort.getCommPort(portName);
                    workState = true;
                    while (!comPort.openPort()){}
                }
                acquireBytes();
            }
        });

        add(chart, BorderLayout.CENTER);
        add(optionsTab, BorderLayout.EAST);
        setSize(1300,450);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public void acquireBytes() {
        try{
            comPort.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
                }
                @Override
                public void serialEvent(SerialPortEvent event) {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                        return;
                    if (!portState)
                        return;
                    while (comPort.bytesAvailable() == -1){System.out.println("No bytes");}
                    byte[] newData = new byte[comPort.bytesAvailable()];
                    comPort.readBytes(newData, newData.length);
                    int[] intBuffer = new int[newData.length];
                    for (int i = 0; i != newData.length; i++) {
                            if (newData[i] < 0)
                                intBuffer[i] = newData[i] + 256;
                         else intBuffer[i] = newData[i];
                        }
                    if (activeCh[0]) {
                           float voltCoefficient = 0.0007326f;
                           float temp = voltCoefficient * (intBuffer[0] + intBuffer[1] * 256);
                           chart.addData(0,mainCh, portPeriod, temp + channelPos[0], portMode, activeCh);
                    }
                    if(activeCh[1])
                    {
                        float voltCoefficient = 0.0007326f;
                        float temp = voltCoefficient * (intBuffer[2] + intBuffer[3] * 256);
                        chart.addData(1, mainCh, portPeriod, temp + channelPos[1], portMode, activeCh);
                    }
                    if(activeCh[2])
                    {
                        float voltCoefficient = 0.0007326f;
                        float temp = voltCoefficient * (intBuffer[4] + intBuffer[5] * 256);
                        chart.addData(2, mainCh, portPeriod, temp + channelPos[2], portMode, activeCh);
                    }
                    try {
                            Thread.sleep((long)portPeriod);
                    } catch (Exception e) {}
                }
            });
        }catch (Exception e) { e.printStackTrace(); }
    }
}


