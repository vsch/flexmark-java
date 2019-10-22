package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

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
     * When {@code >0} then preview of next characters is used and if they will match then inner reference will not be created to
     * allow outer one to match the desired element
     *
     * @return desired nesting level for references, {@code >0} for nested, 0 for not nested
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
    boolean isMatch(@NotNull BasedSequence nodeChars);

    /**
     * Create the desired element that was previously matched with isMatch
     *
     * @param nodeChars char sequence from which to create the node
     * @return Node element to be inserted into the tree
     */
    @NotNull Node createNode(@NotNull BasedSequence nodeChars);

    /**
     * Adjust child nodes' text as needed when some of the link ref text was used in the opening or closing sequence of the node
     * or if the children are not desired then removeIndex them.
     *
     * @param document document, can be used to get parsing options
     * @param node     node whose inline text is to be adjusted to reflect some of the text having been used as part of the opener and/or closer sequence.
     * @return adjusted sequence to use for this node's child text
     */
    @NotNull BasedSequence adjustInlineText(@NotNull Document document, @NotNull Node node);

    /**
     * Allows the delimiter processor to allow/disallow other delimiters in its inline text
     *
     * @param chars    character sub-sequence to test
     * @param document document, can be used to get options
     * @param node     delimited node created by this processor  @return true if delimiters are allowed in this part of the node's text
     */
    boolean allowDelimiters(@NotNull BasedSequence chars, @NotNull Document document, @NotNull Node node);

    /**
     * Allows the processor to adjust nodes' elements after all delimiters have been processed inside the inlined text
     *
     * @param document document, can be used to get parsing options
     * @param node     node whose elements can be adjusted
     */
    void updateNodeElements(@NotNull Document document, @NotNull Node node);
}
