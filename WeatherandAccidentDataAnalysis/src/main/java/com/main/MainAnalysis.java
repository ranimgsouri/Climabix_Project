package com.main;

import com.regression_analysis.DataPreprocessor;
import com.regression_analysis.GravWeatherAnalysis;

import java.util.ArrayList;
import java.util.List;

public class MainAnalysis {

    public static void main(String[] args) {
        // File paths
        String usagersFile = "Cleaned Databases/Cleaned_usagers.csv";
        String londonWeatherFile = "Cleaned Databases/Cleaned_London Weather.csv";
        String tunisWeatherFile = "Cleaned Databases/Cleaned_Tunis Carthage Weather.csv";

        // Columns to analyze
        String[] usagersColumns = {"grav"};
        String[] londonColumns = {"pressure", "temp", "sea_level", "wind_speed"};
        String[] tunisColumns = {"Visibility_km", "Press_kPa"};

        // Preprocess data
        List<Double> grav = DataPreprocessor.preprocessData(usagersFile, usagersColumns, 814).stream()
                .map(row -> row.get(0)) // Extract grav column
                .toList();

        List<List<Double>> londonData = DataPreprocessor.preprocessData(londonWeatherFile, londonColumns, 814);
        List<List<Double>> tunisData = DataPreprocessor.preprocessData(tunisWeatherFile, tunisColumns, 814);

        // Combine weather data
        List<List<Double>> combinedWeatherData = new ArrayList<>();
        for (int i = 0; i < 814; i++) {
            List<Double> combinedRow = new ArrayList<>();
            combinedRow.addAll(londonData.get(i));
            combinedRow.addAll(tunisData.get(i));
            combinedWeatherData.add(combinedRow);
        }

        // Analyze relationship
        GravWeatherAnalysis.analyzeGravWeather(grav, combinedWeatherData);
    }
}
