package com.vladsch.flexmark.util.sequence.builder;

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

        stats.addStats(3, 3, 6);
        assertEquals(1, stats.getCount(3));

        stats.addStats(3, 3, 6);
        assertEquals(2, stats.getCount(3));

        stats.addStats(3, 1, 1);
        assertEquals(3, stats.getCount(3));
        assertEquals(3, stats.getCount(3));

        stats.addStats(5, 0, 0);
        assertEquals(1, stats.getCount(5));

        stats.addStats(5, 0, 0);
        assertEquals(2, stats.getCount(5));

        stats.addStats(5, 1, 1);
        assertEquals(3, stats.getCount(5));
        assertEquals(3, stats.getCount(5));

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "         3,         5,         5,         5,         0,         0,         1,         0,         0,         1,         1,         2,         1,  50.000\n" +
                "         3,         3,         3,         3,         1,         2,         3,         1,         4,         6,         7,        14,        13,  92.857\n" +
                "", stats.getStatsText());
    }

    @Test
    public void basic_statsCollection() {
        final String sC = "0123456789";
        MutableDataSet options = new MutableDataSet();
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();
        options.set(BasedOptionsHolder.SEGMENTED_STATS, stats);

        BasedOptionsSequence s1 = BasedOptionsSequence.of(sC, BasedOptionsHolder.F_COLLECT_SEGMENTED_STATS, options);
        BasedSequence s = BasedSequence.of(s1).subSequence(0, ((CharSequence) s1).length());

        int iMax = s.length();
        for (int i = 0; i < iMax; i++) {
            for (int j = i; j <= iMax; j++) {
                s.subSequence(i, j).prefixWith("  ").suffixWith("\n");
            }
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "        55,         3,         3,         3,         4,         7,        13,        30,        30,        30,       385,       770,      1650, 214.286\n" +
                "        10,         2,         2,         2,         3,         3,         3,        20,        20,        20,        30,        60,       200, 333.333\n" +
                "", stats.getStatsText());
    }

    @Test
    public void aggregatedStatsBuckets() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 65540;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, i);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "         4,     65536,     65537,     65539,     65536,     65537,     65539,     65536,     65537,     65539,    262150,    524300,    262150,  50.000\n" +
                "     65280,       256,     32895,     65535,       256,     32895,     65535,       256,     32895,     65535,2147418240,4294836480,2147418240,  50.000\n" +
                "       240,        16,       135,       255,        16,       135,       255,        16,       135,       255,     32520,     65040,     32520,  50.000\n" +
                "         1,        15,        15,        15,        15,        15,        15,        15,        15,        15,        15,        30,        15,  50.000\n" +
                "         7,         8,        11,        14,         8,        11,        14,         8,        11,        14,        77,       154,        77,  50.000\n" +
                "         1,         7,         7,         7,         7,         7,         7,         7,         7,         7,         7,        14,         7,  50.000\n" +
                "         1,         6,         6,         6,         6,         6,         6,         6,         6,         6,         6,        12,         6,  50.000\n" +
                "         1,         5,         5,         5,         5,         5,         5,         5,         5,         5,         5,        10,         5,  50.000\n" +
                "         1,         4,         4,         4,         4,         4,         4,         4,         4,         4,         4,         8,         4,  50.000\n" +
                "         1,         3,         3,         3,         3,         3,         3,         3,         3,         3,         3,         6,         3,  50.000\n" +
                "         1,         2,         2,         2,         2,         2,         2,         2,         2,         2,         2,         4,         2,  50.000\n" +
                "         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         2,         1,  50.000\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsNonBased() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, i);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "       240,        16,       135,       255,        16,       135,       255,        16,       135,       255,     32520,     65040,     32520,  50.000\n" +
                "         1,        15,        15,        15,        15,        15,        15,        15,        15,        15,        15,        30,        15,  50.000\n" +
                "         7,         8,        11,        14,         8,        11,        14,         8,        11,        14,        77,       154,        77,  50.000\n" +
                "         1,         7,         7,         7,         7,         7,         7,         7,         7,         7,         7,        14,         7,  50.000\n" +
                "         1,         6,         6,         6,         6,         6,         6,         6,         6,         6,         6,        12,         6,  50.000\n" +
                "         1,         5,         5,         5,         5,         5,         5,         5,         5,         5,         5,        10,         5,  50.000\n" +
                "         1,         4,         4,         4,         4,         4,         4,         4,         4,         4,         4,         8,         4,  50.000\n" +
                "         1,         3,         3,         3,         3,         3,         3,         3,         3,         3,         3,         6,         3,  50.000\n" +
                "         1,         2,         2,         2,         2,         2,         2,         2,         2,         2,         2,         4,         2,  50.000\n" +
                "         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         1,         2,         1,  50.000\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsSegments() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, 0);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "       240,        16,       135,       255,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,        15,        15,        15,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         7,         8,        11,        14,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         7,         7,         7,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         6,         6,         6,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         5,         5,         5,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         4,         4,         4,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         3,         3,         3,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         2,         2,         2,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "         1,         1,         1,         1,         0,         0,         0,         0,         0,         0,         0,         0,         0,   0.000\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsLength() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, 0);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "       240,        16,       135,       255,        16,       135,       255,         0,         0,         0,     32520,     65040,         0,   0.000\n" +
                "         1,        15,        15,        15,        15,        15,        15,         0,         0,         0,        15,        30,         0,   0.000\n" +
                "         7,         8,        11,        14,         8,        11,        14,         0,         0,         0,        77,       154,         0,   0.000\n" +
                "         1,         7,         7,         7,         7,         7,         7,         0,         0,         0,         7,        14,         0,   0.000\n" +
                "         1,         6,         6,         6,         6,         6,         6,         0,         0,         0,         6,        12,         0,   0.000\n" +
                "         1,         5,         5,         5,         5,         5,         5,         0,         0,         0,         5,        10,         0,   0.000\n" +
                "         1,         4,         4,         4,         4,         4,         4,         0,         0,         0,         4,         8,         0,   0.000\n" +
                "         1,         3,         3,         3,         3,         3,         3,         0,         0,         0,         3,         6,         0,   0.000\n" +
                "         1,         2,         2,         2,         2,         2,         2,         0,         0,         0,         2,         4,         0,   0.000\n" +
                "         1,         1,         1,         1,         1,         1,         1,         0,         0,         0,         1,         2,         0,   0.000\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsOverhead() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, i);
        }

        assertEquals("" +
                "     count,   min-seg,   avg-seg,   max-seg,   min-len,   avg-len,   max-len,   min-ovr,   avg-ovr,   max-ovr,   tot-len,   tot-chr,   tot-ovr,   ovr %\n" +
                "       240,        16,       135,       255,         0,         0,         0,        16,       135,       255,         0,         0,     32520,   0.000\n" +
                "         1,        15,        15,        15,         0,         0,         0,        15,        15,        15,         0,         0,        15,   0.000\n" +
                "         7,         8,        11,        14,         0,         0,         0,         8,        11,        14,         0,         0,        77,   0.000\n" +
                "         1,         7,         7,         7,         0,         0,         0,         7,         7,         7,         0,         0,         7,   0.000\n" +
                "         1,         6,         6,         6,         0,         0,         0,         6,         6,         6,         0,         0,         6,   0.000\n" +
                "         1,         5,         5,         5,         0,         0,         0,         5,         5,         5,         0,         0,         5,   0.000\n" +
                "         1,         4,         4,         4,         0,         0,         0,         4,         4,         4,         0,         0,         4,   0.000\n" +
                "         1,         3,         3,         3,         0,         0,         0,         3,         3,         3,         0,         0,         3,   0.000\n" +
                "         1,         2,         2,         2,         0,         0,         0,         2,         2,         2,         0,         0,         2,   0.000\n" +
                "         1,         1,         1,         1,         0,         0,         0,         1,         1,         1,         0,         0,         1,   0.000\n" +
                "", stats.getAggregatedStatsText());
    }
}
