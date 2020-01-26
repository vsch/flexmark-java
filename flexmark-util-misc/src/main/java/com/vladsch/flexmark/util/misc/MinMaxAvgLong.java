package com.vladsch.flexmark.util.misc;

public class MinMaxAvgLong {
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private long total = 0;

    public MinMaxAvgLong() {
    }

    public void add(long value) {
        total += value;
        min = Math.min(min, value);
        max = Math.max(max, value);
    }

    public void add(MinMaxAvgLong other) {
        total += other.total;
        min = Math.min(min, other.min);
        max = Math.max(max, other.max);
    }

    public void diff(long start, long end) {
        add(end - start);
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public long getTotal() {
        return total;
    }

    public long getAvg(long count) {
        return count == 0 ? 0 : total / count;
    }
}
