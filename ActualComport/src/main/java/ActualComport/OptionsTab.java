package ActualComport;

import javax.swing.*;
import java.awt.*;

public class OptionsTab extends JPanel  {

    private ChannelTab[] channels = new ChannelTab[4];

    private OptionsListener optionsListener;

    private ExtraTab extraTab = new ExtraTab();

    private InstrumentTab instrumentTab = new InstrumentTab();

    private OptionsEvent ev = new OptionsEvent(this);

    public void setOptionsListener (OptionsListener listener) {
        this.optionsListener = listener;
    }

    private void layoutSetUp()
    {
        setLayout(new GridLayout(2,3,2,0));
        for(int i = 0; i < channels.length-1; i++)
            add(channels[i]);
        add(channels[3]);
        add(instrumentTab);
        add(extraTab);
    }

    public OptionsTab()
    {
        Dimension dim = getPreferredSize();
        dim.width = 600;
        dim.height = 400;
        setPreferredSize(dim);

        for(int i = 0; i < channels.length; i++)
        {
            channels[i] = new ChannelTab(i);
        }
        instrumentTab.setInstrumentListener(new InstrumentListener() {
            @Override
            public void instrumentEventOccurred(InstrumentEvent e) {

                if(e.isAuxState())
                {
                    if(e.getReceiveMode().equals("Осциллограф")) {
                        if(!extraTab.isActive()){
                            extraTab.setEnabled(true);
                        }
                    }
                    else {
                        extraTab.setEnabled(false);
                    }
                    return;
                }

                ev.setExport(e.isExport());
                ev.setActive(e.isActive());
                ev.setReceiveMode(e.getReceiveMode());
                ev.setFile_path(e.getFilePath());

                ev.setMainSrc(extraTab.getMainCh());
                ev.setPeriod(extraTab.getPeriod());
                ev.setEdge(extraTab.getEdge());
                ev.setPriority(extraTab.getPriority());
                ev.setMaxVoltage(extraTab.getMaxVoltage());
                ev.setMidVoltage(extraTab.getMidVoltage());

                getAllParameters();

                boolean[] activeChannels = ev.getActiveChannels();
                if (activeChannels[0] || activeChannels[1] || activeChannels[2] || activeChannels[3]) {
                    if (optionsListener != null)
                        optionsListener.optionsEventOccurred(ev);
                }
                else instrumentTab.setBtnText("Пуск");
            }
        });

        layoutSetUp();
    }


    private void getAllParameters()
    {
        ev.setActiveChannels(boolToArray(channels[0].getState(), channels[1].getState(),
                channels[2].getState(), channels[3].getState()));
        ev.setChannelSrcs(StrToIntArray(channels[0].getSource(), channels[1].getSource(),
                channels[2].getSource(), channels[3].getSource()));
        ev.setChannelCoups(StrToArray(channels[0].getCoupling(),channels[1].getCoupling(),
                channels[2].getCoupling(), channels[3].getCoupling()));
        ev.setChannelPoss(StrToIntArray(channels[0].getPosition(), channels[1].getPosition(),
                channels[2].getPosition(), channels[3].getPosition()));
        ev.setChannelProbes(StrToIntArray(channels[0].getProbe(), channels[1].getProbe(),
                channels[2].getProbe(), channels[3].getProbe()));
        ev.setChannelScales(StrToFloatArray(channels[0].getScale(), channels[1].getScale(),
                channels[2].getScale(), channels[3].getScale()));
    }


    private int firstInt(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && Character.isDigit(string.charAt(j))) j++;
        return Integer.parseInt(string.substring(i, j));
    }

    private String[] StrToArray(String str0, String str1, String str2, String str3){
        String[] temp = new String[4];
        temp[0] = str0;
        temp[1] = str1;
        temp[2] = str2;
        temp[3] = str3;
        return temp;
    }

    private int[] StrToIntArray(String str0, String str1, String str2, String str3){
        int[] temp = new int[4];
        temp[0] = firstInt(str0);
        temp[1] = firstInt(str1);
        temp[2] = firstInt(str2);
        temp[3] = firstInt(str3);
        return temp;
    }

    private float[] StrToFloatArray(String str0, String str1, String str2, String str3){
        float[] temp = new float[4];
        temp[0] = firstFloat(str0);
        temp[1] = firstFloat(str1);
        temp[2] = firstFloat(str2);
        temp[3] = firstFloat(str3);
        return temp;
    }

    private float firstFloat(String string){
        int i = 0;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && (Character.isDigit(string.charAt(j)) || string.charAt(j)=='.')) j++;
        return Float.parseFloat(string.substring(i, j));
    }

    private boolean[] boolToArray(boolean ch0, boolean ch1, boolean ch2, boolean ch3){
        boolean[] temp = new boolean[4];
        if(ch0)
            temp[0] = true;
        if(ch1)
            temp[1] = true;
        if(ch2)
            temp[2] = true;
        if(ch3)
            temp[3] = true;
        return temp;
    }
}

