package com.vladsch.flexmark.internal.util;

public enum ModifyBehavior {
    KEEP_LAST, KEEP_FIRST, FAIL_ON_DUPLICATE, LOCKED;
    
    public static final ModifyBehavior DEFAULT = KEEP_FIRST; 
}
