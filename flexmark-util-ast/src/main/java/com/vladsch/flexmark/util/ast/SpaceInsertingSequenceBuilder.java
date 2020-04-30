package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.misc.BitFieldSet;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.SegmentOptimizer;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpaceInsertingSequenceBuilder implements ISequenceBuilder<SpaceInsertingSequenceBuilder, BasedSequence> {
    @NotNull
    public static SpaceInsertingSequenceBuilder emptyBuilder(@NotNull BasedSequence base) {
        return new SpaceInsertingSequenceBuilder(SequenceBuilder.emptyBuilder(base), false);
    }

    @NotNull
    public static SpaceInsertingSequenceBuilder emptyBuilder(@NotNull BasedSequence base, @NotNull SegmentOptimizer optimizer) {
        return new SpaceInsertingSequenceBuilder(SequenceBuilder.emptyBuilder(base, optimizer), false);
    }

    @NotNull
    public static SpaceInsertingSequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options) {
        return new SpaceInsertingSequenceBuilder(SequenceBuilder.emptyBuilder(base, options), BitFieldSet.any(options, TextContainer.F_ADD_SPACES_BETWEEN_NODES));
    }

    @NotNull
    public static SpaceInsertingSequenceBuilder emptyBuilder(@NotNull BasedSequence base, int options, @NotNull SegmentOptimizer optimizer) {
        return new SpaceInsertingSequenceBuilder(SequenceBuilder.emptyBuilder(base, options, optimizer), BitFieldSet.any(options, TextContainer.F_ADD_SPACES_BETWEEN_NODES));
    }

    @NotNull
    public static SpaceInsertingSequenceBuilder emptyBuilder(@NotNull SequenceBuilder builder) {
        return new SpaceInsertingSequenceBuilder(builder, false);
    }

    final SequenceBuilder out;
    Node lastNode;
    boolean needEol;
    final boolean addSpacesBetweenNodes;
    boolean addSpaces;

    private SpaceInsertingSequenceBuilder(SequenceBuilder out, boolean addSpacesBetweenNodes) {
        this.out = out;
        this.addSpacesBetweenNodes = addSpacesBetweenNodes;
    }

    public SequenceBuilder getOut() {
        return out;
    }

    @Override
    public char charAt(int index) {
        return out.charAt(index);
    }

    public boolean isNeedEol() {
        return needEol;
    }

    public void setNeedEol(boolean needEol) {
        this.needEol = needEol;
    }

    public Node getLastNode() {
        return lastNode;
    }

    public void setLastNode(Node lastNode) {
        if (lastNode instanceof Document) return;

        if (this.lastNode != null && this.lastNode.getEndOffset() < lastNode.getStartOffset()) {
            BasedSequence sequence = getBaseSequence().subSequence(this.lastNode.getEndOffset(), lastNode.getStartOffset());
            this.needEol = sequence.trim(CharPredicate.SPACE_TAB).length() > 0 && sequence.trim(CharPredicate.WHITESPACE).isEmpty();
        }

        addSpaces = addSpacesBetweenNodes;
        this.lastNode = lastNode;
    }

    public boolean needSpace() {
        int partIndex = out.getSegmentBuilder().size();
        while (partIndex >= 0) {
            Object part = out.getSegmentBuilder().getPart(partIndex);
            if (part instanceof Range) {
                if (((Range) part).isNotNull()) {
                    BasedSequence sequence = getBaseSequence().subSequence(((Range) part).getStart(), ((Range) part).getEnd());
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

    public void appendEol() {
        append('\n');
        needEol = false;
    }

    public boolean needEol() {
        if (needEol) return true;

        int partIndex = out.getSegmentBuilder().size();
        while (partIndex >= 0) {
            Object part = out.getSegmentBuilder().getPart(partIndex);
            if (part instanceof Range) {
                if (((Range) part).isNotNull()) {
                    BasedSequence sequence = getBaseSequence().subSequence(((Range) part).getStart(), ((Range) part).getEnd());
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

    @NotNull
    public BasedSequence getBaseSequence() {return out.getBaseSequence();}

    @NotNull
    public BasedSegmentBuilder getSegmentBuilder() {return out.getSegmentBuilder();}

    @Override
    @Nullable
    public BasedSequence getSingleBasedSequence() {return out.getSingleBasedSequence();}

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder getBuilder() {return new SpaceInsertingSequenceBuilder(out.getBuilder(), addSpacesBetweenNodes);}

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder append(@Nullable CharSequence chars, int startIndex, int endIndex) {
        if (addSpaces && chars != null && startIndex < endIndex && !CharPredicate.WHITESPACE.test(chars.charAt(startIndex)) && needSpace()) {
            out.append(' ');
            addSpaces = false;
        }
        out.append(chars, startIndex, endIndex);
        return this;
    }

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder append(char c) {
        if (addSpaces && !CharPredicate.WHITESPACE.test(c) && needSpace()) {
            out.append(' ');
            addSpaces = false;
        }
        out.append(c);
        return this;
    }

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder append(char c, int count) {
        if (addSpaces && !CharPredicate.WHITESPACE.test(c) && needSpace()) {
            out.append(' ');
            addSpaces = false;
        }
        out.append(c, count);
        return this;
    }

    @NotNull
    public SpaceInsertingSequenceBuilder append(int startOffset, int endOffset) {
        if (addSpaces && startOffset < endOffset && !CharPredicate.WHITESPACE.test(out.getBaseSequence().charAt(startOffset)) && needSpace()) {
            out.append(' ');
            addSpaces = false;
        }
        out.append(startOffset, endOffset);
        return this;
    }

    @NotNull
    public SpaceInsertingSequenceBuilder append(@NotNull Range chars) {return append(chars.getStart(), chars.getEnd());}

    @NotNull
    public SpaceInsertingSequenceBuilder addRange(@NotNull Range range) {return append(range.getStart(), range.getEnd());}

    @NotNull
    public SpaceInsertingSequenceBuilder addByOffsets(int startOffset, int endOffset) {return append(startOffset, endOffset);}

    @NotNull
    public SpaceInsertingSequenceBuilder addByLength(int startOffset, int textLength) {return append(startOffset, startOffset + textLength);}

    @Override
    @NotNull
    public BasedSequence toSequence() {return out.toSequence();}

    @Override
    public int length() {return out.length();}

    @NotNull
    public String toStringWithRanges() {return out.toStringWithRanges();}

    @Override
    public String toString() {return out.toString();}

    public String toStringNoAddedSpaces() {return out.toStringNoAddedSpaces();}

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder addAll(Iterable<? extends CharSequence> sequences) {
        return append(sequences);
    }

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder append(Iterable<? extends CharSequence> sequences) {
        for (CharSequence sequence : sequences) {
            append(sequence);
        }
        return this;
    }

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder add(@Nullable CharSequence chars) {return append(chars);}

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder append(@Nullable CharSequence chars) { return chars == null ? this : append(chars, 0, chars.length());}

    @Override
    @NotNull
    public SpaceInsertingSequenceBuilder append(@Nullable CharSequence chars, int startIndex) {return chars == null ? this : append(chars, startIndex, chars.length());}
}
