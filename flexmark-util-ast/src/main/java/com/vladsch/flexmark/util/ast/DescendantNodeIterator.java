package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import java.util.Stack;
import java.util.function.Consumer;

class DescendantNodeIterator implements ReversiblePeekingIterator<Node> {
  private final boolean isReversed;
  private ReversiblePeekingIterator<Node> iterator;
  private Stack<ReversiblePeekingIterator<Node>> iteratorStack;
  private Node result;

  /**
   * iterate nodes, with descendants, depth first until all are done
   *
   * @param iterator iterator to use for iterating nodes and their descendants
   */
  DescendantNodeIterator(ReversiblePeekingIterator<Node> iterator) {
    this.isReversed = iterator.isReversed();
    this.iterator =
        iterator instanceof DescendantNodeIterator
            ? ((DescendantNodeIterator) iterator).iterator
            : iterator;
    this.iteratorStack = null;
    this.result = null;
  }

  @Override
  public boolean isReversed() {
    return isReversed;
  }

  @Override
  public boolean hasNext() {
    return iterator.hasNext();
  }

  @Override
  public Node next() {
    result = iterator.next();

    if (result.getFirstChild() != null) {
      // push the current iterator on to the stack and make the node's children the iterator
      if (iterator.hasNext()) {
        if (iteratorStack == null) iteratorStack = new Stack<>();
        iteratorStack.push(iterator);
      }

      iterator = isReversed ? result.getReversedChildIterator() : result.getChildIterator();
    } else {
      // see if need to pop an iterator
      if (iteratorStack != null && !iteratorStack.isEmpty() && !iterator.hasNext()) {
        // pop a new iterator off the stack
        iterator = iteratorStack.pop();
      }
    }

    return result;
  }

  @Override
  public Node peek() {
    return iterator.peek();
  }

  @Override
  public void remove() {
    if (result == null) {
      throw new IllegalStateException("Either next() was not called yet or the node was removed");
    }
    result.unlink();
    result = null;
  }

  @Override
  public void forEachRemaining(Consumer<? super Node> consumer) {
    while (hasNext()) {
      consumer.accept(next());
    }
  }
}
