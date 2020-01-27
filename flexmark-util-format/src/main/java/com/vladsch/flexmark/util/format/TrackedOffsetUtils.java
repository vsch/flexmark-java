package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.LineInfo;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;

import java.util.List;

import static com.vladsch.flexmark.util.misc.CharPredicate.WHITESPACE;

public class TrackedOffsetUtils {

    /**
     * Resolve any unresolved tracked offsets
     *
     * @param sequence              original sequence for tracked offsets
     * @param appendable            line appendable containing resulting lines
     * @param offsets               tracked offsets
     * @param maxTrailingBlankLines max trailing blank lines to use in resolving offsets
     * @param traceDetails          true if running tests and want detail printout to stdout
     */
    public static void resolveTrackedOffsets(BasedSequence sequence, LineAppendable appendable, List<TrackedOffset> offsets, int maxTrailingBlankLines, boolean traceDetails) {
        if (!offsets.isEmpty()) {
            TrackedOffsetList trackedOffsets = TrackedOffsetList.create(sequence, offsets);

            // need to resolve any unresolved offsets
            int unresolved = trackedOffsets.size();
            int length = 0;
            ISequenceBuilder<?, ?> appendableBuilder = appendable.getBuilder();
            BasedSequence baseSeq = appendableBuilder instanceof SequenceBuilder ? ((SequenceBuilder) appendableBuilder).getBaseSequence() : sequence.getBaseSequence();

            for (LineInfo lineInfo : appendable.getLinesInfo(maxTrailingBlankLines, 0, appendable.getLineCount())) {
                BasedSequence line = lineInfo.getLine();
                List<TrackedOffset> lineTrackedOffsets = trackedOffsets.getTrackedOffsets(line.getStartOffset(), line.getEndOffset());

                if (!lineTrackedOffsets.isEmpty()) {
                    for (TrackedOffset trackedOffset : lineTrackedOffsets) {
                        BasedOffsetTracker tracker = BasedOffsetTracker.create(line);

                        if (!trackedOffset.isResolved()) {
                            int offset = trackedOffset.getOffset();
                            boolean baseIsWhiteSpaceAtOffset = baseSeq.isCharAt(offset, WHITESPACE);

                            if (baseIsWhiteSpaceAtOffset && !(baseSeq.isCharAt(offset - 1, WHITESPACE))) {
                                // we need to use previous non-blank and use that offset
                                OffsetInfo info = tracker.getOffsetInfo(offset - 1, false);
                                trackedOffset.setIndex(info.endIndex + length);
                            } else if (!baseIsWhiteSpaceAtOffset && baseSeq.isCharAt(offset + 1, WHITESPACE)) {
                                // we need to use this non-blank and use that offset
                                OffsetInfo info = tracker.getOffsetInfo(offset, false);
                                trackedOffset.setIndex(info.startIndex + length);
                            } else {
                                OffsetInfo info = tracker.getOffsetInfo(offset, true);
                                trackedOffset.setIndex(info.endIndex + length);
                            }
                            if (traceDetails) {
                                System.out.println(String.format("Resolved %d to %d, start: %d, in line[%d]: '%s'", offset, trackedOffset.getIndex(), length, lineInfo.index, line.getBuilder().append(line).toStringWithRanges(true)));
                            }
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
