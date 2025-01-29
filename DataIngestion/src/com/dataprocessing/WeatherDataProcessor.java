package com.dataprocessing;

public class WeatherDataProcessor implements DataProcessor {
    @Override
    public void readAndIngest(String filePath) {
        System.out.println("Reading weather data from: " + filePath);
        // Here, you can add logic to process the weather data
    }
}
