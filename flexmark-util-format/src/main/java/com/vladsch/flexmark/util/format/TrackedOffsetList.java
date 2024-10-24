package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.Seg;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentOffsetTree;
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
  public static final TrackedOffsetList EMPTY_LIST =
      new TrackedOffsetList(BasedSequence.NULL, Collections.emptyList());

  public static TrackedOffsetList create(
      BasedSequence baseSeq, List<TrackedOffset> trackedOffsets) {
    return trackedOffsets instanceof TrackedOffsetList
        ? (TrackedOffsetList) trackedOffsets
        : new TrackedOffsetList(baseSeq, trackedOffsets);
  }

  static TrackedOffsetList create(BasedSequence baseSeq, int[] offsets) {
    List<TrackedOffset> trackedOffsets = new ArrayList<>(offsets.length);
    for (int offset : offsets) {
      trackedOffsets.add(TrackedOffset.track(offset));
    }
    return new TrackedOffsetList(baseSeq, trackedOffsets);
  }

  private final BasedSequence myBaseSeq;
  private final List<TrackedOffset> myTrackedOffsets;
  private final BasedOffsetTracker myBasedOffsetTracker;

  private TrackedOffsetList(BasedSequence baseSeq, List<TrackedOffset> trackedOffsets) {
    myBaseSeq = baseSeq;
    myTrackedOffsets = new ArrayList<>(trackedOffsets);
    myTrackedOffsets.sort(Comparator.comparing(TrackedOffset::getOffset));
    List<Seg> segments = new ArrayList<>(trackedOffsets.size());
    for (TrackedOffset trackedOffset : myTrackedOffsets) {
      segments.add(Seg.segOf(trackedOffset.getOffset(), trackedOffset.getOffset() + 1));
    }
    SegmentOffsetTree segmentOffsetTree = SegmentOffsetTree.build(segments, "");
    myBasedOffsetTracker = BasedOffsetTracker.create(baseSeq, segmentOffsetTree);
  }

  public TrackedOffsetList getUnresolvedOffsets() {
    List<TrackedOffset> unresolved = new ArrayList<>();

    for (TrackedOffset trackedOffset : myTrackedOffsets) {
      if (!trackedOffset.isResolved()) unresolved.add(trackedOffset);
    }
    return unresolved.isEmpty() ? EMPTY_LIST : new TrackedOffsetList(myBaseSeq, unresolved);
  }

  public BasedSequence getBaseSeq() {
    return myBaseSeq;
  }

  public List<TrackedOffset> getTrackedOffsets() {
    return myTrackedOffsets;
  }

  public BasedOffsetTracker getBasedOffsetTracker() {
    return myBasedOffsetTracker;
  }

  public TrackedOffsetList getTrackedOffsets(int startOffset, int endOffset) {
    OffsetInfo startInfo =
        myBasedOffsetTracker.getOffsetInfo(startOffset, startOffset == endOffset);
    OffsetInfo endInfo = myBasedOffsetTracker.getOffsetInfo(endOffset, true);
    int startSeg = startInfo.pos;
    int endSeg = endInfo.pos;

    if (startSeg < 0 && endSeg >= 0) {
      startSeg = 0;
      endSeg++;
    } else if (startSeg >= 0 && endSeg >= 0) {
      endSeg++;
    } else {
      return EMPTY_LIST;
    }

    endSeg = Math.min(myBasedOffsetTracker.size(), endSeg);

    if (startSeg >= endSeg) {
      return EMPTY_LIST;
    }

    if (myTrackedOffsets.get(startSeg).getOffset() < startOffset) startSeg++;
    if (myTrackedOffsets.get(endSeg - 1).getOffset() > endOffset) endSeg--;

    if (startSeg >= endSeg) {
      return EMPTY_LIST;
    }

    return new TrackedOffsetList(myBaseSeq, myTrackedOffsets.subList(startSeg, endSeg));
  }

  @Override
  public boolean add(TrackedOffset offset) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public TrackedOffset set(int index, TrackedOffset element) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public void add(int index, TrackedOffset element) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public TrackedOffset remove(int index) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public boolean addAll(Collection<? extends TrackedOffset> c) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public boolean addAll(int index, Collection<? extends TrackedOffset> c) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public void replaceAll(UnaryOperator<TrackedOffset> operator) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public void sort(Comparator<? super TrackedOffset> c) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public void clear() {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public boolean remove(Object object) {
    throw new IllegalStateException("Not supported. Immutable list.");
  }

  @Override
  public int size() {
    return myTrackedOffsets.size();
  }

  @Override
  public boolean isEmpty() {
    return myTrackedOffsets.isEmpty();
  }

  @Override
  public boolean contains(Object object) {
    return myTrackedOffsets.contains(object);
  }

  @Override
  public Iterator<TrackedOffset> iterator() {
    return myTrackedOffsets.iterator();
  }

  @Override
  public Object[] toArray() {
    return myTrackedOffsets.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return myTrackedOffsets.toArray(a);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return myTrackedOffsets.containsAll(c);
  }

  @Override
  public boolean equals(Object object) {
    return myTrackedOffsets.equals(object);
  }

  @Override
  public int hashCode() {
    return myTrackedOffsets.hashCode();
  }

  @Override
  public TrackedOffset get(int index) {
    return myTrackedOffsets.get(index);
  }

  @Override
  public int indexOf(Object object) {
    return myTrackedOffsets.indexOf(object);
  }

  @Override
  public int lastIndexOf(Object object) {
    return myTrackedOffsets.lastIndexOf(object);
  }

  @Override
  public ListIterator<TrackedOffset> listIterator() {
    return myTrackedOffsets.listIterator();
  }

  @Override
  public ListIterator<TrackedOffset> listIterator(int index) {
    return myTrackedOffsets.listIterator(index);
  }

  @Override
  public List<TrackedOffset> subList(int fromIndex, int toIndex) {
    return myTrackedOffsets.subList(fromIndex, toIndex);
  }

  @Override
  public Spliterator<TrackedOffset> spliterator() {
    return myTrackedOffsets.spliterator();
  }
}
