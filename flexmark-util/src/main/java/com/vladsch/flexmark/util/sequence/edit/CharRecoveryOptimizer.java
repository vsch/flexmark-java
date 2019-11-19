package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;

public class CharRecoveryOptimizer<S extends IRichSequence<S>> implements SegmentOptimizer<S> {
    private final PositionAnchor myAnchor;

    public CharRecoveryOptimizer(PositionAnchor anchor) {
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

            if (c == SequenceUtils.EOL_CHAR) prevEolPos = i + 1;
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

            if (c1 != c) return charsOffset + i + 1;
        }
        return charsOffset;
    }

    @Override
    public void accept(@NotNull S chars, @NotNull SegmentPosition position) {
        @NotNull EditOp textOp = position.getStringOrNullOp();
        if (!textOp.isPlainText()) return;

        //noinspection ConstantConditions
        @NotNull String text = textOp.getText();

        //noinspection ConstantConditions
        int textLength = text.length();
        int charsLength = chars.length();

        if (textLength == 0 || charsLength == 0) return;  // already processed by previous optimizer

        final @NotNull EditOp originalPrev = position.getRangeOrNullOp(-1);
        final @NotNull EditOp originalNext = position.getRangeOrNullOp(1);

        if (originalNext.isNull() && originalPrev.isNull()) return;

        @NotNull Range prevRange = originalPrev;
        @NotNull Range nextRange = originalNext;

//        CharSequence prevText = prevRange == null ? null : chars.subSequence(prevRange);
//        CharSequence afterPrevText = prevRange == null ? null : chars.subSequence(prevRange.getEnd());
//        CharSequence nextText = nextRange == null ? null : chars.subSequence(nextRange);
//        CharSequence beforeNextText = nextRange == null ? null : chars.subSequence(0, nextRange.getStart());

        prevEolPos = -1;

        int prevPos = prevRange.isNull() ? 0 : prevMatchPos(chars, text, prevRange.getEnd(), nextRange.isNotNull() ? nextRange.getStart() : charsLength);
        int nextPos = nextRange.isNull() ? textLength : nextMatchPos(chars, text, prevRange.isNotNull() ? prevRange.getEnd() : 0, nextRange.getStart());

        // FIX: factor out EOL recovery to separate optimizer and recover EOL in middle of text
        if (prevPos == 0 && nextPos == textLength) {
            // check for EOL recovery
            if (prevRange.isNotNull() && !chars.subSequence(prevRange).endsWithEOL() && text.startsWith("\n")) {
                // see if there is an EOL between prevRange end and nextRange start with only spaces between them
                int eol = chars.endOfLine(prevRange.getEnd());
                if (eol < charsLength && chars.subSequence(prevRange.getEnd(), eol).isBlank()) {
                    // we have an EOL
                    Range eolRange = Range.ofLength(eol, 1);
                    text = text.substring(1);

                    // need to insert EOL range between prevRange and text
                    if (text.isEmpty()) {
                        position.set(EditOp.baseOp(eolRange));
                    } else {
                        position.set(EditOp.plainText(text));
                        position.add(EditOp.baseOp(eolRange));
                    }
                }
            }
            return;
        }

        // these pos are in text coordinates we find the breakdown for the text if there is overlap
        // if there is prevEol it goes to prev, and all after goes to next
//        boolean hadEol = false;

        if (prevEolPos != -1 && prevEolPos < prevPos) {
            prevPos = prevEolPos;
//            hadEol = true;

            // had eol, split is determined by EOL so if nextPos overlaps with prevPos, it can only take after EOL chars.
            if (nextPos < prevPos) {
                nextPos = prevPos;
            }
        }

        assert nextPos <= textLength;

        int matchedPrev = prevPos;
        int matchedNext = textLength - nextPos;
        int maxSpan = (nextRange.isNotNull() ? nextRange.getStart() : charsLength) - (prevRange.isNotNull() ? prevRange.getEnd() : 0);
        int excess = matchedPrev + matchedNext - maxSpan;

        if (excess > 0) {
            // have overlap, put back excess chars taken
            // this cannot happen with one range match only. It would mean we matched more chars than are available between ranges without both ranges matching these from each end
            // overlaps can only occur when string contains sequence that can be matched by both ranges. Otherwise match ends at least one char before other range begins because there is no match
            // otherwise the range would match from the other end.
            assert matchedNext > 0 && matchedPrev > 0;

            // the two positions may not overlap but the matches together may exceed the span between ranges
//            if (hadEol) {
//                // This too cannot happen. If there was an eol then it split the range so there is no overlap.
//                // had EOL next gets to loose max chars since any taken from prev mean eol loss
//                int nextDelta = Math.min(matchedNext, excess);
//                matchedNext -= nextDelta;
//                matchedPrev -= excess - nextDelta;
//            } else {
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
                case CURRENT:
                    // divide between the two with remainder to right??
                    int prevHalf = Math.min(matchedPrev, excess >> 1);
                    matchedPrev -= prevHalf;
                    matchedNext -= excess - prevHalf;
                    break;
            }
//            }
        }

        // now we can compute match pos and ranges
        if (matchedPrev > 0) {
            prevRange = prevRange.endPlus(matchedPrev);
        }

        if (matchedNext > 0) {
            nextRange = nextRange.startMinus(matchedNext);
        }

//        if (!(prevRange == null || nextRange == null || !prevRange.overlaps(nextRange))) {
//            int tmp = 0;
//        }
//
//        // RELEASE : remove assert when tested and stable
//        assert prevRange == null || nextRange == null || !prevRange.overlaps(nextRange);
//
//        if (matchedPrev < 0 || matchedPrev > textLength) {
//            int tmp = 0;
//        }
//
//        if (matchedNext < 0 || matchedNext > textLength) {
//            int tmp = 0;
//        }

        text = text.substring(matchedPrev, textLength - matchedNext);

        Range eolRange = Range.NULL;

        if (prevRange.isNotNull() && !chars.subSequence(prevRange).endsWithEOL() && text.startsWith("\n")) {
            // see if there is an EOL between prevRange end and nextRange start with only spaces between them
            int eol = chars.endOfLine(prevRange.getEnd());
            if (eol < charsLength && chars.subSequence(prevRange.getEnd(), eol).isBlank()) {
                // we have an EOL
                eolRange = Range.ofLength(eol, 1);
                text = text.substring(1);

                // need to insert EOL range between prevRange and text
            }
        }

        if (prevRange.isNotNull() && nextRange.isNotNull() && text.isEmpty() && prevRange.isAdjacentBefore(nextRange)) {
            // remove the string and next range
            prevRange = prevRange.expandToInclude(nextRange);
            position.set(-1, EditOp.baseOp(prevRange));
            position.remove(0, 2);
        } else {
            if (text.isEmpty()) {
                if (eolRange.isNotNull()) {
                    position.set(EditOp.baseOp(eolRange));
                    eolRange = Range.NULL;
                } else {
                    position.remove();
                }
            } else {
                position.set(EditOp.plainText(text));
            }

            if (prevRange.isNotNull()) {
                position.set(-1, EditOp.baseOp(prevRange));
                if (eolRange.isNotNull()) {
                    position.add(EditOp.baseOp(eolRange));
                }
//            } else if (originalPrev.isNotNull()) {
//                // NOTE: cannot happen, prevRange is never set to null if it exists, only next range
//                position.remove(-1);
            }

            if (nextRange.isNotNull()) position.set(1, EditOp.baseOp(nextRange));
            else if (originalNext.isNotNull()) position.remove(1);
        }
    }
}
