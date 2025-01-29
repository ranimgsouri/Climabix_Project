package com.dataprocessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Instantiate processors
        DataProcessor weatherProcessor = new WeatherDataProcessor();
        DataProcessor accidentProcessor = new AccidentDataProcessor();

        // Process weather files
        System.out.println("Processing weather data:");
        String[] weatherFiles = {
            "Databases/London Weather.csv",
            "Databases/Tunis Carthage Weather.csv"
        };
        for (String filePath : weatherFiles) {
            System.out.println("File: " + filePath);
            displayColumnNames(filePath);
            weatherProcessor.readAndIngest(filePath);
            System.out.println();
        }

        // Process accident files
        System.out.println("Processing accident data:");
        String[] accidentFiles = {
            "Databases/lieux.csv",
            "Databases/vehicules.csv",
            "Databases/usagers.csv"
        };
        for (String filePath : accidentFiles) {
            System.out.println("File: " + filePath);
            displayColumnNames(filePath);
            accidentProcessor.readAndIngest(filePath);
            System.out.println();
        }
    }

    // Method to display column names from the first row of the CSV file
    private static void displayColumnNames(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String header = br.readLine(); // Read the first line (header)
            if (header != null) {
                System.out.println("Columns: " + header);
            } else {
                System.out.println("No header found in the file.");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
    }
}
