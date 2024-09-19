package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.misc.Mutable;

public interface MutableAttribute extends Attribute, Mutable<MutableAttribute, Attribute> {
  MutableAttribute copy();

  @Override
  boolean containsValue(CharSequence value);

  @Override
  MutableAttribute replaceValue(CharSequence value);

  @Override
  MutableAttribute setValue(CharSequence value);

  @Override
  MutableAttribute removeValue(CharSequence value);
}
