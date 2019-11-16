package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.sequence.IRichSequence.EOL_CHAR;

public class CharRecoveringSegmentOptimizer<S extends IRichSequence<S>> implements SegmentOptimizer<S> {
    private final PositionAnchor myAnchor;

    public CharRecoveringSegmentOptimizer(PositionAnchor anchor) {
        this.myAnchor = anchor;
    }

    private int prevEolPos;

    int prevMatchPos(@NotNull CharSequence base, CharSequence chars, int startIndex, int endIndex) {
        int length = chars.length();
        endIndex = Math.min(base.length(), endIndex);
        int iMax = Math.min(endIndex - startIndex, length);

        for (int i = 0; i < iMax; i++) {
            char c = base.charAt(i + startIndex);
            char c1 = chars.charAt(i);

            if (c == EOL_CHAR) prevEolPos = i + 1;
            if (c1 != c) return i;
        }
        return iMax;
    }

    int nextMatchPos(@NotNull CharSequence base, CharSequence chars, int startIndex, int fromIndex) {
        startIndex = Math.max(0, startIndex);
        fromIndex = Math.min(base.length(), fromIndex);

        int length = chars.length();
        int iMax = Math.min(fromIndex - startIndex, length);

        int baseOffset = fromIndex - iMax;
        int charsOffset = length - iMax;

        for (int i = iMax; i-- > 0; ) {
            char c = base.charAt(baseOffset + i);
            char c1 = chars.charAt(charsOffset + i);

            if (c1 != c) return i + charsOffset + 1;
        }
        return charsOffset;
    }

    @Override
    public void accept(@NotNull S chars, @NotNull SegmentPosition position) {
        String text = position.getString();
        int textLength = text.length();
        int charsLength = chars.length();

        if (textLength == 0 || charsLength == 0) return;  // already processed by previous optimizer

        Range originalPrev = position.getRangeOrNull(-1);
        Range originalNext = position.getRangeOrNull(1);

        if (originalNext == null && originalPrev == null) return;

        Range prevRange = originalPrev;
        Range nextRange = originalNext;

//        CharSequence prevText = prevRange == null ? null : chars.subSequence(prevRange);
//        CharSequence afterPrevText = prevRange == null ? null : chars.subSequence(prevRange.getEnd());
//        CharSequence nextText = nextRange == null ? null : chars.subSequence(nextRange);
//        CharSequence beforeNextText = nextRange == null ? null : chars.subSequence(0, nextRange.getStart());

        prevEolPos = -1;

        int prevPos = prevRange == null ? 0 : prevMatchPos(chars, text, prevRange.getEnd(), nextRange != null ? nextRange.getStart() : charsLength);
        int nextPos = nextRange == null ? textLength : nextMatchPos(chars, text, prevRange != null ? prevRange.getEnd() : 0, nextRange.getStart());

        if (prevPos == 0 && nextPos == textLength) return;

        // these pos are in text coordinates we find the breakdown for the text if there is overlap
        // if there is prevEol it goes to prev, and all after goes to next
        boolean hadEol = false;

        if (prevEolPos != -1 && prevEolPos < prevPos) {
            prevPos = prevEolPos;
            hadEol = true;

            // had eol, split is determined by EOL so if nextPos overlaps with prevPos, it can only take after EOL chars.
            if (nextPos < prevPos) {
                nextPos = prevPos;
            }
        }

        assert nextPos <= textLength;

        int matchedPrev = prevPos;
        int matchedNext = textLength - nextPos;
        int maxSpan = (nextRange != null ? nextRange.getStart() : charsLength) - (prevRange != null ? prevRange.getEnd() : 0);
        int excess = matchedPrev + matchedNext - maxSpan;

        if (excess > 0) {
            // have overlap, put back excess chars taken
            if (matchedNext == 0) {
                matchedPrev -= excess;
            } else if (matchedPrev == 0) {
                matchedNext -= excess;
            } else /*if (matchedNext != 0 && matchedPrev != 0)*/ {
                // the two positions may not overlap but the matches may exceed the span between ranges
                if (hadEol) {
                    // had EOL next gets to loose max chars since any taken from prev mean eol loss
                    int nextDelta = Math.min(matchedNext, excess);
                    matchedNext -= nextDelta;
                    matchedPrev -= excess - nextDelta;
                } else {
                    switch (myAnchor) {
                        case PREVIOUS:
                            // give it all to next
                            int prevDelta = Math.min(matchedPrev, excess);
                            matchedPrev -= prevDelta;
                            matchedNext -= excess - prevDelta;
                            break;

                        case NEXT:
                            // give it all to prev
                            int nextDelta = Math.min(matchedNext, excess);
                            matchedNext -= nextDelta;
                            matchedPrev -= excess - nextDelta;
                            break;

                        default:
                        case NONE:
                            // divide between the two with remainder to right??
                            int prevHalf = Math.min(matchedPrev, excess >> 1);
                            matchedPrev -= prevHalf;
                            matchedNext -= excess - prevHalf;
                            break;
                    }
                }
            }
        }

        // now we can compute match pos and ranges
        if (matchedPrev > 0) {
            prevRange = prevRange == null ? null : prevRange.endPlus(matchedPrev);
        }

        if (matchedNext > 0) {
            nextRange = nextRange.startMinus(matchedNext);
        }

        if (!(prevRange == null || nextRange == null || !prevRange.overlaps(nextRange))) {
            int tmp = 0;
        }

        // RELEASE: remove assert when tested and stable
        assert prevRange == null || nextRange == null || !prevRange.overlaps(nextRange);

        if (matchedPrev < 0 || matchedPrev > textLength) {
            int tmp = 0;
        }

        if (matchedNext < 0 || matchedNext > textLength) {
            int tmp = 0;
        }

        text = text.substring(matchedPrev, textLength - matchedNext);

        if (prevRange != null && nextRange != null && text.isEmpty() && prevRange.isAdjacentBefore(nextRange)) {
            // remove the string and next range
            prevRange = prevRange.expandToInclude(nextRange);
            position.set(-1, prevRange);
            position.remove(0, 2);
        } else {
            if (text.isEmpty()) position.remove();
            else position.set(text);

            if (prevRange != null) position.set(-1, prevRange);
            else if (originalPrev != null) position.remove(-1);

            if (nextRange != null) position.set(1, nextRange);
            else if (originalNext != null) position.remove(1);
        }
    }
}
