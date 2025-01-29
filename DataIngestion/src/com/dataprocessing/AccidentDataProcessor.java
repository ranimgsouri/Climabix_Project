package com.dataprocessing;

public class AccidentDataProcessor implements DataProcessor {
    @Override
    public void readAndIngest(String filePath) {
        System.out.println("Reading accident data from: " + filePath);
        // Here, you can add logic to process the accident data
    }
}
