package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.misc.Immutable;
import com.vladsch.flexmark.util.sequence.SequenceUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public interface Attribute extends Immutable<Attribute, MutableAttribute> {
    String CLASS_ATTR = "class";
    String ID_ATTR = "id";
    String LINK_STATUS_ATTR = "Link Status";
    String NAME_ATTR = "name";
    String STYLE_ATTR = "style";
    String TITLE_ATTR = "title";
    String TARGET_ATTR = "target";
    Attribute NO_FOLLOW = AttributeImpl.of("rel", "nofollow");
    Set<String> NON_RENDERING_WHEN_EMPTY = new HashSet<>(Arrays.asList(CLASS_ATTR, ID_ATTR, NAME_ATTR, STYLE_ATTR));

    @Deprecated
    char NUL = SequenceUtils.NUL;

    String getName();
    String getValue();
    char getValueListDelimiter();
    char getValueNameDelimiter();
    boolean isNonRendering();

    /**
     * See if the attribute contains the value (if attribute has list delimiter set) or is equal to the value if no list delimiter is set
     *
     * @param value name part of the attribute value list or the value if the attribute does not have a value list delimiter
     * @return true if the attribute contains the valueName
     */
    boolean containsValue(CharSequence value);

    // create a new attribute, if needed
    /**
     * Replace the complete value of this attribute by a new value
     *
     * @param value new value
     * @return new attribute or same attribute if nothing changed or attribute is mutable
     */
    Attribute replaceValue(CharSequence value);

    /**
     * Add a new value or values depending on list and name delimiter settings and value content
     * <p>
     * If the attribute does not have a list delimiter then its value will be set
     * to the given value.
     * <p>
     * If the attribute has a list delimiter but not name delimiter then value
     * will be split by list delimiter and all values will be added to the attribute's
     * value list. New ones added at the end, old ones left as is.
     * <p>
     * If the attribute has a list delimiter and a name delimiter then value will
     * be split by list delimiter and the name portion of each value will
     * be used to find duplicates whose value will be replaced. New ones added at the
     * end, old ones left where they are but with a new value.
     *
     * @param value value or list of values (if attribute has a list delimiter and name delimiter) to change
     * @return new attribute or same attribute if nothing changed or attribute is mutable
     */
    Attribute setValue(CharSequence value);

    /**
     * Add a new value or values depending on list and name delimiter settings and value content.
     * <p>
     * If the attribute does not have a list delimiter and its value is equal
     * to the given value then its value is set to empty
     * <p>
     * If the attribute has a list delimiter but not name delimiter then value
     * will be split by list delimiter and any values in attribute's value list
     * will be removed
     * <p>
     * If the attribute has a list delimiter and a name delimiter then value will
     * be split by list delimiter and only the name portion of each value will
     * be used for removal from the attribute's value list
     *
     * @param value value or list of values (if attribute has a list delimiter and name delimiter) to remove
     * @return new attribute or same attribute if nothing changed or attribute is mutable
     */
    Attribute removeValue(CharSequence value);
}
