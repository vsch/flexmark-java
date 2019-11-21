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

        stats.addStats(0, 10, 10, 0, 3);
        assertEquals(1, stats.getCount(0, 10, 10, 0, 3));

        stats.addStats(0, 10, 10, 0, 3);
        assertEquals(2, stats.getCount(0, 10, 10, 0, 3));

        stats.addStats(0, 10, 10, 1, 3);
        assertEquals(3, stats.getCount(0, 10, 10, 1, 3));
        assertEquals(3, stats.getCount(0, 10, 10, 0, 3));

        stats.addStats(0, 10, 10, 0, 5);
        assertEquals(1, stats.getCount(0, 10, 10, 0, 5));

        stats.addStats(0, 10, 10, 0, 5);
        assertEquals(2, stats.getCount(0, 10, 10, 0, 5));

        stats.addStats(0, 10, 10, 1, 5);
        assertEquals(3, stats.getCount(0, 10, 10, 1, 5));
        assertEquals(3, stats.getCount(0, 10, 10, 0, 5));

        assertEquals("" +
                " count,   seg,  over,   pct,avglen,maxlen, mxnon,avspan,mxspan, start,   end\n" +
                "     3,     5,    48,   120,    10,    10,     1,    10,    10,     0,    10\n" +
                "     3,     3,    48,   120,    10,    10,     1,    10,    10,     0,    10" +
                "\n", stats.getStatsText());
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
                " count,   seg,  over,   pct,avglen,maxlen, mxnon,avspan,mxspan, start,   end\n" +
                "    55,     3,    32,   200,     7,    13,     3,    10,     4,     9,    10\n" +
                "    10,     2,    28,   233,     3,     3,     3,     0,     0,     9,     9" +
                "\n", stats.getStatsText());
    }
}
