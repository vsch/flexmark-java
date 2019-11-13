package com.vladsch.flexmark.util.sequence;

import org.junit.*;

import static org.junit.Assert.*;

@SuppressWarnings("ALL")
public class BaseSequenceManagerTest {
    @Test
    public void test_Reuse() {
        String input1 = "0123456789";
        String input2 = "0123456789";
        String input3 = "0123456789";
        String input4 = "012345678";
        String input5 = "1234567890";

        BaseSequenceManager manager = BaseSequenceManager.INSTANCE;

        // NOTE: must use crate() directly so as not to use BasedSequenceImpl.create()
        BasedSequence sequence1 = manager.getBaseSequence(input1, seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        BasedSequence sequence2 = manager.getBaseSequence(input2, seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        BasedSequence sequence3 = manager.getBaseSequence(input3, seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        BasedSequence sequence4 = manager.getBaseSequence(input4, seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        BasedSequence sequence5 = manager.getBaseSequence(input5, seq -> BasedSequenceImpl.create(seq, 0, seq.length()));

        assertSame(sequence1, sequence2);
        assertSame(sequence1, sequence3);
        assertNotEquals(sequence1, sequence4);
        assertNotEquals(sequence1, sequence5);

        assertTrue(sequence1.equals(input1));
        assertTrue(sequence2.equals(input2));
        assertTrue(sequence3.equals(input3));
        assertTrue(sequence4.equals(input4));
        assertTrue(sequence5.equals(input5));
    }

    @Test
    public void test_Release() {
        BaseSequenceManager manager = BaseSequenceManager.INSTANCE;

        // NOTE: must use crate() directly
        BasedSequence sequence1 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));

        // 0 if map lookup, 1 - set search, 2 - construct and add to map/set, 3 - construct but was added in anther thread, so dropped
        assertEquals(20, manager.getCallType());

        BasedSequence sequence2 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(0, manager.getCallType());

        BasedSequence sequence3 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(0, manager.getCallType());

        BasedSequence sequence4 = manager.getBaseSequence("012345678", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(20, manager.getCallType());

        BasedSequence sequence5 = manager.getBaseSequence("1234567890", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(21, manager.getCallType());
        sequence5 = manager.getBaseSequence("1234567890", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(0, manager.getCallType());

        assertSame(sequence1, sequence2);
        assertSame(sequence1, sequence3);
        assertNotEquals(sequence1, sequence4);
        assertNotEquals(sequence1, sequence5);

        assertTrue(sequence1.equals("0123456789"));
        assertTrue(sequence2.equals("0123456789"));
        assertTrue(sequence3.equals("0123456789"));
        assertTrue(sequence4.equals("012345678"));
        assertTrue(sequence5.equals("1234567890"));

        // should not release since it is held by sequence1, sequence2 and sequence3
        System.gc();
        sequence1 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(0, manager.getCallType());

        // should not release since it is held by sequence2 and sequence3
        sequence1 = null;
        System.gc();
        sequence1 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(0, manager.getCallType());

        // should not release since it is held by sequence2 and sequence3
        sequence1 = null;
        sequence2 = null;
        System.gc();
        sequence1 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(0, manager.getCallType());

        // should release since not held by anyone
        sequence1 = null;
        sequence2 = null;
        sequence3 = null;
        System.gc();

        sequence1 = manager.getBaseSequence("0123456789", seq -> BasedSequenceImpl.create(seq, 0, seq.length()));
        assertEquals(21, manager.getCallType());
    }
}
