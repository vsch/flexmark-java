package com.vladsch.flexmark.ext.attributes;

public enum AttributeImplicitName {
    AS_IS,
    IMPLICIT_PREFERED,
    EXPLICIT_PREFERED,
    ;

    public boolean isNoChange() {
        return this == AS_IS;
    }

    public boolean isImplicit() {
        return this == IMPLICIT_PREFERED;
    }

    public boolean isExplicit() {
        return this == EXPLICIT_PREFERED;
    }
}
