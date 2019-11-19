package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.vladsch.flexmark.util.Utils.escapeJavaString;
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

    public boolean isRange() {
        return isNotNull();
    }

    public boolean isNullOp() {
        return this == NULL_OP;
    }

    @NotNull
    public EditOp withText(@Nullable String text) {
        return (text == null || text.isEmpty()) && (isNull() || isEmpty()) ? NULL_OP : new EditOp(this, text);
    }

    @NotNull
    public EditOp keepText(@NotNull Range other) {
        return other.isNull() ? this : new EditOp(other, myText);
    }

    @NotNull
    public EditOp withSuffix(@NotNull String text) {
        return new EditOp(this, myText + text);
    }

    /**
     * Return length of text or if text is null span of range
     *
     * @return length of this part in the sequence
     */
    public int length() {
        return myText == null ? getSpan() : myText.length();
    }

    public boolean isNotNullOp() {
        return this != NULL_OP;
    }

    /**
     * @return true if this is a sequence from the base
     */
    public boolean isBase() {
        return isNotNull() && myText == null;
    }

    /**
     * @return true if unattached text
     */
    public boolean isPlainText() {
        return myText != null && isNull();
    }

    /**
     * @return true if this is an edit op with text
     */
    public boolean isTextOp() {
        return myText != null;
    }

    /**
     * Return merged op only if can merge and ranges are adjacent or null if cannot merge
     * only null ops ranges and
     *
     * @param other edit op, assumed that this comes before other, all things being equal and there is no range to guide the decision
     * @return null or merged, meaning both this and other can be replaced by the returned op
     */
    @Nullable
    public EditOp mergeWithAdjacent(@NotNull EditOp other) {
        // can merge null ops with anything
        if (this.isNotNullOp()) return other;
        if (other.isNotNullOp()) return this;

        if (this.isNull() && !other.isBase()) {
            // can merge text with all but base segment
            return new EditOp(other, this.myText + other.myText);
        }

        if (!this.isBase() && other.isNull()) {
            // can merge text with all but base segment
            return new EditOp(this, this.myText + other.myText);
        }

        if (this.isTextOp() && other.isTextOp()) {
            if (this.isAdjacentBefore(other)) return new EditOp(this.expandToInclude(other), this.myText + other.myText);
            else if (other.isAdjacentBefore(this)) return new EditOp(other.expandToInclude(this), other.myText + this.myText);
        }

        if (this.isBase() && other.isBase()) {
            if (this.isAdjacentBefore(other)) return new EditOp(this.expandToInclude(other), null);
            else if (other.isAdjacentBefore(this)) return new EditOp(other.expandToInclude(this), null);
        }

        // cannot merge
        return null;
    }

    /**
     * Return merged op or null if cannot merge, will merge edit ops even if they are not adjacent
     * but without any ops in between they can be merged when no base segment is between them
     *
     * Looses base offset information
     *
     * @param other edit op
     * @return null or merged, meaning both this and other can be replaced by the returned op
     */
    @Nullable
    public EditOp mergeWith(@NotNull EditOp other) {
        // can merge null ops with anything
        if (this.isNotNullOp()) return other;
        if (other.isNotNullOp()) return this;

        if (this.isNull() && !other.isBase()) {
            // can merge text with all but base segment
            return new EditOp(other, this.myText + other.myText);
        }

        if (!this.isBase() && other.isNull()) {
            // can merge text with all but base segment
            return new EditOp(this, this.myText + other.myText);
        }

        if (this.isTextOp() && other.isTextOp()) {
            if (this.getEnd() <= other.getStart()) {
                return new EditOp(this.expandToInclude(other), this.myText + other.myText);
            } else if (other.getEnd() <= this.getStart()) {
                return new EditOp(other.expandToInclude(this), other.myText + this.myText);
            }
        }

        if (this.isBase() && other.isBase()) {
            if (this.isAdjacentBefore(other)) return new EditOp(this.expandToInclude(other), null);
            else if (other.isAdjacentBefore(this)) return new EditOp(other.expandToInclude(this), null);
        }

        // cannot merge
        return null;
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
    static EditOp baseOp(@NotNull Range other) {
        return new EditOp(other, null);
    }

    @NotNull
    static EditOp plainText(@Nullable String text) {
        return text == null || text.isEmpty() ? NULL_OP : new EditOp(Range.NULL, text);
    }

    @NotNull
    static EditOp replaceOp(int startOffset, int endOffset, @NotNull String text) {
        return startOffset == endOffset && text.isEmpty() ? NULL_OP : new EditOp(startOffset, endOffset, text);
    }

    @NotNull
    static EditOp deleteOp(int startOffset, int endOffset) {
        return startOffset == endOffset ? NULL_OP : new EditOp(startOffset, endOffset, "");
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
        } else if (isNotNull()) {
            return "[" + getStart() + ", " + getEnd() + ", '" + escapeJavaString(myText) + "')";
        } else {
            return "'" + escapeJavaString(myText) + "'";
        }
    }
}
