package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.formatter.internal.FormatterOptions;
import com.vladsch.flexmark.html.renderer.HtmlIdGeneratorFactory;
import com.vladsch.flexmark.util.options.DataHolder;

public interface TranslationHandlerFactory extends TranslationContext {
    TranslationHandler create(DataHolder options, FormatterOptions formatterOptions, HtmlIdGeneratorFactory idGeneratorFactory);
}
