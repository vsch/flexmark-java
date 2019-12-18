package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.collection.BitFieldSet;
import org.jetbrains.annotations.NotNull;

/**
 * Tracked Offset information
 *
 * NOTE: purposefully equals compares the offset only and will equal an integer of the same value
 *   to allow use of TrackedOffset as a key but lookup to be done by offset
 */
public class TrackedOffset implements Comparable<TrackedOffset> {
    private enum Flags {
        AFTER_SPACE_EDIT,
        AFTER_INSERT,
        AFTER_DELETE,
    }

    final private static int F_AFTER_SPACE_EDIT = BitFieldSet.intMask(Flags.AFTER_SPACE_EDIT);
    final private static int F_AFTER_INSERT = BitFieldSet.intMask(Flags.AFTER_INSERT);
    final private static int F_AFTER_DELETE = BitFieldSet.intMask(Flags.AFTER_DELETE);

    final private int offset;
    final private int flags;

    public TrackedOffset(int offset, boolean afterSpaceEdit, boolean afterInsert, boolean afterDelete) {
        this.offset = offset;
        int flags = 0;
        if (afterSpaceEdit) flags |= F_AFTER_SPACE_EDIT;
        if (afterInsert) flags |= F_AFTER_INSERT;
        if (afterDelete) flags |= F_AFTER_DELETE;
        this.flags = flags;
    }

    public int getOffset() {
        return offset;
    }

    public boolean isAfterSpaceEdit() {
        return BitFieldSet.any(flags, F_AFTER_SPACE_EDIT);
    }

    public boolean isAfterInsert() {
        return BitFieldSet.any(flags, F_AFTER_INSERT);
    }

    public boolean isAfterDelete() {
        return BitFieldSet.any(flags, F_AFTER_DELETE);
    }

    @Override
    public int compareTo(@NotNull TrackedOffset o) {
        return Integer.compare(offset, o.offset);
    }

    public int compareTo(@NotNull Integer o) {
        return Integer.compare(offset, o);
    }

    public int compareTo(int offset) {
        return Integer.compare(this.offset, offset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || (getClass() != o.getClass() && !(o instanceof Integer))) return false;

        if (o instanceof Integer) {
            return ((Integer) o) == offset;
        }

        TrackedOffset offset = (TrackedOffset) o;

        return this.offset == offset.offset;
    }

    @Override
    public int hashCode() {
        return offset;
    }

    @Override
    public String toString() {
        return "{ [" + offset + (BitFieldSet.any(flags, F_AFTER_SPACE_EDIT | F_AFTER_DELETE) ? (isAfterSpaceEdit() ? ", s" : "") + (isAfterDelete() ? ", d" : "") : "") + ") }";
    }
}
