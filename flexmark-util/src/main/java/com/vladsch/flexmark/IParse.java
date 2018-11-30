package com.vladsch.flexmark;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.IOException;
import java.io.Reader;

/**
 * Interface to generic parser for RenderingTestCase customizations
 */
public interface IParse {
    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    Node parse(BasedSequence input);

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    Node parse(String input);

    /**
     * Parse the specified reader into a tree of nodes. The caller is responsible for closing the reader.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the reader to parse
     * @return the root node
     * @throws IOException when reading throws an exception
     */
    Node parseReader(Reader input) throws IOException;

    /**
     * Return an IParse instance configured for passed in options
     * @param options options to use for a new instance
     * @return a new instance of IParse implementation with the given options applied
     */
    IParse withOptions(DataHolder options);

    /**
     * Get Options for parsing
     * @return DataHolder for options
     */
    DataHolder getOptions();
    boolean transferReferences(Document document, Document included);
}
