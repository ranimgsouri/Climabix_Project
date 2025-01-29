package com.opencsv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException; // Import the CsvException class
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataCleaner {

    public static void main(String[] args) {
        try {
            // Folder paths
            String inputFolderPath = "C:\\Users\\ranim\\eclipse-workspace\\WeatherdataCleaner\\Databases\\";
            String outputFolderPath = "C:\\Users\\ranim\\eclipse-workspace\\WeatherdataCleaner\\Cleaned Databases\\";

            // List of files to be cleaned
            List<String> inputFiles = Arrays.asList(
                "London Weather.csv",
                "lieux.csv",
                "usagers.csv",
                "vehicules.csv",
                "Tunis Carthage Weather.csv"
            );

            // Process each file
            for (String fileName : inputFiles) {
                String inputFilePath = inputFolderPath + fileName;
                String outputFilePath = outputFolderPath + "Cleaned_" + fileName;
                cleanData(inputFilePath, outputFilePath, fileName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Clean data and save the cleaned file
    private static void cleanData(String inputFilePath, String outputFilePath, String fileName) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            List<String[]> data = reader.readAll();
            List<String[]> cleanedData = new ArrayList<>();

            // Remove empty columns
            List<Integer> emptyColumnIndexes = findEmptyColumns(data);
            for (String[] row : data) {
                String[] cleanedRow = removeEmptyColumns(row, emptyColumnIndexes);
                cleanedData.add(cleanedRow);
            }

            // Clean NaN values in all files
            cleanedData = cleanNaNValues(cleanedData);

            // Detect and remove outliers
            List<Integer> numericColumns = identifyNumericColumns(cleanedData);
            cleanedData = removeOutliers(cleanedData, numericColumns);

            // Write cleaned data
            writer.writeAll(cleanedData);
            System.out.println("Data cleaned and written to " + outputFilePath);
        }
    }

    // Find columns that are completely empty
    private static List<Integer> findEmptyColumns(List<String[]> data) {
        List<Integer> emptyColumns = new ArrayList<>();
        if (data.isEmpty()) return emptyColumns;

        int numColumns = data.get(0).length;
        for (int col = 0; col < numColumns; col++) {
            boolean isEmpty = true;
            for (String[] row : data) {
                if (row[col] != null && !row[col].trim().isEmpty()) {
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty) {
                emptyColumns.add(col);
            }
        }
        return emptyColumns;
    }

    // Remove empty columns from a row
    private static String[] removeEmptyColumns(String[] row, List<Integer> emptyColumnIndexes) {
        List<String> newRow = new ArrayList<>();
        for (int i = 0; i < row.length; i++) {
            if (!emptyColumnIndexes.contains(i)) {
                newRow.add(row[i]);
            }
        }
        return newRow.toArray(new String[0]);
    }

    // Identify numeric columns based on data
    private static List<Integer> identifyNumericColumns(List<String[]> data) {
        List<Integer> numericColumns = new ArrayList<>();
        if (data.isEmpty()) return numericColumns;

        // Check the first row (header) to identify numeric columns
        String[] header = data.get(0);
        for (int i = 0; i < header.length; i++) {
            try {
                Double.parseDouble(header[i]);  // Try to parse as numeric
                numericColumns.add(i);
            } catch (NumberFormatException e) {
                // Ignore non-numeric columns
            }
        }
        return numericColumns;
    }

    // Remove outliers from numeric columns
    private static List<String[]> removeOutliers(List<String[]> data, List<Integer> numericColumns) {
        List<String[]> cleanedData = new ArrayList<>();
        for (String[] row : data) {
            boolean isOutlier = false;
            for (int col : numericColumns) {
                try {
                    double value = Double.parseDouble(row[col]);
                    if (isOutlier(value)) {
                        isOutlier = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    // Skip if the value is not numeric
                }
            }
            if (!isOutlier) {
                cleanedData.add(row);
            }
        }
        return cleanedData;
    }

    // Simple outlier detection based on IQR (Interquartile Range)
    private static boolean isOutlier(double value) {
        double lowerBound = 0; // Define as needed based on your dataset
        double upperBound = 1000; // Define as needed based on your dataset
        return value < lowerBound || value > upperBound;
    }

    // Clean NaN values from a dataset
    private static List<String[]> cleanNaNValues(List<String[]> data) {
        List<String[]> cleanedData = new ArrayList<>();
        for (String[] row : data) {
            String[] cleanedRow = new String[row.length];
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && row[i].equalsIgnoreCase("NaN")) {
                    cleanedRow[i] = "";  // Replace NaN with an empty string or another default value
                } else {
                    cleanedRow[i] = row[i];
                }
            }
            cleanedData.add(cleanedRow);
        }
        return cleanedData;
    }
}
