package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.dependency.Dependent;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Set;

public interface DependentPostProcessorFactory extends PostProcessorFactory, Dependent<DependentPostProcessorFactory> {
    /**
     * Node types that this post processor processes
     *
     * @return set of block node types
     */
    Set<Class<? extends Node>> getNodeTypes();

    /**
     * @param document for which to create the post processor
     * @return post processor for the document
     */
    PostProcessor create(Document document);
}
