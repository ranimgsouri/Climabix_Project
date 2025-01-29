package com.correlation;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // List of file paths for the cleaned databases
        String[] filePaths = {
            "Cleaned Databases/Cleaned_London Weather.csv",
            "Cleaned Databases/Cleaned_Tunis Carthage Weather.csv",
            "Cleaned Databases/Cleaned_usagers.csv",
            "Cleaned Databases/Cleaned_lieux.csv",
            "Cleaned Databases/Cleaned_vehicules.csv"
        };
        processSpearmanCorrelation(
                "Cleaned Databases/Cleaned_usagers.csv", 
                 
        
                "grav", 
                "place"
            );


        // Process each file for specific correlations
        for (String filePath : filePaths) {
            System.out.printf("Processing file: %s%n", filePath);

            // Calculate Correlation for Var1 and Var2
            processCorrelation(filePath, new CorrelationBetweenVar1Var2());

            // Calculate Correlation for Var3 and Var4
            processCorrelation(filePath, new CorrelationBetweenVar3Var4());
        }

      
    }

    private static void processCorrelation(String filePath, PearsonCorrelation correlation) {
        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read the header
            int xIndex = -1, yIndex = -1;

            // Identify column indices
            if (line != null) {
                String[] headers = line.split(",");
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].equals(correlation.getVariableX())) {
                        xIndex = i;
                    }
                    if (headers[i].equals(correlation.getVariableY())) {
                        yIndex = i;
                    }
                }
            }

            if (xIndex == -1 || yIndex == -1) {
                // Suppress message when column names are not found
                return; // Exit silently
            }

            // Read data
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > xIndex && values.length > yIndex) { // Ensure indices are valid
                    try {
                        xData.add(Double.parseDouble(values[xIndex]));
                        yData.add(Double.parseDouble(values[yIndex]));
                    } catch (NumberFormatException e) {
                        // Suppress invalid numeric data message
                    }
                }
            }
        } catch (Exception e) {
            // Suppress general file reading errors
            return;
        }

        if (xData.isEmpty() || yData.isEmpty()) {
            // Suppress empty data message
            return;
        }

        // Calculate and display the correlation
        double result = correlation.calculateCorrelation(xData, yData);
        System.out.printf("Correlation between %s and %s: %.4f%n", correlation.getVariableX(), correlation.getVariableY(), result);

        // Generate scatter plot
        generateScatterPlot(xData, yData, correlation.getVariableX(), correlation.getVariableY(), result);
    }

    private static void processSpearmanCorrelation(String filePath, String variableX, String variableY) {
        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read the header
            int xIndex = -1, yIndex = -1;

            // Identify column indices
            if (line != null) {
                String[] headers = line.split(",");
                for (int i = 0; i < headers.length; i++) {
                    if (headers[i].equals(variableX)) {
                        xIndex = i;
                    }
                    if (headers[i].equals(variableY)) {
                        yIndex = i;
                    }
                }
            }

            if (xIndex == -1 || yIndex == -1) {
                System.err.printf("Columns %s or %s not found in the file: %s%n", variableX, variableY, filePath);
                return;
            }

            // Read data
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length > xIndex && values.length > yIndex) { // Ensure indices are valid
                    try {
                        xData.add(Double.parseDouble(values[xIndex]));
                        yData.add(Double.parseDouble(values[yIndex]));
                    } catch (NumberFormatException e) {
                        // Skip invalid numeric data
                    }
                }
            }
        } catch (Exception e) {
            System.err.printf("Error reading the file %s: %s%n", filePath, e.getMessage());
            return;
        }

        if (xData.isEmpty() || yData.isEmpty()) {
            System.err.printf("No valid data found for columns %s and %s in file: %s%n", variableX, variableY, filePath);
            return;
        }

        // Calculate and display the Spearman correlation
        SpearmanCorrelation spearmanCorrelation = new SpearmanCorrelation(variableX, variableY);
        double result = spearmanCorrelation.calculateCorrelation(xData, yData);
        System.out.printf("Spearman Correlation between %s and %s: %.4f%n", variableX, variableY, result);

        // Generate scatter plot
        generateScatterPlot(xData, yData, variableX, variableY, result);}
    
    private static void generateScatterPlot1(List<Double> xData, List<Double> yData, String xLabel, String yLabel, double correlation) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Scatter Plot with Spearman Correlation")
                .xAxisTitle(xLabel)
                .yAxisTitle(yLabel)
                .build();

        // Add data
        chart.addSeries("Data Points", xData, yData);

        // Display correlation in the title
        chart.setTitle(String.format("Scatter Plot: Spearman Correlation = %.4f", correlation));

        // Save the chart as an image file
        try {
            String projectDir = System.getProperty("user.dir");
            String outputPath = projectDir + File.separator + "Generated_Graphs" + File.separator + xLabel + "_vs_" + yLabel + "_spearman_correlation.png";
            File outputDir = new File(projectDir + File.separator + "Generated_Graphs");
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // Create directory if it doesn't exist
            }
            BitmapEncoder.saveBitmap(chart, outputPath, BitmapFormat.PNG);
            System.out.println("Chart saved to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error saving the chart: " + e.getMessage());
        }

        // Optionally, display the chart
        new SwingWrapper<>(chart).displayChart();
    }

    private static void generateScatterPlot(List<Double> xData, List<Double> yData, String xLabel, String yLabel, double correlation) {
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Scatter Plot with Correlation")
                .xAxisTitle(xLabel)
                .yAxisTitle(yLabel)
                .build();

        // Add data
        chart.addSeries("Data Points", xData, yData);

        // Display correlation in the title
        chart.setTitle(String.format("Scatter Plot: Correlation = %.4f", correlation));

        // Save the chart as an image file
        try {
            String projectDir = System.getProperty("user.dir");
            String outputPath = projectDir + File.separator + "Generated_Graphs" + File.separator + xLabel + "_vs_" + yLabel + "_correlation.png";
            File outputDir = new File(projectDir + File.separator + "Generated_Graphs");
            if (!outputDir.exists()) {
                outputDir.mkdirs(); // Create directory if it doesn't exist
            }
            BitmapEncoder.saveBitmap(chart, outputPath, BitmapFormat.PNG);
            System.out.println("Chart saved to: " + outputPath);
        } catch (IOException e) {
            System.err.println("Error saving the chart: " + e.getMessage());
        }

        // Optionally, display the chart
        new SwingWrapper<>(chart).displayChart();
    }


    }
