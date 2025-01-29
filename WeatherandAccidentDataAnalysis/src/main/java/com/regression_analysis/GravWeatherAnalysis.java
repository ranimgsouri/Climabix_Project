package com.regression_analysis;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.List;

public class GravWeatherAnalysis {

    public static void analyzeGravWeather(List<Double> grav, List<List<Double>> weatherData) {
        int n = grav.size();
        int p = weatherData.get(0).size();

        // Prepare data for regression
        double[][] x = new double[n][p];
        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = grav.get(i);
            for (int j = 0; j < p; j++) {
                x[i][j] = weatherData.get(i).get(j);
            }
        }

        // Perform regression analysis
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);

        double[] coefficients = regression.estimateRegressionParameters();

        System.out.println("Regression coefficients:");
        for (int i = 0; i < coefficients.length; i++) {
            System.out.printf("Coefficient %d: %.4f%n", i, coefficients[i]);
        }
    }
}
