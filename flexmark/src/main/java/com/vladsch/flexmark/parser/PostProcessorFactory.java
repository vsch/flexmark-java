package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.dependency.Dependent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface PostProcessorFactory extends Function<Document, PostProcessor>, Dependent {
    /**
     * A map of nodes of interest as keys and values a set of classes, if implemented by an ancestors then the node should be excluded from processing by this processor
     * i.e. DoNotDecorate.class if the processor adds links so that existing links will be ignored.
     *
     * @return a map of desired node types mapped to a set of ancestors under which the post processor does not process the block
     */
    @Nullable Map<Class<?>, Set<Class<?>>> getNodeTypes();

    /**
     * @param document for which to create the post processor
     * @return post processor for the document
     */
    @Override
    @NotNull PostProcessor apply(@NotNull Document document);
}
