package com.vladsch.flexmark.util.html;

import java.util.LinkedHashMap;

public class MutableAttributes extends Attributes {
  public MutableAttributes() {
    super();
  }

  public MutableAttributes(Attributes attributes) {
    super(attributes);
  }

  @Override
  public MutableAttributes toMutable() {
    return this;
  }

  @Override
  public Attributes toImmutable() {
    return new Attributes(this);
  }

  private LinkedHashMap<String, Attribute> getAttributes() {
    if (attributes == null) {
      attributes = new LinkedHashMap<>();
    }
    return attributes;
  }

  /**
   * Attribute dependent value replacement class and style append new values to existing ones others
   * set it to the new value
   *
   * @param key attribute name
   * @param value new value
   * @return new attribute
   */
  public Attribute replaceValue(CharSequence key, CharSequence value) {
    String useKey = String.valueOf(key);
    Attribute attribute;
    if (attributes == null) {
      attribute = AttributeImpl.of(useKey, value);
    } else {
      attribute = attributes.get(useKey);
      if (attribute != null) {
        attribute = attribute.replaceValue(value);
      } else {
        attribute = AttributeImpl.of(useKey, value);
      }
    }
    getAttributes().put(useKey, attribute);
    return attribute;
  }

  public MutableAttributes addValues(Attributes attributes) {
    for (Attribute attribute : attributes.values()) {
      addValue(attribute.getName(), attribute.getValue());
    }
    return this;
  }

  public Attribute addValue(CharSequence key, CharSequence value) {
    Attribute attribute;
    String useKey = String.valueOf(key);
    if (attributes == null) {
      attribute = AttributeImpl.of(key, value);
    } else {
      attribute = attributes.get(useKey);
      if (attribute != null) {
        attribute = attribute.setValue(value);
      } else {
        attribute = AttributeImpl.of(useKey, value);
      }
    }
    getAttributes().put(useKey, attribute);
    return attribute;
  }

  public Attribute remove(CharSequence key) {
    if (attributes == null || key == null || key.length() == 0) {
      return null;
    }

    String useKey = String.valueOf(key);
    Attribute oldAttribute = attributes.get(useKey);
    attributes.remove(useKey);
    return oldAttribute;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    String sep = "";
    for (String attrName : keySet()) {
      sb.append(sep).append(attrName);
      Attribute attribute = attributes.get(attrName);
      if (!attribute.getValue().isEmpty())
        sb.append("=").append("\"").append(attribute.getValue().replace("\"", "\\\"")).append("\"");
      sep = " ";
    }

    return "MutableAttributes{" + sb.toString() + '}';
  }
}
