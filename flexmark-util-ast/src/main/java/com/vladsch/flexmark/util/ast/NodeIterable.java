package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversibleIterator;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import java.util.function.Consumer;

class NodeIterable implements ReversiblePeekingIterable<Node> {
  private final Node firstNode;
  private final Node lastNode;
  private final boolean reversed;

  NodeIterable(Node firstNode, Node lastNode, boolean reversed) {
    this.firstNode = firstNode;
    this.lastNode = lastNode;
    this.reversed = reversed;
  }

  @Override
  public ReversiblePeekingIterator<Node> iterator() {
    return new NodeIterator(firstNode, lastNode, reversed);
  }

  @Override
  public void forEach(Consumer<? super Node> consumer) {
    ReversibleIterator<Node> iterator = iterator();
    while (iterator.hasNext()) {
      consumer.accept(iterator.next());
    }
  }

  @Override
  public ReversiblePeekingIterable<Node> reversed() {
    return new NodeIterable(firstNode, lastNode, !reversed);
  }

  @Override
  public boolean isReversed() {
    return reversed;
  }

  @Override
  public ReversiblePeekingIterator<Node> reversedIterator() {
    return new NodeIterator(firstNode, lastNode, !reversed);
  }

  static final ReversiblePeekingIterable<Node> EMPTY =
      new ReversiblePeekingIterable<>() {

        @Override
        public ReversiblePeekingIterator<Node> iterator() {
          return NodeIterator.EMPTY;
        }

        @Override
        public ReversiblePeekingIterable<Node> reversed() {
          return this;
        }

        @Override
        public void forEach(Consumer<? super Node> consumer) {}

        @Override
        public boolean isReversed() {
          return false;
        }

        @Override
        public ReversiblePeekingIterator<Node> reversedIterator() {
          return NodeIterator.EMPTY;
        }
      };
}
