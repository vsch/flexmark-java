package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceFull;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static com.vladsch.flexmark.util.sequence.edit.ISegmentBuilder.F_INCLUDE_ANCHORS;
import static com.vladsch.flexmark.util.sequence.edit.ISegmentBuilder.F_TRACK_FIRST256;
import static com.vladsch.flexmark.util.sequence.edit.SegmentTree.*;
import static org.junit.Assert.assertEquals;

public class SegmentTreeTest {

    static void loop(int start, int end, int span, int param, BiConsumer<Integer, Integer> consumer) {
        int iMaxStart = start + span;
        int iMinEnd = end - span;

        if (iMaxStart >= iMinEnd) {
            for (int i = start; i < end; i++) {
                consumer.accept(param, i);
            }
        } else {
            for (int i = start; i < iMaxStart; i++) {
                consumer.accept(param, i);
            }

            for (int i = iMinEnd; i < end; i++) {
                consumer.accept(param, i);
            }
        }
    }

    static void loopSizes(BiConsumer<Integer, Integer> consumer) {
        loop(0, 16, 8, 0, consumer);
        loop(16, 256, 8, 1, consumer);
        loop(256, 65536, 8, 2, consumer);
        loop(65536, 65536 * 256, 8, 3, consumer);
        loop(65536 * 256, MAX_VALUE, 8, 4, consumer);
    }

    static void loopSizesShort(BiConsumer<Integer, Integer> consumer) {
        loop(0, 16, 8, 0, consumer);
        loop(16, 256, 8, 1, consumer);
        loop(256, 65536, 8, 2, consumer);
        loop(65536, 65536 * 256, 8, 3, consumer);
        loop(65536 * 256, MAX_VALUE, 8, 4, consumer);
    }

    static void loopEnd(int startOffset, BiConsumer<Integer, Integer> consumer) {
        loop(startOffset + 0, startOffset + 16, 8, 0, consumer);
        loop(startOffset + 16, startOffset + 256, 8, 1, consumer);
        loop(startOffset + 256, startOffset + 65536, 8, 2, consumer);
        loop(startOffset + 65536, startOffset + 65536 * 256, 8, 3, consumer);
        loop(startOffset + 65536 * 256, MAX_VALUE, 8, 4, consumer);
    }

    static void loopEndShort(int startOffset, BiConsumer<Integer, Integer> consumer) {
        loop(startOffset + 0, startOffset + 16, 8, 0, consumer);
        loop(startOffset + 16, startOffset + 256, 8, 1, consumer);
        loop(startOffset + 256, startOffset + 65536, 8, 2, consumer);
        loop(startOffset + 65536, startOffset + 65536 * 8, 8, 3, consumer);
    }

    @Test
    public void test_flags() {
        assertEquals(0xe000_0000, F_ANCHOR_FLAGS);
    }

    @Test
    public void test_anchorOffset() {
        assertEquals(0, SegmentTree.getAnchorOffset(0x0000_0000));
        assertEquals(1, SegmentTree.getAnchorOffset(0x2000_0000));
        assertEquals(2, SegmentTree.getAnchorOffset(0x4000_0000));
        assertEquals(3, SegmentTree.getAnchorOffset(0x6000_0000));
        assertEquals(4, SegmentTree.getAnchorOffset(0x8000_0000));
        assertEquals(5, SegmentTree.getAnchorOffset(0xA000_0000));
        assertEquals(6, SegmentTree.getAnchorOffset(0xC000_0000));
        assertEquals(7, SegmentTree.getAnchorOffset(0xE000_0000));
    }

    int highestBit(int value) {
        int iMax = 31;
        for (int i = iMax; i-- > 0; ) {
            if ((value & (1 << i)) != 0) return i;
        }
        return -1;
    }

    @Test
    public void test_aggrLength() {
        loopSizes((b, i) -> {
            int[] aggrSegData1 = { i, -1, -2, -4 };
            int[] aggrSegData2 = { -1, -2, i, -3 };
            assertEquals("i: " + i, (int) i, aggrLength(0, aggrSegData1));
            assertEquals("i: " + i, (int) i, aggrLength(1, aggrSegData2));
        });
    }

    @Test
    public void test_byteOffset() {
        loopSizesShort((b, i) -> {
            for (int j = 0; j < 8; j++) {
                int[] aggrSegData1 = { -1, i | (j << 29), -2, -3 };
                int[] aggrSegData2 = { -1, -2, -3, i | (j << 29) };
                assertEquals("i: " + i + " j: " + j, (int) i, byteOffset(0, aggrSegData1));
                assertEquals("i: " + i + " j: " + j, (int) i, byteOffset(1, aggrSegData2));
            }
        });
    }

    @Test
    public void test_hasPreviousAnchor() {
        loopSizesShort((b, i) -> {
            for (int j = 0; j < 8; j++) {
                int[] aggrSegData1 = { -1, i | (j << 29), -2, -3 };
                int[] aggrSegData2 = { -1, -2, -3, i | (j << 29) };
                assertEquals("i: " + i + " j: " + j, j > 0, hasPreviousAnchor(0, aggrSegData1));
                assertEquals("i: " + i + " j: " + j, j > 0, hasPreviousAnchor(1, aggrSegData2));
            }
        });
    }

