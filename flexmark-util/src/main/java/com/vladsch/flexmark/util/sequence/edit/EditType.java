package com.vladsch.flexmark.util.sequence.edit;

public enum EditType {
    NULL(false),
    BASE(false),
    INSERT(true),
    DELETE(false),
    REPLACE(true);

    final public boolean hasParams;

    EditType(boolean hasParams) {
        this.hasParams = hasParams;
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isInsert() {
        return this == INSERT;
    }

    public boolean isBase() {
        return this == BASE;
    }

    public boolean isDelete() {
        return this == DELETE;
    }

    public boolean isReplace() {
        return this == REPLACE;
    }
}
