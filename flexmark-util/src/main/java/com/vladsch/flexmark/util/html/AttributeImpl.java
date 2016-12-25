package com.vladsch.flexmark.util.html;

public class AttributeImpl implements Attribute {
    private final String myName;
    private final char myValueListDelimiter;
    private final char myValueNameDelimiter;
    private final String myValue;

    private AttributeImpl(String name, String value, char valueListDelimiter, char valueNameDelimiter) {
        myName = name;
        myValueListDelimiter = valueListDelimiter;
        myValueNameDelimiter = valueNameDelimiter;
        myValue = value;
    }

    @Override
    public MutableAttribute toMutable() {
        return MutableAttributeImpl.of(this);
    }

    @Override
    public char getValueListDelimiter() {
        return myValueListDelimiter;
    }

    @Override
    public char getValueNameDelimiter() {
        return myValueNameDelimiter;
    }

    @Override
    public String getName() {
        return myName;
    }

    @Override
    public String getValue() {
        return myValue;
    }

    @Override
    public boolean isNonRendering() {
        return myName.indexOf(' ') != -1 || myValue.isEmpty() && NON_RENDERING_WHEN_EMPTY.contains(myName);
    }

    @SuppressWarnings("WeakerAccess")
    public static int indexOfValue(final String value, final String valueName, final char valueListDelimiter, final char valueNameDelimiter) {
        if (valueName.isEmpty() || value.isEmpty()) return -1;

        if (valueListDelimiter == NUL) {
            return value.equals(valueName) ? 0 : -1;
        } else {
            int lastPos = 0;
            while (lastPos < value.length()) {
                int pos = value.indexOf(valueName, lastPos);
                if (pos == -1) break;
                // see if it is 0 or preceded by a space, or at the end or followed by a space
                int endPos = pos + valueName.length();
                if (pos == 0
                        || value.charAt(pos - 1) == valueListDelimiter
                        || valueNameDelimiter != NUL && value.charAt(pos - 1) == valueNameDelimiter) {
                    if (endPos >= value.length()
                            || value.charAt(endPos) == valueListDelimiter
                            || valueNameDelimiter != NUL && value.charAt(endPos) == valueNameDelimiter) {
                        return pos;
                    }
                }

                lastPos = endPos + 1;
            }
        }
        return -1;
    }

    @Override
    public boolean containsValue(String value) {
        return indexOfValue(myValue, value, myValueListDelimiter, myValueNameDelimiter) != -1;
    }

    @Override
    public Attribute replaceValue(final String value) {
        return myValue.equals(value) ? this : of(myName, value, myValueListDelimiter, myValueNameDelimiter);
    }

    @Override
    public Attribute setValue(final String value) {
        MutableAttribute mutable = toMutable().setValue(value);
        return mutable.equals(this) ? this : mutable.toImmutable();
    }

    @Override
    public Attribute removeValue(final String value) {
        MutableAttribute mutable = toMutable().removeValue(value);
        return mutable.equals(this) ? this : mutable.toImmutable();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (!myName.equals(attribute.getName())) return false;
        if (!myValue.equals(attribute.getValue())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = myName.hashCode();
        result = 31 * result + myValue.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AttributeImpl { " +
                "myName='" + myName + '\'' +
                ", myValue='" + myValue + '\'' +
                " }";
    }

    public static AttributeImpl of(Attribute other) {
        return of(other.getName(), other.getValue(), other.getValueListDelimiter(), other.getValueNameDelimiter());
    }

    public static AttributeImpl of(String attrName) {
        return of(attrName, attrName, NUL, NUL);
    }

    public static AttributeImpl of(String attrName, String value) {
        return of(attrName, value, NUL, NUL);
    }

    public static AttributeImpl of(String attrName, String value, char valueListDelimiter) {
        return of(attrName, value, valueListDelimiter, NUL);
    }

    public static AttributeImpl of(String attrName, String value, char valueListDelimiter, char valueNameDelimiter) {
        if (CLASS_ATTR.equals(attrName)) {
            return new AttributeImpl(attrName, value, ' ', NUL);
        } else if (STYLE_ATTR.equals(attrName)) {
            return new AttributeImpl(attrName, value, ';', ':');
        }
        return new AttributeImpl(attrName, value, valueListDelimiter, valueNameDelimiter);
    }
}
