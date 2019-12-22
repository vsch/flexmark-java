package com.vladsch.flexmark.util.format.options;

public enum ElementPlacementSort {
    AS_IS,
    SORT,
    SORT_UNUSED_LAST,
    SORT_DELETE_UNUSED,
    DELETE_UNUSED,
    ;

    public boolean isUnused() {
        return this == SORT_UNUSED_LAST || this == SORT_DELETE_UNUSED || this == DELETE_UNUSED;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isDeleteUnused() {
        return this == SORT_DELETE_UNUSED || this == DELETE_UNUSED;
    }

    public boolean isSort() {
        return this == SORT_UNUSED_LAST || this == SORT_DELETE_UNUSED || this == SORT;
    }
}
