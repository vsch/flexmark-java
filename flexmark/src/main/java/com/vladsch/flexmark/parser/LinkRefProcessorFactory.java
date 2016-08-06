package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.util.ComputableFactory;

/**
 * Processing of elements which are based on a link ref: [] or ![]
 * This includes footnote references [^...] and wiki links [[...]]
 */
public interface LinkRefProcessorFactory extends ComputableFactory<LinkRefProcessor, Document> {
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
     * Create a link ref processor for the document
     * @param document on which the processor will work
     * @return link ref processor
     */
    @Override LinkRefProcessor create(Document document);
}
