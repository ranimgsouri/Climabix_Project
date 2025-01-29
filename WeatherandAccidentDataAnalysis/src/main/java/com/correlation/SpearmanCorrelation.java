package com.correlation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpearmanCorrelation extends Correlation {

    public SpearmanCorrelation(String variableX, String variableY) {
        super(variableX, variableY);
    }

    @Override
    public double calculateCorrelation(List<Double> xData, List<Double> yData) {
        if (xData.size() != yData.size()) {
            throw new IllegalArgumentException("Datasets must have the same size");
        }

        int n = xData.size();
        List<Double> xRanks = calculateRanks(xData); // Calculate ranks for X
        List<Double> yRanks = calculateRanks(yData); // Calculate ranks for Y

        double sumD2 = 0.0;
        for (int i = 0; i < n; i++) {
            double d = xRanks.get(i) - yRanks.get(i);
            sumD2 += d * d; // Compute the sum of squared rank differences
        }

        // Spearman correlation formula
        return 1.0 - (6.0 * sumD2) / (n * (n * n - 1));
    }

    /**
     * Helper method to calculate ranks for a dataset, including handling ties.
     */
    private List<Double> calculateRanks(List<Double> data) {
        int n = data.size();
      
        List<Double> sorted = new ArrayList<>(data); // Make a copy to sort
        Collections.sort(sorted);

        Map<Double, Double> rankMap = new HashMap<>(); // To store ranks for each value
        for (int i = 0; i < n; i++) {
            double value = sorted.get(i);
            if (!rankMap.containsKey(value)) {
                int start = i;
                while (i + 1 < n && sorted.get(i + 1).equals(value)) {
                    i++;
                }
                // Compute the average rank for tied values
                double avgRank = (start + i + 2.0) / 2.0; 
                for (int j = start; j <= i; j++) {
                    rankMap.put(sorted.get(j), avgRank);
                }
            }
        }

        // Map original data values to their ranks
        List<Double> ranks = new ArrayList<>();
        for (double value : data) {
            ranks.add(rankMap.get(value));
        }
        
        return ranks;
    }
}

