package ActualComport;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;


public class ChartPane extends JPanel {
    private JFreeChart chart;
    private double x = 0;
    private double prevY = 1;
    private double period = 10;
    private double freq = 1;

    private float midValue = -1;
    private float maxValue = -1;

    private String edge = null;
    private String priority = null;

    private int mainCh = -1;
    private int[] readingAddition = new int[4];

    private float[] readingScales = new float[4];

    private boolean borderOnChart = false;
    private boolean centerAxisOnChart = false;

    private boolean[] seriesOnChart = new boolean[4];

    private XYSeries[] series = new XYSeries[4];
    private XYSeries borderSeries;
    private XYSeries centerAxis;

    private XYSeriesCollection dataset = new XYSeriesCollection();

    public void clearChart(){
        dataset.removeAllSeries();
        for(int i = 0; i < seriesOnChart.length; i++)
        {
            seriesOnChart[i] = false;
        }
        borderOnChart = false;
        centerAxisOnChart = false;
        x = 0;
    }

    private void restartBorder() {
        borderSeries.clear();
        centerAxis.clear();
        centerAxis.add(0, midValue);
        centerAxis.add(period, midValue);
        borderSeries.add(0, maxValue);
        borderSeries.add(period, maxValue);
    }

    public void setOscilloscope(double period, String priority, String edge, float midValue, float maxValue){
        this.edge = edge;
        this.priority = priority;

        this.period = period;
        this.midValue = midValue;
        this.maxValue = maxValue;
        restartBorder();

        if(!centerAxisOnChart){
            centerAxisOnChart = true;
            dataset.addSeries(centerAxis);
        }

        if(!borderOnChart){
            borderOnChart = true;
            dataset.addSeries(borderSeries);
        }
    }

    public void chartSetUp(boolean[] activeChannels, double freq, int mainCh, int[] additions, float[] scales){
        seriesPresent(activeChannels);
        this.freq = freq;
        this.mainCh = mainCh;
        readingScales = scales;
        readingAddition = additions;
    }

    public void addStraight(float[] readings){
        for(int i = 0; i < seriesOnChart.length; i++)
            if (seriesOnChart[i]){
                try {
                    System.out.println(readings[i]*readingScales[i] + readingAddition[i]);
                    series[i].add(x, readings[i]*readingScales[i] + readingAddition[i]);
            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                }
            }
        x += freq;
    }

