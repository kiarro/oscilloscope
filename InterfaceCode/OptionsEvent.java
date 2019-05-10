package ActualComport;

import java.util.EventObject;

public class OptionsEvent extends EventObject {

    private String comportName;
    private String acquisitionMode;

    private int[] channelSrcs;

    private int[] channelProbes;

    private String[] channelCoups;

    private int[] channelScales;

    private boolean[] activeChannels;

    private int[] channelPoss;

    private int timebase;

    private int mainSrc;

    public int getTimebase() {
        return timebase;
    }

    public void setTimebase(int timebase) {
        this.timebase = timebase;
    }

    public int getMainSrc() {
        return mainSrc;
    }

    public void setMainSrc(int mainSrc) {
        this.mainSrc = mainSrc;
    }

    public String getComportName() {
        return comportName;
    }

    public void setComportName(String comportName) {
        this.comportName = comportName;
    }

    public String getAcquisitionMode() {
        return acquisitionMode;
    }

    public void setAcquisitionMode(String acquisitionMode) {
        this.acquisitionMode = acquisitionMode;
    }

    public int[] getChannelSrcs() {
        return channelSrcs;
    }

    public void setChannelSrcs(int[] channelsSrc) {
        this.channelSrcs = channelsSrc;
    }

    public int[] getChannelProbes() {
        return channelProbes;
    }

    public void setChannelProbes(int[] channelsProbe) {
        this.channelProbes = channelsProbe;
    }

    public String[] getChannelCoups() {
        return channelCoups;
    }

    public void setChannelCoups(String[] channelsCoup) {
        this.channelCoups = channelsCoup;
    }

    public int[] getChannelScales() {
        return channelScales;
    }

    public void setChannelScales(int[] channelsScale) {
        this.channelScales = channelsScale;
    }

    public int[] getChannelPoss() {
        return channelPoss;
    }

    public void setChannelPoss(int[] channelsPos) {
        this.channelPoss = channelsPos;
    }

    public boolean[] getActiveChannels() {
        return activeChannels;
    }

    public void setActiveChannels(boolean[] activeChannels) {
        this.activeChannels = activeChannels;
    }



    public OptionsEvent (Object source) {
        super(source);
        int[] channelsSrc = new int[3];

        int[] channelsProbe = new int[3];

        String[] channelsCoup = new String[3];

        int[] channelsScale = new int[3];

        boolean[] activeChannels = new boolean[3];

        int[] channelsPos = new int[3];

        timebase = -1;

        mainSrc = -1;
    }
}
