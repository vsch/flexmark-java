package com.vladsch.flexmark.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SegmentedSequenceStats {
    public static class StatsEntry implements Comparable<StatsEntry> {
        final int segments;
        int count;
        final MinMaxAvgInt nonBased = new MinMaxAvgInt();
        final MinMaxAvgInt length = new MinMaxAvgInt();
        final MinMaxAvgInt start = new MinMaxAvgInt();
        final MinMaxAvgInt end = new MinMaxAvgInt();
        final MinMaxAvgInt span = new MinMaxAvgInt();
        final MinMaxAvgInt overhead = new MinMaxAvgInt();
        final MinMaxAvgFloat pct = new MinMaxAvgFloat();

        public StatsEntry(int segments) {
            this.segments = segments;
        }

        public void add(int nonBased, int length, int start, int end) {
            count++;
            this.nonBased.add(nonBased);
            this.length.add(length);
            this.start.add(start);
            this.end.add(end);
            this.span.add(end - start);
            int overhead = 4 * (length + 1) + 4 * nonBased;
            this.overhead.add(overhead);
            this.pct.add(length == 0 ? 0f : 100.0f * overhead / (4 * length));
        }

        public void add(@NotNull StatsEntry other) {
            count += other.count;
            this.nonBased.add(other.nonBased);
            this.length.add(other.length);
            this.start.add(other.start);
            this.end.add(other.end);
            this.span.add(other.span);
            this.overhead.add(other.overhead);
            this.pct.add(other.pct);
        }

        @Override
        public int compareTo(@NotNull SegmentedSequenceStats.StatsEntry o) {
            int segs = Integer.compare(segments, o.segments);
            if (segs != 0) return segs;
            else return Integer.compare(count, o.count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return segments == ((StatsEntry) o).segments;
        }

        @Override
        public int hashCode() {
            return segments;
        }
    }

    final private HashMap<StatsEntry, StatsEntry> myStats = new HashMap<>();

    private SegmentedSequenceStats() {

    }

    public void addStats(int segments, int nonBasedChars, int length, int startOffset, int endOffset) {
        StatsEntry entry = new StatsEntry(segments);
        entry = myStats.computeIfAbsent(entry, k -> k);
        entry.add(nonBasedChars, length, startOffset, endOffset);
    }

    public int getCount(int segments, int nonBasedChars, int length, int startOffset, int endOffset) {
        StatsEntry entry = new StatsEntry(segments);
        if (myStats.containsKey(entry)) {
            return myStats.get(entry).count;
        }
        return 0;
    }

    @NotNull
    public String getStatsText(List<StatsEntry> entries) {
        StringBuilder out = new StringBuilder();
        int iMax = entries.size();

        out.append(
                String.format("%10s,%10s,%5s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s",
                        "count",
                        "seg",
                        "pct",
                        "min-ovr",
                        "avg-ovr",
                        "max-ovr",
                        "min-non",
                        "avg-non",
                        "max-non",
                        "min-len",
                        "avg-len",
                        "max-len",
                        "min-span",
                        "avg-span",
                        "max-span",
                        "min-start",
                        "avg-start",
                        "max-start",
                        "min-end",
                        "avg-end",
                        "max-end"
                )
        ).append("\n");


        for (int i = iMax; i-- > 0; ) {
            StatsEntry entry = entries.get(i);
            out.append(
                    String.format("%10d,%10d,%5.0f,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d",
                            entry.count,
                            entry.segments,
                            entry.pct.getAvg(entry.count),
                            entry.overhead.getMin(),
                            entry.overhead.getAvg(entry.count),
                            entry.overhead.getMax(),
                            entry.nonBased.getMin(),
                            entry.nonBased.getAvg(entry.count),
                            entry.nonBased.getMax(),
                            entry.length.getMin(),
                            entry.length.getAvg(entry.count),
                            entry.length.getMax(),
                            entry.span.getMin(),
                            entry.span.getAvg(entry.count),
                            entry.span.getMax(),
                            entry.start.getMin(),
                            entry.start.getAvg(entry.count),
                            entry.start.getMax(),
                            entry.end.getMin(),
                            entry.end.getAvg(entry.count),
                            entry.end.getMax()
                    )
            ).append("\n");
        }
        return out.toString();
    }

    private int findLastLess(int[] multiples, int value) {
        int lastMultiple = 1;
        for (int m : multiples) {
            if (m > value) return lastMultiple;
        }
        return multiples[multiples.length - 1];
    }

    @NotNull
    public String getAggregatedStatsText() {
        return getStatsText(getAggregatedStats());
    }

    @NotNull
    public List<StatsEntry> getAggregatedStats() {
        List<StatsEntry> entries = getStats();
        ArrayList<StatsEntry> aggr = new ArrayList<>();
        ArrayList<Range> ranges = new ArrayList<>();

        ArrayList<Integer> steps = new ArrayList<>();


        // @formatter:off
        int step = 1; int start = 1; int nextStep = 16;
//        for (int i = start; i < nextStep; i += step) steps.add(i);
//        start = 16; step = 16; nextStep = 256;
//        for (int i = start; i < nextStep; i += step) steps.add(i);
        steps.add(1);
        steps.add(2);
        steps.add(3);
        steps.add(4);
        steps.add(5);
        steps.add(6);
        steps.add(7);
        steps.add(8);
        steps.add(16);
        steps.add(256);
        start = 65536; step = 65536; nextStep = 65536*16;
        for (int i = start; i < nextStep; i += step) steps.add(i);
        // @formatter:on

        int buckets = steps.size();
        ArrayList<SegmentedSequenceStats.StatsEntry> aggregated = new ArrayList<>(buckets);
        int iMax = entries.size();

        int currentBucket = buckets - 1;
        int currentBucketSegments = steps.get(currentBucket);

        for (int i = 0; i < buckets; i++) {
            aggregated.add(null);
        }

        for (int i = iMax; i-- > 0; ) {
            StatsEntry entry = entries.get(i);
            if (entry.segments < currentBucketSegments) {
                // find the next bucket to hold this entry
                while (currentBucket > 0) {
                    currentBucket--;
                    currentBucketSegments = steps.get(currentBucket);
                    if (entry.segments >= currentBucketSegments) break;
                }
                assert currentBucket >= 0;
            }

            if (entry.segments < currentBucketSegments) {
                int tmp = 0;
            }

            assert (entry.segments >= currentBucketSegments);

            StatsEntry aggrEntry = aggregated.get(currentBucket);

            if (aggrEntry == null) {
                aggrEntry = new StatsEntry(currentBucketSegments);
                aggregated.set(currentBucket, aggrEntry);
            }

            aggrEntry.add(entry);
        }

        aggregated.removeIf(Objects::isNull);

        return aggregated;
    }

    @NotNull
    public String getStatsText() {
        List<StatsEntry> entries = getStats();
        return getStatsText(entries);
    }

    public void clear() {
        myStats.clear();
    }

    @NotNull
    public List<StatsEntry> getStats() {
        ArrayList<StatsEntry> entries = new ArrayList<>(myStats.keySet());

        entries.sort(StatsEntry::compareTo);
        return entries;
    }

    @NotNull
    public static SegmentedSequenceStats getInstance() {
        return new SegmentedSequenceStats();
    }
}
