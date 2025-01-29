package com.visualization;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Gaussian Curve Visualizations
            analyzeCSVForGaussian("Cleaned Databases/Cleaned_vehicules.csv", new String[]{"choc"});
            analyzeCSVForGaussian("Cleaned Databases/Cleaned_lieux.csv", new String[]{"prof"});
            analyzeCSVForGaussian("Cleaned Databases/Cleaned_London Weather.csv", new String[]{"sea_level"});
            analyzeCSVForGaussian("Cleaned Databases/Cleaned_Tunis Carthage Weather.csv", new String[]{"Temp_C"});
            analyzeCSVForGaussian("Cleaned Databases/Cleaned_usagers.csv", new String[]{"place"});

            // Pie Chart Visualizations
            analyzeCSVForPieChart("Cleaned Databases/Cleaned_vehicules.csv", "catv");
            analyzeCSVForPieChart("Cleaned Databases/Cleaned_usagers.csv", "sexe");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analyzeCSVForGaussian(String filePath, String[] columns) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();

        try (CSVParser parser = format.parse(new FileReader(filePath))) {
            System.out.println("Headers in file: " + parser.getHeaderNames());

            for (String column : columns) {
                if (!parser.getHeaderNames().contains(column)) {
                    System.out.printf("Column '%s' not found in file '%s'. Skipping.%n", column, filePath);
                    continue;
                }

                List<Double> values = new ArrayList<>();
                for (CSVRecord record : parser) {
                    // Check if the record has enough fields
                    if (record.size() <= parser.getHeaderMap().get(column)) {
                        System.out.printf("Skipping incomplete row: %s%n", record);
                        continue;
                    }

                    String value = record.get(column);
                    if (value != null && !value.isEmpty()) {
                        try {
                            values.add(Double.parseDouble(value));
                        } catch (NumberFormatException e) {
                            System.out.printf("Invalid number format for value '%s' in column '%s'. Skipping.%n", value, column);
                        }
                    }
                }

                if (values.isEmpty()) {
                    System.out.printf("No valid data found for column '%s' in file '%s'. Skipping.%n", column, filePath);
                    continue;
                }

                // Create Gaussian Curve Visualization
                String outputFileName = column + "_gaussian_curve.png";
                VisualizationTool gaussianVisualization = new GaussianCurveVisualization(values, outputFileName);
                gaussianVisualization.createVisualization();
                gaussianVisualization.displayVisualization();
            }
        }
    }


    private static void analyzeCSVForPieChart(String filePath, String column) throws IOException {
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader();

        try (CSVParser parser = format.parse(new FileReader(filePath))) {
            Map<String, Integer> frequencyMap = new HashMap<>();
            for (CSVRecord record : parser) {
                String value = record.get(column);
                if (value != null && !value.isEmpty()) {
                    frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
                }
            }
            VisualizationTool pieChartVisualization = new PieChartVisualization(frequencyMap, column + "_pie_chart.png");
            pieChartVisualization.createVisualization();
            pieChartVisualization.displayVisualization();
        }
    }
}
