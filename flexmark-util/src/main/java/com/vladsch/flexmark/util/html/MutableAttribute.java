package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Mutable;

public interface MutableAttribute extends Attribute, Mutable<MutableAttribute, Attribute> {
    MutableAttribute copy();

    boolean containsValue(String value);
    MutableAttribute replaceValue(String value);
    MutableAttribute setValue(String value);
    MutableAttribute removeValue(String value);
}
