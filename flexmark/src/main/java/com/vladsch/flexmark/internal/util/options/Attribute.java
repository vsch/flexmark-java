package com.vladsch.flexmark.internal.util.options;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vlad on 2016-07-13.
 */
public class Attribute {
    public static final Attribute NO_FOLLOW = new Attribute("rel", "nofollow");
    public static final Set<String> NON_RENDERING_WHEN_EMPTY = new HashSet<>(Arrays.asList("class", "id", "name"));

    final private String myName;
    final private String myValue;

    public Attribute(String name, String value) {
        myName = name;
        myValue = value;
    }

    public String getName() {
        return myName;
    }

    public String getValue() {
        return myValue;
    }

    protected int indexOfValue(String value) {
        if (value.isEmpty() || myValue.isEmpty()) return -1; 
        
        int lastPos = 0;
        while (lastPos < myValue.length()) {
            int pos = myValue.indexOf(value, lastPos);
            if (pos == -1) break;
            // see if it is 0 or preceded by a space, or at the end or followed by a space
            int endPos = pos + value.length();
            if (pos == 0 || myValue.charAt(pos - 1) == ' ') {
                if (endPos >= myValue.length() || myValue.charAt(endPos) == ' ') {
                    return pos;
                }
            }

            lastPos = endPos + 1;
        }
        return -1;
    }
    
    public boolean isNonRendering() {
        return getValue().isEmpty() && NON_RENDERING_WHEN_EMPTY.contains(getName());
    }

    public Attribute replaceValue(String value) {
        if (myValue.equals(value)) return this; 
        return new Attribute(myName, value);
    }

    public Attribute addValue(String value) {
        if (value.isEmpty() || indexOfValue(value) >= 0) return this; 
        return new Attribute(myName, myValue.isEmpty() ? value : myValue + " " + value);
    }

    public Attribute removeValue(String value) {
        int pos = indexOfValue(value);
        if (pos >= 0) {
            int endPos = pos + value.length();
            if (endPos >= myValue.length()) {
                if (pos > 0) pos--;
                return new Attribute(myName, myValue.substring(0, pos));
            }
            if (pos == 0) {
                if (endPos < myValue.length()) endPos++; 
                return new Attribute(myName, myValue.substring(endPos));
            }
            return new Attribute(myName, myValue.substring(0, pos-1) + myValue.substring(endPos));
        }
        return this;
    }
    
    public boolean containsValue(String value) {
        return indexOfValue(value) != -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (!myName.equals(attribute.myName)) return false;
        return myValue.equals(attribute.myValue);
    }

    @Override
    public int hashCode() {
        int result = myName.hashCode();
        result = 31 * result + myValue.hashCode();
        return result;
    }
}
