package com.vladsch.flexmark.util.misc;

public class MinMaxAvgInt {
    private int min = Integer.MAX_VALUE;
    private int max = Integer.MIN_VALUE;
    private int total = 0;

    public MinMaxAvgInt() {
    }

    public void add(int value) {
        total += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public void add(MinMaxAvgInt other) {
        total += other.total;
        min = Math.min(min, other.min);
        max = Math.max(max, other.max);
    }

    public void diff(int start, int end) {
        add(end - start);
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getTotal() {
        return total;
    }

    public int getAvg(int count) {
        return count == 0 ? 0 : total / count;
    }
}
