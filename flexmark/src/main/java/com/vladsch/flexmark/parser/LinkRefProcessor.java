package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Node;

/**
 * Processing of elements which are based on a link ref: [] or ![]
 * This includes footnote references [^...] and wiki links [[...]]
 */
public interface LinkRefProcessor {
    /**
     * Whether the image ref is desired, if not then ! will be stripped off the prefix and treated as plain text
     *
     * @return true if ! is part of the desired element, false otherwise
     */
    boolean getWantExclamationPrefix();

    /**
     * Whether the element consists of nested [] inside the link ref. For example Wiki link [[]] processor would return 1
     * Only immediately nested [] are considered. [[  ]] is nesting 1, [ [ ]] is not considered
     * <p>
     * When >0 then preview of next characters is used and if they will match then inner reference will not be created to
     * allow outer one to match the desired element
     *
     * @return nesting level for references, >0 for nesting
     */
    int getBracketNestingLevel();

    /**
     * Test whether the element matches desired one. For processors that allow nesting this will be called one additional.
     * time for each nesting level. Last call for the actual match, all others to pre-match to prevent a non-nested element from being
     * created.
     *
     * @param nodeChars text to match, including [] or ![]
     * @return true if it is a match
     */
    boolean isMatch(BasedSequence nodeChars);

    /**
     * Create the desired element that was previously matched with isMatch
     *
     * @param nodeChars    char sequence from which to create the node
     * @return Node element to be inserted into the tree
     */
    Node createNode(BasedSequence nodeChars);

    /**
     * Adjust child nodes' text as needed when some of the link ref text was used in the opening or closing sequence of the node
     * or if the children are not desired then removeIndex them.
     */
    void adjustInlineText(Node node);
}
