package com.vladsch.flexmark.ext.attributes;

public enum AttributeImplicitName {
    AS_IS,
    IMPLICIT_PREFERRED,
    EXPLICIT_PREFERRED,
    ;

    public boolean isNoChange() {
        return this == AS_IS;
    }

    public boolean isImplicit() {
        return this == IMPLICIT_PREFERRED;
    }

    public boolean isExplicit() {
        return this == EXPLICIT_PREFERRED;
    }
}
