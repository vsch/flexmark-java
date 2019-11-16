package com.vladsch.flexmark.util.sequence;

import org.junit.Test;

import static org.junit.Assert.*;

public class BaseSequenceEntryTest {
    @Test
    public void test_basic() {
        String input1 = "0123456789";
        String input2 = "0123456789";
        String input3 = "0123456789";
        String input4 = "012345678";
        String input5 = "1234567890";

        // NOTE: must use crate() directly so as not to get cached bases from BasedSequence.of() use through BasedSequenceImpl
        BasedSequence sequence1 = BasedSequenceImpl.create(input1);
        BasedSequence sequence2 = BasedSequenceImpl.create(input2);
        BasedSequence sequence3 = BasedSequenceImpl.create(input3);
        BasedSequence sequence4 = BasedSequenceImpl.create(input4);
        BasedSequence sequence5 = BasedSequenceImpl.create(input5);

        BaseSequenceEntry entry = new BaseSequenceEntry();

        // getEqualsCall(): 0 - quick class and/or length, 1 - hash, 2 - quick lookup, 3 - content
        int[] equalsCall = { 0 };
        assertTrue(entry.testEquals(sequence1, input1, equalsCall));
        assertEquals(0, equalsCall[0]);

        assertTrue(entry.testEquals(sequence1, input2, equalsCall));
        assertEquals(0, equalsCall[0]);
        assertTrue(entry.testEquals(sequence1, input3, equalsCall));
        assertEquals(0, equalsCall[0]);
        assertFalse(entry.testEquals(sequence1, input4, equalsCall));
        assertEquals(0, equalsCall[0]);
        assertFalse(entry.testEquals(sequence1, input5, equalsCall));
        assertEquals(1, equalsCall[0]);
        assertTrue(entry.testEquals(sequence1, sequence1, equalsCall));
        assertEquals(0, equalsCall[0]);

        // first time around expensive content comparison, second time hash map lookup i
        assertTrue(entry.testEquals(sequence1, sequence2, equalsCall));
        assertEquals(3, equalsCall[0]);
        assertTrue(entry.testEquals(sequence1, sequence2, equalsCall));
        assertEquals(2, equalsCall[0]);

        sequence2 = null;

        // should release weak hash map ref to sequence2
        System.gc();

        sequence2 = BasedSequenceImpl.create(input2);

        // first time around expensive content comparison, second time hash map lookup i
        assertTrue(entry.testEquals(sequence1, sequence2, equalsCall));
        assertEquals(3, equalsCall[0]);
        assertTrue(entry.testEquals(sequence1, sequence2, equalsCall));
        assertEquals(2, equalsCall[0]);

        assertTrue(entry.testEquals(sequence1, sequence3, equalsCall));
        assertEquals(2, equalsCall[0]);

        assertFalse(entry.testEquals(sequence1, sequence4, equalsCall));
        assertEquals(0, equalsCall[0]);

        assertFalse(entry.testEquals(sequence1, sequence5, equalsCall));
        assertEquals(1, equalsCall[0]);
    }
}
