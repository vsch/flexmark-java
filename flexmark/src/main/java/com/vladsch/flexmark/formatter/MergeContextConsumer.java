package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;

public interface MergeContextConsumer {
  void accept(TranslationContext docContext, Document doc, int index);
}
