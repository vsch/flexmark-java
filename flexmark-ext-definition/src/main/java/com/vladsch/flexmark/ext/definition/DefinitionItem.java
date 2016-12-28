
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.*;

/**
 * A Definition item block node, starts with : followed by any content like a list item
 */
public class DefinitionItem extends ListItem {
    public DefinitionItem() {

    }

    @Override
    public boolean isParagraphWrappingDisabled() {
        return false;
    }
}
