package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @NotNull Node parse(@NotNull BasedSequence input);

    /**
     * Parse the specified input text into a tree of nodes.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the text to parse
     * @return the root node
     */
    @NotNull Node parse(@NotNull String input);

    /**
     * Parse the specified reader into a tree of nodes. The caller is responsible for closing the reader.
     * <p>
     * Note that this method is thread-safe (a new parser state is used for each invocation).
     *
     * @param input the reader to parse
     * @return the root node
     * @throws IOException when reading throws an exception
     */
    @NotNull Node parseReader(@NotNull Reader input) throws IOException;

    /**
     * Get Options for parsing
     *
     * @return DataHolder for options
     */
    @Nullable DataHolder getOptions();

    /**
     * Transfer reference definition between documents
     *
     * @param document        destination document
     * @param included        source document
     * @param onlyIfUndefined true if only should transfer references not already defined in the destination document,
     *                        false to transfer all,
     *                        null to use repository's KEEP_TYPE to make the determination (if KEEP_FIRST then only transfer if undefined,
     * @return true if any references were transferred
     */
    boolean transferReferences(@NotNull Document document, @NotNull Document included, @Nullable Boolean onlyIfUndefined);
}
