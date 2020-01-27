package com.vladsch.flexmark.util.sequence.builder;

import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.PositionAnchor;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.sequence.SequenceUtils.*;

public class CharRecoveryOptimizer implements SegmentOptimizer {
    final private PositionAnchor anchor;

    public CharRecoveryOptimizer(PositionAnchor anchor) {
        this.anchor = anchor;
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

    public Object[] apply(@NotNull CharSequence chars, Object[] parts) {
        // optimizer already applied
        if (parts.length != 3 || !(parts[0] instanceof Range && parts[1] instanceof CharSequence && parts[2] instanceof Range)) return parts;

        final @NotNull Range originalPrev = (Range) parts[0];
        @NotNull CharSequence originalText = (CharSequence) parts[1];
        final @NotNull Range originalNext = (Range) parts[2];

        // optimizer already applied
        final int textLength = originalText.length();
        if (originalPrev.isNull() && originalNext.isNull() || textLength == 0) return parts;

        final int charsLength = chars.length();

        @NotNull Range prevRange = originalPrev;
        @NotNull CharSequence text = originalText;
        @NotNull Range nextRange = originalNext;

        prevEolPos = -1;

        int prevPos = prevRange.isNull() ? 0 : prevMatchPos(chars, text, prevRange.getEnd(), nextRange.isNotNull() ? nextRange.getStart() : charsLength);
        int nextPos = nextRange.isNull() ? textLength : nextMatchPos(chars, text, prevRange.isNotNull() ? prevRange.getEnd() : 0, nextRange.getStart());

        // FIX: factor out EOL recovery to separate optimizer and recover EOL in middle of text
        if (prevPos == 0 && nextPos == textLength) {
            // check for EOL recovery
            if (prevRange.isNotNull() && !endsWithEOL(chars.subSequence(prevRange.getStart(), prevRange.getEnd())) && startsWith(text, "\n")) {
                // see if there is an EOL between prevRange end and nextRange start with only spaces between them
                int eol = endOfLine(chars, prevRange.getEnd());
                if (eol < charsLength && isBlank(chars.subSequence(prevRange.getEnd(), eol))) {
                    // we have an EOL
                    Range eolRange = Range.ofLength(eol, 1);
                    text = text.subSequence(1, textLength);

                    if (nextRange.isEmpty() && nextRange.getStart() < eolRange.getEnd()) {
                        // serves no purpose and causes
                        nextRange = Range.NULL;
                    }

                    // need to insert EOL range between prevRange and text
                    if (text.length() == 0) {
                        // no text left, can replace that with EOL range
                        parts[1] = eolRange;
                        parts[2] = nextRange;
                    } else {
                        // EOL range between prevRange and text and replace text with left-over text
                        if (prevRange.isNull()) {
                            // replace prev range
                            parts[0] = eolRange;
                            parts[1] = text;
                            parts[2] = nextRange;
                        } else if (nextRange.isNull()) {
                            // replace next range
                            parts[1] = eolRange;
                            parts[2] = text;
                        } else {
                            // create new parts array and insert eol range
                            parts = new Object[parts.length + 1];
                            parts[0] = prevRange;
                            parts[1] = eolRange;
                            parts[2] = text;
                            parts[3] = nextRange;
                        }
                    }
                }
            }
            return parts;
        }

        // these pos are in text coordinates we find the breakdown for the text if there is overlap
        // if there is prevEol it goes to prev, and all after goes to next
        if (prevEolPos != -1 && prevEolPos < prevPos) {
            prevPos = prevEolPos;

            // had eol, split is determined by EOL so if nextPos overlaps with prevPos, it can only take after EOL chars.
            if (nextPos < prevPos) {
                nextPos = prevPos;
            }
        }

        assert nextPos <= textLength : "prevRange: " + originalPrev + ", '" + Utils.escapeJavaString(text) + "', nextRange: " + originalNext;

        int matchedPrev = prevPos;
        int matchedNext = textLength - nextPos;
        int maxSpan = Math.min(textLength, (nextRange.isNotNull() ? nextRange.getStart() : charsLength) - (prevRange.isNotNull() ? prevRange.getEnd() : 0));
        int excess = matchedPrev + matchedNext - maxSpan;

        if (excess > 0) {
            // have overlap, put back excess chars taken
            // this cannot happen with one range match only. It would mean we matched more chars than are available between ranges without both ranges matching these from each end
            // overlaps can only occur when string contains sequence that can be matched by both ranges. Otherwise match ends at least one char before other range begins because there is no match
            // otherwise the range would match from the other end.
            assert matchedNext > 0 && matchedPrev > 0 : "prevRange: " + originalPrev + ", '" + Utils.escapeJavaString(text) + "', nextRange: " + originalNext;

            // the two positions may not overlap but the matches together may exceed the span between ranges
            switch (anchor) {
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
        }

        // now we can compute match pos and ranges
        if (matchedPrev > 0) {
            prevRange = prevRange.endPlus(matchedPrev);
        }

        if (matchedNext > 0) {
            nextRange = nextRange.startMinus(matchedNext);
        }

        // RELEASE : remove asserts when tested and stable
//        if (prevRange.overlaps(nextRange)) {
//            assert false;
//        }
//        if (matchedPrev < 0 || matchedPrev > textLength) {
//            assert false;
//        }
//        if (matchedNext < 0 || textLength - matchedNext < 0) {
//            assert false;
//        }
//        if (matchedPrev > textLength - matchedNext) {
//            assert false;
//        }

        text = text.subSequence(matchedPrev, textLength - matchedNext);

        Range eolRange = Range.NULL;

        if (prevRange.isNotNull() && !endsWithEOL(chars.subSequence(prevRange.getStart(), prevRange.getEnd())) && startsWith(text, "\n")) {
            // see if there is an EOL between prevRange end and nextRange start with only spaces between them
            int eol = endOfLine(chars, prevRange.getEnd());
            if (eol < charsLength && (nextRange.isNull() || eol < nextRange.getStart()) && isBlank(chars.subSequence(prevRange.getEnd(), eol))) {
                // we have an EOL
                eolRange = Range.ofLength(eol, 1);
                text = text.subSequence(1, text.length());
            }
        }

        if (prevRange.isNotNull() && nextRange.isNotNull() && text.length() == 0 && prevRange.isAdjacentBefore(nextRange)) {
            // remove the string and next range
            parts[0] = prevRange.expandToInclude(nextRange);
            parts[1] = null;
            parts[2] = null;
        } else {
            if (eolRange.isNotNull()) {
                // need to insert eolRange, can replace prevRange if it is NULL or text if it is empty, or move text to nextRange if it is NULL
                if (nextRange.isEmpty() && nextRange.getStart() < eolRange.getEnd()) {
                    // serves no purpose and causes
                    nextRange = Range.NULL;
                }

                if (text.length() == 0) {
                    parts[0] = prevRange;
                    parts[1] = eolRange;
                    parts[2] = nextRange;
                } else if (prevRange.isNull()) {
                    parts[0] = eolRange;
                    parts[1] = text;
                    parts[2] = nextRange;
                } else if (nextRange.isNull()) {
                    parts[0] = prevRange;
                    parts[1] = eolRange;
                    parts[2] = text;
                } else {
                    // insert a new position
                    parts = new Object[parts.length + 1];
                    parts[0] = prevRange;
                    parts[1] = eolRange;
                    parts[2] = text;
                    parts[3] = nextRange;
                }
            } else {
                parts[0] = prevRange;
                parts[1] = text;
                parts[2] = nextRange;
            }
        }

        return parts;
    }
}
