package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.PPrBase.Spacing;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DocxContextImpl<T> implements DocxContext<T>, BlockFormatProvider<T>, RunFormatProvider<T>, ParaContainer, RunContainer {
    protected final WordprocessingMLPackage myPackage;
    protected final MainDocumentPart myDocumentPart;
    protected final ObjectFactory myFactory;
    protected final DocxHelper myDocxHelper;

    // docx stuff
    protected final HashMap<String, Relationship> myHyperlinks;
    protected HashMap<T, BlockFormatProvider<T>> myBlockFormatProviders;
    protected HashMap<T, RunFormatProvider<T>> myRunFormatProviders;

    protected BlockFormatProvider<T> myBlockFormatProvider;
    protected RunFormatProvider<T> myRunFormatProvider;
    protected ParaContainer myParaContainer;
    protected RunContainer myRunContainer;

    public DocxContextImpl(WordprocessingMLPackage out) {
        myPackage = out;
        myFactory = new ObjectFactory();
        myRunFormatProvider = this;
        myDocxHelper = new DocxHelper(myPackage, myFactory);
        myRunContainer = this;
        myHyperlinks = new HashMap<String, Relationship>();
        myParaContainer = this;
        myDocumentPart = out.getMainDocumentPart();
        myBlockFormatProviders = new HashMap<T, BlockFormatProvider<T>>();
        myRunFormatProviders = new HashMap<T, RunFormatProvider<T>>();
        myBlockFormatProvider = this;
    }

    @Override
    public T getContextFrame() {
        return null;
    }

    @Override
    public T getProviderFrame() {
        return getContextFrame();
    }

    @Override
    public void setParaContainer(final ParaContainer container) {
        myParaContainer = container;
    }

    @Override
    public void setBlockFormatProvider(final BlockFormatProvider<T> formatProvider) {
        myBlockFormatProviders.put(getContextFrame(), formatProvider);
        myBlockFormatProvider = formatProvider;

        // let it initialize, closing done after rendering
        formatProvider.open();
    }

    @Override
    public BlockFormatProvider<T> getBlockFormatProvider(final T node) {
        return myBlockFormatProviders.get(node);
    }

    @Override
    public BlockFormatProvider<T> getBlockFormatProvider() {
        return myBlockFormatProvider;
    }

    @Override
    public void setRunContainer(final RunContainer container) {
        myRunContainer = container;
    }

    @Override
    public void setRunFormatProvider(final RunFormatProvider<T> formatProvider) {
        myRunFormatProviders.put(getContextFrame(), formatProvider);
        myRunFormatProvider = formatProvider;

        // let it initialize, closing done after rendering
        formatProvider.open();
    }

    @Override
    public RunFormatProvider<T> getRunFormatProvider(final T node) {
        return myRunFormatProviders.get(node);
    }

    @Override
    public RunFormatProvider<T> getRunFormatProvider() {
        return myRunFormatProvider;
    }

    // document format provider
    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public BlockFormatProvider<T> getBlockParent() {
        return null;
    }

    @Override
    public RunFormatProvider<T> getRunParent() {
        return null;
    }

    @Override
    public String getStyleId() {
        return BlockFormatProvider.DEFAULT_STYLE;
    }

    @Override
    public Style getStyle() {
        return getStyle(BlockFormatProvider.DEFAULT_STYLE);
    }

    @Override
    public void adjustPPrForFormatting(final PPr pP) {

    }

    @Override
    public void getPPr(final PPr pPr) {

    }

    @Override
    public void getParaRPr(final RPr rPr) {

    }

    @Override
    public void addP(final P p) {
        myDocumentPart.getContent().add(p);
    }

    @Override
    public void addR(final R r) {
        getP().getContent().add(r);
    }

    @Override
    public void getRPr(final RPr rPr) {

    }

    @Override
    public P getLastP() {
        final List<Object> content = myDocumentPart.getContent();
        if (content == null || content.size() == 0) return null;
        final Object o = content.get(content.size() - 1);
        return o instanceof P ? (P) o :null;
    }

    @Override
    public R getLastR() {
        final P p = myParaContainer.getLastP();
        if (p == null) return null;
        final List<Object> content = p.getContent();
        if (content == null || content.size() == 0) return null;
        final Object o = content.get(content.size() - 1);
        return o instanceof R ? (R) o :null;
    }

    @Override
    public PStyle createPStyle(String style) {
        P p = getP();
        PPr pPr = p.getPPr();

        // Create object for pStyle if one does not already exist
        PPrBase.PStyle basePStyle = myFactory.createPPrBasePStyle();
        pPr.setPStyle(basePStyle);
        basePStyle.setVal(style);
        return basePStyle;
    }

    @Override
    public P createP() {
        return createP(null);
    }

    @Override
    public P createP(String style) {
        P p = myFactory.createP();
        PPr pPr = myFactory.createPPr();

        p.setPPr(pPr);

        myParaContainer.addP(p);

        if (style == null) {
            myBlockFormatProvider.getPPr(pPr);
            myBlockFormatProvider.adjustPPrForFormatting(pPr);

            if (StyleUtil.isEmpty(p.getPPr())) {
                p.setPPr(null);
            } else if (StyleUtil.isEmpty(p.getPPr().getRPr())) {
                p.getPPr().setRPr(null);
            }
        } else {
            createPStyle(style);
        }
        return p;
    }

    @Override
    public P getP() {
        P p = myParaContainer.getLastP();
        if (p != null) {
            return p;
        }
        return createP();
    }

    @Override
    public R createR() {
        P p = getP();
        R r = myFactory.createR();
        RPr rPr = myFactory.createRPr();
        r.setRPr(rPr);

        myRunContainer.addR(r);

        if (myBlockFormatProvider != this || myRunFormatProvider != this) {
            RPr blockRPr = myFactory.createRPr();
            myBlockFormatProvider.getParaRPr(blockRPr);

            myRunFormatProvider.getRPr(rPr);
            StyleUtil.apply(rPr, blockRPr);
            StyleUtil.apply(blockRPr, rPr);

            // minimize the rPr
            final RStyle rStyle = rPr.getRStyle();
            if (rStyle != null && rStyle.getVal() != null) {
                Style style = getStyle(rStyle.getVal());
                if (style != null) {
                    RPr styleRPr = myDocxHelper.getExplicitRPr(style.getRPr(), p.getPPr());
                    myDocxHelper.keepDiff(rPr, styleRPr);
                }
            }

            final PStyle pStyle = p.getPPr().getPStyle();
            if (pStyle != null && pStyle.getVal() != null) {
                Style style = getStyle(pStyle.getVal());
                if (style != null) {
                    RPr styleRPr = myDocxHelper.getExplicitRPr(style.getRPr(), p.getPPr());
                    myDocxHelper.keepDiff(rPr, styleRPr);
                }
            }

            if (StyleUtil.isEmpty(rPr.getRFonts())) {
                // Style util adds empty destination before checking if there is anything to copy
                rPr.setRFonts(null);
            }

            if (StyleUtil.isEmpty(rPr)) {
                r.setRPr(null);
            }
        }
        return r;
    }

    @Override
    public R getR() {
        R r = myRunContainer.getLastR();
        if (r == null) {
            return createR();
        } else {
            return r;
        }
    }

    @Override
    public RPr getRPr() {
        R r = getR();
        RPr rPr = r.getRPr();

        if (rPr == null) {
            rPr = myFactory.createRPr();
            r.setRPr(rPr);
        }
        return rPr;
    }

    @Override
    public DocxHelper getHelper() {
        return myDocxHelper;
    }

    @Override
    public ObjectFactory getFactory() {
        return myFactory;
    }

    @Override
    public void addBlankLine(final int size, final String styleId) {
        addBlankLine(BigInteger.valueOf(size), styleId);
    }

    @Override
    public void addBlankLine(final long size, final String styleId) {
        addBlankLine(BigInteger.valueOf(size), styleId);
    }

    @Override
    public void addBlankLine(final BigInteger size, final String styleId) {
        if (size.compareTo(BigInteger.ZERO) > 0) {
            // now add empty for spacing
            P p = myFactory.createP();
            PPr pPr = myFactory.createPPr();
            p.setPPr(pPr);

            myParaContainer.addP(p);

            if (styleId != null && !styleId.isEmpty()) {
                if (pPr.getPStyle() == null) {
                    PStyle pStyle = myDocxHelper.myFactory.createPPrBasePStyle();
                    pPr.setPStyle(pStyle);
                }
                pPr.getPStyle().setVal(styleId);
            }

            // Create new Spacing which we override
            Spacing spacing = myFactory.createPPrBaseSpacing();
            pPr.setSpacing(spacing);

            spacing.setBefore(BigInteger.ZERO);
            spacing.setAfter(BigInteger.ZERO);
            spacing.setLine(size);
            spacing.setLineRule(STLineSpacingRule.EXACT);
        }
    }

    @Override
    public void addBlankLines(final int count) {
        if (count > 0) {
            // now add empty for spacing
            PPr pPr = myFactory.createPPr();
            myBlockFormatProvider.getPPr(pPr);

            PPr explicitPPr = myDocxHelper.getExplicitPPr(pPr);
            final ParaRPr rPr = explicitPPr.getRPr();
            BigInteger size = rPr.getSz().getVal().max(rPr.getSzCs().getVal());

            addBlankLine(size.multiply(BigInteger.valueOf(count)), null);
        }
    }

    @Override
    public Text addWrappedText() {
        // Create object for t (wrapped in JAXBElement)
        Text text = myFactory.createText();
        JAXBElement<Text> textWrapped = myFactory.createRT(text);
        getR().getContent().add(textWrapped);
        return text;
    }

    @Override
    public void addLineBreak() {
        addBreak(null);
    }

    @Override
    public void contextFramed(Runnable runnable) {
        myBlockFormatProviders.put(getContextFrame(), myBlockFormatProvider);
        myRunFormatProviders.put(getContextFrame(), myRunFormatProvider);
        BlockFormatProvider<T> oldRenderingBlockFormatProvider = myBlockFormatProvider;
        RunFormatProvider<T> oldRenderingRunFormatProvider = myRunFormatProvider;
        ParaContainer oldRenderingParaContainer = myParaContainer;
        RunContainer oldRenderingRunContainer = myRunContainer;
        T oldNode = getContextFrame();

        runnable.run();

        if (oldNode != getContextFrame()) {
            RunFormatProvider<T> runFormatProvider = myRunFormatProviders.remove(oldNode);

            if (runFormatProvider != oldRenderingRunFormatProvider) {
                runFormatProvider.close();
            }
        }
        myRunFormatProvider = oldRenderingRunFormatProvider;

        if (oldNode != getContextFrame()) {
            BlockFormatProvider<T> blockFormatProvider = myBlockFormatProviders.remove(oldNode);
            if (blockFormatProvider != oldRenderingBlockFormatProvider) {
                blockFormatProvider.close();
            }
        }

        myBlockFormatProvider = oldRenderingBlockFormatProvider;
        myRunContainer = oldRenderingRunContainer;
        myParaContainer = oldRenderingParaContainer;
    }

    @Override
    public void addPageBreak() {
        addBreak(STBrType.PAGE);
    }

    @Override
    public void addBreak(STBrType breakType) {
        // Create object for br
        R r = myFactory.createR();
        Br br = myFactory.createBr();
        if (breakType != null) br.setType(breakType);
        r.getContent().add(br);

        myRunContainer.addR(r);
    }

    @Override
    public RPr addBold() {
        RPr rPr = getRPr();
        rPr.setB(myFactory.createBooleanDefaultTrue());
        rPr.setBCs(myFactory.createBooleanDefaultTrue());
        return rPr;
    }

    @Override
    public Color createColor() {
        RPr rPr = getRPr();
        Color color = myFactory.createColor();
        color.setVal("BB002F");
        rPr.setColor(color);
        return color;
    }

    @Override
    public HpsMeasure createHpsMeasure(long val) {
        RPr rPr = getRPr();
        HpsMeasure hpsmeasure = myFactory.createHpsMeasure();
        rPr.setSz(hpsmeasure);
        hpsmeasure.setVal(BigInteger.valueOf(val));
        return hpsmeasure;
    }

    public void createHorizontalLine() {
        P p = createP(HORIZONTAL_LINE_STYLE);
        R r = createR();
    }

    @Override
    public Text text(final String text) {
        R r = createR();
        Text textElem = addWrappedText();
        textElem.setValue(text);
        textElem.setSpace(RunFormatProvider.SPACE_PRESERVE);
        return textElem;
    }

    @Override
    public WordprocessingMLPackage getPackage() {
        return myPackage;
    }

    @Override
    public MainDocumentPart getDocxDocument() {
        return myDocumentPart;
    }

    @Override
    public Style getStyle(final String styleName) {
        return myDocumentPart.getStyleDefinitionsPart().getStyleById(styleName);
    }

    @Override
    public Relationship getHyperlinkRelationship(String url) {
        Relationship rel = myHyperlinks.get(url);
        if (rel != null) return rel;

        // We need to add a relationship to word/_rels/document.xml.rels
        // but since its external, we don't use the
        // usual wordMLPackage.getMainDocumentPart().addTargetPart
        // mechanism
        org.docx4j.relationships.ObjectFactory factory = new org.docx4j.relationships.ObjectFactory();

        rel = factory.createRelationship();
        rel.setType(Namespaces.HYPERLINK);
        rel.setTarget(url);
        rel.setTargetMode("External");

        myDocumentPart.getRelationshipsPart().addRelationship(rel);
        myHyperlinks.put(url, rel);
        return rel;
    }

    @Override
    public void renderFencedCodeLines(final CharSequence... lines) {
        renderFencedCodeLines(Arrays.asList(lines));
    }

    @Override
    public void renderFencedCodeLines(final List<? extends CharSequence> lines) {
        contextFramed(new Runnable() {
            @Override
            public void run() {
                setBlockFormatProvider(new FencedCodeBlockFormatProvider<T>(DocxContextImpl.this));
                createP();

                int[] leadColumns = new int[lines.size()];
                int minSpaces = Integer.MAX_VALUE;
                int i = 0;
                for (CharSequence line : lines) {
                    leadColumns[i] = BasedSequenceImpl.of(line).countLeadingColumns(0, " \t");
                    minSpaces = Utils.min(minSpaces, leadColumns[i]);
                    i++;
                }

                ArrayList<BasedSequence> trimmedLines = new ArrayList<BasedSequence>();
                i = 0;
                for (CharSequence line : lines) {
                    StringBuilder sb = new StringBuilder();

                    int spaces = leadColumns[i] - minSpaces;
                    while (spaces-- > 0) sb.append(' ');
                    sb.append(BasedSequenceImpl.of(line).trim());

                    // Create object for p
                    text(sb.toString());

                    i++;

                    if (i < lines.size()) {
                        addLineBreak();
                    }
                }
            }
        });
    }
}
