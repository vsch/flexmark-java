package com.vladsch.flexmark.internal.util;

public enum ModificationBehavior {
    KEEP_LAST, KEEP_FIRST, FAIL_ON_DUPLICATE, LOCKED;

    public static final ModificationBehavior DEFAULT = KEEP_FIRST; 
}
