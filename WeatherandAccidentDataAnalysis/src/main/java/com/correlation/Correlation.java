package com.correlation;

import java.util.List;

public abstract class Correlation {
    protected String variableX;
    protected String variableY;

    public Correlation(String variableX, String variableY) {
        this.variableX = variableX;
        this.variableY = variableY;
    }

    public abstract double calculateCorrelation(List<Double> xData, List<Double> yData);

    public String getVariableX() {
        return variableX;
    }

    public String getVariableY() {
        return variableY;
    }
}
