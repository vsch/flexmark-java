package com.vladsch.flexmark.util.sequence.builder.tree;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.SegmentedSequenceFull;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.CharRecoveryOptimizer;
import com.vladsch.flexmark.util.sequence.builder.SegmentBuilderBase;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;
import java.util.function.BiConsumer;

import static com.vladsch.flexmark.util.misc.Utils.escapeJavaString;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_INCLUDE_ANCHORS;
import static com.vladsch.flexmark.util.sequence.builder.ISegmentBuilder.F_TRACK_FIRST256;
import static com.vladsch.flexmark.util.sequence.builder.tree.SegmentTree.MAX_VALUE;
import static org.junit.Assert.assertEquals;

public class SegmentOffsetTreeTest {
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

    void assertCharAt(@NotNull BasedSequence sequence, @NotNull SegmentBuilderBase<?> segments, @NotNull SegmentTree segTree) {
        BasedSequence sequenceFull = SegmentedSequenceFull.create(sequence, segments);
        SegmentOffsetTree segOffsetTree = segTree.getSegmentOffsetTree(sequence);

        System.out.println(segments.toStringWithRangesVisibleWhitespace(sequence));
        System.out.println(segTree.toString(sequence));
        System.out.println(segOffsetTree.toString(sequence));

        int iMax = sequenceFull.length();
        Segment seg = null;
        for (int i = 0; i < iMax; i++) {
            int offset = sequenceFull.getIndexOffset(i);

            if (offset >= 0) {
                if (seg == null || seg.offsetNotInSegment(offset)) {
                    if (offset == 6) {
                        int tmp = 0;
                    }
                    seg = segOffsetTree.findSegmentByOffset(offset, sequence, seg);
                    assert seg != null && (!seg.offsetNotInSegment(offset) || seg.pos == segOffsetTree.size() - 1) : "offset: " + offset + " seg: " + seg;
//                    System.out.println("i=" + i + " pos=" + seg.getPos() + ", segOff=" + seg);
                }

                int actual = offset - seg.getStartOffset() + seg.getStartIndex();
                assertEquals("i=" + i + " offset=" + offset + " seg=" + seg + " segStartIndex=" + seg.getStartIndex(), i, actual);
            }
        }

        seg = null;
        for (int i = iMax; i-- > 0; ) {
            int offset = sequenceFull.getIndexOffset(i);

            if (offset >= 0) {
                if (seg == null || seg.offsetNotInSegment(offset)) {
                    seg = segOffsetTree.findSegmentByOffset(offset, sequence, seg);
                    assert seg != null && (!seg.offsetNotInSegment(offset) || seg.pos == segOffsetTree.size() - 1) : "offset: " + offset + " seg: " + seg;
//                    System.out.println("i=" + i + " pos=" + seg.getPos() + ", segOff=" + seg);
                }

                int actual = offset - seg.getStartOffset() + seg.getStartIndex();
                assertEquals("i=" + i + " offset=" + offset + " seg=" + seg + " segStartIndex=" + seg.getStartIndex(), i, actual);
            }
        }
    }

