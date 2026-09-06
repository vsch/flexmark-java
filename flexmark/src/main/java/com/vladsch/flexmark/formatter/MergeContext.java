package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;
import org.jspecify.annotations.Nullable;

public interface MergeContext {
    Document getDocument(TranslationContext context);
    void forEachPrecedingDocument(@Nullable Document document, MergeContextConsumer consumer);
}
