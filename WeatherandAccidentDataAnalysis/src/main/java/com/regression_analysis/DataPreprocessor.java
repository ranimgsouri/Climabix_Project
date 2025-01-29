package com.regression_analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class DataPreprocessor {

    public static List<List<Double>> preprocessData(String filePath, String[] selectedColumns, int rowLimit) {
        List<List<Double>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header
            String[] headers = line.split(",");

            // Identify column indices
            int[] columnIndices = new int[selectedColumns.length];
            for (int i = 0; i < selectedColumns.length; i++) {
                for (int j = 0; j < headers.length; j++) {
                    if (headers[j].equals(selectedColumns[i])) {
                        columnIndices[i] = j;
                        break;
                    }
                }
            }

            // Extract data for selected columns
            int rowCount = 0;
            while ((line = br.readLine()) != null && rowCount < rowLimit) {
                String[] values = line.split(",");
                List<Double> row = new ArrayList<>();
                for (int index : columnIndices) {
                    try {
                        row.add(Double.parseDouble(values[index]));
                    } catch (NumberFormatException e) {
                        row.add(null); // Handle missing/invalid values
                    }
                }
                data.add(row);
                rowCount++;
            }
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        return data;
    }
}
