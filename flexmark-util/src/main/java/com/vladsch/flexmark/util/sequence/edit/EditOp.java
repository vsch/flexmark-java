package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.vladsch.flexmark.util.Utils.escapeString;
import static com.vladsch.flexmark.util.Utils.quoteString;

/**
 * Representation of an edit operation on an abstract char sequence
 */
public class EditOp {
    final public static EditOp NULL = new EditOp(Range.NULL, null);

    final private @NotNull Range myRange;
    final private @Nullable CharSequence myParam;

    private EditOp(@NotNull Range range, @Nullable CharSequence param) {
        myRange = range;
        myParam = param;
    }

    @NotNull
    public Range getRange() {
        return myRange;
    }

    @Nullable
    public CharSequence getParam() {
        return myParam;
    }

    @NotNull
    public CharSequence getOpParam() {
        assert myParam != null;
        return myParam;
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isNotNull() {
        return this != NULL;
    }

    @NotNull
    public EditType getEditType() {
        if (this == NULL) {
            return EditType.NULL;
        } else {
            if (myParam == null || myParam.length() == 0) {
                return EditType.DELETE;
            } else {
                return myRange.getSpan() == 0 ? EditType.INSERT : EditType.REPLACE;
            }
        }
    }

    @NotNull
    static EditOp insertOp(int index, @NotNull CharSequence param) {
        return param.length() == 0 ? EditOp.NULL : new EditOp(Range.of(index, index), param);
    }

    @NotNull
    static EditOp deleteOp(@NotNull Range range) {
        return range.isEmpty() ? EditOp.NULL : new EditOp(range, null);
    }

    @NotNull
    static EditOp replaceOp(@NotNull Range range, @NotNull CharSequence param) {
        return range.isEmpty() && param.length() == 0 ? EditOp.NULL : new EditOp(range, param);
    }

    @Override
    public String toString() {
        switch (getEditType()) {
            case NULL:
                return "EditOp.NULL";

            case DELETE:
                return "EditOp{DELETE: " + myRange + "}";

            case INSERT:
                return "EditOp{INSERT: " + myRange.getStart() + ", " + quoteString(myParam) + "}";

            case REPLACE:
                return "EditOp{REPLACE: " + myRange + ", " + quoteString(myParam) + "}";
        }

        // FIX: when stable change to throw IllegalStateException
        return "EditOp{UNKNOWN}";
    }
}
