package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.misc.BitFieldSet;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;

class SpaceInsertingSequenceBuilder
    implements ISequenceBuilder<SpaceInsertingSequenceBuilder, BasedSequence> {

  static SpaceInsertingSequenceBuilder emptyBuilder(BasedSequence base, int options) {
    return new SpaceInsertingSequenceBuilder(
        SequenceBuilder.emptyBuilder(base, options),
        BitFieldSet.any(options, TextContainer.F_ADD_SPACES_BETWEEN_NODES));
  }

  private final SequenceBuilder out;
  private Node lastNode;
  private boolean needEol;
  private final boolean addSpacesBetweenNodes;
  private boolean addSpaces;

  private SpaceInsertingSequenceBuilder(SequenceBuilder out, boolean addSpacesBetweenNodes) {
    this.out = out;
    this.addSpacesBetweenNodes = addSpacesBetweenNodes;
  }

  @Override
  public char charAt(int index) {
    return out.charAt(index);
  }

  void setLastNode(Node lastNode) {
    if (lastNode instanceof Document) {
      return;
    }

    if (this.lastNode != null && this.lastNode.getEndOffset() < lastNode.getStartOffset()) {
      BasedSequence sequence =
          getBaseSequence().subSequence(this.lastNode.getEndOffset(), lastNode.getStartOffset());
      this.needEol =
          sequence.trim(CharPredicate.SPACE_TAB).length() > 0
              && sequence.trim(CharPredicate.WHITESPACE).isEmpty();
    }

    addSpaces = addSpacesBetweenNodes;
    this.lastNode = lastNode;
  }

  private boolean needSpace() {
    int partIndex = out.getSegmentBuilder().size();
    while (partIndex >= 0) {
      Object part = out.getSegmentBuilder().getPart(partIndex);
      if (part instanceof Range) {
        if (((Range) part).isNotNull()) {
          BasedSequence sequence =
              getBaseSequence().subSequence(((Range) part).getStart(), ((Range) part).getEnd());
          if (sequence.length() > 0) {
            return !CharPredicate.WHITESPACE.test(sequence.charAt(sequence.length() - 1));
          }
        }
      } else if (part instanceof CharSequence) {
        CharSequence sequence = (CharSequence) part;
        if (sequence.length() > 0) {
          return !CharPredicate.WHITESPACE.test(sequence.charAt(sequence.length() - 1));
        }
      } else {
        throw new IllegalStateException("Invalid part type " + part.getClass().getSimpleName());
      }

      partIndex--;
    }
    return false;
  }

  void appendEol() {
    append('\n');
    needEol = false;
  }

  boolean needEol() {
    if (needEol) {
      return true;
    }

    int partIndex = out.getSegmentBuilder().size();
    while (partIndex >= 0) {
      Object part = out.getSegmentBuilder().getPart(partIndex);
      if (part instanceof Range) {
        if (((Range) part).isNotNull()) {
          BasedSequence sequence =
              getBaseSequence().subSequence(((Range) part).getStart(), ((Range) part).getEnd());
          if (sequence.length() > 0) {
            return !CharPredicate.EOL.test(sequence.charAt(sequence.length() - 1));
          }
        }
      } else if (part instanceof CharSequence) {
        CharSequence sequence = (CharSequence) part;
        if (sequence.length() > 0) {
          return !CharPredicate.EOL.test(sequence.charAt(sequence.length() - 1));
        }
      } else {
        throw new IllegalStateException("Invalid part type " + part.getClass().getSimpleName());
      }

      partIndex--;
    }
    return false;
  }

  private BasedSequence getBaseSequence() {
    return out.getBaseSequence();
  }

  @Override
  public BasedSequence getSingleBasedSequence() {
    return out.getSingleBasedSequence();
  }

  @Override
  public SpaceInsertingSequenceBuilder getBuilder() {
    return new SpaceInsertingSequenceBuilder(out.getBuilder(), addSpacesBetweenNodes);
  }

  @Override
  public SpaceInsertingSequenceBuilder append(CharSequence chars, int startIndex, int endIndex) {
    if (addSpaces
        && chars != null
        && startIndex < endIndex
        && !CharPredicate.WHITESPACE.test(chars.charAt(startIndex))
        && needSpace()) {
      out.append(' ');
      addSpaces = false;
    }
    out.append(chars, startIndex, endIndex);
    return this;
  }

  @Override
  public SpaceInsertingSequenceBuilder append(char c) {
    if (addSpaces && !CharPredicate.WHITESPACE.test(c) && needSpace()) {
      out.append(' ');
      addSpaces = false;
    }
    out.append(c);
    return this;
  }

  @Override
  public SpaceInsertingSequenceBuilder append(char c, int count) {
    if (addSpaces && !CharPredicate.WHITESPACE.test(c) && needSpace()) {
      out.append(' ');
      addSpaces = false;
    }
    out.append(c, count);
    return this;
  }

  private SpaceInsertingSequenceBuilder append(int startOffset, int endOffset) {
    if (addSpaces
        && startOffset < endOffset
        && !CharPredicate.WHITESPACE.test(out.getBaseSequence().charAt(startOffset))
        && needSpace()) {
      out.append(' ');
      addSpaces = false;
    }
    out.append(startOffset, endOffset);
    return this;
  }

  @Override
  public BasedSequence toSequence() {
    return out.toSequence();
  }

  @Override
  public int length() {
    return out.length();
  }

  @Override
  public String toString() {
    return out.toString();
  }

  @Override
  public SpaceInsertingSequenceBuilder addAll(Iterable<? extends CharSequence> sequences) {
    return append(sequences);
  }

  @Override
  public SpaceInsertingSequenceBuilder append(Iterable<? extends CharSequence> sequences) {
    for (CharSequence sequence : sequences) {
      append(sequence);
    }
    return this;
  }

  @Override
  public SpaceInsertingSequenceBuilder add(CharSequence chars) {
    return append(chars);
  }

  @Override
  public SpaceInsertingSequenceBuilder append(CharSequence chars) {
    return chars == null ? this : append(chars, 0, chars.length());
  }

  @Override
  public SpaceInsertingSequenceBuilder append(CharSequence chars, int startIndex) {
    return chars == null ? this : append(chars, startIndex, chars.length());
  }
}
