package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.Seg;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentOffsetTree;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

public class TrackedOffsetList implements List<TrackedOffset> {
    @NotNull
    public static TrackedOffsetList create(@NotNull BasedSequence baseSeq, @NotNull List<TrackedOffset> trackedOffsets) {
        return new TrackedOffsetList(baseSeq, trackedOffsets);
    }

    @NotNull
    public static TrackedOffsetList create(@NotNull BasedSequence baseSeq, @NotNull int[] offsets) {
        ArrayList<TrackedOffset> trackedOffsets = new ArrayList<>(offsets.length);
        for (int offset : offsets) {
            trackedOffsets.add(TrackedOffset.track(offset));
        }
        return new TrackedOffsetList(baseSeq, trackedOffsets);
    }

    final private @NotNull BasedSequence myBaseSeq;
    final private @NotNull List<TrackedOffset> myTrackedOffsets;
    final private @NotNull BasedOffsetTracker myBasedOffsetTracker;

    private TrackedOffsetList(@NotNull BasedSequence baseSeq, @NotNull List<TrackedOffset> trackedOffsets) {
        myBaseSeq = baseSeq;
        myTrackedOffsets = new ArrayList<>(trackedOffsets);
        myTrackedOffsets.sort(Comparator.comparing(TrackedOffset::getOffset));
        ArrayList<Seg> segments = new ArrayList<>(trackedOffsets.size());
        for (TrackedOffset trackedOffset : myTrackedOffsets) {
            segments.add(Seg.segOf(trackedOffset.getOffset(), trackedOffset.getOffset() + 1));
        }
        SegmentOffsetTree segmentOffsetTree = SegmentOffsetTree.build(segments, "");
        myBasedOffsetTracker = BasedOffsetTracker.create(baseSeq, segmentOffsetTree);
        assert myBasedOffsetTracker.size() == myTrackedOffsets.size();
    }

    @NotNull
    public BasedSequence getBaseSeq() {
        return myBaseSeq;
    }

    @NotNull
    public List<TrackedOffset> getTrackedOffsets() {
        return myTrackedOffsets;
    }

    @NotNull
    public BasedOffsetTracker getBasedOffsetTracker() {
        return myBasedOffsetTracker;
    }

    @NotNull
    List<TrackedOffset> findTrackedOffset(int startOffset, int endOffset) {
        OffsetInfo startInfo = myBasedOffsetTracker.getOffsetInfo(startOffset, startOffset == endOffset);
        OffsetInfo endInfo = myBasedOffsetTracker.getOffsetInfo(endOffset, true);
        int startSeg = Math.max(0, startInfo.pos);
        int endSeg = Math.max(startSeg, Math.min(myTrackedOffsets.size(), endInfo.pos + 1));

        if (startSeg >= endSeg) return Collections.emptyList();
        else return myTrackedOffsets.subList(startSeg, endSeg);
    }

// @formatter:off

    @Override public boolean add(TrackedOffset offset) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public TrackedOffset set(int index, TrackedOffset element) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public void add(int index, TrackedOffset element) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public TrackedOffset remove(int index) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public boolean addAll(@NotNull Collection<? extends TrackedOffset> c) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public boolean addAll(int index, @NotNull Collection<? extends TrackedOffset> c) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public boolean removeAll(@NotNull Collection<?> c) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public boolean retainAll(@NotNull Collection<?> c) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public void replaceAll(UnaryOperator<TrackedOffset> operator) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public void sort(Comparator<? super TrackedOffset> c) { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public void clear() { throw new IllegalStateException("Not supported. Immutable list."); }
    @Override public boolean remove(Object o) { throw new IllegalStateException("Not supported. Immutable list."); }

// @formatter:on

    @Override
    public int size() {return myTrackedOffsets.size();}

    @Override
    public boolean isEmpty() {return myTrackedOffsets.isEmpty();}

    @Override
    public boolean contains(Object o) {return myTrackedOffsets.contains(o);}

    @NotNull
    @Override
    public Iterator<TrackedOffset> iterator() {return myTrackedOffsets.iterator();}

    @NotNull
    @Override
    public Object[] toArray() {return myTrackedOffsets.toArray();}

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {return myTrackedOffsets.toArray(a);}

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {return myTrackedOffsets.containsAll(c);}

    @Override
    public boolean equals(Object o) {return myTrackedOffsets.equals(o);}

    @Override
    public int hashCode() {return myTrackedOffsets.hashCode();}

    @Override
    public TrackedOffset get(int index) {return myTrackedOffsets.get(index);}

    @Override
    public int indexOf(Object o) {return myTrackedOffsets.indexOf(o);}

    @Override
    public int lastIndexOf(Object o) {return myTrackedOffsets.lastIndexOf(o);}

    @NotNull
    @Override
    public ListIterator<TrackedOffset> listIterator() {return myTrackedOffsets.listIterator();}

    @NotNull
    @Override
    public ListIterator<TrackedOffset> listIterator(int index) {return myTrackedOffsets.listIterator(index);}

    @NotNull
    @Override
    public List<TrackedOffset> subList(int fromIndex, int toIndex) {return myTrackedOffsets.subList(fromIndex, toIndex);}

    @Override
    public Spliterator<TrackedOffset> spliterator() {return myTrackedOffsets.spliterator();}
}
