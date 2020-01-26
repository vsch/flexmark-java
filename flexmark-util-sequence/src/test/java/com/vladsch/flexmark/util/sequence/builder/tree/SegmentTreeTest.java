package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceFull;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.PlainSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import static com.vladsch.flexmark.util.misc.Utils.escapeJavaString;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_INCLUDE_ANCHORS;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_TRACK_FIRST256;
import static com.vladsch.flexmark.util.sequence.builder.tree.SegmentTree.*;
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
                    assertEquals("i: " + i + " j: " + j, j >= i ? null : new SegmentTreePos(0, 0, 0), findSegmentPos(j, aggrSegData1, 0, 1));
                    assertEquals("i: " + i + " j: " + j, j >= i ? null : new SegmentTreePos(1, 0, 0), findSegmentPos(j, aggrSegData2, 1, 1));
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
                    assertEquals("i: " + i + " j: " + j, j >= i + 1000 ? null : new SegmentTreePos(j >= i ? 1 : 0, j >= i ? i : 0, 0), findSegmentPos(j, aggrSegData1, 0, 2));
                    assertEquals("i: " + i + " j: " + j, j >= i + 1000 ? null : new SegmentTreePos((j >= i ? 2 : 1), (j >= i ? i : 0), 0), findSegmentPos(j, aggrSegData2, 1, 3));
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

                        assertEquals("k: " + finalK + " i: " + i + " j: " + j + " s:" + segment[0], segment[0] >= finalK ? null : new SegmentTreePos(segment[0], startIndex[0], 0), findSegmentPos(j, aggrSegData1, 0, finalK));
                        assertEquals("k: " + finalK + " i: " + i + " j: " + j + " s:" + segment[0], segment[0] >= finalK ? null : new SegmentTreePos(segment[0] + 1, startIndex[0], 0), findSegmentPos(j, aggrSegData2, 1, finalK + 1));
                    });
                }
            }
        });
    }

    @Test
    public void test_getPrevAnchor() {
        // TEST: need test for this
    }

    void assertCharAt(@NotNull BasedSequence sequence, @NotNull PlainSegmentBuilder segments, @NotNull SegmentTree segTree) {
        BasedSequence sequenceFull = SegmentedSequenceFull.create(sequence, segments);
        int iMax = sequenceFull.length();
        System.out.println(segTree.toString(sequence));
        Segment seg = null;

        for (int i = 0; i < iMax; i++) {
            if (seg == null || seg.notInSegment(i)) {
                seg = segTree.findSegment(i, sequence, seg);
                assert seg != null;
                System.out.println("i: " + i + " pos: " + seg.getPos() + ", seg: " + seg);
            }

            String expected = Character.toString(sequenceFull.charAt(i));
            String actual = Character.toString(seg.charAt(i));
            assertEquals("i: " + i, expected, actual);
        }

        seg = null;
        for (int i = iMax; i-- > 0; ) {
            if (seg == null || seg.notInSegment(i)) {
                seg = segTree.findSegment(i, sequence, seg);
                assert seg != null : "i: " + i;
                System.out.println("i: " + i + " pos: " + seg.getPos() + ", seg: " + seg);
            }

            String expected = Character.toString(sequenceFull.charAt(i));
            String actual = Character.toString(seg.charAt(i));
            assertEquals("i: " + i, expected, actual);
        }
    }

    @Test
    public void test_build1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=4, na=2: [0), [2, 5), [6, 9), [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[3, 1:, 0:], [6, 4:] }, seg: { 0:[0), 1:[2, 5), 4:[6, 9), 7:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);
    }

    @Test
    public void test_build2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append("abcd");
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:0, u=1:4, t=1:4, l=10, sz=5, na=3: [0), [2, 5), a:'abcd', [6, 9), [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[3, 1:, 0:], [7, 4:], [10, 9:] }, seg: { 0:[0), 1:[2, 5), 4:a:'abcd', 9:[6, 9), 12:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);
    }

    @Test
    public void test_buildSubSequence() {
        String input = "0123456789";
        String expected = "> 0123456789\n";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("> ");
        segments.append(0, 10);
        segments.append("\n");
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:1, u=2:3, t=2:3, l=13, sz=4, na=3: a:'> ', [0, 10), a:'\\n', [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[2, 0:], [12, 3:], [13, 6:] }, seg: { 0:a:'> ', 3:[0, 10), 6:a:'\\n', 7:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);

        SegmentTreeRange segRange = segTree.getSegmentRange(0, 12, 0, segTree.size(), sequence, null);
        assertEquals("SegmentTreeRange{startIndex=0, endIndex=12, startOffset=0, endOffset=10, startPos=0, endPos=2, length=12}", segRange.toString());

        SequenceBuilder builder = sequence.getBuilder();
        segTree.addSegments(builder.getSegmentBuilder(), segRange.startIndex, segRange.startIndex + segRange.length, segRange.startOffset, segRange.endOffset, segRange.startPos, segRange.endPos);
        assertEquals("⟦⟧> ⟦0123456789⟧", builder.toStringWithRanges(true));
    }

    @Test
    public void test_buildSubSequence2() {
        String input = "0123456789";
        String expected = "> 0123456789\n";
        BasedSequence sequence = BasedSequence.of(input);
        PlainSegmentBuilder segments = PlainSegmentBuilder.emptyBuilder(F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append("> ");
        segments.append(0, 10);
        segments.append("\n");
        assertEquals("PlainSegmentBuilder{[0, 10), s=0:1, u=2:3, t=2:3, l=13, sz=4, na=3: a:'> ', [0, 10), a:'\\n', [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[2, 0:], [12, 3:], [13, 6:] }, seg: { 0:a:'> ', 3:[0, 10), 6:a:'\\n', 7:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);

        SegmentTreeRange segRange = segTree.getSegmentRange(0, 13, 0, segTree.size(), sequence, null);
        assertEquals("SegmentTreeRange{startIndex=0, endIndex=13, startOffset=0, endOffset=0, startPos=0, endPos=3, length=13}", segRange.toString());

        SequenceBuilder builder = sequence.getBuilder();
        segTree.addSegments(builder.getSegmentBuilder(), segRange.startIndex, segRange.startIndex + segRange.length, segRange.startOffset, segRange.endOffset, segRange.startPos, segRange.endPos);
        assertEquals("⟦⟧> ⟦0123456789⟧\\n⟦⟧", builder.toStringWithRanges(true));
    }

    // ************************************************************************
    // NOTE: Segment building directly from SegmentTree data
    // ************************************************************************

    @Test
    public void test_buildSegments1() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);

        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }

        assertEquals("BasedSegmentBuilder{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[2, 0:], [10, 1:], [11, 4:], [13, 7:], [21, 8:], [23, 11:], [25, 14:], [34, 15:] }, seg: { 0:a:2x' ', 1:[0, 8), 4:[9, 10), 7:a:2x' ', 8:[10, 18), 11:[19, 21), 14:a:2x' ', 15:[21, 30) } }", segTree.toString(sequence));

        BasedSegmentBuilder segments2 = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
        segTree.addSegments(segments2, 0, segments.length(), segments.getStartOffsetIfNeeded(), segments.getEndOffsetIfNeeded(), 0, segTree.size());

        assertEquals("BasedSegmentBuilder{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments2.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments2.length());
        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments2.toStringWithRangesVisibleWhitespace(input));
    }

    @Test
    public void test_buildSegments2() {
        String input = "" +
                "  line 1 \n" +
                "  line 2 \n" +
                "\n" +
                "  line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);

        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
            if (!trim.isEmpty()) segments.append("    ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }

        assertEquals("BasedSegmentBuilder{[0, 30), s=3:6, u=3:6, t=3:6, l=34, sz=8, na=8: a:2x' ', [0, 8), [9, 10), a:2x' ', [10, 18), [19, 21), a:2x' ', [21, 30) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("  ⟦  line 1⟧⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧  ⟦  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[2, 0:], [10, 1:], [11, 4:], [13, 7:], [21, 8:], [23, 11:], [25, 14:], [34, 15:] }, seg: { 0:a:2x' ', 1:[0, 8), 4:[9, 10), 7:a:2x' ', 8:[10, 18), 11:[19, 21), 14:a:2x' ', 15:[21, 30) } }", segTree.toString(sequence));

        BasedSegmentBuilder segments3 = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
        BasedSequence sequence1 = SegmentedSequenceFull.create(sequence, segments);
        BasedSequence sequence2 = sequence1.subSequence(10, segments.length() - 10);
        assertEquals("\\n    line 2\\n\\n ", sequence2.toVisibleWhitespaceString());
        sequence2.addSegments(segments3);
        assertEquals("BasedSegmentBuilder{[9, 21), s=2:3, u=2:3, t=2:3, l=14, sz=6, na=5: [9, 10), a:2x' ', [10, 18), [19, 21), a:' ', [21) }", escapeJavaString(segments3.toStringPrep()));
        assertEquals("⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧ ⟦⟧", segments3.toStringWithRangesVisibleWhitespace(input));

        BasedSegmentBuilder segments2 = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
        SegmentTreeRange treeRange = segTree.getSegmentRange(10, segments.length() - 10, 0, segTree.size(), sequence, null);
        segTree.addSegments(segments2, treeRange);

        assertEquals("BasedSegmentBuilder{[9, 21), s=2:3, u=2:3, t=2:3, l=14, sz=6, na=5: [9, 10), a:2x' ', [10, 18), [19, 21), a:' ', [21) }", escapeJavaString(segments2.toStringPrep()));
        assertEquals(segments2.toString(sequence).length(), segments2.length());
        assertEquals("⟦\\n⟧  ⟦  line 2⟧⟦\\n\\n⟧ ⟦⟧", segments2.toStringWithRangesVisibleWhitespace(input));
    }

