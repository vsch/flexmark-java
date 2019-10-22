package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Processing of elements which are based on a link ref: [] or ![]
 * This includes footnote references [^...] and wiki links [[...]]
 */
public interface LinkRefProcessorFactory extends Function<Document, LinkRefProcessor> {
    /**
     * Whether the image ref is desired, if not then ! will be stripped off the prefix and treated as plain text
     *
     * @param options options
     * @return true if ! is part of the desired element, false otherwise
     */
    boolean getWantExclamationPrefix(@NotNull DataHolder options);

    /**
     * Whether the element consists of nested [] inside the link ref. For example Wiki link [[]] processor would return 1
     * Only immediately nested [] are considered. [[  ]] is nesting 1, [ [ ]] is not considered
     * <p>
     * When {@code >0} then preview of next characters is used and if they will match then inner reference will not be created to
     * allow outer one to match the desired element
     *
     * @param options options
     * @return nesting level for references, {@code >0} for nesting
     */
    int getBracketNestingLevel(@NotNull DataHolder options);

    /**
     * Create a link ref processor for the document
     *
     * @param document on which the processor will work
     * @return link ref processor
     */
    @Override
    @NotNull LinkRefProcessor apply(@NotNull Document document);
}
