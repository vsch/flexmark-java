package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.formatter.internal.CoreNodeFormatter;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.NodeContext;
import com.vladsch.flexmark.util.format.TrackedOffsetList;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.Collection;

/**
 * The context for node rendering, including configuration and functionality for the node renderer
 * to use.
 */
public interface NodeFormatterContext
    extends NodeContext<Node, NodeFormatterContext>,
        TranslationContext,
        LinkResolverContext,
        ExplicitAttributeIdProvider {
  /**
   * @return the HTML writer to use
   */
  MarkdownWriter getMarkdown();

  /**
   * Render the specified node and its children using the configured renderers. This should be used
   * to render child nodes; be careful not to pass the node that is being rendered, that would
   * result in an endless loop.
   *
   * @param node the node to render
   */
  @Override
  void render(Node node);

  /**
   * Render the children of the node, used by custom renderers
   *
   * @param parent node the children of which are to be rendered
   */
  @Override
  void renderChildren(Node parent);

  /**
   * @return current rendering phase
   */
  FormattingPhase getFormattingPhase();

  /** pass node rendering to previously registered handler */
  void delegateRender();

  /**
   * Get the current rendering context {@link DataHolder}. These are the options passed to {@link
   * Formatter#builder(DataHolder)}. To get the document options you should use {@link
   * #getDocument()} as the data holder.
   *
   * @return the current renderer options {@link DataHolder}
   */
  @Override
  DataHolder getOptions();

  /**
   * @return the {@link FormatterOptions} for the context.
   */
  FormatterOptions getFormatterOptions();

  /**
   * @return the {@link Document} node of the current context
   */
  @Override
  Document getDocument();

  /**
   * @return predicate for prefix chars which compact like block quote prefix char
   */
  CharPredicate getBlockQuoteLikePrefixPredicate();

  /**
   * @return char sequence of all prefix chars which compact like block quote prefix char
   */
  BasedSequence getBlockQuoteLikePrefixChars();

  /**
   * @return tracked offset list
   */
  TrackedOffsetList getTrackedOffsets();

  boolean isRestoreTrackedSpaces();

  /**
   * NOTE: parser can only use a contiguous sequence, not segmented. Therefore, the AST offsets and
   * base sequence from AST nodes has always an index into sequence equal to the offset. This
   * sequence is set to not {@link BasedSequence#NULL} when the format sequence used for tracked
   * offsets is not contiguous and TrackedOffset.offset is an offset from this sequence and need to
   * be converted to index into this sequence to be used as an offset into AST sequence for offset
   * conversion
   *
   * @return original sequence used for tracked offsets.
   */
  BasedSequence getTrackedSequence();

  /**
   * Get iterable of nodes of given types, in order of their appearance in the document tree, depth
   * first traversal. Only node classes returned by {@link NodeFormatter#getNodeClasses()} of all
   * loaded extensions will be available to formatters.
   *
   * <p>{@link CoreNodeFormatter} registers {@link com.vladsch.flexmark.ast.RefNode} if {@link
   * Formatter#REFERENCE_SORT} is set to {@link ElementPlacementSort#SORT_UNUSED_LAST} so that
   *
   * @param classes node classes to return
   * @return iterable
   */
  Iterable<? extends Node> nodesOfType(Class<?>[] classes);

  Iterable<? extends Node> nodesOfType(Collection<Class<?>> classes);

  /**
   * Get iterable of nodes of given types, in reverse order of their appearance in the document
   * tree, depth first traversal. Only node classes returned by {@link
   * NodeFormatter#getNodeClasses()} of all loaded extensions will be available to formatters.
   *
   * <p>{@link CoreNodeFormatter} registers {@link com.vladsch.flexmark.ast.RefNode} if {@link
   * Formatter#REFERENCE_SORT} is set to {@link ElementPlacementSort#SORT_UNUSED_LAST} so that
   *
   * @param classes node classes to return
   * @return iterable
   */
  Iterable<? extends Node> reversedNodesOfType(Class<?>[] classes);

  Iterable<? extends Node> reversedNodesOfType(Collection<Class<?>> classes);
}