    @Test
    public void test_build1() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=4, na=2: [0), [2, 5), [6, 9), [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[3, 1:, 0:], [6, 4:] }, seg: { 0:[0), 1:[2, 5), 4:[6, 9), 7:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);
    }

    @Test
    public void test_build2() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append("abcd");
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=1:4, t=1:4, l=10, sz=5, na=3: [0), [2, 5), a:'abcd', [6, 9), [10) }", segments.toStringPrep());

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[3, 1:, 0:], [7, 4:], [10, 9:] }, seg: { 0:[0), 1:[2, 5), 4:a:'abcd', 9:[6, 9), 12:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments, segTree);
    }

    @Test
    public void test_build1SegmentTree() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder segments = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=0:0, t=0:0, l=6, sz=4, na=2: [0), [2, 5), [6, 9), [10) }", segments.getSegmentBuilder().toStringPrep());

        SegmentTree segTree = segments.toSequence().getSegmentTree();
        assertEquals("SegmentTree{aggr: {[3, 1:, 0:], [6, 4:] }, seg: { 0:[0), 1:[2, 5), 4:[6, 9), 7:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments.getSegmentBuilder(), segTree);
    }

    @Test
    public void test_build2SegmentTree() {
        String input = "0123456789";
        BasedSequence sequence = BasedSequence.of(input);
        SequenceBuilder segments = SequenceBuilder.emptyBuilder(sequence, F_INCLUDE_ANCHORS | F_TRACK_FIRST256);

        segments.append(0, 0);
        segments.append(2, 5);
        segments.append("abcd");
        segments.append(6, 9);
        segments.append(10, 10);
        assertEquals("BasedSegmentBuilder{[0, 10), s=0:0, u=1:4, t=1:4, l=10, sz=5, na=3: [0), [2, 5), a:'abcd', [6, 9), [10) }", segments.getSegmentBuilder().toStringPrep());

        SegmentTree segTree = segments.toSequence().getSegmentTree();
        assertEquals("SegmentTree{aggr: {[3, 1:, 0:], [7, 4:], [10, 9:] }, seg: { 0:[0), 1:[2, 5), 4:a:'abcd', 9:[6, 9), 12:[10) } }", segTree.toString(sequence));
        assertCharAt(sequence, segments.getSegmentBuilder(), segTree);
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
        assertCharAt(sequence, segments, segTree);
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
        assertCharAt(sequence, segments, segTree);
    }

    @Test
    public void test_optimizersCompound3Anchors() {
        String input = "" +
                "line 1\n" +
                "line 2 \n" +
                "\n" +
                "line 3\n" +
                "";
        BasedSequence sequence = BasedSequence.of(input);
        CharRecoveryOptimizer optimizer = new CharRecoveryOptimizer(PositionAnchor.CURRENT);
        BasedSegmentBuilder segments = BasedSegmentBuilder.emptyBuilder(sequence, optimizer, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);

        @NotNull List<BasedSequence> lines = sequence.splitListEOL(false);
        for (BasedSequence line : lines) {
            BasedSequence trim = line.trim();
//            if (!trim.isEmpty()) segments.append("  ");
            segments.append(trim.getSourceRange());
            segments.append("\n");
        }
        assertEquals("BasedSegmentBuilder{[0, 23), s=0:0, u=0:0, t=0:0, l=22, sz=2, na=2: [0, 13), [14, 23) }", escapeJavaString(segments.toStringPrep()));
        assertEquals(segments.toString(sequence).length(), segments.length());
        assertEquals("⟦line 1\\nline 2⟧⟦\\n\\nline 3\\n⟧", segments.toStringWithRangesVisibleWhitespace(input));

        SegmentTree segTree = SegmentTree.build(segments.getSegments(), segments.getText());
        assertEquals("SegmentTree{aggr: {[13, 0:], [22, 3:] }, seg: { 0:[0, 13), 3:[14, 23) } }", segTree.toString(sequence));

        BasedSegmentBuilder segments3 = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
        BasedSequence sequence1 = SegmentedSequenceFull.create(sequence, segments);
        BasedSequence sequence2 = sequence1.subSequence(5, segments.length() - 5);
        assertEquals("1\\nline 2\\n\\nli", sequence2.toVisibleWhitespaceString());
        sequence2.addSegments(segments3);
        assertEquals("BasedSegmentBuilder{[5, 18), s=0:0, u=0:0, t=0:0, l=12, sz=2, na=2: [5, 13), [14, 18) }", escapeJavaString(segments3.toStringPrep()));
        assertEquals("⟦1\\nline 2⟧⟦\\n\\nli⟧", segments3.toStringWithRangesVisibleWhitespace(input));

        BasedSegmentBuilder segments2 = BasedSegmentBuilder.emptyBuilder(sequence, F_TRACK_FIRST256 | F_INCLUDE_ANCHORS);
        SegmentTreeRange treeRange = segTree.getSegmentRange(5, segments.length() - 5, 0, segTree.size(), sequence, null);
        segTree.addSegments(segments2, treeRange);

        assertEquals("BasedSegmentBuilder{[5, 18), s=0:0, u=0:0, t=0:0, l=12, sz=2, na=2: [5, 13), [14, 18) }", escapeJavaString(segments2.toStringPrep()));
        assertEquals(segments2.toString(sequence).length(), segments2.length());
        assertEquals("⟦1\\nline 2⟧⟦\\n\\nli⟧", segments2.toStringWithRangesVisibleWhitespace(input));
        assertCharAt(sequence, segments, segTree);
    }
}
