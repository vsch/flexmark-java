package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;

public class AttributeImpl implements Attribute {
    final private String name;
    final private char valueListDelimiter;
    final private char valueNameDelimiter;
    final private String value;

    private AttributeImpl(CharSequence name, CharSequence value, char valueListDelimiter, char valueNameDelimiter) {
        this.name = String.valueOf(name);
        this.valueListDelimiter = valueListDelimiter;
        this.valueNameDelimiter = valueNameDelimiter;
        this.value = value == null ? "" : String.valueOf(value);
    }

    @Override
    public MutableAttribute toMutable() {
        return MutableAttributeImpl.of(this);
    }

    @Override
    public char getValueListDelimiter() {
        return valueListDelimiter;
    }

    @Override
    public char getValueNameDelimiter() {
        return valueNameDelimiter;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isNonRendering() {
        return name.indexOf(' ') != -1 || value.isEmpty() && NON_RENDERING_WHEN_EMPTY.contains(name);
    }

    @SuppressWarnings("WeakerAccess")
    public static int indexOfValue(CharSequence value, CharSequence valueName, char valueListDelimiter, char valueNameDelimiter) {
        if (valueName.length() == 0 || value.length() == 0) return -1;

        if (valueListDelimiter == SequenceUtils.NUL) {
            return value.equals(valueName) ? 0 : -1;
        } else {
            int lastPos = 0;
            BasedSequence subSeq = BasedSequence.of(value);
            while (lastPos < value.length()) {
                int pos = subSeq.indexOf(valueName, lastPos);
                if (pos == -1) break;
                // see if it is 0 or preceded by a space, or at the end or followed by a space
                int endPos = pos + valueName.length();
                if (pos == 0
                        || value.charAt(pos - 1) == valueListDelimiter
                        || valueNameDelimiter != SequenceUtils.NUL && value.charAt(pos - 1) == valueNameDelimiter) {
                    if (endPos >= value.length()
                            || value.charAt(endPos) == valueListDelimiter
                            || valueNameDelimiter != SequenceUtils.NUL && value.charAt(endPos) == valueNameDelimiter) {
                        return pos;
                    }
                }

                lastPos = endPos + 1;
            }
        }
        return -1;
    }

    @Override
    public boolean containsValue(CharSequence value) {
        return indexOfValue(this.value, value, valueListDelimiter, valueNameDelimiter) != -1;
    }

    @Override
    public Attribute replaceValue(CharSequence value) {
        return value.equals(this.value) ? this : of(name, value, valueListDelimiter, valueNameDelimiter);
    }

    @Override
    public Attribute setValue(CharSequence value) {
        MutableAttribute mutable = toMutable().setValue(value);
        return mutable.equals(this) ? this : mutable.toImmutable();
    }

    @Override
    public Attribute removeValue(CharSequence value) {
        MutableAttribute mutable = toMutable().removeValue(value);
        return mutable.equals(this) ? this : mutable.toImmutable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (!name.equals(attribute.getName())) return false;
        return value.equals(attribute.getValue());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AttributeImpl { " +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                " }";
    }

    public static AttributeImpl of(Attribute other) {
        return of(other.getName(), other.getValue(), other.getValueListDelimiter(), other.getValueNameDelimiter());
    }

    public static AttributeImpl of(CharSequence attrName) {
        return of(attrName, attrName, SequenceUtils.NUL, SequenceUtils.NUL);
    }

    public static AttributeImpl of(CharSequence attrName, CharSequence value) {
        return of(attrName, value, SequenceUtils.NUL, SequenceUtils.NUL);
    }

    public static AttributeImpl of(CharSequence attrName, CharSequence value, char valueListDelimiter) {
        return of(attrName, value, valueListDelimiter, SequenceUtils.NUL);
    }

    public static AttributeImpl of(CharSequence attrName, CharSequence value, char valueListDelimiter, char valueNameDelimiter) {
        if (attrName.equals(CLASS_ATTR)) {
            return new AttributeImpl(attrName, value, ' ', SequenceUtils.NUL);
        } else if (attrName.equals(STYLE_ATTR)) {
            return new AttributeImpl(attrName, value, ';', ':');
        }
        return new AttributeImpl(attrName, value, valueListDelimiter, valueNameDelimiter);
    }
}
