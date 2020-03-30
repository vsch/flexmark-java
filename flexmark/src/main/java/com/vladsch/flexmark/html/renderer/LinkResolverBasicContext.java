package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

public interface LinkResolverBasicContext {
    /**
     * Get the current rendering context {@link DataHolder}. These are the options passed or set on the {@link HtmlRenderer#builder()} or passed to {@link HtmlRenderer#builder(DataHolder)}.
     * To get the document options you should use {@link #getDocument()} as the data holder.
     *
     * @return the current renderer options {@link DataHolder}
     */
    @NotNull DataHolder getOptions();
    
    /**
     * @return the {@link Document} node of the current context
     */
    @NotNull Document getDocument();
}