//    @Test
//    public void test_buildSegments2() {
//        String input = "" +
//                "  line 1 \n" +
//                "  line 2 \n" +
//                "\n" +
//                "  line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals("BasedSegmentBuilder{[0, 30), s=0:0, u=0:0, t=0:0, l=28, sz=3, na=3: [0, 8), [9, 18), [19, 30) }", escapeJavaString(segments.toStringPrep()));
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        assertEquals("⟦  line 1⟧⟦\\n  line 2⟧⟦\\n\\n  line 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "  line 1\n" +
//                "  line 2\n" +
//                "\n" +
//                "  line 3\n" +
//                "", segments.toString(sequence));
//    }
//
//    @Test
//    public void test_optimizersCompound3Anchors() {
//        String input = "" +
//                "line 1\n" +
//                "line 2 \n" +
//                "\n" +
//                "line 3\n" +
//                "";
//        BasedSequence sequence = BasedSequence.of(input);
//        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
//        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
//
//        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
//        for (BasedSequence line : lines) {
//            BasedSequence trim = line.trim();
////            if (!trim.isEmpty()) segments.append("  ");
//            segments.append(trim.getSourceRange());
//            segments.append("\n");
//        }
//        assertEquals("BasedSegmentBuilder{[0, 23), s=0:0, u=0:0, t=0:0, l=22, sz=2, na=2: [0, 13), [14, 23) }", escapeJavaString(segments.toStringPrep()));
//        assertEquals(segments.toString(sequence).length(), segments.length());
//
//        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));
//
//        assertEquals("" +
//                "line 1\n" +
//                "line 2\n" +
//                "\n" +
//                "line 3\n" +
//                "", segments.toString(sequence));
//    }
}
