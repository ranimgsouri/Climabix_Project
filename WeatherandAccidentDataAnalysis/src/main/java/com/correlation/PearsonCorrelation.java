package com.correlation;

import java.util.List;

public class PearsonCorrelation extends Correlation {
    public PearsonCorrelation(String variableX, String variableY) {
        super(variableX, variableY);
    }

    @Override
    public double calculateCorrelation(List<Double> xData, List<Double> yData) {
        if (xData.size() != yData.size() || xData.isEmpty()) {
            throw new IllegalArgumentException("Data lists must have the same non-zero size.");
        }

        int n = xData.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0, sumY2 = 0;

        for (int i = 0; i < n; i++) {
            double x = xData.get(i);
            double y = yData.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }

        double numerator = n * sumXY - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        if (denominator == 0) {
            throw new ArithmeticException("Division by zero in correlation calculation.");
        }

        return numerator / denominator;
    }
}
