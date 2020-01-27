package com.vladsch.flexmark.util.html;

import java.util.*;
import java.util.function.BiConsumer;

public class Attributes {
    final public static Attributes EMPTY = new Attributes();

    private LinkedHashMap<String, Attribute> attributes;

    public Attributes() {
        attributes = null;
    }

    public Attributes(Attributes attributes) {
        this.attributes = attributes == null || attributes.attributes == null ? null : new LinkedHashMap<>(attributes.attributes);
    }

    protected LinkedHashMap<String, Attribute> getAttributes() {
        if (attributes == null) {
            attributes = new LinkedHashMap<>();
        }
        return attributes;
    }

    public Attribute get(CharSequence key) {
        if (attributes == null || key == null || key.length() == 0) return null;

        String useKey = String.valueOf(key);
        return attributes.get(useKey);
    }

    public String getValue(CharSequence key) {
        if (attributes == null || key == null || key.length() == 0) return "";

        String useKey = String.valueOf(key);
        Attribute attribute = attributes.get(useKey);
        if (attribute == null) return "";
        return attribute.getValue();
    }

    public Attribute replaceValue(Attribute attribute) {
        return replaceValue(attribute.getName(), attribute.getValue());
    }

    /**
     * Attribute dependent value replacement
     * class and style append new values to existing ones
     * others set it to the new value
     *
     * @param key   attribute name
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
            if (attribute != null) attribute = attribute.replaceValue(value);
            else attribute = AttributeImpl.of(useKey, value);
        }
        getAttributes().put(useKey, attribute);
        return attribute;
    }

    public Attribute addValue(Attribute attribute) {
        return addValue(attribute.getName(), attribute.getValue());
    }

    public Attributes addValues(Attributes attributes) {
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
            if (attribute != null) attribute = attribute.setValue(value);
            else attribute = AttributeImpl.of(useKey, value);
        }
        getAttributes().put(useKey, attribute);
        return attribute;
    }

    public Attribute removeValue(Attribute attribute) {
        return removeValue(attribute.getName(), attribute.getValue());
    }

    public Attribute remove(Attribute attribute) {
        return remove(attribute.getName());
    }

    public Attribute removeValue(CharSequence key, CharSequence value) {
        if (attributes == null || key == null || key.length() == 0) return null;

        String useKey = String.valueOf(key);
        Attribute oldAttribute = attributes.get(useKey);
        Attribute attribute = oldAttribute.removeValue(value);
        getAttributes().put(useKey, attribute);
        return attribute;
    }

    public boolean contains(CharSequence key) {
        if (attributes == null || key == null || key.length() == 0) return false;

        String useKey = String.valueOf(key);
        return attributes.containsKey(useKey);
    }

    public boolean containsValue(CharSequence key, CharSequence value) {
        if (attributes == null) return false;
        String useKey = String.valueOf(key);
        Attribute attribute = attributes.get(useKey);
        return attribute != null && attribute.containsValue(value);
    }

    public boolean isEmpty() {
        return attributes == null || attributes.isEmpty();
    }

    public void clear() {
        attributes = null;
    }

    @SuppressWarnings("unchecked")
    public Set<String> keySet() {
        return attributes != null ? attributes.keySet() : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    public Collection<Attribute> values() {
        return attributes != null ? attributes.values() : Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Set<Map.Entry<String, Attribute>> entrySet() {
        return attributes != null ? attributes.entrySet() : Collections.EMPTY_SET;
    }

    public void forEach(BiConsumer<String, Attribute> action) {
        if (attributes != null) {
            for (Map.Entry<String, Attribute> entry : attributes.entrySet()) {
                action.accept(entry.getKey(), entry.getValue());
            }
        }
    }

    public int size() {
        return attributes == null ? 0 : attributes.size();
    }

    public Attribute remove(CharSequence key) {
        if (attributes == null || key == null || key.length() == 0) return null;

        String useKey = String.valueOf(key);
        Attribute oldAttribute = attributes.get(useKey);
        attributes.remove(useKey);
        return oldAttribute;
    }

    public void replaceValues(Attributes attributes) {
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap<>(attributes.attributes);
        } else {
            this.attributes.putAll(attributes.attributes);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String sep = "";
        for (String attrName : keySet()) {
            sb.append(sep).append(attrName);
            Attribute attribute = attributes.get(attrName);
            if (!attribute.getValue().isEmpty()) sb.append("=").append("\"").append(attribute.getValue().replace("\"", "\\\"")).append("\"");
            sep = " ";
        }

        return "Attributes{" + sb.toString() + '}';
    }
}
