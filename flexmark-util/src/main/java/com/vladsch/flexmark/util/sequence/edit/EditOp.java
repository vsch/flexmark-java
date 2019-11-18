package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.vladsch.flexmark.util.Utils.quoteJavaString;

/**
 * Representation of an edit operation on an abstract char sequence
 */
public class EditOp extends Range {
    final public static EditOp NULL_OP = new EditOp(Range.NULL, null);

    final private @Nullable String myText;

    private EditOp(int start, int end, @Nullable String text) {
        super(start, end);
        myText = text;
    }

    private EditOp(@NotNull Range other, @Nullable String text) {
        super(other);
        myText = text;
    }

    @Nullable
    public String getText() {
        return myText;
    }

    public boolean isNullOp() {
        return this == NULL_OP;
    }

    public boolean isNotNullOp() {
        return this != NULL_OP;
    }

    public boolean isBase() {
        return myText == null;
    }

    public boolean isText() {
        return myText != null;
    }

    @NotNull
    public EditType getEditType() {
        if (this == NULL_OP) {
            return EditType.NULL;
        } else if (myText == null) {
            return EditType.BASE;
        } else {
            if (myText.length() == 0) {
                return EditType.DELETE;
            } else {
                return getSpan() == 0 ? EditType.INSERT : EditType.REPLACE;
            }
        }
    }

    @NotNull
    static EditOp baseOp(int startOffset, int endOffset) {
        return new EditOp(startOffset, endOffset, null);
    }

    @NotNull
    static EditOp textOp(int startOffset, int endOffset, @Nullable String text) {
        return startOffset >= endOffset && (text == null || text.isEmpty()) ? NULL_OP : new EditOp(startOffset, endOffset, text);
    }

    @NotNull
    static EditOp replaceOp(int startOffset, int endOffset, @NotNull String text) {
        return startOffset >= endOffset && text.isEmpty() ? NULL_OP : new EditOp(startOffset, endOffset, text);
    }

    @NotNull
    static EditOp deleteOp(int startOffset, int endOffset) {
        return startOffset >= endOffset ? NULL_OP : new EditOp(startOffset, endOffset, "");
    }

    @NotNull
    static EditOp insertOp(int startOffset, @NotNull String text) {
        return text.isEmpty() ? NULL_OP : new EditOp(startOffset, startOffset, text);
    }

    @Override
    public String toString() {
        if (this == NULL_OP) {
            return "NULL_OP";
        } else if (myText == null) {
            return "[" + getStart() + ", " + getEnd() + ")";
        } else {
            return "[" + getStart() + ", " + getEnd() + ", " + quoteJavaString(myText) + ")";
        }
    }
}
