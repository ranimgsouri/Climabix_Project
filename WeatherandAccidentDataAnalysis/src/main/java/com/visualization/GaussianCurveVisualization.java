package com.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GaussianCurveVisualization implements VisualizationTool {
    private List<Double> values;
    private String outputFileName;

    public GaussianCurveVisualization(List<Double> values, String outputFileName) {
        this.values = values;
        this.outputFileName = outputFileName;
    }

    @Override
    public void createVisualization() {
        // Create a histogram dataset
        HistogramDataset dataset = new HistogramDataset();
        double[] data = values.stream().mapToDouble(Double::doubleValue).toArray();
        dataset.addSeries("Frequency", data, 20); // 20 bins

        // Create histogram
        JFreeChart histogram = ChartFactory.createHistogram(
            "Gaussian Curve",
            "Values",
            "Frequency",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        );

        // Overlay Gaussian curve
        XYPlot plot = (XYPlot) histogram.getPlot();
        double mean = values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double stdDev = Math.sqrt(values.stream().mapToDouble(v -> Math.pow(v - mean, 2)).sum() / values.size());
        double step = (Collections.max(values) - Collections.min(values)) / 100;

        XYSeries gaussianSeries = new XYSeries("Gaussian Curve");
        for (double x = Collections.min(values); x <= Collections.max(values); x += step) {
            double y = (1 / (stdDev * Math.sqrt(2 * Math.PI))) * Math.exp(-0.5 * Math.pow((x - mean) / stdDev, 2));
            gaussianSeries.add(x, y * values.size());
        }

        XYSeriesCollection gaussianDataset = new XYSeriesCollection(gaussianSeries);
        plot.setDataset(1, gaussianDataset);
        plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));

        // Save chart as PNG
        try {
            File chartFile = new File("Generated_Graphs/" + outputFileName);
            ImageIO.write(histogram.createBufferedImage(800, 600), "PNG", chartFile);
            System.out.printf("Graph saved as %s.%n", chartFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.printf("Error saving graph: %s%n", e.getMessage());
        }
    }

    @Override
    public void displayVisualization() {
        System.out.println("Gaussian curve visualization created: " + outputFileName);
    }
}
