package com.vladsch.flexmark.util;

public class MinMaxAvgFloat {
    private float min;
    private float max;
    private float total;

    public MinMaxAvgFloat() {
    }

    public void add(float value) {
        total += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public void add(MinMaxAvgFloat other) {
        total += other.total;
        min = Math.min(min, other.min);
        max = Math.max(max, other.max);
    }

    public void diff(float start, float end) {
        add(end - start);
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public float getTotal() {
        return total;
    }

    public float getAvg(float count) {
        return count == 0 ? 0 : total / count;
    }
}
