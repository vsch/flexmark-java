package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.AnchorRefTarget;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.formatter.TranslationContext;
import com.vladsch.flexmark.formatter.TranslationHandler;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Collection;

/**
 * The context for node rendering, including configuration and functionality for the node renderer to use.
 */
public interface NodeFormatterContext extends TranslationContext {
    /**
     * @return the HTML writer to use
     */
    MarkdownWriter getMarkdown();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param out appendable to use for generated html
     * @return a new rendering context with a given appendable for its output
     */
    NodeFormatterContext getSubContext(Appendable out);

    /**
     * Render the specified node and its children using the configured renderers. This should be used to render child
     * nodes; be careful not to pass the node that is being rendered, that would result in an endless loop.
     *
     * @param node the node to render
     */
    void render(Node node);

    /**
     * Render the children of the node, used by custom renderers
     *
     * @param parent node the children of which are to be rendered
     */
    void renderChildren(Node parent);

    /**
     * @return current rendering phase
     */
    FormattingPhase getFormattingPhase();

    /**
     * Get the current rendering context {@link DataHolder}. These are the options passed or set on the {@link Formatter#builder()} or passed to {@link Formatter#builder(DataHolder)}.
     * To get the document options you should use {@link #getDocument()} as the data holder.
     *
     * @return the current renderer options {@link DataHolder}
     */
    DataHolder getOptions();

    /**
     * @return the {@link FormatterOptions} for the context.
     */
    FormatterOptions getFormatterOptions();

    /**
     * @return the {@link Document} node of the current context
     */
    Document getDocument();

    /**
     * @return the current node being rendered
     */
    Node getCurrentNode();

    /**
     * Get iterable of nodes of given types, in order of their appearance in the document tree, depth first traversal.
     * Only node classes returned by {@link NodeFormatter#getNodeClasses()} of all loaded extensions
     * will be available to formatters.
     * <p>
     * {@link CoreNodeFormatter} registers {@link com.vladsch.flexmark.ast.RefNode}
     * if {@link Formatter#REFERENCE_SORT} is set to
     * {@link ElementPlacementSort#SORT_UNUSED_LAST} so that
     *
     * @param classes node classes to return
     * @return iterable
     */
    Iterable<? extends Node> nodesOfType(Class<?>[] classes);
    Iterable<? extends Node> nodesOfType(Collection<Class<?>> classes);
    /**
     * Get iterable of nodes of given types, in reverse order of their appearance in the document tree, depth first traversal.
     * Only node classes returned by {@link NodeFormatter#getNodeClasses()} of all loaded extensions
     * will be available to formatters.
     * <p>
     * {@link CoreNodeFormatter} registers {@link com.vladsch.flexmark.ast.RefNode}
     * if {@link Formatter#REFERENCE_SORT} is set to
     * {@link ElementPlacementSort#SORT_UNUSED_LAST} so that
     *
     * @param classes node classes to return
     * @return iterable
     */
    Iterable<? extends Node> reversedNodesOfType(Class<?>[] classes);
    Iterable<? extends Node> reversedNodesOfType(Collection<Class<?>> classes);
}
