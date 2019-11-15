package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class CharMatchingSegmentOptimizer<S extends IRichSequence<S>> implements SegmentOptimizer<S> {
    private final TrackerDirection direction;

    public CharMatchingSegmentOptimizer(TrackerDirection direction) {
        this.direction = direction;
    }

    @Override
    public void accept(@NotNull S chars, @NotNull SegmentPosition position) {
        String text = position.getString();
        if (text.isEmpty()) return;  // already processed by previous optimizer

        Range originalPrev = position.getRangeOrNull(-1);
        Range originalNext = position.getRangeOrNull(1);
        Range prevRange = originalPrev;
        Range nextRange = originalNext;

        int matchedPrev = 0;
        int matchedNext = 0;

        if (prevRange != null) {
            matchedPrev = chars.matchedCharCount(text, prevRange.getEnd(), false);
            prevRange = prevRange.endPlus(matchedPrev);
        }

        if (nextRange != null) {
            matchedNext = chars.matchedCharCountReversed(text, nextRange.getStart(), false);
            nextRange = nextRange.startMinus(matchedNext);
        }

        if (matchedNext == 0 && matchedPrev == 0) return;

        Range overlapRange = prevRange != null && nextRange != null ? prevRange.intersect(nextRange) : null;

        if (overlapRange != null && overlapRange.isNotEmpty()) {
            // need to allocate matches between next/prev based on direction
            int overlap = overlapRange.getSpan();

            int eolPos;
            // if eol was added to prev, then all after EOL goes to next
            String leftExtension = chars.subSequence(position.getRange(-1).getEnd(), overlapRange.getStart()).toString();
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
                String overlapped = text.substring(overlapRange.getStart() - position.getRange(-1).getEnd(), overlapRange.getEnd() - position.getRange(-1).getEnd());
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

        text = text.substring(matchedPrev, text.length() - matchedNext);

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
