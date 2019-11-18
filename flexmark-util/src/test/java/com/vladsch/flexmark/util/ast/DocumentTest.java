package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentTest {

    @Test
    public void testLineNumberWithUnixEol() {
        Document document = new Document(new MutableDataSet(), BasedSequence.of("Hello\nWorld").subSequence(0, ((CharSequence) "Hello\nWorld").length()));

        assertEquals(0, document.getLineNumber(0));
        assertEquals(0, document.getLineNumber(5));
        assertEquals(1, document.getLineNumber(6));
        assertEquals(1, document.getLineNumber(10));
    }

    @Test
    public void testLineNumberWithUnixEol2() {
        Document document = new Document(new MutableDataSet(), BasedSequence.of("Hello\n\nWorld").subSequence(0, ((CharSequence) "Hello\n\nWorld").length()));

        assertEquals(0, document.getLineNumber(0));
        assertEquals(0, document.getLineNumber(5));
        assertEquals(1, document.getLineNumber(6));
        assertEquals(2, document.getLineNumber(7));
        assertEquals(2, document.getLineNumber(8));
        assertEquals(2, document.getLineNumber(10));
    }

    @Test
    public void testLineNumberWithWindowsEol() {
        Document document = new Document(new MutableDataSet(), BasedSequence.of("Hello\r\nWorld").subSequence(0, ((CharSequence) "Hello\r\nWorld").length()));

        assertEquals(0, document.getLineNumber(0));
        assertEquals(0, document.getLineNumber(5));
        assertEquals(0, document.getLineNumber(6));
        assertEquals(1, document.getLineNumber(7));
        assertEquals(1, document.getLineNumber(11));
    }

    @Test
    public void testLineNumberWithWindowsEol2() {
        Document document = new Document(new MutableDataSet(), BasedSequence.of("Hello\r\n\r\nWorld").subSequence(0, ((CharSequence) "Hello\r\n\r\nWorld").length()));

        assertEquals(0, document.getLineNumber(0));
        assertEquals(0, document.getLineNumber(5));
        assertEquals(0, document.getLineNumber(6));
        assertEquals(1, document.getLineNumber(7));
        assertEquals(1, document.getLineNumber(8));
        assertEquals(2, document.getLineNumber(9));
        assertEquals(2, document.getLineNumber(10));
        assertEquals(2, document.getLineNumber(11));
    }

    @Test
    public void testLineNumberWithOldMacEol() {
        Document document = new Document(new MutableDataSet(), BasedSequence.of("Hello\rWorld").subSequence(0, ((CharSequence) "Hello\rWorld").length()));

        assertEquals(0, document.getLineNumber(0));
        assertEquals(0, document.getLineNumber(5));
        assertEquals(1, document.getLineNumber(6));
        assertEquals(1, document.getLineNumber(10));
    }

    @Test
    public void testLineNumberWithOldMacEol2() {
        Document document = new Document(new MutableDataSet(), BasedSequence.of("Hello\r\rWorld").subSequence(0, ((CharSequence) "Hello\r\rWorld").length()));

        assertEquals(0, document.getLineNumber(0));
        assertEquals(0, document.getLineNumber(5));
        assertEquals(1, document.getLineNumber(6));
        assertEquals(2, document.getLineNumber(7));
        assertEquals(2, document.getLineNumber(8));
        assertEquals(2, document.getLineNumber(10));
    }
}
