package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class CharMatchingSegmentOptimizer<S extends IRichSequence<S>> implements SegmentOptimizer<S> {
    private final TrackerDirection direction;

    public CharMatchingSegmentOptimizer(TrackerDirection direction) {
        this.direction = direction;
    }

    @NotNull
    @Override
    public SegmentParams apply(@NotNull S chars, @NotNull SegmentParams params) {
        String text = params.text;
        if (text.isEmpty()) return params;  // already processed by previous optimizer

        Range prevRange = params.prevRange;
        Range nextRange = params.nextRange;
        String newString = text;

        int matchedPrev = 0;
        int matchedNext = 0;

        if (prevRange != null) {
            matchedPrev = chars.matchedCharCount(text, prevRange.getEnd(), false);
        }

        if (nextRange != null) {
            matchedNext = chars.matchedCharCountReversed(text, nextRange.getStart(), false);
        }

        if (matchedNext == 0 && matchedPrev == 0) return params;

        if (matchedPrev > 0) {
            prevRange = prevRange.endPlus(matchedPrev);
        }

        if (matchedNext > 0) {
            nextRange = nextRange.startMinus(matchedNext);
        }

        Range overlapRange = prevRange != null && nextRange != null ? prevRange.intersect(nextRange) : null;

        if (overlapRange != null && overlapRange.isNotEmpty()) {
            // need to allocate matches between next/prev based on direction
            assert matchedNext > 0 && matchedPrev > 0;

            int overlap = overlapRange.getSpan();

            int eolPos;
            // if eol was added to prev, then all after EOL goes to next
            String leftExtension = chars.subSequence(params.prevRange.getEnd(), overlapRange.getStart()).toString();
            eolPos = leftExtension.indexOf("\n");
            if (eolPos != -1) {
                // give all after eol to next
                matchedPrev -= overlap - eolPos;
                prevRange = prevRange.endMinus(overlap - eolPos);

                matchedNext += eolPos;
                nextRange = nextRange.startPlus(eolPos);
            }

            if (eolPos == -1) {
                // if eol is in overlapped section, all after EOL goes to next
                String overlapped = text.substring(overlapRange.getStart() - params.prevRange.getEnd(), overlapRange.getEnd() - params.prevRange.getEnd());
                eolPos = overlapped.indexOf("\n");
                if (eolPos != -1) {
                    // this determines the split, not direction
                    eolPos++;
                    matchedPrev -= overlap - eolPos;
                    prevRange = prevRange.endMinus(overlap - eolPos);

                    matchedNext -= eolPos;
                    nextRange = nextRange.startPlus(eolPos);
                }
            }

            if (eolPos == -1) {
                switch (direction) {
                    case LEFT:
                        // give it all to next
                        matchedPrev -= overlap;
                        prevRange = prevRange.endMinus(overlap);
                        break;

                    case RIGHT:
                        // give it all to prev
                        matchedNext -= overlap;
                        nextRange = nextRange.startPlus(overlap);
                        break;

                    default:
                    case NONE:
                        // divide between the two with remainder to right??
                        int half = overlap >> 1;
                        matchedPrev -= overlap - half;
                        prevRange = prevRange.endMinus(overlap - half);
                        matchedNext -= half;
                        nextRange = nextRange.startPlus(half);
                        break;
                }
            }
        }

        if (matchedPrev > 0 && matchedNext > 0) {
            newString = text.substring(matchedPrev, text.length() - matchedNext);
        } else if (matchedPrev > 0) {
            newString = text.substring(matchedPrev);
        } else if (matchedNext > 0) {
            newString = text.substring(0, text.length() - matchedNext);
        }

        if (prevRange != null && nextRange != null && newString.isEmpty() && prevRange.isAdjacentBefore(nextRange)) {
            // remove the string and next range
            prevRange = prevRange.expandToInclude(nextRange);
            nextRange = null;
        }

        return params.with(prevRange, newString, nextRange);
    }
}
