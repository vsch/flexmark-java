package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.ComputableFactory;
import com.vladsch.flexmark.internal.util.dependency.Dependent;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Set;

public interface PostProcessorFactory extends ComputableFactory<PostProcessor, Document>, Dependent<PostProcessorFactory> {
    /**
     * Node types that this post processor processes
     *
     * @return set of block node types
     */
    Set<Class<? extends Node>> getNodeTypes();

    /**
     * Node types whose descendants should be excluded from processing by this processor 
     * i.e. DoNotLinkify.class if the processor adds links so that existing links will be ignored.
     *
     * @return set of block node types
     */
    Set<Class<? extends Node>> getExcludeDescendantsOfTypes();

    /**
     * @param document for which to create the post processor
     * @return post processor for the document
     */
    @Override
    PostProcessor create(Document document);
}