    public void addPeriodic(float[] readings){
            switch (priority){
                case ("Период"):
                    if(x <= period) {
                        for(int i = 0; i < seriesOnChart.length; i++)
                            if (seriesOnChart[i]) {
                                try {
                                    if (readings[i]*readingScales[i] + readingAddition[i] > maxValue)
                                        series[i].add(x, maxValue);
                                    else series[i].add(x, readings[i]*readingScales[i] + readingAddition[i]);
                                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                                }
                            }
                    }
                    else {
                        switch (edge){
                            case("-+"):
                                if((prevY < 0) && (readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue >= 0)) {
                                    restartChart();
                                    for(int i = 0; i < seriesOnChart.length; i++)
                                        if (seriesOnChart[i]) {
                                            try {
                                                if (x <= period) {
                                                    if (readings[i] * readingScales[i] + readingAddition[i] > maxValue)
                                                        series[i].add(x, maxValue);
                                                    else series[i].add(x, readings[i] * readingScales[i] + readingAddition[i]);
                                                }
                                            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {

                                            }
                                        }
                                }
                                try {
                                    prevY = readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue;
                                } catch (NullPointerException | IndexOutOfBoundsException e) {
                                    System.out.println("kek");
                                }
                                break;
                            case("+-"):
                                if((prevY > 0) && (readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue <= 0)) {
                                    restartChart();
                                    for(int i = 0; i < seriesOnChart.length; i++)
                                        if (seriesOnChart[i]) {
                                            try {
                                                if (x <= period) {
                                                    if (readings[i] * readingScales[i] + readingAddition[i] > maxValue)
                                                        series[i].add(x, maxValue);
                                                    else series[i].add(x, readings[i] * readingScales[i] + readingAddition[i]);
                                                }
                                            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {

                                            }
                                        }
                                }
                                try {
                                    prevY = readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue;
                                } catch (NullPointerException | IndexOutOfBoundsException e) {
                                    System.out.println("kek");
                                }
                                break;
                        }
                    }
                    break;
                case ("Край"):
                    for(int i = 0; i < seriesOnChart.length; i++)
                        if (seriesOnChart[i]) {
                            try {
                                if (x <= period) {
                                    if (readings[i] * readingScales[i] + readingAddition[i] > maxValue)
                                        series[i].add(x, maxValue);
                                    else series[i].add(x, readings[i] * readingScales[i] + readingAddition[i]);
                                }
                                else {
                                    restartChart();
                                    for(int j = 0; j < seriesOnChart.length; j++)
                                        if (seriesOnChart[j]) {
                                            try {
                                                if (x <= period) {
                                                    if (readings[j] * readingScales[j] + readingAddition[j] > maxValue)
                                                        series[j].add(x, maxValue);
                                                    else series[j].add(x, readings[j] * readingScales[j] + readingAddition[j]);
                                                }
                                            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {

                                            }
                                        }
                                }
                            } catch (IllegalArgumentException | IndexOutOfBoundsException e) {

                            }
                        }
                    switch (edge){
                        case("-+"):
                            if((prevY < 0) && (readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue >= 0)) {
                                restartChart();
                                for(int i = 0; i < seriesOnChart.length; i++)
                                    if (seriesOnChart[i]) {
                                        try {
                                            if (x <= period) {
                                                if (readings[i] * readingScales[i] + readingAddition[i] > maxValue)
                                                    series[i].add(x, maxValue);
                                                else series[i].add(x, readings[i] * readingScales[i] + readingAddition[i]);
                                            }
                                        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {

                                        }
                                    }
                            }
                            try {
                                prevY = readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue;
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                System.out.println("kek");
                            }
                            break;
                        case("+-"):
                            if((prevY > 0) && (readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue <= 0)) {
                                restartChart();
                                for(int i = 0; i < seriesOnChart.length; i++)
                                    if (seriesOnChart[i]) {
                                        try {
                                            if (x <= period) {
                                                if (readings[i] * readingScales[i] + readingAddition[i] > maxValue)
                                                    series[i].add(x, maxValue);
                                                else series[i].add(x, readings[i] * readingScales[i] + readingAddition[i]);
                                            }
                                        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {

                                        }
                                    }
                            }
                            try {
                                prevY = readings[mainCh]*readingScales[mainCh] + readingAddition[mainCh] - midValue;
                            } catch (NullPointerException | IndexOutOfBoundsException e) {
                                System.out.println("kek");
                            }
                            break;
                    }
                    break;
            }
//        if((prevY < 0) && (readings[mainCh] >= 0)) {
//            restartChart();
//        }
//        try {
//            prevY = readings[mainCh];
//        } catch (NullPointerException | IndexOutOfBoundsException e) {
//            System.out.println("kek");
//        }
        x += freq;

    }

    public void changeTitle(String title){
        chart.setTitle(title);
    }

    public ArrayList<ArrayList<String>> getGraphs(){
        ArrayList<ArrayList<String>> table = new ArrayList<>(4);
        for(int i=0; i < 4; i++) {
            table.add(new ArrayList());
        }
        for (int i = 0; i < seriesOnChart.length; i++)
        {
            for(int j = 0; j < series[i].getItemCount(); j++) {
                table.get(i).add(series[i].getItems().get(j).toString());
            }
        }
        return table;
    }


    public void restartChart() {
        for(int i = 0; i < series.length; i++)
            series[i].clear();
        restartBorder();
        x = 0;
    }

    private void seriesPresent(boolean[] activeChannels) {
        for(int i = 0; i < activeChannels.length; i++)
        {
            if(activeChannels[i]){
                if(!seriesOnChart[i]) {
                    dataset.addSeries(series[i]);
                    seriesOnChart[i] = true;
                }
            }
            else {
                if(seriesOnChart[i]){
                    dataset.removeSeries(series[i]);
                    seriesOnChart[i] = false;
                }
            }
        }
    }

    public ChartPane(){
        for(int i = 0; i < 4; i++)
        {
            series[i] = new XYSeries("Channel " + i + " Readings");
        }
        borderSeries = new XYSeries("Border");
        centerAxis = new XYSeries("MidValue");
        chart = ChartFactory.createXYLineChart("Осциллограф", "Время (Миллисекунды)",
                "Показания АЦП", dataset, PlotOrientation.VERTICAL,
                true,false,false);
        add(new ChartPanel(chart), BorderLayout.CENTER);
    }
}
