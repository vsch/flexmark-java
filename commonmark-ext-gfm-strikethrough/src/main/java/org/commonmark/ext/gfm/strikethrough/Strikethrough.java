package org.commonmark.ext.gfm.strikethrough;

import org.commonmark.node.CustomNode;
import org.commonmark.node.Segmented;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class Strikethrough extends CustomNode implements Segmented {

    private static final String DELIMITER = "~~";

    @Override
    public String getOpeningDelimiter() {
        return DELIMITER;
    }

    @Override
    public String getClosingDelimiter() {
        return DELIMITER;
    }
}