    @Test
    public void test_previousAnchorOffset() {
        loopSizesShort((b, i) -> {
            for (int j = 0; j < 8; j++) {
                int[] aggrSegData1 = { -1, i | (j << 29), -2, -3 };
                int[] aggrSegData2 = { -1, -2, -3, i | (j << 29) };
                assertEquals("i: " + i + " j: " + j, i - j, previousAnchorOffset(0, aggrSegData1));
                assertEquals("i: " + i + " j: " + j, i - j, previousAnchorOffset(1, aggrSegData2));
            }
        });
    }

    @Test
    public void test_findSegSegIndex1() {
        loopSizes((b, i) -> {
            if (i > 0) {
                int[] aggrSegData1 = { i, -1, -2, -3 };
                int[] aggrSegData2 = { -1, -2, i, -3 };

                loopEnd(i, (bj, j) -> {
                    assertEquals("i: " + i + " j: " + j, j >= i ? null : new SegTreePos(0, 0, 0), findSegmentPos(j, aggrSegData1, 0, 1));
                    assertEquals("i: " + i + " j: " + j, j >= i ? null : new SegTreePos(1, 0, 0), findSegmentPos(j, aggrSegData2, 1, 1));
                });
            }
        });
    }

    @Test
    public void test_findSegSegIndex2() {
        loopSizesShort((b, i) -> {
            if (i > 0) {
                int[] aggrSegData1 = { i, -1, i + 1000, -3, -4, -5 };
                int[] aggrSegData2 = { -1, -2, i, -3, i + 1000, -4 };

                loopEnd(i, (bj, j) -> {
                    if (i == 1 && j == 65529) {
                        int tmp = 0;
                    }
                    assertEquals("i: " + i + " j: " + j, j >= i + 1000 ? null : new SegTreePos(j >= i ? 1 : 0, j >= i ? i : 0, 0), findSegmentPos(j, aggrSegData1, 0, 2));
                    assertEquals("i: " + i + " j: " + j, j >= i + 1000 ? null : new SegTreePos((j >= i ? 2 : 1), (j >= i ? i : 0), 0), findSegmentPos(j, aggrSegData2, 1, 3));
                });
            }
        });
    }

    @Test
    public void test_findSegSegIndexN() {
        loopSizesShort((b, i) -> {
            if (i > 0) {
                for (int k = 2; k < 16; k++) {
                    int[] aggrSegData1 = new int[k * 2 + 4];
                    int[] aggrSegData2 = new int[k * 2 + 4];

                    Arrays.fill(aggrSegData1, -1);
                    Arrays.fill(aggrSegData2, -1);

                    for (int l = 0; l < k; l++) {
                        aggrSegData1[l * 2] = i + l * 1000;
                        aggrSegData2[l * 2 + 2] = i + l * 1000;
                    }

                    int finalK = k;
                    loopEnd(i, (bj, j) -> {
                        int[] segment = { 0 };
                        int[] startIndex = { 0 };

                        for (int l = 0; l < finalK; l++) {
                            segment[0] = l;
                            startIndex[0] = i + Math.max(0, l - 1) * 1000;
                            if (j >= startIndex[0] && j < i + l * 1000) break;
                            segment[0] = l + 1;
                        }

                        if (finalK == 4 && i == 1 && j == 65529) {
                            int tmp = 0;
                        }

                        assertEquals("k: " + finalK + " i: " + i + " j: " + j + " s:" + segment[0], segment[0] >= finalK ? null : new SegTreePos(segment[0], startIndex[0], 0), findSegmentPos(j, aggrSegData1, 0, finalK));
                        assertEquals("k: " + finalK + " i: " + i + " j: " + j + " s:" + segment[0], segment[0] >= finalK ? null : new SegTreePos(segment[0] + 1, startIndex[0], 0), findSegmentPos(j, aggrSegData2, 1, finalK + 1));
                    });
                }
            }
        });
    }

    @Test
    public void test_getPrevAnchor() {
        // TEST: need test for this
    }

    void assertCharAt(@NotNull BasedSequence sequence, @NotNull SegmentBuilder segments, @NotNull SegmentTree segTree) {
        BasedSequence sequenceFull = SegmentedSequenceFull.create(sequence, segments);
        int iMax = sequenceFull.length();
        System.out.println(segTree.toString(sequence));

        for (int i = 0; i < iMax; i++) {
            Segment seg = segTree.findSegment(i, sequence);
            assert seg != null;

            System.out.println("i: " + i + " pos: " + seg.getPos() + ", seg: " + seg);

            String expected = Character.toString(sequenceFull.charAt(i));
            String actual = Character.toString(seg.charAt(i));
            assertEquals("i: " + i, expected, actual);
        }
    }

    @Test
    public void test_build1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("SegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=4, na=2: [0), [2, 5), [6, 9), [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SeqSegTree{aggr: {[3, 1:, 0:], [6, 4:] }, seg: { 0:[0), 1:[2, 5), 4:[6, 9), 7:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);
    }

    @Test
    public void test_build2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SegmentBuilder segments = SegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append("abcd");
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("SegmentBuilder{[0, 10), s=0:0, u=1:4, t=1:4, l=10, sz=5, na=3: [0), [2, 5), a:'abcd', [6, 9), [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SeqSegTree{aggr: {[3, 1:, 0:], [7, 4:], [10, 9:] }, seg: { 0:[0), 1:[2, 5), 4:a:'abcd', 9:[6, 9), 12:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);
    }
}
