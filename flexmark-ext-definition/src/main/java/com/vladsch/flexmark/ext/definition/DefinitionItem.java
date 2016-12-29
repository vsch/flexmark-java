
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.options.DataHolder;

/**
 * A Definition item block node, starts with : followed by any content like a list item
 */
public class DefinitionItem extends ListItem {
    public DefinitionItem() {

    }

    @Override
    public boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options) {
        boolean isDisabled = super.isParagraphWrappingDisabled(node, listOptions, options);
        return isDisabled;
    }
}
