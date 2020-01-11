package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.misc.BitFieldSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Tracked Offset information
 * <p>
 * NOTE: purposefully equals compares the offset only and will equal an integer of the same value
 * to allow use of TrackedOffset as a key but lookup to be done by offset
 */
final public class TrackedOffset implements Comparable<TrackedOffset> {
    private enum Flags {
        AFTER_SPACE_EDIT,
        AFTER_INSERT,
        AFTER_DELETE,
    }

    final private static int F_AFTER_SPACE_EDIT = BitFieldSet.intMask(Flags.AFTER_SPACE_EDIT);
    final private static int F_AFTER_INSERT = BitFieldSet.intMask(Flags.AFTER_INSERT);
    final private static int F_AFTER_DELETE = BitFieldSet.intMask(Flags.AFTER_DELETE);

    final private @Nullable TrackedOffset original;
    final private int offset;
    final private int flags;
    private int spacesBefore;
    private int spacesAfter;
    private boolean isSpliced;         // spaces reset to 0
    private int index;

    private TrackedOffset(int offset, boolean afterSpaceEdit, boolean afterInsert, boolean afterDelete) {
        this.original = null;
        this.offset = offset;
        int flags = 0;
        if (afterSpaceEdit) flags |= F_AFTER_SPACE_EDIT;
        if (afterInsert) flags |= F_AFTER_INSERT;
        if (afterDelete) flags |= F_AFTER_DELETE;
        this.flags = flags;
        this.index = -1;
        this.spacesBefore = -1;
        this.spacesAfter = -1;
    }

    private TrackedOffset(@NotNull TrackedOffset other) {
        this.original = other.original;
        this.offset = other.offset;
        this.flags = other.flags;
        this.index = -1;
        this.spacesBefore = other.spacesBefore;
        this.spacesAfter = other.spacesAfter;
    }

    private TrackedOffset(@NotNull TrackedOffset other, int offset) {
        this.original = other;
        this.offset = offset;
        this.flags = other.flags;
        this.index = -1;
        this.spacesBefore = other.spacesBefore;
        this.spacesAfter = other.spacesAfter;
    }

    public int getOffset() {
        return offset;
    }

    public int getSpacesBefore() {
        return spacesBefore;
    }

    public void setSpacesBefore(int spacesBefore) {
        this.spacesBefore = spacesBefore;
    }

    public int getSpacesAfter() {
        return spacesAfter;
    }

    public void setSpacesAfter(int spacesAfter) {
        this.spacesAfter = spacesAfter;
    }

    public boolean isSpliced() {
        return isSpliced;
    }

    public void setSpliced(boolean spliced) {
        this.isSpliced = spliced;
    }

    public boolean isResolved() {
        return index != -1;
    }

    public int getIndex() {
        return index == -1 ? offset : index;
    }

    public void setIndex(int index) {
        if (this.original != null) this.original.index = index;
        this.index = index;
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

    @NotNull
    public TrackedOffset plusOffsetDelta(int delta) {
        return new TrackedOffset(this, offset + delta);
    }

    @NotNull
    public TrackedOffset withOffset(int offset) {
        return new TrackedOffset(this, offset);
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
        return "{" + offset +
                (isSpliced() ? " ><" : "") +
                (spacesBefore >= 0 || spacesAfter >= 0 ? " " + (spacesBefore >= 0 ? Integer.toString(spacesBefore) : "?") + "|" + (spacesAfter >= 0 ? Integer.toString(spacesAfter) : "?") : "") +
                (BitFieldSet.any(flags, F_AFTER_SPACE_EDIT | F_AFTER_INSERT | F_AFTER_DELETE) ? " " + (isAfterSpaceEdit() ? "s" : "") + (isAfterInsert() ? "i" : "") + (isAfterDelete() ? "d" : "") : "") +
                (isResolved() ? " -> " + index : "") +
                "}";
    }

    public static TrackedOffset track(@NotNull TrackedOffset other) {
        return new TrackedOffset(other);
    }

    public static TrackedOffset track(int offset) {
        return track(offset, false, false, false);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static TrackedOffset track(int offset, @Nullable Character c, boolean afterDelete) {
        return track(offset, c != null && c == ' ', c != null && !afterDelete, afterDelete);
    }

    public static TrackedOffset track(int offset, boolean afterSpaceEdit, boolean afterInsert, boolean afterDelete) {
        assert !afterInsert && !afterDelete || afterInsert != afterDelete : "Cannot have both afterInsert and afterDelete true";
        return new TrackedOffset(offset, afterSpaceEdit, afterInsert, afterDelete);
    }
}
