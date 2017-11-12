package com.vladsch.flexmark.docx.converter.util;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.FootnotesPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import java.math.BigInteger;
import java.util.List;

public interface DocxContext<T> extends DocxContextFrameProvider<T> {

    DocxHelper getHelper();
    void setBlockFormatProvider(BlockFormatProvider<T> formatProvider);
    void setRunFormatProvider(RunFormatProvider<T> formatProvider);
    void setContentContainer(ContentContainer container);
    void setParaContainer(ParaContainer container);
    void setRunContainer(RunContainer container);
    BlockFormatProvider<T> getBlockFormatProvider(T node);
    RunFormatProvider<T> getRunFormatProvider(T node);
    /**
     * Get current format provider
     *
     * @return format provider
     */
    BlockFormatProvider<T> getBlockFormatProvider();
    RunFormatProvider<T> getRunFormatProvider();

    void addBlankLine(int size, String styleId);
    void addBlankLine(long size, String styleId);
    void addBlankLine(BigInteger size, String styleId);
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

    PPrBase.PStyle createPStyle(String style);
    /**
     * Get a new P element for the current block
     *
     * @return current P element or null if none is open
     */
    P createP();

    P createP(String style);
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
     * @return Text element
     */
    Text addWrappedText();
    void addPageBreak();
    void addBreak(STBrType breakType);

    List<Object> getContent();

    RPr addBold();
    RPr getRPr();
    Color createColor();
    HpsMeasure createHpsMeasure(long val);
    /**
     * Add text to current P, create R and add wrapped text
     *
     * @param text text to add
     * @return text element
     */
    Text text(String text);

    /**
     * Get a paragraph style of given name
     *
     * @param styleName name of the style
     * @return style
     */
    Style getStyle(String styleName);

    /**
     * Get an external hyperlink relationship for the given url
     *
     * @param url url
     * @return relationship
     */
    Relationship getHyperlinkRelationship(String url);

    /**
     * Add a line break to current R
     */
    void addLineBreak();

    /**
     * Run within a context, after run the format providers and containers will be restored.
     * <p>
     * Use when you need to create a container or format provider but only for a part of the
     * node rendering process.
     *
     * @param runnable code to run
     */
    void contextFramed(Runnable runnable);
    void renderFencedCodeLines(CharSequence... lines);
    void renderFencedCodeLines(List<? extends CharSequence> lines);

    /**
     * Get or create FootnotesPart for the document
     *
     * @return footnotes part
     * @throws Docx4JException if cannot create part
     */
    FootnotesPart getFootnotesPart() throws Docx4JException;

    /**
     * Add footnote and return it
     *
     * @param footnoteID re-use footnote id or 0 if new footnote
     * @return footnote element
     * @throws Docx4JException thrown if cannot get or create footnotes part of the document
     */
    CTFtnEdn addFootnote(final BigInteger footnoteID) throws Docx4JException;
}
