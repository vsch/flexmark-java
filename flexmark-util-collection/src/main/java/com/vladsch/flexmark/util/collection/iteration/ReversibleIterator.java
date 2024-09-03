package com.vladsch.flexmark.util.collection.iteration;

import java.util.Iterator;

public interface ReversibleIterator<E> extends Iterator<E> {
  boolean isReversed();
}
