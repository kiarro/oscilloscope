package ActualComport;

import java.util.EventObject;

public class OptionsEvent extends EventObject {

    private boolean export = false;
    private boolean active = false;

    private boolean[] activeChannels = new boolean[4];

    private int period = -1;
    private int mainSrc = -1;

    private float maxVoltage = -1;
    private float midVoltage = -1;

    private int[] channelSrcs = new int[4];
    private int[] channelProbes = new int[4];
    private float[] channelScales = new float[4];
    private int[] channelPoss = new int[4];

    private String receiveMode = null;
    private String file_path = null;
    private String edge = null;
    private String priority = null;

    private String[] channelCoups;

    public float getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(float maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public float getMidVoltage() {
        return midVoltage;
    }

    public void setMidVoltage(float midVoltage) {
        this.midVoltage = midVoltage;
    }

    public String getEdge() {
        return edge;
    }

    public void setEdge(String edge) {
        this.edge = edge;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public boolean isExport() {
        return export;
    }

    public void setExport(boolean export) {
        this.export = export;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getMainSrc() {
        return mainSrc;
    }

    public void setMainSrc(int mainSrc) {
        this.mainSrc = mainSrc;
    }

    public String getReceiveMode() {
        return receiveMode;
    }

    public void setReceiveMode(String receiveMode) {
        this.receiveMode = receiveMode;
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

    public float[] getChannelScales() {
        return channelScales;
    }

    public void setChannelScales(float[] channelsScale) {
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
    }
}
