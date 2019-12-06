package com.vladsch.flexmark.experimental.util.sequence.edit;

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
    public void accept(@NotNull SegmentBuilderBase<?> builder, @NotNull S chars, @NotNull SegmentPosition position) {
        @NotNull Seg textOp = position.getSeg();
        if (!textOp.isText()) return;

        @NotNull String text = textOp.getText();

        final int textLength = text.length();
        final int charsLength = chars.length();

        if (textLength == 0 || charsLength == 0) return;  // already processed by previous optimizer

        final @NotNull Seg originalPrev = position.getSeg(-1);
        final @NotNull Seg originalNext = position.getSeg(1);

        if (originalNext.isNullRange() && originalPrev.isNullRange()) return;

        @NotNull Range prevRange = originalPrev.getRange();
        @NotNull Range nextRange = originalNext.getRange();

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
                    builder.removedText(text.substring(0, 1));
                    text = text.substring(1);

                    // need to insert EOL range between prevRange and text
                    if (text.isEmpty()) {
                        position.set(Seg.baseSeg(eolRange));
                    } else {
                        position.set(Seg.textSeg(eolRange.getEnd(), Math.max(eolRange.getEnd(), textOp.getStart()), text));
                        position.add(Seg.baseSeg(eolRange));
                    }
                    builder.changeTextLength(-1);
                    builder.updateOffsets(builder.lastSeg());
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

        // NOTE: need to adjust char stats
        builder.removedText(text.substring(0, matchedPrev));
        builder.removedText(text.substring(textLength - matchedNext));

        text = text.substring(matchedPrev, textLength - matchedNext);

        Range eolRange = Range.NULL;
        int eolOffset = -1;

        if (prevRange.isNotNull() && !chars.subSequence(prevRange).endsWithEOL() && text.startsWith("\n")) {
            // see if there is an EOL between prevRange end and nextRange start with only spaces between them
            int eol = chars.endOfLine(prevRange.getEnd());
            if (eol < charsLength && chars.subSequence(prevRange.getEnd(), eol).isBlank()) {
                // we have an EOL
                eolRange = Range.ofLength(eol, 1);
                eolOffset = eolRange.getEnd();
                builder.removedText(text.substring(0, 1));
                text = text.substring(1);
                // need to insert EOL range between prevRange and text
            }
        }

        if (prevRange.isNotNull() && nextRange.isNotNull() && text.isEmpty() && prevRange.isAdjacentBefore(nextRange)) {
            // remove the string and next range
            prevRange = prevRange.expandToInclude(nextRange);
            position.set(-1, Seg.baseSeg(prevRange));
            position.remove(0, 2);
        } else {
            if (text.isEmpty()) {
                if (eolRange.isNotNull()) {
                    position.set(Seg.baseSeg(eolRange));
                    eolRange = Range.NULL;
                } else {
                    position.remove();
                }
            } else {
                int start = Math.max(eolOffset, prevRange.isNotNull() ? prevRange.getEnd() : nextRange.getStart());
                int end = Math.max(start, nextRange.isNotNull() ? nextRange.getStart() : prevRange.getEnd());

                position.set(Seg.textSeg(start, end, text));
            }

            if (prevRange.isNotNull()) {
                position.set(-1, Seg.baseSeg(prevRange));
                if (eolRange.isNotNull()) {
                    position.add(Seg.baseSeg(eolRange));
                }
//            } else if (originalPrev.isNotNull()) {
//                // NOTE: cannot happen, prevRange is never set to null if it exists, only next range
//                position.remove(-1);
            }

            if (nextRange.isNotNull()) {
                Seg seg = Seg.baseSeg(nextRange);
                position.set(1, seg);
                builder.updateOffsets(seg);
            } else if (!originalNext.isNullRange()) position.remove(1);
        }

        builder.changeTextLength(text.length() - textLength);
        builder.updateOffsets(builder.lastSeg());
    }
}
