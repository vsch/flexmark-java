package com.vladsch.flexmark.experimental.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class SegTest {

    @Test
    public void test_basic() {
        Seg op = Seg.NULL;
        assertTrue(op.isNullRange());
        assertTrue(op.isNull());
        assertTrue(op.isNullString());
        assertFalse(op.isString());
        assertFalse(op.isText());
        assertFalse(op.isBase());
        assertFalse(op.isAnchor());
    }

    @Test
    public void test_toString() {
        Seg op = Seg.NULL;
        assertEquals("NULL", op.toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("[0, 1)", op.toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("[1)", op.toString());

        op = Seg.anchorSeg(1);
        assertEquals("[1)", op.toString());

        op = Seg.baseSeg(5, 10);
        assertEquals("[5, 10)", op.toString());
    }

    @Test
    public void test_withText() {
        Seg op = Seg.NULL;
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL", op.withText("").toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.withText("").toString());

        op = Seg.stringSeg("");
        assertEquals("'abc'", op.withText("abc").toString());

        op = Seg.stringSeg("a");
        assertEquals("NULL", op.withText("").toString());
        assertEquals("'abc'", op.withText("abc").toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("[0, 1)", op.withText("").toString());
        assertEquals("[0, 1, 'abc')", op.withText("abc").toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("[1)", op.withText("").toString());
        assertEquals("[1, 'abc')", op.withText("abc").toString());
    }

    @Test
    public void test_withTextSuffix() {
        Seg op = Seg.NULL;
        assertEquals("'abc'", op.withTextSuffix("abc").toString());
        assertEquals("NULL", op.withTextSuffix("").toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.withTextSuffix("").toString());

        op = Seg.stringSeg("");
        assertEquals("'abc'", op.withTextSuffix("abc").toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.withTextSuffix("").toString());
        assertEquals("'aabc'", op.withTextSuffix("abc").toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("[0, 1)", op.withTextSuffix("").toString());
        assertEquals("[0, 1, 'abc')", op.withTextSuffix("abc").toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("[1)", op.withTextSuffix("").toString());
        assertEquals("[1, 'abc')", op.withTextSuffix("abc").toString());
    }

    @Test
    public void test_withTextPrefix() {
        Seg op = Seg.NULL;
        assertEquals("'abc'", op.withTextPrefix("abc").toString());
        assertEquals("NULL", op.withTextPrefix("").toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.withTextPrefix("").toString());

        op = Seg.stringSeg("");
        assertEquals("'abc'", op.withTextPrefix("abc").toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.withTextPrefix("").toString());
        assertEquals("'abca'", op.withTextPrefix("abc").toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("[0, 1)", op.withTextPrefix("").toString());
        assertEquals("[0, 1, 'abc')", op.withTextPrefix("abc").toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("[1)", op.withTextPrefix("").toString());
        assertEquals("[1, 'abc')", op.withTextPrefix("abc").toString());
    }

    @Test
    public void test_withRange() {
        Seg op = Seg.NULL;
        assertEquals("NULL", op.withRange(Range.NULL).toString());
        assertEquals("[0, 1)", op.withRange(Range.of(0, 1)).toString());
        assertEquals("[1)", op.withRange(Range.of(1, 1)).toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.toString());
        assertEquals("NULL", op.withRange(Range.NULL).toString());
        assertEquals("[0, 1)", op.withRange(Range.of(0, 1)).toString());
        assertEquals("[1)", op.withRange(Range.of(1, 1)).toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.withRange(Range.NULL).toString());
        assertEquals("[0, 1, 'a')", op.withRange(Range.of(0, 1)).toString());
        assertEquals("[1, 'a')", op.withRange(Range.of(1, 1)).toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("NULL", op.withRange(Range.NULL).toString());
        assertEquals("[0, 1)", op.withRange(Range.of(0, 1)).toString());
        assertEquals("[1)", op.withRange(Range.of(1, 1)).toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("NULL", op.withRange(Range.NULL).toString());
        assertEquals("[0, 1)", op.withRange(Range.of(0, 1)).toString());
        assertEquals("[1)", op.withRange(Range.of(1, 1)).toString());
    }

    @Test
    public void test_withRange2() {
        Seg op = Seg.NULL;
        assertEquals("NULL", op.withRange(Range.NULL.getStart(), Range.NULL.getEnd()).toString());
        assertEquals("[0, 1)", op.withRange(0, 1).toString());
        assertEquals("[1)", op.withRange(1, 1).toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.toString());
        assertEquals("NULL", op.withRange(Range.NULL.getStart(), Range.NULL.getEnd()).toString());
        assertEquals("[0, 1)", op.withRange(0, 1).toString());
        assertEquals("[1)", op.withRange(1, 1).toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.withRange(Range.NULL.getStart(), Range.NULL.getEnd()).toString());
        assertEquals("[0, 1, 'a')", op.withRange(0, 1).toString());
        assertEquals("[1, 'a')", op.withRange(1, 1).toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("NULL", op.withRange(Range.NULL.getStart(), Range.NULL.getEnd()).toString());
        assertEquals("[0, 1)", op.withRange(0, 1).toString());
        assertEquals("[1)", op.withRange(1, 1).toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("NULL", op.withRange(Range.NULL.getStart(), Range.NULL.getEnd()).toString());
        assertEquals("[0, 1)", op.withRange(0, 1).toString());
        assertEquals("[1)", op.withRange(1, 1).toString());
    }

    @Test
    public void test_withStart() {
        Seg op = Seg.NULL;
        assertEquals("NULL", op.withStart(Range.NULL.getStart()).toString());
        assertEquals("[0)", op.withStart(0).toString());
        assertEquals("[1)", op.withStart(1).toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.toString());
        assertEquals("NULL", op.withStart(Range.NULL.getStart()).toString());
        assertEquals("[0)", op.withStart(0).toString());
        assertEquals("[1)", op.withStart(1).toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.withStart(Range.NULL.getStart()).toString());
        assertEquals("[0, 'a')", op.withStart(0).toString());
        assertEquals("[1, 'a')", op.withStart(1).toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("NULL", op.withStart(Range.NULL.getStart()).toString());
        assertEquals("[0, 1)", op.withStart(0).toString());
        assertEquals("[1)", op.withStart(1).toString());
        assertEquals("[2, 1)", op.withStart(2).toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("NULL", op.withStart(Range.NULL.getStart()).toString());
        assertEquals("[0, 1)", op.withStart(0).toString());
        assertEquals("[1)", op.withStart(1).toString());

        op = Seg.baseSeg(1, 3);
        assertEquals("NULL", op.withStart(Range.NULL.getStart()).toString());
        assertEquals("[0, 3)", op.withStart(0).toString());
        assertEquals("[3)", op.withStart(3).toString());
        assertEquals("[1, 3)", op.withStart(1).toString());
    }

    @Test
    public void test_withEnd() {
        Seg op = Seg.NULL;
        assertEquals("NULL", op.withEnd(Range.NULL.getEnd()).toString());
        assertEquals("[1)", op.withEnd(1).toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.toString());
        assertEquals("NULL", op.withEnd(Range.NULL.getEnd()).toString());
        assertEquals("[1)", op.withEnd(1).toString());

        op = Seg.stringSeg("");
        assertEquals("NULL", op.withEnd(Range.NULL.getEnd()).toString());
        assertEquals("[1)", op.withEnd(1).toString());

        op = Seg.stringSeg("a");
        assertEquals("'a'", op.withEnd(Range.NULL.getEnd()).toString());
        assertEquals("[1, 'a')", op.withEnd(1).toString());

        op = Seg.baseSeg(0, 1);
        assertEquals("NULL", op.withEnd(Range.NULL.getEnd()).toString());
        assertEquals("[0, 1)", op.withEnd(1).toString());
        assertEquals("[0, 2)", op.withEnd(2).toString());

        op = Seg.baseSeg(1, 1);
        assertEquals("NULL", op.withEnd(Range.NULL.getEnd()).toString());
        assertEquals("[1, 3)", op.withEnd(3).toString());
        assertEquals("[1)", op.withEnd(1).toString());
    }
}
