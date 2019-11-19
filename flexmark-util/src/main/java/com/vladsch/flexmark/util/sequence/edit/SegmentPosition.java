package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.IPositionBase;
import com.vladsch.flexmark.util.collection.iteration.IPositionUpdater;
import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import org.jetbrains.annotations.NotNull;

public class SegmentPosition extends IPositionBase<EditOp, SegmentPosition> {
    public SegmentPosition(@NotNull IPositionUpdater<EditOp, SegmentPosition> parent, int index, @NotNull PositionAnchor anchor) {
        super(parent, index, anchor);
    }

    /**
     * @return unattached text op or NULL_OP
     */
    @NotNull
    public EditOp getStringOrNullOp() {
        return getStringOrNullOp(0);
    }

    /**
     * @return op with range or NULL_OP
     */
    @NotNull
    public EditOp getRangeOrNullOp() {
        return getRangeOrNullOp(0);
    }

    /**
     * @return unattached text op or NULL_OP
     */
    @NotNull
    public EditOp getStringOrNullOp(int index) {
        EditOp op = getOrNull(index);
        return op == null || !op.isPlainText() ? EditOp.NULL_OP : op;
    }

    /**
     * @return op with range or NULL_OP
     */
    @NotNull
    public EditOp getRangeOrNullOp(int index) {
        EditOp op = getOrNull(index);
        return op == null || op.isNull() ? EditOp.NULL_OP : op;
    }

    /**
     * @return op or NULL_OP
     */
    @NotNull
    public EditOp getOrNullOp(int index) {
        EditOp op = getOrNull(index);
        return op == null ? EditOp.NULL_OP : op;
    }
}
