package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.Range;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Represents edit operations performed on some char sequence
 *
 */
public class EditOps {
    final public static EditOp[] EMPTY_OPS = new EditOp[0];
    final public static EditOps NULL = new EditOps(Collections.emptyList());

    final private @NotNull List<EditOp> myEditOps;

    EditOps(@NotNull List<EditOp> editOps) {
        myEditOps = editOps;
    }

    public boolean isNull() {
        return this == NULL;
    }

    public EditOp[] getEditOps() {
        return myEditOps.toArray(EMPTY_OPS);
    }

    public List<EditOp> getEditOpsList() {
        return myEditOps;
    }

    @Override
    public String toString() {
        DelimitedBuilder sb = new DelimitedBuilder(", ");
        sb.append("EditOps{");
        for (EditOp editOp : myEditOps) {
            sb.append(editOp.toString()).mark();
        }
        sb.unmark().append('}');

        return sb.toString();
    }
}
