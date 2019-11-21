package com.vladsch.flexmark.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SegmentedSequenceStats {
    public static class StatsEntry implements Comparable<StatsEntry> {
        final int length;
        final int segments;
        int nonBasedChars;
        int maxLength;
        int totalLength;
        int startOffset;
        int endOffset;
        int spanOffset;
        int totalSpanOffset;
        int count;

        public StatsEntry(int startOffset, int endOffset, int length, int nonBasedChars, int segments) {
            this.segments = segments;
            this.length = length;
            this.startOffset = startOffset;
            this.endOffset = endOffset;
            this.nonBasedChars = nonBasedChars;
        }

        @Override
        public int compareTo(@NotNull SegmentedSequenceStats.StatsEntry o) {
            int counts = Integer.compare(segments, o.segments);
            if (counts != 0) return counts;
            else return Integer.compare(count, o.count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            StatsEntry stats = (StatsEntry) o;

            if (segments != stats.segments) return false;
//            if (startOffset != stats.startOffset) return false;
//            if (endOffset != stats.endOffset) return false;
//            if (length != stats.length) return false;
//            if (nonBasedChars != stats.nonBasedChars) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int result = 0;
            result = 31 * result + segments;
//            result = 31 * result + startOffset;
//            result = 31 * result + endOffset;
//            result = 31 * result + length;
//            result = 31 * result + nonBasedChars;
            return result;
        }

        @Override
        public String toString() {
            return startOffset + "," + endOffset + "," + length + "," + nonBasedChars + "," + segments;
        }
    }

    final private HashMap<StatsEntry, StatsEntry> myStats = new HashMap<>();

    private SegmentedSequenceStats() {

    }

    public void addStats(int startOffset, int endOffset, int length, int nonBasedChars, int segments) {
        StatsEntry entry = new StatsEntry(startOffset, endOffset, length, nonBasedChars, segments);
        entry = myStats.computeIfAbsent(entry, k -> k);
        entry.count++;
        entry.totalLength += length;
        entry.totalSpanOffset += endOffset - startOffset;
        entry.maxLength = Math.max(entry.maxLength, length);
        entry.startOffset = Math.max(entry.startOffset, startOffset);
        entry.endOffset = Math.max(entry.endOffset, endOffset);
        entry.spanOffset = Math.max(entry.spanOffset, endOffset - startOffset);
        entry.nonBasedChars = Math.max(entry.nonBasedChars, nonBasedChars);
    }

    public int getCount(int startOffset, int endOffset, int length, int nonBasedChars, int segments) {
        StatsEntry entry = new StatsEntry(startOffset, endOffset, length, nonBasedChars, segments);
        if (myStats.containsKey(entry)) {
            return myStats.get(entry).count;
        }
        return 0;
    }

    @NotNull
    public String getStatsText() {
        List<StatsEntry> entries = getStats();

        StringBuilder out = new StringBuilder();
        int iMax = entries.size();

        out.append(
                String.format("%6s,%6s,%6s,%6s,%6s,%6s,%6s,%6s,%6s,%6s,%6s",
                        "count",
                        "seg",
                        "over",
                        "pct",
                        "avglen",
                        "maxlen",
                        "mxnon",
                        "avspan",
                        "mxspan",
                        "start",
                        "end")
        ).append("\n");

        for (int i = iMax; i-- > 0; ) {
            StatsEntry entry = entries.get(i);
            int overhead = 4 * (entry.length + 1) + 4 * entry.nonBasedChars;
            float pct = 100.0f * overhead / (4 * entry.length);
            out.append(
                    String.format("%6d,%6d,%6d,%6.0f,%6d,%6d,%6d,%6d,%6d,%6d,%6d",
                            entry.count,
                            entry.segments,
                            overhead,
                            pct,
                            entry.totalLength / entry.count,
                            entry.maxLength,
                            entry.nonBasedChars,
                            entry.spanOffset,
                            entry.totalSpanOffset / entry.count,
                            entry.startOffset,
                            entry.endOffset)
            ).append("\n");
        }
        return out.toString();
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
