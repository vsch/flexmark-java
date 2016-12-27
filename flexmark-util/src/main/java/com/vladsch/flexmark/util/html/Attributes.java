package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.BiConsumer;
import com.vladsch.flexmark.util.sequence.BasedSequence;

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

    public Attribute get(CharSequence key) {
        if (myAttributes == null || key == null || key.length() == 0) return null;

        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        return myAttributes.get(useKey);
    }

    public String getValue(CharSequence key) {
        if (myAttributes == null || key == null || key.length() == 0) return "";

        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        Attribute attribute = myAttributes.get(useKey);
        if (attribute == null) return "";
        return attribute.getValue();
    }

    public Attribute replaceValue(Attribute attribute) {
        return replaceValue(attribute.getName(), attribute.getValue());
    }

    public Attribute replaceValue(CharSequence key, CharSequence value) {
        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        Attribute attribute;
        if (myAttributes == null) {
            attribute = AttributeImpl.of(useKey, value);
        } else {
            attribute = myAttributes.get(useKey);
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
        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        if (myAttributes == null) {
            attribute = AttributeImpl.of(key, value);
        } else {
            attribute = myAttributes.get(useKey);
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
        if (myAttributes == null || key == null || key.length() == 0) return null;

        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        Attribute oldAttribute = myAttributes.get(useKey);
        Attribute attribute = oldAttribute.removeValue(value);
        getAttributes().put(useKey, attribute);
        return attribute;
    }

    public boolean contains(CharSequence key) {
        if (myAttributes == null || key == null || key.length() == 0) return false;

        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        return myAttributes.containsKey(useKey);
    }

    public boolean containsValue(CharSequence key, CharSequence value) {
        if (myAttributes == null) return false;
        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        Attribute attribute = myAttributes.get(useKey);
        return attribute != null && attribute.containsValue(value);
    }

    public boolean isEmpty() {
        return myAttributes == null || myAttributes.isEmpty();
    }

    public void clear() {
        myAttributes = null;
    }

    @SuppressWarnings("unchecked")
    public Set<BasedSequence> keySet() {
        return myAttributes != null ? myAttributes.keySet() : Collections.EMPTY_SET;
    }

    @SuppressWarnings("unchecked")
    public Collection<Attribute> values() {
        return myAttributes != null ? myAttributes.values() : Collections.EMPTY_LIST;
    }

    @SuppressWarnings("unchecked")
    public Set<Map.Entry<BasedSequence, Attribute>> entrySet() {
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

    public Attribute remove(CharSequence key) {
        if (myAttributes == null || key == null || key.length() == 0) return null;

        String useKey = key instanceof String ? (String) key : String.valueOf(key);
        Attribute oldAttribute = myAttributes.get(useKey);
        myAttributes.remove(useKey);
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
