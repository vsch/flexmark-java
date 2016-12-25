package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.BiConsumer;

import java.util.*;

public class Attributes {
    public static final Attributes EMPTY = new Attributes();

    private LinkedHashMap<String, Attribute> myAttributes;

    public Attributes() {
        myAttributes = null;
    }

    public Attributes(Attributes attributes) {
        myAttributes = attributes.myAttributes == null ? null : new LinkedHashMap<>(attributes.myAttributes);
    }

    protected LinkedHashMap<String, Attribute> getAttributes() {
        if (myAttributes == null) {
            myAttributes = new LinkedHashMap<>();
        }
        return myAttributes;
    }

    public Attribute get(String key) {
        if (myAttributes == null || key == null || key.isEmpty()) return null;

        return myAttributes.get(key);
    }

    public String getValue(String key) {
        if (myAttributes == null || key == null || key.isEmpty()) return "";

        Attribute attribute = myAttributes.get(key);
        if (attribute == null) return "";
        return attribute.getValue();
    }

    public Attribute replaceValue(Attribute attribute) {
        return replaceValue(attribute.getName(), attribute.getValue());
    }

    public Attribute replaceValue(String key, String value) {
        Attribute attribute;
        if (myAttributes == null) {
            attribute = AttributeImpl.of(key, value);
        } else {
            attribute = myAttributes.get(key);
            if (attribute != null) attribute = attribute.replaceValue(value);
            else attribute = AttributeImpl.of(key, value);
        }
        getAttributes().put(key, attribute);
        return attribute;
    }

    public Attribute addValue(Attribute attribute) {
        return addValue(attribute.getName(), attribute.getValue());
    }

    public Attribute addValue(String key, String value) {
        Attribute attribute;
        if (myAttributes == null) {
            attribute = AttributeImpl.of(key, value);
        } else {
            attribute = myAttributes.get(key);
            if (attribute != null) attribute = attribute.setValue(value);
            else attribute = AttributeImpl.of(key, value);
        }
        getAttributes().put(key, attribute);
        return attribute;
    }

    public Attribute removeValue(Attribute attribute) {
        return removeValue(attribute.getName(), attribute.getValue());
    }

    public Attribute remove(Attribute attribute) {
        return remove(attribute.getName());
    }

    public Attribute removeValue(String key, String value) {
        if (myAttributes == null || key == null || key.isEmpty()) return null;

        Attribute oldAttribute = myAttributes.get(key);
        Attribute attribute = oldAttribute.removeValue(value);
        getAttributes().put(key, attribute);
        return attribute;
    }

    public boolean contains(String key) {
        return myAttributes != null && myAttributes.containsKey(key);
    }

    public boolean containsValue(String key, String value) {
        if (myAttributes == null) return false;
        Attribute attribute = myAttributes.get(key);
        return attribute != null && attribute.containsValue(value);
    }

    public boolean isEmpty() {
        return myAttributes == null || myAttributes.isEmpty();
    }

    public void clear() {
        myAttributes = null;
    }

    @SuppressWarnings("unchecked")
    public Set<String> keySet() {
        return myAttributes != null ? myAttributes.keySet() : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    public Collection<Attribute> values() {
        return myAttributes != null ? myAttributes.values() : Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Set<Map.Entry<String, Attribute>> entrySet() {
        return myAttributes != null ? myAttributes.entrySet() : Collections.EMPTY_SET;
    }

    public void forEach(BiConsumer<String, Attribute> action) {
        if (myAttributes != null) {
            for (Map.Entry<String, Attribute> entry : myAttributes.entrySet()) {
                action.accept(entry.getKey(), entry.getValue());
            }
        }
    }

    public int size() {
        return myAttributes == null ? 0 : myAttributes.size();
    }

    public Attribute remove(String key) {
        if (myAttributes == null || key == null || key.isEmpty()) return null;

        Attribute oldAttribute = myAttributes.get(key);
        myAttributes.remove(key);
        return oldAttribute;
    }

    public void replaceValues(Attributes attributes) {
        if (myAttributes == null) {
            myAttributes = new LinkedHashMap<>(attributes.myAttributes);
        } else {
            myAttributes.putAll(attributes.myAttributes);
        }
    }
}
