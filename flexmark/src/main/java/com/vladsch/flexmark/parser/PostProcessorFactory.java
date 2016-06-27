package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.node.Document;

public interface PostProcessorFactory {
    /**
     * @param document for which to create the post processor
     * @return post processor for the document
     */
    PostProcessor create(Document document);
}
