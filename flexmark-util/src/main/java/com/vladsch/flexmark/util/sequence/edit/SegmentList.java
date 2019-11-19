package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionListBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SegmentList extends PositionListBase<EditOp, SegmentPosition> {
    public SegmentList() {
        super(new ArrayList<>(), SegmentPosition::new);
    }

    public SegmentList(ArrayList<EditOp> parts) {
        super(parts, SegmentPosition::new);
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
