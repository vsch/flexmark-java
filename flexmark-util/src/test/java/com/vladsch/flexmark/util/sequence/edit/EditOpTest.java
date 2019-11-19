package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.junit.Test;

import static org.junit.Assert.*;

public class EditOpTest {

    @Test
    public void test_basic() {
        EditOp op = EditOp.NULL_OP;
        assertTrue(op.isNullOp());
        assertTrue(op.isNull());
        assertFalse(op.isNotNullOp());
        assertFalse(op.isNotNull());
        assertFalse(op.isBase());
        assertFalse(op.isTextOp());
        assertFalse(op.isPlainText());
    }

    @Test
    public void test_toString() {
        EditOp op = EditOp.NULL_OP;
        assertEquals("NULL_OP", op.toString());

        op = EditOp.plainText("");
        assertEquals("NULL_OP", op.toString());

        op = EditOp.plainText("a");
        assertEquals("'a'", op.toString());

        op = EditOp.baseOp(0, 1);
        assertEquals("[0, 1)", op.toString());

        op = EditOp.baseOp(1, 1);
        assertEquals("[1, 1)", op.toString());

        op = EditOp.baseOp(5, 10);
        assertEquals("[5, 10)", op.toString());

        op = EditOp.insertOp(5, "abc");
        assertEquals("[5, 5, 'abc')", op.toString());

        op = EditOp.insertOp(10, "");
        assertEquals("NULL_OP", op.toString());

        op = EditOp.insertOp(5, "");
        assertEquals("NULL_OP", op.toString());

        op = EditOp.replaceOp(5, 10, "abc");
        assertEquals("[5, 10, 'abc')", op.toString());

        op = EditOp.replaceOp(5, 10, "");
        assertEquals("[5, 10, '')", op.toString());

        op = EditOp.replaceOp(5, 5, "");
        assertEquals("NULL_OP", op.toString());

        op = EditOp.deleteOp(5, 10);
        assertEquals("[5, 10, '')", op.toString());

        op = EditOp.deleteOp(5, 5);
        assertEquals("NULL_OP", op.toString());
    }

    @Test
    public void test_withText() {
        EditOp op = EditOp.NULL_OP;
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.plainText("");
        assertEquals("NULL_OP", op.withText("").toString());

        op = EditOp.plainText("");
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.plainText("a");
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.baseOp(0, 1);
        assertEquals("[0, 1, '')", op.withText("").toString());
        assertEquals("[0, 1, 'abc')", op.withText("abc").toString());
        assertEquals("[0, 1)", op.withText(null).toString());

        op = EditOp.baseOp(1, 1);
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("[1, 1, 'abc')", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.insertOp(5, "abc");
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("[5, 5, 'abc')", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.insertOp(10, "");
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.insertOp(5, "");
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.replaceOp(5, 10, "abc");
        assertEquals("[5, 10, '')", op.withText("").toString());
        assertEquals("[5, 10, 'abc')", op.withText("abc").toString());
        assertEquals("[5, 10)", op.withText(null).toString());

        op = EditOp.replaceOp(5, 10, "");
        assertEquals("[5, 10, '')", op.withText("").toString());
        assertEquals("[5, 10, 'abc')", op.withText("abc").toString());
        assertEquals("[5, 10)", op.withText(null).toString());

        op = EditOp.replaceOp(5, 5, "");
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());

        op = EditOp.deleteOp(5, 10);
        assertEquals("[5, 10, '')", op.withText("").toString());
        assertEquals("[5, 10, 'abc')", op.withText("abc").toString());
        assertEquals("[5, 10)", op.withText(null).toString());

        op = EditOp.deleteOp(5, 5);
        assertEquals("NULL_OP", op.withText("").toString());
        assertEquals("'abc'", op.withText("abc").toString());
        assertEquals("NULL_OP", op.withText(null).toString());
    }

    @Test
    public void test_keepText() {
        EditOp op = EditOp.NULL_OP;
        assertEquals("NULL_OP", op.keepText(Range.NULL).toString());
        assertEquals("[0, 1)", op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1)", op.keepText(Range.of(1, 1)).toString());

        op = EditOp.plainText("");
        assertEquals("NULL_OP", op.toString());
        assertEquals("NULL_OP", op.keepText(Range.NULL).toString());
        assertEquals("[0, 1)", op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1)", op.keepText(Range.of(1, 1)).toString());

        op = EditOp.plainText("");
        assertEquals("NULL_OP",  op.keepText(Range.NULL).toString());
        assertEquals("[0, 1)",   op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1)",   op.keepText(Range.of(1, 1)).toString());

        op = EditOp.plainText("a");
        assertEquals("'a'", op.keepText(Range.NULL).toString());
        assertEquals("[0, 1, 'a')",  op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1, 'a')",  op.keepText(Range.of(1, 1)).toString());

        op = EditOp.baseOp(0, 1);
        assertEquals("[0, 1)",op.keepText(Range.NULL).toString());
        assertEquals("[0, 1)", op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1)", op.keepText(Range.of(1, 1)).toString());

        op = EditOp.baseOp(1, 1);
        assertEquals("[1, 1)", op.keepText(Range.NULL).toString());
        assertEquals("[0, 1)",  op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1)",  op.keepText(Range.of(1, 1)).toString());

        op = EditOp.insertOp(5, "abc");
        assertEquals("[5, 5, 'abc')", op.keepText(Range.NULL).toString());
        assertEquals("[0, 1, 'abc')",op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1, 'abc')", op.keepText(Range.of(1, 1)).toString());

        op = EditOp.insertOp(10, "");
        assertEquals("NULL_OP",op.keepText(Range.NULL).toString());
        assertEquals("[0, 1)", op.keepText(Range.of(0, 1)).toString());
        assertEquals("[1, 1)", op.keepText(Range.of(1, 1)).toString());
    }
}
