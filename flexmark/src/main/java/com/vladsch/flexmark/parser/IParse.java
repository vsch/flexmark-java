package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Node;

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
     * @param options
     * @return
     */
    IParse withOptions(DataHolder options);
}
