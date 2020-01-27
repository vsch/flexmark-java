package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.SequenceUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class MutableAttributeImpl implements MutableAttribute {
    final private String name;
    final private char valueListDelimiter;
    final private char valueNameDelimiter;

    private String value;
    private LinkedHashMap<String, String> values;

    private MutableAttributeImpl(CharSequence name, CharSequence value, char valueListDelimiter, char valueNameDelimiter) {
        this.name = String.valueOf(name);
        this.valueListDelimiter = valueListDelimiter;
        this.valueNameDelimiter = valueNameDelimiter;
        this.value = value == null ? "" : String.valueOf(value);
        values = null;
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
        return name;
    }

    @Override
    public String getValue() {
        if (value == null) {
            value = valueFromMap();
        }
        return value;
    }

    public void resetToValuesMap() {
        if (values == null) throw new IllegalStateException("resetToValuesMap called when values is null");
        value = null;
    }

    @SuppressWarnings("WeakerAccess")
    protected Map<String, String> getValueMap() {
        if (values == null) {
            values = new LinkedHashMap<>();
            if (valueListDelimiter != SequenceUtils.NUL) {
                if (!value.isEmpty()) {
                    int lastPos = 0;
                    while (lastPos < value.length()) {
                        int pos = value.indexOf(valueListDelimiter, lastPos);

                        int endPos = pos == -1 ? value.length() : pos;
                        if (lastPos < endPos) {
                            String value = this.value.substring(lastPos, endPos);
                            int namePos = valueNameDelimiter != SequenceUtils.NUL ? value.indexOf(valueNameDelimiter) : -1;

                            if (namePos == -1) {
                                values.put(value, "");
                            } else {
                                values.put(value.substring(0, namePos), value.substring(namePos + 1));
                            }
                        }
                        if (pos == -1) break;
                        lastPos = endPos + 1;
                    }
                }
            } else {
                values.put(value, "");
            }
        }
        return values;
    }

    /**
     * Return the attribute value string by splicing the values of the map using valueListDelimiter and valueNameDelimiter
     * with replacements of the given name/value if provided. If the name is not empty and value is empty then this will be
     * removed from the final string
     *
     * @return string for value of this attribute from map
     */
    @SuppressWarnings("WeakerAccess")
    protected String valueFromMap() {
        if (valueListDelimiter != SequenceUtils.NUL) {
            StringBuilder sb = new StringBuilder();
            if (valueNameDelimiter != SequenceUtils.NUL) {
                String sep = "";
                String del = String.valueOf(valueListDelimiter);
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    if (!entry.getKey().isEmpty()/* && !entry.getValue().isEmpty()*/) {
                        sb.append(sep);
                        sep = del;
                        sb.append(entry.getKey()).append(valueNameDelimiter).append(entry.getValue());
                    }
                }
            } else {
                String sep = "";
                String del = String.valueOf(valueListDelimiter);
                for (String key : values.keySet()) {
                    if (!key.isEmpty()) {
                        sb.append(sep);
                        sb.append(key);
                        sep = del;
                    }
                }
            }
            value = sb.toString();
        } else {
            value = values == null || values.isEmpty() ? "" : values.keySet().iterator().next();
        }
        return value;
    }

    @Override
    public boolean isNonRendering() {
        return name.indexOf(' ') != -1 || value.isEmpty() && NON_RENDERING_WHEN_EMPTY.contains(name);
    }

    public MutableAttributeImpl replaceValue(CharSequence value) {
        String useValue = value == null ? "" : String.valueOf(value);
        if (this.value == null || value == null || !this.value.equals(useValue)) {
            this.value = useValue;
            values = null;
        }
        return this;
    }

    public MutableAttributeImpl setValue(CharSequence value) {
        if (valueListDelimiter != SequenceUtils.NUL) {
            if (value != null && value.length() != 0) {
                Map<String, String> valueMap = getValueMap();

                forEachValue(value, (itemName, itemValue) -> {
                    if (valueNameDelimiter != SequenceUtils.NUL && itemValue.isEmpty()) {
                        valueMap.remove(itemName);
                    } else {
                        valueMap.put(itemName, itemValue);
                    }
                });

                this.value = null;
            }
        } else {
            if (this.value == null || !this.value.contentEquals(value)) {
                this.value = value == null ? "" : String.valueOf(value);
                values = null;
            }
        }

        return this;
    }

    private void forEachValue(CharSequence value, BiConsumer<String, String> consumer) {
        String useValue = value == null ? "" : String.valueOf(value);
        int lastPos = 0;
        while (lastPos < useValue.length()) {
            int pos = useValue.indexOf(valueListDelimiter, lastPos);

            int endPos = pos == -1 ? useValue.length() : pos;
            if (lastPos < endPos) {
                String valueItem = useValue.substring(lastPos, endPos).trim();
                if (!valueItem.isEmpty()) {
                    int namePos = valueNameDelimiter == SequenceUtils.NUL ? -1 : valueItem.indexOf(valueNameDelimiter);
                    String itemName = namePos == -1 ? valueItem : valueItem.substring(0, namePos);
                    String itemValue = namePos == -1 ? "" : valueItem.substring(namePos + 1);

                    consumer.accept(itemName, itemValue);
                }
            }

            if (pos == -1) break;
            lastPos = endPos + 1;
        }
    }

    public MutableAttributeImpl removeValue(CharSequence value) {
        if (valueListDelimiter != SequenceUtils.NUL) {
            if (value != null && value.length() != 0) {
                Map<String, String> valueMap = getValueMap();
                boolean[] removed = { false };

                forEachValue(value, (itemName, itemValue) -> {
                    if (valueMap.remove(itemName) != null) {
                        removed[0] = true;
                    }
                });

                if (removed[0]) this.value = null;
            }
        } else {
            if (this.value == null || !this.value.contentEquals(value)) {
                this.value = "";
                values = null;
            }
        }
        return this;
    }

    public boolean containsValue(CharSequence value) {
        return AttributeImpl.indexOfValue(this.value, value, valueListDelimiter, valueNameDelimiter) != -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attribute)) return false;

        Attribute attribute = (Attribute) o;

        if (!name.equals(attribute.getName())) return false;
        return getValue().equals(attribute.getValue());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MutableAttributeImpl { " +
                "name='" + name + '\'' +
                ", value='" + getValue() + '\'' +
                " }";
    }

    public static MutableAttributeImpl of(Attribute other) {
        return of(other.getName(), other.getValue(), other.getValueListDelimiter(), other.getValueNameDelimiter());
    }

    public static MutableAttributeImpl of(CharSequence attrName) {
        return of(attrName, attrName, SequenceUtils.NUL, SequenceUtils.NUL);
    }

    public static MutableAttributeImpl of(CharSequence attrName, CharSequence value) {
        return of(attrName, value, SequenceUtils.NUL, SequenceUtils.NUL);
    }

    public static MutableAttributeImpl of(CharSequence attrName, CharSequence value, char valueListDelimiter) {
        return of(attrName, value, valueListDelimiter, SequenceUtils.NUL);
    }

    public static MutableAttributeImpl of(CharSequence attrName, CharSequence value, char valueListDelimiter, char valueNameDelimiter) {
        if (CLASS_ATTR.contentEquals(attrName)) {
            return new MutableAttributeImpl(attrName, value, ' ', SequenceUtils.NUL);
        } else if (STYLE_ATTR.contentEquals(attrName)) {
            return new MutableAttributeImpl(attrName, value, ';', ':');
        }
        return new MutableAttributeImpl(attrName, value, valueListDelimiter, valueNameDelimiter);
    }
}
