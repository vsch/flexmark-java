package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;
import org.jetbrains.annotations.NotNull;

public interface MergeContextConsumer {
  void accept(@NotNull TranslationContext docContext, @NotNull Document doc, int index);
}
