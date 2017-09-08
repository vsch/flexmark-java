package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.util.ValueRunnable;
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
    /**
     * @return the Wordprocessing package
     */
    WordprocessingMLPackage getWordprocessingPackage();

    /**
     * @return the main document
     */
    MainDocumentPart getDocxDocument();

    /**
     * Get the wml object factory
     *
     * @return object factory
     */
    ObjectFactory getObjectFactory();

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
     * Set the runnable to handle adopting newly created R to a parent
     *
     * Default adopter is current P element add content
     * @param adopter adopter code
     */
    void setAdopterR(ValueRunnable<R> adopter);

    /**
     * Remove the runnable to handle adopting newly created R to a parent
     *
     * Default adopter is current P element add content
     * @param adopter adopter code
     */
    void clearAdopterR(ValueRunnable<R> adopter);

    /**
     * Create and add wrapped Text element to R element
     *
     * @param r R element to which to add wrapped text
     * @return Text element
     */
    org.docx4j.wml.Text addWrappedText(R r);


    public static final int     ABSOLUTE = 0x00;
    public static final int     ADD_LEFT = 0x01;
    public static final int     ADD_RIGHT = 0x02;
    public static final int     ADD_HANGING = 0x04;
    public static final int     ADD_HANGING_TO_LEFT = 0x08;
    public static final int     ADD_LEFT_RIGHT = (ADD_LEFT + ADD_RIGHT);
    public static final int     ADD_LEFT_HANGING = (ADD_LEFT + ADD_HANGING);
    public static final int     ADD_RIGHT_HANGING = (ADD_RIGHT + ADD_HANGING);
    public static final int     ADD_LEFT_RIGHT_HANGING = (ADD_LEFT + ADD_RIGHT + ADD_HANGING);
    public static final int     ADD_LEFT_HANGING_TO_LEFT = (ADD_LEFT + ADD_HANGING_TO_LEFT);

    /**
     * Set PPrBase Indent, left and right are additive, hanging is not
     *
     *
     * @param pPr     PPr instance for which to set indent
     * @param left    left indent (null for none)
     * @param right   right indent (null for none)
     * @param hanging hanging indent (null for none)
     * @param flags
     * @return resulting indent
     */
    PPrBase.Ind setPPrIndent(PPr pPr, BigInteger left, BigInteger right, BigInteger hanging, final int flags);

    /**
     * Add text to current P, create R and add wrapped text
     * @param text
     * @return
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
     * Add initializer for defaults for newly created R elements
     */
    void pushRPrInitializer(ValueRunnable<RPr> initializer);

    /**
     * Add initializer for defaults for newly created R elements
     */
    void popRPrInitializer(ValueRunnable<RPr> initializer);

    /**
     * run given runnable with passed in initializer
     */
    void withRPrInitializer(ValueRunnable<RPr> initializer, Runnable runnable);

    /**
     * Add initializer for defaults for newly created R elements
     */
    void pushPPrInitializer(ValueRunnable<PPr> initializer);

    /**
     * Add initializer for defaults for newly created R elements
     */
    void popPPrInitializer(ValueRunnable<PPr> initializer);

    /**
     * run given runnable with passed in initializer
     */
    void withPPrInitializer(ValueRunnable<PPr> initializer, Runnable runnable);

    void setBeforeP(final Runnable runnable);
    void clearBeforeP(final Runnable runnable);
    Runnable getBeforeP();

    void setAfterP(final Runnable runnable);
    void clearAfterP(final Runnable runnable);
    Runnable getAfterP();

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
}
