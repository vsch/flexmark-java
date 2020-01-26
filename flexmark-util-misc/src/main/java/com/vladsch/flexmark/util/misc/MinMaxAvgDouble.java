package com.vladsch.flexmark.util.misc;

public class MinMaxAvgDouble {
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double total = 0.0;

    public MinMaxAvgDouble() {
    }

    public void add(double value) {
        total += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public void add(MinMaxAvgDouble other) {
        total += other.total;
        min = Math.min(min, other.min);
        max = Math.max(max, other.max);
    }

    public void diff(double start, double end) {
        add(end - start);
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getTotal() {
        return total;
    }

    public double getAvg(double count) {
        return count == 0 ? 0 : total / count;
    }
}
