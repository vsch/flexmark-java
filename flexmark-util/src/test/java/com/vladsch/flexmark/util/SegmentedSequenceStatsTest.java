package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedOptionsHolder;
import com.vladsch.flexmark.util.sequence.BasedOptionsSequence;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SegmentedSequenceStatsTest {
    @Test
    public void basic_stats() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        stats.addStats(3, 0, 0, 10, 0, 10);
        assertEquals(1, stats.getCount(3));

        stats.addStats(3, 0, 0, 10, 0, 10);
        assertEquals(2, stats.getCount(3));

        stats.addStats(3, 1, 1, 10, 0, 10);
        assertEquals(3, stats.getCount(3));
        assertEquals(3, stats.getCount(3));

        stats.addStats(5, 0, 0, 10, 0, 10);
        assertEquals(1, stats.getCount(5));

        stats.addStats(5, 0, 0, 10, 0, 10);
        assertEquals(2, stats.getCount(5));

        stats.addStats(5, 1, 1, 10, 0, 10);
        assertEquals(3, stats.getCount(5));
        assertEquals(3, stats.getCount(5));

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "         3,         5,         5,         5,         0,         0,         1,         0,         0,         1,        10,        10,        10,        10,        10,        10\n" +
                "         3,         3,         3,         3,         0,         0,         1,         0,         0,         1,        10,        10,        10,        10,        10,        10\n" +
                "", stats.getStatsText());
    }

    @Test
    public void basic_statsCollection() {
        final String sC = "0123456789";
        MutableDataSet options = new MutableDataSet();
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();
        options.set(BasedOptionsHolder.SEGMENTED_STATS, stats);

        BasedOptionsSequence s1 = new BasedOptionsSequence(sC, BasedOptionsHolder.O_COLLECT_SEGMENTED_STATS, options);
        BasedSequence s = BasedSequence.of(s1).subSequence(0, ((CharSequence) s1).length());

        int iMax = s.length();
        for (int i = 0; i < iMax; i++) {
            for (int j = i; j <= iMax; j++) {
                s.subSequence(i, j).prefixWith("  ").suffixWith("\n");
            }
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "        45,         3,         3,         3,         3,         3,         3,         2,         2,         2,         4,         6,        12,         1,         3,         9\n" +
                "        10,         2,         2,         2,         3,         3,         3,         2,         2,         2,         4,         8,        13,         1,         5,        10\n" +
                "        10,         1,         1,         1,         3,         3,         3,         1,         1,         1,         3,         3,         3,         0,         0,         0\n" +
                "", stats.getStatsText());
    }

    @Test
    public void aggregatedStatsBuckets() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 65536;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, i, i, i, i);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "     65280,       256,     32895,     65535,       256,     32895,     65535,       256,     32895,     65535,       256,     32895,     65535,         0,         0,         0\n" +
                "       240,        16,       135,       255,        16,       135,       255,        16,       135,       255,        16,       135,       255,         0,         0,         0\n" +
                "         1,        15,        15,        15,        15,        15,        15,        15,        15,        15,        15,        15,        15,         0,         0,         0\n" +
                "         7,         8,        11,        14,         8,        11,        14,         8,        11,        14,         8,        11,        14,         0,         0,         0\n" +
                "         1,         7,         7,         7,         7,         7,         7,         7,         7,         7,         7,         7,         7,         0,         0,         0\n" +
                "         1,         6,         6,         6,         6,         6,         6,         6,         6,         6,         6,         6,         6,         0,         0,         0\n" +
                "         1,         5,         5,         5,         5,         5,         5,         5,         5,         5,         5,         5,         5,         0,         0,         0\n" +
                "         1,         4,         4,         4,         4,         4,         4,         4,         4,         4,         4,         4,         4,         0,         0,         0\n" +
                "         1,         3,         3,         3,         3,         3,         3,         3,         3,         3,         3,         3,         3,         0,         0,         0\n" +
                "         1,         2,         2,         2,         2,         2,         2,         2,         2,         2,         2,         2,         2,         0,         0,         0\n" +
                "         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsNonBased() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, i, 0, 0, 0);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "       240,        16,       135,       255,        16,       135,       255,        16,       135,       255,         0,         0,         0,         0,         0,         0\n" +
                "         1,        15,        15,        15,        15,        15,        15,        15,        15,        15,         0,         0,         0,         0,         0,         0\n" +
                "         7,         8,        11,        14,         8,        11,        14,         8,        11,        14,         0,         0,         0,         0,         0,         0\n" +
                "         1,         7,         7,         7,         7,         7,         7,         7,         7,         7,         0,         0,         0,         0,         0,         0\n" +
                "         1,         6,         6,         6,         6,         6,         6,         6,         6,         6,         0,         0,         0,         0,         0,         0\n" +
                "         1,         5,         5,         5,         5,         5,         5,         5,         5,         5,         0,         0,         0,         0,         0,         0\n" +
                "         1,         4,         4,         4,         4,         4,         4,         4,         4,         4,         0,         0,         0,         0,         0,         0\n" +
                "         1,         3,         3,         3,         3,         3,         3,         3,         3,         3,         0,         0,         0,         0,         0,         0\n" +
                "         1,         2,         2,         2,         2,         2,         2,         2,         2,         2,         0,         0,         0,         0,         0,         0\n" +
                "         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         0,         0,         0,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsLength() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, 0, i, 0, 0);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "       240,        16,       135,       255,         0,         0,         0,         0,         0,         0,        16,       135,       255,         0,         0,         0\n" +
                "         1,        15,        15,        15,         0,         0,         0,         0,         0,         0,        15,        15,        15,         0,         0,         0\n" +
                "         7,         8,        11,        14,         0,         0,         0,         0,         0,         0,         8,        11,        14,         0,         0,         0\n" +
                "         1,         7,         7,         7,         0,         0,         0,         0,         0,         0,         7,         7,         7,         0,         0,         0\n" +
                "         1,         6,         6,         6,         0,         0,         0,         0,         0,         0,         6,         6,         6,         0,         0,         0\n" +
                "         1,         5,         5,         5,         0,         0,         0,         0,         0,         0,         5,         5,         5,         0,         0,         0\n" +
                "         1,         4,         4,         4,         0,         0,         0,         0,         0,         0,         4,         4,         4,         0,         0,         0\n" +
                "         1,         3,         3,         3,         0,         0,         0,         0,         0,         0,         3,         3,         3,         0,         0,         0\n" +
                "         1,         2,         2,         2,         0,         0,         0,         0,         0,         0,         2,         2,         2,         0,         0,         0\n" +
                "         1,         1,         1,         1,         0,         0,         0,         0,         0,         0,         1,         1,         1,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsStart() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, 0, i, i, i);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "       240,        16,       135,       255,         0,         0,         0,         0,         0,         0,        16,       135,       255,         0,         0,         0\n" +
                "         1,        15,        15,        15,         0,         0,         0,         0,         0,         0,        15,        15,        15,         0,         0,         0\n" +
                "         7,         8,        11,        14,         0,         0,         0,         0,         0,         0,         8,        11,        14,         0,         0,         0\n" +
                "         1,         7,         7,         7,         0,         0,         0,         0,         0,         0,         7,         7,         7,         0,         0,         0\n" +
                "         1,         6,         6,         6,         0,         0,         0,         0,         0,         0,         6,         6,         6,         0,         0,         0\n" +
                "         1,         5,         5,         5,         0,         0,         0,         0,         0,         0,         5,         5,         5,         0,         0,         0\n" +
                "         1,         4,         4,         4,         0,         0,         0,         0,         0,         0,         4,         4,         4,         0,         0,         0\n" +
                "         1,         3,         3,         3,         0,         0,         0,         0,         0,         0,         3,         3,         3,         0,         0,         0\n" +
                "         1,         2,         2,         2,         0,         0,         0,         0,         0,         0,         2,         2,         2,         0,         0,         0\n" +
                "         1,         1,         1,         1,         0,         0,         0,         0,         0,         0,         1,         1,         1,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsEnd() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, 0, i, 0, i);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-non,   avg-non,   max-non,  min-nseg,  avg-nseg,  max-nseg,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span\n" +
                "       240,        16,       135,       255,         0,         0,         0,         0,         0,         0,        16,       135,       255,        16,       135,       255\n" +
                "         1,        15,        15,        15,         0,         0,         0,         0,         0,         0,        15,        15,        15,        15,        15,        15\n" +
                "         7,         8,        11,        14,         0,         0,         0,         0,         0,         0,         8,        11,        14,         8,        11,        14\n" +
                "         1,         7,         7,         7,         0,         0,         0,         0,         0,         0,         7,         7,         7,         7,         7,         7\n" +
                "         1,         6,         6,         6,         0,         0,         0,         0,         0,         0,         6,         6,         6,         6,         6,         6\n" +
                "         1,         5,         5,         5,         0,         0,         0,         0,         0,         0,         5,         5,         5,         5,         5,         5\n" +
                "         1,         4,         4,         4,         0,         0,         0,         0,         0,         0,         4,         4,         4,         4,         4,         4\n" +
                "         1,         3,         3,         3,         0,         0,         0,         0,         0,         0,         3,         3,         3,         3,         3,         3\n" +
                "         1,         2,         2,         2,         0,         0,         0,         0,         0,         0,         2,         2,         2,         2,         2,         2\n" +
                "         1,         1,         1,         1,         0,         0,         0,         0,         0,         0,         1,         1,         1,         1,         1,         1\n" +
                "", stats.getAggregatedStatsText());
    }
}
