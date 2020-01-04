package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.LineInfo;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;

import java.util.List;

import static com.vladsch.flexmark.util.misc.CharPredicate.WHITESPACE;

public class TrackedOffsetUtils {

    /**
     * Resolve any unresolved tracked offsets
     *
     * @param baseSeq               original sequence for tracked offsets
     * @param appendable            line appendable containing resulting lines
     * @param offsets               tracked offsets
     * @param maxTrailingBlankLines max trailing blank lines to use in resolving offsets
     */
    public static void resolveTrackedOffsets(BasedSequence baseSeq, LineAppendable appendable, List<TrackedOffset> offsets, int maxTrailingBlankLines) {
        if (!offsets.isEmpty()) {
            TrackedOffsetList trackedOffsets = TrackedOffsetList.create(baseSeq, offsets);

            // need to resolve any unresolved offsets
            int unresolved = trackedOffsets.size();
            int length = 0;

            for (LineInfo lineInfo : appendable.getLinesInfo(maxTrailingBlankLines, 0, appendable.getLineCount())) {
                BasedSequence line = lineInfo.getLine();
                List<TrackedOffset> lineTrackedOffsets = trackedOffsets.getTrackedOffsets(line.getStartOffset(), line.getEndOffset());

                if (!lineTrackedOffsets.isEmpty()) {
                    for (TrackedOffset trackedOffset : lineTrackedOffsets) {
                        BasedOffsetTracker tracker = BasedOffsetTracker.create(line);

                        if (!trackedOffset.isResolved()) {
                            if (baseSeq.isBaseCharAt(trackedOffset.getOffset(), WHITESPACE) && !baseSeq.isBaseCharAt(trackedOffset.getOffset() - 1, WHITESPACE)) {
                                // we need to use previous non-blank and use that offset
                                OffsetInfo info = tracker.getOffsetInfo(trackedOffset.getOffset() - 1, false);
                                trackedOffset.setIndex(info.endIndex + length);
                            } else if (baseSeq.isBaseCharAt(trackedOffset.getOffset() + 1, WHITESPACE) && !baseSeq.isBaseCharAt(trackedOffset.getOffset(), WHITESPACE)) {
                                // we need to use this non-blank and use that offset
                                OffsetInfo info = tracker.getOffsetInfo(trackedOffset.getOffset(), false);
                                trackedOffset.setIndex(info.startIndex + length);
                            } else {
                                OffsetInfo info = tracker.getOffsetInfo(trackedOffset.getOffset(), true);
                                trackedOffset.setIndex(info.endIndex + length);
                            }
                            System.out.println(String.format("Resolved %d to %d, start: %d, in line[%d]: '%s'", trackedOffset.getOffset(), trackedOffset.getIndex(), length, lineInfo.index, line.getBuilder().append(line).toStringWithRanges(true)));
                            unresolved--;
                        }
                    }
                }

                length += line.length();
                if (unresolved <= 0) break;
            }
        }
    }
}
