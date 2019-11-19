package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

public class BasedSegmentBuilder extends SegmentBuilder {
    private final @NotNull BasedSequence myBase;

    protected BasedSegmentBuilder(@NotNull BasedSequence base) {
        super();
        myBase = base;
    }

    public BasedSegmentBuilder(@NotNull BasedSegmentBuilder other) {
        super(other);
        myBase = other.myBase;
    }

    @Override
    public void handleOverlap(@NotNull SegmentPosition position, @NotNull Range range) {
        // convert overlap to text from our base
        // NOTE: one after the last range should be String or nothing, if it was a Range then it would be the last one
        EditOp textOp = position.getStringOrNullOp(1);
        Range prevRange = position.getRangeOrNullOp();
        assert prevRange.isNotNull() && prevRange.overlaps(range);
        String text = textOp.isPlainText() ? textOp.getText() : null;

        if (!prevRange.doesContain(range)) {
            // add overlapping part as range followed by text
            Range overlap = prevRange.intersect(range);

            // FIX: can be replace op with range
            if (text != null) {
                position.set(1, EditOp.plainText(text + myBase.subSequence(overlap).toString()));
            } else {
                position.append(EditOp.plainText(myBase.subSequence(overlap).toString()));
            }

            overlap = range.withStart(overlap.getEnd());

            if (overlap.isNotEmpty()) {
                position.append(EditOp.baseOp(overlap));
            }
        } else {
            // fully contains added range, add whole thing as text appended to previous text
            if (text != null) {
                position.set(1, EditOp.plainText(text + myBase.subSequence(range)));
            } else {
                position.add(1, EditOp.plainText(myBase.subSequence(range).toString()));
            }
        }
    }

    @NotNull
    public static BasedSegmentBuilder sequenceBuilder(@NotNull BasedSequence sequence) {
        return new BasedSegmentBuilder(sequence);
    }
}
