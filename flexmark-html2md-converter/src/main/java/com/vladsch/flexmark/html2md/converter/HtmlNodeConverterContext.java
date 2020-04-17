package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.NodeContext;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * The context for node rendering, including configuration and functionality for the node renderer to use.
 */
public interface HtmlNodeConverterContext extends NodeContext<Node, HtmlNodeConverterContext> {
    /**
     * @return the {@link LineAppendable} writer to use
     */
    HtmlMarkdownWriter getMarkdown();

    void delegateRender();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull HtmlNodeConverterContext getSubContext();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param options options to use for the context (only options which do not affect the context construction will be used)
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull HtmlNodeConverterContext getSubContext(@Nullable DataHolder options);

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param options options to use for the context (only options which do not affect the context construction will be used)
     * @param builder sequence builder to user for appended text for tracking original base offsets
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull HtmlNodeConverterContext getSubContext(@Nullable DataHolder options, @NotNull ISequenceBuilder<?, ?> builder);

    /**
     * Render the specified node and its children using the configured renderers. This should be used to render child
     * nodes; be careful not to pass the node that is being rendered, that would result in an endless loop.
     *
     * @param node the node to render
     */
    void render(@NotNull Node node);

    /**
     * Render the children of the node, used by custom renderers
     *
     * @param parent           node the children of which are to be rendered
     * @param outputAttributes true if attributes should be output
     * @param prePopAction     runnable to run before popping state
     */
    void renderChildren(@NotNull Node parent, boolean outputAttributes, @Nullable Runnable prePopAction);

    /**
     * @return current rendering phase
     */
    HtmlConverterPhase getFormattingPhase();

    /**
     * Get the current rendering context {@link DataHolder}. These are the options passed or set on the {@link FlexmarkHtmlConverter#builder()} or passed to {@link FlexmarkHtmlConverter#builder(DataHolder)}.
     * To get the document options you should use {@link #getDocument()} as the data holder.
     *
     * @return the current renderer options {@link DataHolder}
     */
    @NotNull DataHolder getOptions();

    /**
     * @return the {@link HtmlConverterOptions} for the context.
     */
    @NotNull HtmlConverterOptions getHtmlConverterOptions();

    /**
     * @return the {@link Document} node of the current context
     */
    @NotNull Document getDocument();

    /**
     * @return the {@link com.vladsch.flexmark.util.ast.Document} node of the current context
     */
    @Nullable com.vladsch.flexmark.util.ast.Document getForDocument();

    @Nullable HtmlConverterState getState();

    @NotNull HashMap<String, Reference> getReferenceUrlToReferenceMap();

    @NotNull HashSet<Reference> getExternalReferences();

    boolean isTrace();

    @NotNull Stack<HtmlConverterState> getStateStack();

    void setTrace(boolean trace);

    @NotNull com.vladsch.flexmark.util.ast.Node parseMarkdown(@NotNull String markdown);

    @Nullable Reference getOrCreateReference(@NotNull String url, @NotNull String text, @Nullable String title);

    @NotNull ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, @Nullable Boolean urlEncode);

    @NotNull ResolvedLink resolveLink(@NotNull LinkType linkType, @NotNull CharSequence url, @Nullable Attributes attributes, @Nullable Boolean urlEncode);

    /**
     * @return the current node being rendered
     */
    @Nullable Node getCurrentNode();

    void pushState(@NotNull Node parent);

    void popState(@Nullable LineAppendable out);

    void excludeAttributes(String... excludes);

    void processAttributes(@NotNull Node node);

    int outputAttributes(@NotNull LineAppendable out, @NotNull String initialSep);

    void transferIdToParent();

    void transferToParentExcept(String... excludes);

    void transferToParentOnly(String... includes);

    @Nullable Node peek();

    @Nullable Node peek(int skip);

    @Nullable Node next();

    void skip();

    @Nullable Node next(int skip);

    void skip(int skip);

    // processing related helpers
    void processUnwrapped(@NotNull Node element);

    void processWrapped(@NotNull Node node, @Nullable Boolean isBlock, boolean escapeMarkdown);

    void processTextNodes(@NotNull Node node, boolean stripIdAttribute);

    void processTextNodes(@NotNull Node node, boolean stripIdAttribute, @NotNull CharSequence wrapText);

    void processTextNodes(
            @NotNull Node node,
            boolean stripIdAttribute,
            @Nullable CharSequence textPrefix,
            @Nullable CharSequence textSuffix
    );

    void wrapTextNodes(
            @NotNull Node node,
            @NotNull CharSequence wrapText,
            boolean needSpaceAround
    );

    @NotNull String processTextNodes(@NotNull Node node);

    void appendOuterHtml(@NotNull Node node);

    boolean isInlineCode();

    void setInlineCode(boolean inlineCode);

    @NotNull String escapeSpecialChars(@NotNull String text);

    @NotNull String prepareText(@NotNull String text);

    @NotNull String prepareText(@NotNull String text, boolean inCode);

    void inlineCode(@NotNull Runnable inlineRunnable);

    void processConditional(@NotNull ExtensionConversion extensionConversion, @NotNull Node node, @NotNull Runnable processNode);

    void renderDefault(@NotNull Node node);
}
