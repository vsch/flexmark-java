package com.vladsch.flexmark.util.html;

import java.util.*;
import java.util.function.BiConsumer;

public class Attributes {
    final public static Attributes EMPTY = new Attributes();

    protected LinkedHashMap<String, Attribute> attributes;

    public Attributes() {
        attributes = null;
    }

    public Attributes(Attributes attributes) {
        this.attributes = attributes == null || attributes.attributes == null ? null : new LinkedHashMap<>(attributes.attributes);
    }
    
    public MutableAttributes toMutable() {
        return new MutableAttributes(this);
    }

    public Attributes toImmutable() {
        return this;
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

    @SuppressWarnings("unchecked")
    public Set<String> keySet() {
        // CAUTION: attributes can be modified through this mutable set
        return attributes != null ? attributes.keySet() : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    public Collection<Attribute> values() {
        return attributes != null ? attributes.values() : Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Set<Map.Entry<String, Attribute>> entrySet() {
        // CAUTION: attributes can be modified through this mutable set
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
