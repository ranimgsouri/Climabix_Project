package com.Weather_and_Accident_Analysis;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import javax.imageio.ImageIO;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.data.statistics.HistogramDataset;

public class WeatherandAccidentDataAnalysis {

    public static void main(String[] args) {
        try {
            // Calculate for cleaned_vehicules.csv
            analyzeCSV("Cleaned Databases/Cleaned_vehicules.csv", new String[]{"choc"});
            // Calculate for cleaned_lieux.csv
            analyzeCSV("Cleaned Databases/Cleaned_lieux.csv", new String[]{"prof"}); // Adjust column names as required
            // Calculate for Cleaned_London Weather.csv
            analyzeCSV("Cleaned Databases/Cleaned_London Weather.csv", new String[]{"sea_level"});
            // Calculate for Cleaned_Tunis Carthage Weather.csv
            analyzeCSV("Cleaned Databases/Cleaned_Tunis Carthage Weather.csv", new String[]{"Temp_C"});
            analyzeCSV("Cleaned Databases/Cleaned_usagers.csv", new String[]{"place"});// Adjust column names as required
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeCSV(String filePath, String[] columns) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader(); // Adjust delimiter if needed

        try (CSVParser parser = format.parse(new FileReader(filePath))) {
            System.out.println("Headers in file: " + parser.getHeaderNames());

            for (String column : columns) {
                if (!parser.getHeaderNames().contains(column)) {
                    System.out.printf("Column '%s' not found in file '%s'. Skipping.%n", column, filePath);
                    continue;
                }

                List<Double> values = new ArrayList<>();
                for (CSVRecord record : parser) {
                    if (record.size() <= parser.getHeaderMap().get(column)) {
                        System.out.printf("Skipping incomplete row: %s%n", record);
                        continue;
                    }

                    String valueStr = record.get(column);
                    if (valueStr != null && !valueStr.isEmpty()) {
                        try {
                            values.add(Double.parseDouble(valueStr));
                        } catch (NumberFormatException e) {
                            System.out.printf("Invalid number format for value '%s' in column '%s'. Skipping.%n", valueStr, column);
                        }
                    }
                }

                if (values.isEmpty()) {
                    System.out.printf("No valid data found for column '%s' in file '%s'. Skipping.%n", column, filePath);
                    continue;
                }

                // Calculate Mean and Median
                double mean = calculateMean(values);
                double median = calculateMedian(values);

                // Output results
                System.out.printf("File: %s, Column: %s, Mean: %.2f, Median: %.2f%n", filePath, column, mean, median);

                
            }
        }
    }

    private static double calculateMean(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private static double calculateMedian(List<Double> values) {
        if (values.isEmpty()) {
            throw new IllegalArgumentException("Cannot calculate median for an empty list.");
        }

        Collections.sort(values);
        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
        } else {
            return values.get(size / 2);
        }
    }

    
}