package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Builder of editing operations for a concrete Range of an abstract character sequence
 */
public class EditOpsBuilder {
    final private @NotNull Range myOriginalRange;
    final private @NotNull ArrayList<EditOp> myEditOps;
    private @NotNull Range myRange;   // represents current range of editing sequence

    public EditOpsBuilder(@NotNull Range originalRange, @NotNull ArrayList<EditOp> editOps) {
        myOriginalRange = originalRange;
        myEditOps = editOps;
        myRange = originalRange;
    }

    @NotNull
    public Range getOriginalRange() {
        return myOriginalRange;
    }

    @NotNull
    public Range getRange() {
        return myRange;
    }

    public boolean isEmpty() {
        return myEditOps.isEmpty();
    }

    public void clear() {
        myEditOps.clear();
        myRange = myOriginalRange;
    }

    public EditOps getEditOps() {
        return myRange.isEmpty() ? EditOps.NULL : new EditOps(myEditOps);
    }

    /**
     * Converts all operations to first deletes in original range offsets followed by inserts in original range offsets
     * then sorts them by starting range, since ranges no longer overlap,
     * then combines delete and insert where delete end equals insert start and changes these to replace ops
     *
     * @return optimized edit ops
     */
    public EditOps getOptimizedEditOps() {
        return myRange.isEmpty() ? EditOps.NULL : new EditOps(myEditOps);
    }

    @NotNull
    public EditOpsBuilder insertOp(int index, @NotNull CharSequence param) {
        EditOp editOp = EditOp.insertOp(index, param);
        if (editOp.isNotNull()) {
            // validate it against our current range and add to the end of the list
            if (!myRange.isValidIndex(index))
                throw new IllegalArgumentException("insertOp index: " + index + " is not valid for current range: " + myRange);

            myEditOps.add(editOp);

            // extend our range by inserted length
            myRange = myRange.withEnd(myRange.getEnd() + editOp.getOpParam().length());
        }
        return this;
    }

    @NotNull
    public EditOpsBuilder deleteOp(@NotNull Range range) {
        EditOp editOp = EditOp.deleteOp(range);
        if (editOp.isNotNull()) {
            // validate it against our current range and add to the end of the list
            if (!myRange.doesContain(range))
                throw new IllegalArgumentException("deleteOp range: " + range + " is not valid for current range: " + myRange);

            // shorten our range by deleted span
            int end = myRange.getEnd() - editOp.getRange().getSpan();

            myRange = myRange.withEnd(end);

            if (myRange.getStart() == end) {
                // everything was deleted, can replace with a single delete original length
                clear();
                editOp = EditOp.deleteOp(myOriginalRange);
                myEditOps.add(editOp);
                myRange = myOriginalRange.withEnd(myOriginalRange.getStart());
            }

            myEditOps.add(editOp);
        }
        return this;
    }

    @NotNull
    public EditOpsBuilder replaceOp(@NotNull Range range, @NotNull CharSequence param) {
        EditOp editOp = EditOp.replaceOp(range, param);
        if (editOp.isNotNull()) {
            // validate it against our current range and add to the end of the list
            if (!myRange.doesContain(range))
                throw new IllegalArgumentException("replaceOp range: " + range + " is not valid for current range: " + myRange);

            // shorten or lengthen depending on diff between range span and param length
            int end = myRange.getEnd() - editOp.getRange().getSpan() + param.length();
            myRange = myRange.withEnd(end);
            myEditOps.add(editOp);
        }
        return this;
    }

    @NotNull
    public static EditOpsBuilder opsBuilder(@NotNull Range range) {
        return new EditOpsBuilder(range, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "EditOpsBuilder{ original=" + myOriginalRange + ", range=" + myRange + ", ops=" + myEditOps + " }";
    }
}
