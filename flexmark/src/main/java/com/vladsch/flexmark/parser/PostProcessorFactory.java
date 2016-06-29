package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.internal.util.ComputableFactory;
import com.vladsch.flexmark.node.Document;

public interface PostProcessorFactory extends ComputableFactory<PostProcessor, Document> {
    /**
     * @param document for which to create the post processor
     * @return post processor for the document
     */
    @Override PostProcessor create(Document document);
}
