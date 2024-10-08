package com.vladsch.flexmark.util.html;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

public class Attributes {
  protected LinkedHashMap<String, Attribute> attributes;

  public Attributes() {
    attributes = null;
  }

  Attributes(Attributes attributes) {
    this.attributes =
        attributes == null || attributes.attributes == null
            ? null
            : new LinkedHashMap<>(attributes.attributes);
  }

  public MutableAttributes toMutable() {
    return new MutableAttributes(this);
  }

  public Attributes toImmutable() {
    return this;
  }

  public Attribute get(CharSequence key) {
    if (attributes == null || key == null || key.length() == 0) {
      return null;
    }

    String useKey = String.valueOf(key);
    return attributes.get(useKey);
  }

  public String getValue(CharSequence key) {
    if (attributes == null || key == null || key.length() == 0) {
      return "";
    }

    String useKey = String.valueOf(key);
    Attribute attribute = attributes.get(useKey);
    if (attribute == null) {
      return "";
    }
    return attribute.getValue();
  }

  public boolean contains(CharSequence key) {
    if (attributes == null || key == null || key.length() == 0) {
      return false;
    }

    String useKey = String.valueOf(key);
    return attributes.containsKey(useKey);
  }

  public boolean containsValue(CharSequence key, CharSequence value) {
    if (attributes == null) {
      return false;
    }
    String useKey = String.valueOf(key);
    Attribute attribute = attributes.get(useKey);
    return attribute != null && attribute.containsValue(value);
  }

  public boolean isEmpty() {
    return attributes == null || attributes.isEmpty();
  }

  Set<String> keySet() {
    // CAUTION: attributes can be modified through this mutable set
    return attributes != null ? attributes.keySet() : Collections.emptySet();
  }

  Collection<Attribute> values() {
    return attributes != null ? attributes.values() : Collections.emptySet();
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

    return "Attributes{" + sb.toString() + '}';
  }
}
