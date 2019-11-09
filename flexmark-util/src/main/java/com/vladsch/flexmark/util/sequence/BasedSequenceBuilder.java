package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A Builder for Segmented BasedSequences
 */
public final class BasedSequenceBuilder implements SequenceBuilder<BasedSequenceBuilder, BasedSequence> {
    private final ArrayList<BasedSequence> segments;
    private final BasedSequence base;
    private int lastEndOffset = -1;

    public BasedSequenceBuilder(BasedSequence base) {
        this.segments = new ArrayList<>();
        this.base = base.getBaseSequence();
    }

    public BasedSequenceBuilder(BasedSequence base, int initialCapacity) {
        this.segments = new ArrayList<>(initialCapacity);
        this.base = base;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder subContext() {
        return new BasedSequenceBuilder(base);
    }

    @NotNull
    @Override
    public BasedSequenceBuilder append(@NotNull BasedSequenceBuilder other) {
        if (base != other.base) {
            throw new IllegalArgumentException("append BasedSequenceBuilder called with other having different base: " + base.hashCode() + " got: " + other.base.hashCode());
        }

        if (!other.isEmpty()) {
            BasedSequence s = other.getLastSegment();
            if (lastEndOffset >= 0) {
                if (s.getStartOffset() < lastEndOffset) {
                    throw new IllegalArgumentException("append BasedSequenceBuilder called with last segment of other having startOffset < lastEndOffset, startOffset: " + s.getStartOffset() + " lastEndOffset: " + lastEndOffset);
                }
            }

            lastEndOffset = s.getEndOffset();
            segments.addAll(other.segments);
        }
        return this;
    }

    @NotNull
    @Override
    public BasedSequenceBuilder append(@NotNull CharSequence s) {
        if (s instanceof BasedSequence) return append((BasedSequence) s);
        else if (s.length() > 0) return append(s.toString());
        else return this;
    }

    public BasedSequenceBuilder append(BasedSequence s) {
        if (s.isNotNull()) {
            if (s.getBaseSequence() != base) {
                throw new IllegalArgumentException("append called with segment having different base: " + base.hashCode() + " got: " + s.getBaseSequence().hashCode());
            }

            if (lastEndOffset >= 0) {
                if (s.getStartOffset() < lastEndOffset) {
                    throw new IllegalArgumentException("append called with segment having startOffset < lastEndOffset, startOffset: " + s.getStartOffset() + " lastEndOffset: " + lastEndOffset);
                }
            }

            lastEndOffset = s.getEndOffset();
            segments.add(s);
        }
        return this;
    }

    public BasedSequenceBuilder append(String str) {
        if (str.isEmpty()) return this;
        else {
            BasedSequence useBase;
            if (segments.isEmpty()) {
                useBase = base.subSequence(0, 0);
            } else {
                useBase = segments.get(segments.size() - 1);
                useBase = useBase.subSequence(useBase.length(), useBase.length());
            }
            return append(PrefixedSubSequence.prefixOf(str, useBase));
        }
    }

    @NotNull
    @Override
    public BasedSequence toSequence() {
        return SegmentedSequence.of(segments);
    }

    @NotNull
    public BasedSequence[] toSegments() {
        return segments.toArray(BasedSequence.EMPTY_SEGMENTS);
    }

    @NotNull
    public List<BasedSequence> getSegments() {
        return segments;
    }

    @NotNull
    public BasedSequence getLastSegment() {
        return segments.size() == 0 ? BasedSequence.NULL : segments.get(segments.size() - 1);
    }

    public void setLastSegment(@NotNull BasedSequence s) {
        if (segments.size() == 0) {
            throw new IllegalStateException("setLastSegment called with no segments");
        }

        if (s.getBaseSequence() != base) {
            throw new IllegalArgumentException("setLastSegment called with segment having different base: " + base.hashCode() + " got: " + s.getBaseSequence().hashCode());
        }

        if (segments.size() > 1) {
            BasedSequence lastSegment = segments.get(segments.size() - 2);
            if (lastSegment.getEndOffset() < s.getStartOffset()) {
                throw new IllegalArgumentException("setLastSegment called with segment starting before previous endOffset: " + lastSegment.getEndOffset() + " segment startOffset: " + s.getStartOffset());
            }
        }

        segments.set(segments.size() - 1, s);
    }

    @Override
    public int length() {
        int total = 0;
        BasedSequence last = null;
        for (BasedSequence s : segments) {
            if (s.isEmpty()) continue;

            if (last != null && last.getEndOffset() < s.getStartOffset() && (BasedSequence.WHITESPACE_CHARS.indexOf(last.charAt(last.length() - 1)) == -1) && BasedSequence.WHITESPACE_CHARS.indexOf(s.charAt(0)) == -1) {
                total++;
            }

            total++;
            last = s;
        }
        return total;
    }

    @Override
    public boolean isEmpty() {
        for (BasedSequence s : segments) {
            if (!s.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        int total = 0;
        StringBuilder sb = new StringBuilder();
        BasedSequence last = null;
        for (BasedSequence s : segments) {
            if (s.isEmpty()) continue;

            if (last != null && last.getEndOffset() < s.getStartOffset()
                && (BasedSequence.WHITESPACE_CHARS.indexOf(last.charAt(last.length() - 1)) == -1)
                && BasedSequence.WHITESPACE_CHARS.indexOf(s.charAt(0)) == -1
                && s.baseSubSequence(last.getEndOffset(), s.getStartOffset()).endsWith(" ")
            ) {
                sb.append(' ');
            }

            s.appendTo(sb);
            last = s;
        }

        return sb.toString();
    }
}
