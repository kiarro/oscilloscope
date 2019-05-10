package ActualComport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsTab extends JPanel implements ActionListener {

    private ChannelTab channel0Tab;
    private ChannelTab channel1Tab;
    private ChannelTab channel2Tab;
    private OptionsListener optionsListener;

    private JButton applyBtn;

    private TimeTriggerTab timeTriggerTab;

    private InstrumentTab instrumentTab;


    public void setOptionsListener (OptionsListener listener) {
        this.optionsListener = listener;
    }

    public OptionsTab()
    {
        Dimension dim = getPreferredSize();
        dim.width = 600;
        dim.height = 400;
        setPreferredSize(dim);


        applyBtn = new JButton("Apply");

        channel0Tab = new ChannelTab("0");
        channel1Tab = new ChannelTab("1");
        channel2Tab = new ChannelTab("2");

        timeTriggerTab = new TimeTriggerTab();

        instrumentTab = new InstrumentTab();

        applyBtn.addActionListener(this);

        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 0.5f;
        gc.weighty = 0.5f;
        gc.gridheight = 2;
        gc.gridx = 0;
        gc.gridy = 0;
        add(channel0Tab, gc);

        gc.gridx = 1;
        gc.gridy = 0;
        add(channel1Tab, gc);

        gc.gridx = 2;
        gc.gridy = 0;
        add(channel2Tab, gc);


        gc.gridheight = 2;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.weightx = 1;
        gc.weighty = 0.4f;
        gc.gridx = 0;
        gc.gridy = 2;
        add(timeTriggerTab, gc);


        gc.gridheight = 1;
        gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = 2;
        add(instrumentTab, gc);


        gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = 3;
        gc.insets = new Insets(5,7,7,7);
        add(applyBtn,gc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(instrumentTab.getComportName() +" "+ instrumentTab.getAcquisitionMode());
        OptionsEvent ev = new OptionsEvent(this);

        ev.setComportName(instrumentTab.getComportName());
        ev.setAcquisitionMode(instrumentTab.getAcquisitionMode());

        ev.setActiveChannels(enabledToArray(channel0Tab.getState(), channel1Tab.getState(), channel2Tab.getState()));
        ev.setChannelSrcs(StrToIntArray(channel0Tab.getSource(), channel1Tab.getSource(), channel2Tab.getSource()));
        ev.setChannelCoups(StrToArray(channel0Tab.getCoupling(),channel1Tab.getCoupling(),channel2Tab.getCoupling()));
        ev.setChannelPoss(StrToIntArray(channel0Tab.getPosition(), channel1Tab.getPosition(), channel2Tab.getPosition()));
        ev.setChannelProbes(StrToIntArray(channel0Tab.getProbe(), channel1Tab.getProbe(), channel2Tab.getProbe()));
        ev.setChannelScales(StrToIntArray(channel0Tab.getScale(), channel1Tab.getScale(), channel2Tab.getScale()));

        ev.setTimebase(firstInt(timeTriggerTab.getTimebase()));
        ev.setMainSrc(firstInt(timeTriggerTab.getMainSrc()));

        if (optionsListener != null)
            optionsListener.optionsEventOccurred(ev);
    }


    private int firstInt(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && Character.isDigit(string.charAt(j))) j++;
        return Integer.parseInt(string.substring(i, j));
    }

    private String[] StrToArray(String str0, String str1, String str2){
        String[] temp = new String[3];
        temp[0] = str0;
        temp[1] = str1;
        temp[2] = str2;
        return temp;
    }

    private int[] StrToIntArray(String str0, String str1, String str2){
        int[] temp = new int[3];
        temp[0] = firstInt(str0);
        temp[1] = firstInt(str1);
        temp[2] = firstInt(str2);
        return temp;
    }

    private boolean[] enabledToArray(boolean ch0, boolean ch1, boolean ch2){
        boolean[] temp = new boolean[3];
        if(ch0)
            temp[0] = true;
        if(ch1)
            temp[1] = true;
        if(ch2)
            temp[2] = true;
        return temp;
    }
}

