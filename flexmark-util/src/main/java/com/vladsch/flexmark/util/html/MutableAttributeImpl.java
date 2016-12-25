package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.BiConsumer;
import com.vladsch.flexmark.util.mappers.NullCharacterMapper;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class MutableAttributeImpl implements MutableAttribute {
    private final String myName;
    private final char myValueListDelimiter;
    private final char myValueNameDelimiter;

    private String myValue;
    private LinkedHashMap<String, String> myValues;

    private MutableAttributeImpl(String name, String value, char valueListDelimiter, char valueNameDelimiter) {
        myName = name;
        myValueListDelimiter = valueListDelimiter;
        myValueNameDelimiter = valueNameDelimiter;
        myValue = value == null ? "" : value;
        myValues = null;
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
    public Attribute toImmutable() {
        return AttributeImpl.of(this);
    }

    @Override
    public MutableAttribute toMutable() {
        return this;
    }

    @Override
    public MutableAttribute copy() {
        return MutableAttributeImpl.of(this);
    }

    @Override
    public String getName() {
        return myName;
    }

    @Override
    public String getValue() {
        if (myValue == null) {
            myValue = valueFromMap();
        }
        return myValue;
    }

    public void resetToValuesMap() {
        if (myValues == null) throw new IllegalStateException("resetToValuesMap called when myValues is null");
        myValue = null;
    }

    @SuppressWarnings("WeakerAccess")
    protected Map<String, String> getValueMap() {
        if (myValues == null) {
            myValues = new LinkedHashMap<>();
            if (myValueListDelimiter != NUL) {
                if (!myValue.isEmpty()) {
                    int lastPos = 0;
                    while (lastPos < myValue.length()) {
                        int pos = myValue.indexOf(myValueListDelimiter, lastPos);

                        int endPos = pos == -1 ? myValue.length() : pos;
                        if (lastPos < endPos) {
                            final String value = myValue.substring(lastPos, endPos);
                            int namePos = myValueNameDelimiter != NUL ? value.indexOf(myValueNameDelimiter) : -1;

                            if (namePos == -1) {
                                myValues.put(value, "");
                            } else {
                                myValues.put(value.substring(0, namePos), value.substring(namePos + 1));
                            }
                        }
                        if (pos == -1) break;
                        lastPos = endPos + 1;
                    }
                }
            } else {
                myValues.put(myValue, "");
            }
        }
        return myValues;
    }

    /**
     * Return the attribute value string by splicing the values of the map using myValueListDelimiter and myValueNameDelimiter
     * with replacements of the given name/value if provided. If the name is not empty and value is empty then this will be
     * removed from the final string
     *
     * @return string for value of this attribute from map
     */
    @SuppressWarnings("WeakerAccess")
    protected String valueFromMap() {
        if (myValueListDelimiter != NUL) {
            StringBuilder sb = new StringBuilder();
            if (myValueNameDelimiter != NUL) {
                for (Map.Entry<String, String> entry : myValues.entrySet()) {
                    if (!entry.getKey().isEmpty() && !entry.getValue().isEmpty()) {
                        sb.append(entry.getKey()).append(myValueNameDelimiter).append(entry.getValue()).append(myValueListDelimiter);
                    }
                }
            } else {
                for (String key : myValues.keySet()) {
                    if (!key.isEmpty()) {
                        sb.append(key).append(myValueListDelimiter);
                    }
                }
            }
            if (myValueListDelimiter == ' ' && sb.length() > 0) {
                sb.delete(sb.length() - 1, sb.length());
            }
            myValue = sb.toString();
        } else {
            myValue = myValues == null || myValues.isEmpty() ? "" : myValues.keySet().iterator().next();
        }
        return myValue;
    }

    @Override
    public boolean isNonRendering() {
        return myName.indexOf(' ') != -1 || myValue.isEmpty() && NON_RENDERING_WHEN_EMPTY.contains(myName);
    }

    public MutableAttributeImpl replaceValue(String value) {
        if (myValue == null || value == null || !myValue.equals(value)) {
            myValue = value == null ? "" : value;
            myValues = null;
        }
        return this;
    }

    public MutableAttributeImpl setValue(String value) {
        if (myValueListDelimiter != NUL) {
            if (value != null && !value.isEmpty()) {
                final Map<String, String> valueMap = getValueMap();

                forEachValue(value, new BiConsumer<String, String>() {
                    @Override
                    public void accept(final String itemName, final String itemValue) {
                        if (myValueNameDelimiter != NUL && itemValue.isEmpty()) {
                            valueMap.remove(itemName);
                        } else {
                            valueMap.put(itemName, itemValue);
                        }
                    }
                });

                myValue = null;
            }
        } else {
            if (myValue == null || value == null || !myValue.equals(value)) {
                myValue = value == null ? "" : value;
                myValues = null;
            }
        }

        return this;
    }

    private void forEachValue(final String value, BiConsumer<String, String> consumer) {
        int lastPos = 0;
        while (lastPos < value.length()) {
            int pos = value.indexOf(myValueListDelimiter, lastPos);

            int endPos = pos == -1 ? value.length() : pos;
            if (lastPos < endPos) {
                final String valueItem = value.substring(lastPos, endPos).trim();
                if (!valueItem.isEmpty()) {
                    final int namePos = myValueNameDelimiter == NUL ? -1 : valueItem.indexOf(myValueNameDelimiter);
                    final String itemName = namePos == -1 ? valueItem : valueItem.substring(0, namePos);
                    final String itemValue = namePos == -1 ? "" : valueItem.substring(namePos + 1);

                    consumer.accept(itemName, itemValue);
                }
            }

            if (pos == -1) break;
            lastPos = endPos + 1;
        }
    }

    public MutableAttributeImpl removeValue(String value) {
        if (myValueListDelimiter != NUL) {
            if (value != null && !value.isEmpty()) {
                final Map<String, String> valueMap = getValueMap();
                final boolean[] removed = { false };

                forEachValue(value, new BiConsumer<String, String>() {
                    @Override
                    public void accept(final String itemName, final String itemValue) {
                        if (valueMap.remove(itemName) != null) {
                            removed[0] = true;
                        }
                    }
                });

                if (removed[0]) myValue = null;
            }
        } else {
            if (myValue == null || !myValue.equals(value)) {
                myValue = "";
                myValues = null;
            }
        }
        return this;
    }

    public boolean containsValue(String value) {
        return AttributeImpl.indexOfValue(myValue, value, myValueListDelimiter, myValueNameDelimiter) != -1;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (!myName.equals(attribute.getName())) return false;
        if (!getValue().equals(attribute.getValue())) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = myName.hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MutableAttributeImpl { " +
                "myName='" + myName + '\'' +
                ", myValue='" + getValue() + '\'' +
                " }";
    }

    public static MutableAttributeImpl of(Attribute other) {
        return of(other.getName(), other.getValue(), other.getValueListDelimiter(), other.getValueNameDelimiter());
    }

    public static MutableAttributeImpl of(String attrName) {
        return of(attrName, attrName, NUL, NUL);
    }

    public static MutableAttributeImpl of(String attrName, String value) {
        return of(attrName, value, NUL, NUL);
    }

    public static MutableAttributeImpl of(String attrName, String value, char valueListDelimiter) {
        return of(attrName, value, valueListDelimiter, NUL);
    }

    public static MutableAttributeImpl of(String attrName, String value, char valueListDelimiter, char valueNameDelimiter) {
        if (CLASS_ATTR.equals(attrName)) {
            return new MutableAttributeImpl(attrName, value, ' ', NUL);
        } else if (STYLE_ATTR.equals(attrName)) {
            return new MutableAttributeImpl(attrName, value, ';', ':');
        }
        return new MutableAttributeImpl(attrName, value, valueListDelimiter, valueNameDelimiter);
    }
}
