package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.internal.DocxHelper;
import com.vladsch.flexmark.docx.converter.internal.DocxRendererOptions;
import com.vladsch.flexmark.docx.converter.internal.DocxRendererPhase;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import java.math.BigInteger;
import java.util.Collection;

/**
 * The context for node rendering, including configuration and functionality for the node renderer to use.
 */
public interface DocxRendererContext extends LinkResolverContext {

    DocxHelper getHelper();

    void setBlockFormatProvider(final BlockFormatProvider formatProvider);
    void setRunFormatProvider(final RunFormatProvider formatProvider);

    void setParaContainer(final ParaContainer container);
    void setRunContainer(final RunContainer container);

    BlockFormatProvider getBlockFormatProvider(final Node node);
    RunFormatProvider getRunFormatProvider(final Node node);

    /**
     * Get current format provider
     * @return format provider
     */
    BlockFormatProvider getBlockFormatProvider();
    RunFormatProvider getRunFormatProvider();

    void addBlankLine(int size, final String styleId);
    void addBlankLine(long size, final String styleId);
    void addBlankLine(BigInteger size, final String styleId);

    void addBlankLines(int count);

    /**
     * @return the Wordprocessing package
     */
    WordprocessingMLPackage getPackage();

    /**
     * @return the main document
     */
    MainDocumentPart getDocxDocument();

    /**
     * Get the wml object factory
     *
     * @return object factory
     */
    ObjectFactory getFactory();

    /**
     * Get a new P element for the current block
     *
     * @return current P element or null if none is open
     */
    P createP();

    /**
     * Get the last P element for the current block
     *
     * @return current P element or null if none is open
     */
    P getP();

    /**
     * Create R element, with an RPr and add it to current P element
     *
     * @return R element
     */
    R createR();

    /**
     * Get last R element of current P or create a new one
     *
     * @return R element
     */
    R getR();

    /**
     * Create and add wrapped Text element to R element
     *
     * @param r R element to which to add wrapped text
     * @return Text element
     */
    org.docx4j.wml.Text addWrappedText(R r);

    void addPageBreak();
    void addBreak(STBrType breakType);
    /**
     * Add text to current P, create R and add wrapped text
     * @param text  text to add
     * @return text element
     */
    Text text(String text);

    /**
     * generates a new boolean default true value
     *
     * @return new instance of boolean default true
     */
    BooleanDefaultTrue getBooleanDefaultTrue();

    /**
     * generates a new boolean default false value
     *
     * @return new instance of boolean default true
     */
    BooleanDefaultFalse getBooleanDefaultFalse();

    /**
     * Get a paragraph style of given name
     *
     * @param styleName name of the style
     * @return style
     */
    Style getStyle(final String styleName);

    /**
     * @return current rendering phase
     */
    DocxRendererPhase getPhase();

    /**
     * @return the {@link DocxRendererOptions} for the context.
     */
    DocxRendererOptions getDocxRendererOptions();

    /**
     * Get an external hyperlink relationship for the given url
     *
     * @param url url
     *
     * @return relationship
     */
    Relationship getHyperlinkRelationship(String url);

    /**
     * Get iterable of nodes of given types, in order of their appearance in the document tree, depth first traversal.
     * Only node classes returned by {@link NodeDocxRenderer#getNodeClasses()} of all loaded extensions
     * will be available to formatters.
     * <p>
     * @param classes node classes to return
     * @return iterable
     */
    Iterable<? extends Node> nodesOfType(Class<?>[] classes);
    Iterable<? extends Node> nodesOfType(Collection<Class<?>> classes);
    /**
     * Get iterable of nodes of given types, in reverse order of their appearance in the document tree, depth first traversal.
     * Only node classes returned by {@link NodeDocxRenderer#getNodeClasses()} of all loaded extensions
     * will be available to formatters.
     *
     * @param classes node classes to return
     * @return iterable
     */
    Iterable<? extends Node> reversedNodesOfType(Class<?>[] classes);
    Iterable<? extends Node> reversedNodesOfType(Collection<Class<?>> classes);

    /**
     * Add a line break to current R
     */
    void addLineBreak();
}
