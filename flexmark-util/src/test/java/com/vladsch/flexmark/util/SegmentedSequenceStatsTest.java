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

        stats.addStats(3, 0, 10, 0, 10);
        assertEquals(1, stats.getCount(3, 0, 10, 0, 10));

        stats.addStats(3, 0, 10, 0, 10);
        assertEquals(2, stats.getCount(3, 0, 10, 0, 10));

        stats.addStats(3, 1, 10, 0, 10);
        assertEquals(3, stats.getCount(3, 1, 10, 0, 10));
        assertEquals(3, stats.getCount(3, 0, 10, 0, 10));

        stats.addStats(5, 0, 10, 0, 10);
        assertEquals(1, stats.getCount(5, 0, 10, 0, 10));

        stats.addStats(5, 0, 10, 0, 10);
        assertEquals(2, stats.getCount(5, 0, 10, 0, 10));

        stats.addStats(5, 1, 10, 0, 10);
        assertEquals(3, stats.getCount(5, 1, 10, 0, 10));
        assertEquals(3, stats.getCount(5, 0, 10, 0, 10));

        assertEquals("" +
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "         3,         5,  113,         0,        45,        48,         0,         0,         1,         0,        10,        10,         0,        10,        10,         0,         0,         0,         0,        10,        10\n" +
                "         3,         3,  113,         0,        45,        48,         0,         0,         1,         0,        10,        10,         0,        10,        10,         0,         0,         0,         0,        10,        10\n" +
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
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "        55,         3,  164,         0,        44,        68,         0,         3,         3,         0,         7,        13,         0,         4,        10,         0,         3,         9,         0,         7,        10\n" +
                "        10,         2,  233,         0,        28,        28,         0,         3,         3,         0,         3,         3,         0,         0,         0,         0,         4,         9,         0,         4,         9\n" +
                "", stats.getStatsText());
    }

    @Test
    public void aggregatedStatsBuckets() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 65536;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, i, i, i);
        }

        assertEquals("" +
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "     65280,       256,  200,         0,        -4,    524284,         0,     32895,     65535,         0,     32895,     65535,         0,         0,         0,         0,     32895,     65535,         0,     32895,     65535\n" +
                "       240,        16,  201,         0,      1088,      2044,         0,       135,       255,         0,       135,       255,         0,         0,         0,         0,       135,       255,         0,       135,       255\n" +
                "         8,         8,  209,         0,        96,       124,         0,        11,        15,         0,        11,        15,         0,         0,         0,         0,        11,        15,         0,        11,        15\n" +
                "         1,         7,  214,         0,        60,        60,         0,         7,         7,         0,         7,         7,         0,         0,         0,         0,         7,         7,         0,         7,         7\n" +
                "         1,         6,  217,         0,        52,        52,         0,         6,         6,         0,         6,         6,         0,         0,         0,         0,         6,         6,         0,         6,         6\n" +
                "         1,         5,  220,         0,        44,        44,         0,         5,         5,         0,         5,         5,         0,         0,         0,         0,         5,         5,         0,         5,         5\n" +
                "         1,         4,  225,         0,        36,        36,         0,         4,         4,         0,         4,         4,         0,         0,         0,         0,         4,         4,         0,         4,         4\n" +
                "         1,         3,  233,         0,        28,        28,         0,         3,         3,         0,         3,         3,         0,         0,         0,         0,         3,         3,         0,         3,         3\n" +
                "         1,         2,  250,         0,        20,        20,         0,         2,         2,         0,         2,         2,         0,         0,         0,         0,         2,         2,         0,         2,         2\n" +
                "         1,         1,  300,         0,        12,        12,         0,         1,         1,         0,         1,         1,         0,         0,         0,         0,         1,         1,         0,         1,         1\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsNonBased() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, i, 0, 0, 0);
        }

        assertEquals("" +
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "       240,        16,    0,         0,       546,      1024,         0,       135,       255,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         8,         8,    0,         0,        50,        64,         0,        11,        15,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         7,    0,         0,        32,        32,         0,         7,         7,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         6,    0,         0,        28,        28,         0,         6,         6,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         5,    0,         0,        24,        24,         0,         5,         5,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         4,    0,         0,        20,        20,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         3,    0,         0,        16,        16,         0,         3,         3,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         2,    0,         0,        12,        12,         0,         2,         2,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         1,    0,         0,         8,         8,         0,         1,         1,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsLength() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, i, 0, 0);
        }

        assertEquals("" +
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "       240,        16,  101,         0,       546,      1024,         0,         0,         0,         0,       135,       255,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         8,         8,  109,         0,        50,        64,         0,         0,         0,         0,        11,        15,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         7,  114,         0,        32,        32,         0,         0,         0,         0,         7,         7,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         6,  117,         0,        28,        28,         0,         0,         0,         0,         6,         6,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         5,  120,         0,        24,        24,         0,         0,         0,         0,         5,         5,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         4,  125,         0,        20,        20,         0,         0,         0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         3,  133,         0,        16,        16,         0,         0,         0,         0,         3,         3,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         2,  150,         0,        12,        12,         0,         0,         0,         0,         2,         2,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "         1,         1,  200,         0,         8,         8,         0,         0,         0,         0,         1,         1,         0,         0,         0,         0,         0,         0,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsStart() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, 0, i, 0);
        }

        assertEquals("" +
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "       240,        16,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,      -255,      -135,         0,         0,       135,       255,         0,         0,         0\n" +
                "         8,         8,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,       -15,       -11,         0,         0,        11,        15,         0,         0,         0\n" +
                "         1,         7,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -7,        -7,         0,         0,         7,         7,         0,         0,         0\n" +
                "         1,         6,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -6,        -6,         0,         0,         6,         6,         0,         0,         0\n" +
                "         1,         5,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -5,        -5,         0,         0,         5,         5,         0,         0,         0\n" +
                "         1,         4,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -4,        -4,         0,         0,         4,         4,         0,         0,         0\n" +
                "         1,         3,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -3,        -3,         0,         0,         3,         3,         0,         0,         0\n" +
                "         1,         2,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -2,        -2,         0,         0,         2,         2,         0,         0,         0\n" +
                "         1,         1,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,        -1,        -1,         0,         0,         1,         1,         0,         0,         0\n" +
                "", stats.getAggregatedStatsText());
    }

    @Test
    public void aggregatedStatsEnd() {
        SegmentedSequenceStats stats = SegmentedSequenceStats.getInstance();

        int iMax = 256;
        for (int i = 1; i < iMax; i++) {
            stats.addStats(i, 0, 0, 0, i);
        }

        assertEquals("" +
                "     count,       seg,  pct,   min-ovr,   avg-ovr,   max-ovr,   min-non,   avg-non,   max-non,   min-len,   avg-len,   max-len,  min-span,  avg-span,  max-span, min-start, avg-start, max-start,   min-end,   avg-end,   max-end\n" +
                "       240,        16,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,       135,       255,         0,         0,         0,         0,       135,       255\n" +
                "         8,         8,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,        11,        15,         0,         0,         0,         0,        11,        15\n" +
                "         1,         7,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         7,         7,         0,         0,         0,         0,         7,         7\n" +
                "         1,         6,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         6,         6,         0,         0,         0,         0,         6,         6\n" +
                "         1,         5,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         5,         5,         0,         0,         0,         0,         5,         5\n" +
                "         1,         4,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         4,         4,         0,         0,         0,         0,         4,         4\n" +
                "         1,         3,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         3,         3,         0,         0,         0,         0,         3,         3\n" +
                "         1,         2,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         2,         2,         0,         0,         0,         0,         2,         2\n" +
                "         1,         1,    0,         0,         4,         4,         0,         0,         0,         0,         0,         0,         0,         1,         1,         0,         0,         0,         0,         1,         1\n" +
                "", stats.getAggregatedStatsText());
    }
}
