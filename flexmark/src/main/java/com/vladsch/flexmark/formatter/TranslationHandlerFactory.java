package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.html.renderer.HtmlIdGeneratorFactory;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

public interface TranslationHandlerFactory extends TranslationContext {
    @NotNull TranslationHandler create(@NotNull DataHolder options, @NotNull HtmlIdGeneratorFactory idGeneratorFactory);
}
