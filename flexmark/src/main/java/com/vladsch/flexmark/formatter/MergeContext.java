package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;

public interface MergeContext {

  Document getDocument(TranslationContext context);

  void forEachPrecedingDocument(Document document, MergeContextConsumer consumer);
}
