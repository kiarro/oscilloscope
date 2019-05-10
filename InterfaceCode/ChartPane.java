package ActualComport;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;


public class ChartPane extends JPanel {
    private JFreeChart chart;
    private double x;
    private XYSeries series0;
    private XYSeries series1;
    private XYSeries series2;
    private String currentMode;

    private boolean[] seriesOnChart;

    private int[] previousState;

    XYSeriesCollection dataset;

    public void clearChart(){
        series0.clear();
        series1.clear();
        series2.clear();
        dataset.removeAllSeries();
    }

    public void chartSetUp(boolean[] activeChannels){
        if(activeChannels[0]){
            if(!seriesOnChart[0]) {
                dataset.addSeries(series0);
                seriesOnChart[0] = true;
            }
        }
        else if(seriesOnChart[0]){
            dataset.removeSeries(series0);
            seriesOnChart[0] = false;
        }
        if(activeChannels[1]){
            if(!seriesOnChart[1]) {
                dataset.addSeries(series1);
                seriesOnChart[1] = true;
            }
        }
        else if(seriesOnChart[1]){
            dataset.removeSeries(series1);
            seriesOnChart[1] = false;
        }
        if(activeChannels[2]){
            if(!seriesOnChart[2]) {
                dataset.addSeries(series2);
                seriesOnChart[2] = true;
            }
        }
        else if(seriesOnChart[2]){
            dataset.removeSeries(series2);
            seriesOnChart[2] = false;
        }
    }

    private void writeData(){}

    public void addData(int chNum, int mainCh, double period, float y, String mode, boolean[] activeChannels)
    {
        chartSetUp(activeChannels);
        if(mode == currentMode) {
            switch (currentMode) {
                case ("Continuous mode"):
                    switch (chNum){
                        case(0):
                            series0.add(x, y);
                            break;
                        case(1):
                            series1.add(x, y);
                            break;
                        case(2):
                            series2.add(x, y);
                            break;
                    }
                    x += period;
                    break;
                case ("Record mode"):
                    switch (chNum) {
                        case (0):
                            series0.add(x, y);
                            break;
                        case (1):
                            series1.add(x, y);
                            break;
                        case (2):
                            series2.add(x, y);
                            break;
                    }
                    x += period;
                    writeData();
                    break;
                case ("Period mode"):
                    if((chNum == mainCh) && (y == 0)){
                        series0.clear();
                        series1.clear();
                        series2.clear();
                        x = 0;
                        break;
                    }
                    switch (chNum) {
                        case (0):
                            series0.add(x, y);
                            break;
                        case (1):
                            series1.add(x, y);
                            break;
                        case (2):
                            series2.add(x, y);
                            break;
                    }
                    x += period;
                    break;
            }
        }
        else {
            currentMode = mode;
            series0.clear();
            series1.clear();
            series2.clear();
            x = 0;
        }
        repaint();
    }

    public void changeTitle(String title){
        chart.setTitle(title);
    }

    public ChartPane(){
        x = 0;
        currentMode = null;
        series0 = new XYSeries("Channel 0 Readings");
        series1 = new XYSeries("Channel 1 Readings");
        series2 = new XYSeries("Channel 2 Readings");
        dataset = new XYSeriesCollection(series0);
        seriesOnChart = new boolean[3];
        seriesOnChart[0] = true;
        chart = ChartFactory.createXYLineChart("Oscilloscope", "Time (seconds)", "ADC Readings", dataset, PlotOrientation.VERTICAL, false,false,false);
        add(new ChartPanel(chart), BorderLayout.CENTER);
    }
}
