package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.ComputableFactory;
import com.vladsch.flexmark.internal.util.dependency.Dependent;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Map;
import java.util.Set;

public interface PostProcessorFactory extends ComputableFactory<PostProcessor, Document>, Dependent<PostProcessorFactory> {
    /**
     * A map of nodes of interest as keys and values a set of classes, if implemented by an ancestors then the node should be excluded from processing by this processor
     * i.e. DoNotDecorate.class if the processor adds links so that existing links will be ignored.
     *
     * @return a map of desired node types mapped to a set of ancestors under which the post processor does not process the block
     */
    Map<Class<? extends Node>, Set<Class<?>>> getNodeTypes();

    /**
     * @param document for which to create the post processor
     * @return post processor for the document
     */
    @Override
    PostProcessor create(Document document);
}
