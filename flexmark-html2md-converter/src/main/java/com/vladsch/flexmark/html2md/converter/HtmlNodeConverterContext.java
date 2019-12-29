package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.NodeContext;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.LineAppendable;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
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
    HtmlNodeConverterContext getSubContext();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param options options to use for the context (only options which do not affect the context construction will be used)
     *
     * @return a new rendering context with a given appendable for its output
     */
    HtmlNodeConverterContext getSubContext(DataHolder options);

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param options options to use for the context (only options which do not affect the context construction will be used)
     * @param builder sequence builder to user for appended text for tracking original base offsets
     *
     * @return a new rendering context with a given appendable for its output
     */
    HtmlNodeConverterContext getSubContext(DataHolder options, @NotNull SequenceBuilder builder);

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
     * @param parent           node the children of which are to be rendered
     * @param outputAttributes true if attributes should be output
     * @param prePopAction     runnable to run before popping state
     */
    void renderChildren(Node parent, boolean outputAttributes, Runnable prePopAction);

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
    DataHolder getOptions();

    /**
     * @return the {@link HtmlConverterOptions} for the context.
     */
    HtmlConverterOptions getHtmlConverterOptions();

    /**
     * @return the {@link Document} node of the current context
     */
    Document getDocument();

    /**
     * @return the {@link com.vladsch.flexmark.util.ast.Document} node of the current context
     */
    com.vladsch.flexmark.util.ast.Document getForDocument();

    HtmlConverterState getState();

    HashMap<String, Reference> getReferenceUrlToReferenceMap();

    HashSet<Reference> getExternalReferences();

    boolean isTrace();

    Stack<HtmlConverterState> getStateStack();

    void setTrace(boolean trace);

    com.vladsch.flexmark.util.ast.Node parseMarkdown(String markdown);

    Reference getOrCreateReference(String url, String text, String title);

    ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode);

    ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode);

    /**
     * @return the current node being rendered
     */
    Node getCurrentNode();

    void pushState(Node parent);

    void popState(LineAppendable out);

    void excludeAttributes(String... excludes);

    void processAttributes(Node node);

    int outputAttributes(LineAppendable out, String initialSep);

    void transferIdToParent();

    void transferToParentExcept(String... excludes);

    void transferToParentOnly(String... includes);

    Node peek();

    Node peek(int skip);

    Node next();

    void skip();

    Node next(int skip);

    void skip(int skip);

    // processing related helpers
    void processUnwrapped(Node element);

    void processWrapped(Node node, Boolean isBlock, boolean escapeMarkdown);

    void processTextNodes(Node node, boolean stripIdAttribute);

    void processTextNodes(Node node, boolean stripIdAttribute, CharSequence wrapText);

    void processTextNodes(
            Node node,
            boolean stripIdAttribute,
            CharSequence textPrefix,
            CharSequence textSuffix
    );

    void wrapTextNodes(
            Node node,
            CharSequence wrapText,
            boolean needSpaceAround
    );

    String processTextNodes(Node node);

    void appendOuterHtml(Node node);

    boolean isInlineCode();

    void setInlineCode(boolean inlineCode);

    String escapeSpecialChars(String text);

    String prepareText(String text);

    String prepareText(String text, boolean inCode);

    void inlineCode(Runnable inlineRunnable);

    void processConditional(ExtensionConversion extensionConversion, Node node, Runnable processNode);

    void renderDefault(Node node);
}
