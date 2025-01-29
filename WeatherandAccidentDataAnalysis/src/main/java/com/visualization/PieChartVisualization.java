package com.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PieChartVisualization implements VisualizationTool {
    private Map<String, Integer> data;
    private String outputFileName;

    public PieChartVisualization(Map<String, Integer> data, String outputFileName) {
        this.data = data;
        this.outputFileName = outputFileName;
    }

    @Override
    public void createVisualization() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
            "Pie Chart",
            dataset,
            true,
            true,
            false
        );

        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSimpleLabels(true);

        // Save chart as PNG
        try {
            File chartFile = new File("Generated_Graphs/" + outputFileName);
            ImageIO.write(pieChart.createBufferedImage(800, 600), "PNG", chartFile);
            System.out.printf("Graph saved as %s.%n", chartFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.printf("Error saving graph: %s%n", e.getMessage());
        }
    }

    @Override
    public void displayVisualization() {
        System.out.println("Pie chart visualization created: " + outputFileName);
    }
}
