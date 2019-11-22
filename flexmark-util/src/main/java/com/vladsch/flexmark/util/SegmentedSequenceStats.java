package com.vladsch.flexmark.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SegmentedSequenceStats {
    public static class StatsEntry implements Comparable<StatsEntry> {
        int segments;
        int count;
        final MinMaxAvgLong segStats = new MinMaxAvgLong();
        final MinMaxAvgLong nonBased = new MinMaxAvgLong();
        final MinMaxAvgLong nonBasedSeg = new MinMaxAvgLong();
        final MinMaxAvgLong length = new MinMaxAvgLong();
        final MinMaxAvgLong start = new MinMaxAvgLong();
        final MinMaxAvgLong end = new MinMaxAvgLong();
        final MinMaxAvgLong span = new MinMaxAvgLong();

        public StatsEntry(int segments) {
            assert segments >= 1 : "segments: " + segments + " < 1";
            this.segments = segments;
        }

        public void add(int segments, int nonBased, int nonBasedSeg, int length, int start, int end) {
            assert segments >= 1 : "segments: " + segments + " < 1";
            assert nonBased >= 0 : "nonBased: " + nonBased + " < 0";
            assert nonBasedSeg >= (nonBased > 0 ? 1 : 0) && nonBasedSeg <= nonBased : "nonBasedSeg: " + nonBasedSeg + " not in [" + (nonBased > 0 ? 1 : 0) + ", " + nonBased + "]";
            assert length >= 0 : "length: " + length + " < 0";
            assert end >= 0 : " end:" + end + " < 0 ";
            assert start >= 0 && start <= end : " start:" + start + " not in [" + 0 + ", " + end + "]";

            count++;
            this.segStats.add(segments);
            this.nonBased.add(nonBased);
            this.nonBasedSeg.add(nonBasedSeg);
            this.length.add(length);
            this.start.add(start);
            this.end.add(end);
            this.span.add(Math.max(0, end - start));
        }

        public void add(@NotNull StatsEntry other) {
            count += other.count;
            this.segStats.add(other.segStats);
            this.nonBased.add(other.nonBased);
            this.nonBasedSeg.add(other.nonBasedSeg);
            this.length.add(other.length);
            this.start.add(other.start);
            this.end.add(other.end);
            this.span.add(other.span);
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

    public void addStats(int segments, int nonBasedChars, int nonBaseSegs, int length, int startOffset, int endOffset) {
        StatsEntry entry = new StatsEntry(segments);
        entry = myStats.computeIfAbsent(entry, k -> k);
        entry.add(segments, nonBasedChars, nonBaseSegs, length, startOffset, endOffset);
    }

    public int getCount(int segments) {
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
                String.format("%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s,%10s",
                        "count",
                        "min-seg",
                        "avg-seg",
                        "max-seg",
                        "min-non",
                        "avg-non",
                        "max-non",
                        "min-nseg",
                        "avg-nseg",
                        "max-nseg",
                        "min-len",
                        "avg-len",
                        "max-len",
                        "min-span",
                        "avg-span",
                        "max-span"
                )
        ).append("\n");

        for (int i = iMax; i-- > 0; ) {
            StatsEntry entry = entries.get(i);
            out.append(
                    String.format("%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d,%10d",
                            entry.count,
                            entry.count == 1 ? entry.segments : entry.segStats.getMin(),
                            entry.count == 1 ? entry.segments : entry.segStats.getAvg(entry.count),
                            entry.count == 1 ? entry.segments : entry.segStats.getMax(),
                            entry.nonBased.getMin(),
                            entry.nonBased.getAvg(entry.count),
                            entry.nonBased.getMax(),
                            entry.nonBasedSeg.getMin(),
                            entry.nonBasedSeg.getAvg(entry.count),
                            entry.nonBasedSeg.getMax(),
                            entry.length.getMin(),
                            entry.length.getAvg(entry.count),
                            entry.length.getMax(),
                            entry.span.getMin(),
                            entry.span.getAvg(entry.count),
                            entry.span.getMax()
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
        steps.add(15);
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
