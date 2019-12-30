package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

public class TrackedOffsetListTest {

    int[] offsets(List<TrackedOffset> trackedOffsets) {
        int[] result = new int[trackedOffsets.size()];
        int i = 0;
        for (TrackedOffset trackedOffset : trackedOffsets) {
            result[i++] = trackedOffset.getOffset();
        }
        return result;
    }

    @Test
    public void test_findTrackedOffsetEmpty() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { });

        int[] expected = { };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetEmpty1() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0 });

        int[] expected = { };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(2, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetBefore1() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0 });

        int[] expected = { };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(1, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetEmpty2() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 8 });

        int[] expected = { };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 7));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetAfter1() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 8 });

        int[] expected = { 8 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 8));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetBefore() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0 });

        int[] expected = { 0 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetFirst() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 2 });

        int[] expected = { 2 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetBeforePrefix() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0, 2, 3, 1 });

        int[] expected = { };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 2));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetPrefix() {
        BasedSequence input = BasedSequence.of("* 234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0, 2, 3, 1 });

        int[] expected = { 0, 1 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 2));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetDoublePrefix1() {
        BasedSequence input = BasedSequence.of("* [ ] 234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0, 2, 3, 1, 4, 5, 6 });

        int[] expected = { 0, 1 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 2));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetDoublePrefix2() {
        BasedSequence input = BasedSequence.of("* [ ] 234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 0, 2, 3, 1, 4, 5, 6 });

        int[] expected = { 2, 3, 4, 5 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(2, 6));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetLast() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 8 });

        int[] expected = { 8 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }

    @Test
    public void test_findTrackedOffsetAfter() {
        BasedSequence input = BasedSequence.of("  234567  ");
        TrackedOffsetList trackedOffsets = TrackedOffsetList.create(input.trim(), new int[] { 9 });

        int[] expected = { 9 };
        int[] actual = offsets(trackedOffsets.getTrackedOffsets(0, 10));
        assertArrayEquals("\nexpected: " + Arrays.toString(expected) + "\nactual: " + Arrays.toString(actual) + "\n", expected, actual);
    }
}
