package com.vladsch.flexmark.docx.converter.util;

import com.vladsch.flexmark.docx.converter.DocxRendererOptions;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.DocumentSettingsPart;
import org.docx4j.openpackaging.parts.WordprocessingML.FootnotesPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.openpackaging.parts.relationships.RelationshipsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.PPrBase.Spacing;
import org.docx4j.wml.R.ContinuationSeparator;
import org.docx4j.wml.R.FootnoteRef;
import org.docx4j.wml.R.Separator;

import jakarta.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class DocxContextImpl<T> implements DocxContext<T>, BlockFormatProvider<T>, RunFormatProvider<T>, ParaContainer, RunContainer, ContentContainer {
    protected final WordprocessingMLPackage myPackage;
    protected final MainDocumentPart myDocumentPart;
    protected final ObjectFactory myFactory;
    protected final DocxHelper myDocxHelper;
    protected final DocxRendererOptions myRendererOptions;
    protected final DataHolder options;

    // docx stuff
    protected final HashMap<RelationshipsPart, HashMap<String, Relationship>> myHyperlinks;
    protected HashMap<T, BlockFormatProvider<T>> myBlockFormatProviders;
    protected HashMap<T, RunFormatProvider<T>> myRunFormatProviders;

    protected BlockFormatProvider<T> myBlockFormatProvider;
    protected RunFormatProvider<T> myRunFormatProvider;
    protected ContentContainer myContentContainer;
    protected ParaContainer myParaContainer;
    protected RunContainer myRunContainer;
    protected int myFootnoteRef;
    protected AtomicInteger myBookmarkID;

    public DocxContextImpl(WordprocessingMLPackage out, DataHolder options) {
        myPackage = out;
        this.options = options;
        myRendererOptions = new DocxRendererOptions(this.options, out);
        myFactory = new ObjectFactory();
        myDocxHelper = new DocxHelper(myPackage, myFactory, myRendererOptions);
        myRunFormatProvider = this;
        myRunContainer = this;
        myHyperlinks = new HashMap<>();
        myParaContainer = this;
        myContentContainer = this;
        myDocumentPart = out.getMainDocumentPart();
        myBlockFormatProviders = new HashMap<>();
        myRunFormatProviders = new HashMap<>();
        myBlockFormatProvider = this;
        myFootnoteRef = 1;
        myBookmarkID = new AtomicInteger(1);
    }

    // must be the first thing called after creation
    public void setParent(DocxContext<?> parent) {

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
    public List<Object> getContent() {
        return myContentContainer == this ? myDocumentPart.getContent() : myContentContainer.getContent();
    }

    @Override
    public Object getLastContentElement() {
        if (myContentContainer == this) {
            List<Object> content = getContent();
            return content != null && content.size() > 0 ? content.get(content.size() - 1) : null;
        } else {
            return myContentContainer.getLastContentElement();
        }
    }

    @Override
    public void addContentElement(Object element) {
        if (myContentContainer == this) {
            getContent().add(element);
        } else {
            myContentContainer.addContentElement(element);
        }
    }

    @Override
    public void setContentContainer(ContentContainer container) {
        myContentContainer = container;
    }

    @Override
    public void setParaContainer(ParaContainer container) {
        myParaContainer = container;
    }

    @Override
    public void setBlockFormatProvider(BlockFormatProvider<T> formatProvider) {
        myBlockFormatProviders.put(getContextFrame(), formatProvider);
        myBlockFormatProvider = formatProvider;

        // let it initialize, closing done after rendering
        formatProvider.open();
    }

    @Override
    public BlockFormatProvider<T> getBlockFormatProvider(T node) {
        return myBlockFormatProviders.get(node);
    }

    @Override
    public BlockFormatProvider<T> getBlockFormatProvider() {
        return myBlockFormatProvider;
    }

    @Override
    public void setRunContainer(RunContainer container) {
        myRunContainer = container;
    }

    @Override
    public void setRunFormatProvider(RunFormatProvider<T> formatProvider) {
        myRunFormatProviders.put(getContextFrame(), formatProvider);
        myRunFormatProvider = formatProvider;

        // let it initialize, closing done after rendering
        formatProvider.open();
    }

    @Override
    public RunFormatProvider<T> getRunFormatProvider(T node) {
        return myRunFormatProviders.get(node);
    }

    @Override
    public RunFormatProvider<T> getRunFormatProvider() {
        return myRunFormatProvider;
    }

    @Override
    public DocxRendererOptions getRenderingOptions() {
        return myRendererOptions;
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
        return myRendererOptions.DEFAULT_STYLE;
    }

    @Override
    public Style getStyle() {
        return getStyle(myRendererOptions.DEFAULT_STYLE);
    }

    @Override
    public void adjustPPrForFormatting(PPr pP) {

    }

    @Override
    public void getPPr(PPr pPr) {

    }

    @Override
    public void getParaRPr(RPr rPr) {

    }

    @Override
    public void addP(P p) {
        myContentContainer.addContentElement(p);
    }

    @Override
    public void addR(R r) {
        getP().getContent().add(r);
    }

    @Override
    public void getRPr(RPr rPr) {

    }

    @Override
    public P getLastP() {
        Object o = getLastContentElement();
        return o instanceof P ? (P) o : null;
    }

    @Override
    public R getLastR() {
        P p = myParaContainer.getLastP();
        if (p == null) return null;
        List<Object> content = p.getContent();
        if (content == null || content.size() == 0) return null;
        Object o = content.get(content.size() - 1);
        return o instanceof R ? (R) o : null;
    }

    @Override
    public PStyle createPStyle(String style) {
        P p = getP();
        PPr pPr = p.getPPr();

        // Create object for pStyle if one does not already exist
        PStyle basePStyle = myFactory.createPPrBasePStyle();
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
            RStyle rStyle = rPr.getRStyle();
            if (rStyle != null && rStyle.getVal() != null) {
                Style style = getStyle(rStyle.getVal());
                if (style != null) {
                    RPr styleRPr = myDocxHelper.getExplicitRPr(style.getRPr(), p.getPPr());
                    myDocxHelper.keepDiff(rPr, styleRPr);
                }
            }

            PStyle pStyle = p.getPPr().getPStyle();
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
    public BooleanDefaultTrue getBooleanDefaultTrue(boolean value) {
        BooleanDefaultTrue defaultTrue = getFactory().createBooleanDefaultTrue();
        defaultTrue.setVal(value);
        return defaultTrue;
    }

    @Override
    public CTShd getCTShd() {
        RPr rPr = getRPr();
        CTShd ctShd = rPr.getShd();
        if (ctShd == null) {
            ctShd = myFactory.createCTShd();
            rPr.setShd(ctShd);
        }
        return ctShd;
    }

    @Override
    public int getNextBookmarkId() {
        return myBookmarkID.getAndIncrement();
    }

    @Override
    public AtomicInteger getBookmarkIdAtomic() {
        return myBookmarkID;
    }

    /**
     * Insert bookmark start into current P
     *
     * @param bookmarkName    name of the bookmark (optional), if not given the it will be BM_{id}
     * @param isBlockBookmark true if block bookmark
     * @return CTBookmark
     */
    @Override
    public CTBookmark createBookmarkStart(String bookmarkName, boolean isBlockBookmark) {
        CTBookmark bm = myFactory.createCTBookmark();
        int id = getNextBookmarkId();
        bm.setId(BigInteger.valueOf(id));

        if (bookmarkName != null && !bookmarkName.isEmpty()) {
            bm.setName(getValidBookmarkName(bookmarkName));
        } else {
            bm.setName(String.format(Locale.US, "BM_%d", id));
        }
        JAXBElement<CTBookmark> bmStart = myFactory.createBodyBookmarkStart(bm);
        if (isBlockBookmark) {
            myContentContainer.addContentElement(bmStart);
        } else {
            // add as inline
            getP().getContent().add(bmStart);
        }
        return bm;
    }

    @Override
    public CTMarkupRange createBookmarkEnd(CTBookmark bookmarkStart, boolean isBlockBookmark) {
        CTMarkupRange mr = myFactory.createCTMarkupRange();
        mr.setId(bookmarkStart.getId());
        JAXBElement<CTMarkupRange> bmEnd = myFactory.createBodyBookmarkEnd(mr);
        if (isBlockBookmark) {
            myContentContainer.addContentElement(bmEnd);
        } else {
            // add as inline
            getP().getContent().add(bmEnd);
        }
        return mr;
    }

    @Override
    public P.Hyperlink createBookmarkHyperlink(String bookmarkName, String linkText) {
        P.Hyperlink h = MainDocumentPart.hyperlinkToBookmark(bookmarkName, linkText);
        getP().getContent().add(h);
        return null;
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
    public void addBlankLine(int size, String styleId) {
        addBlankLine(BigInteger.valueOf(size), styleId);
    }

    @Override
    public void addBlankLine(long size, String styleId) {
        addBlankLine(BigInteger.valueOf(size), styleId);
    }

    @Override
    public void addBlankLine(BigInteger size, String styleId) {
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
    public void addBlankLines(int count) {
        if (count > 0) {
            // now add empty for spacing
            PPr pPr = myFactory.createPPr();
            myBlockFormatProvider.getPPr(pPr);

            PPr explicitPPr = myDocxHelper.getExplicitPPr(pPr);
            ParaRPr rPr = explicitPPr.getRPr();
            BigInteger size = rPr.getSz().getVal().max(rPr.getSzCs().getVal());

            addBlankLine(size.multiply(BigInteger.valueOf(count)), null);
        }
    }

    @Override
    public Text addText(String value, boolean noProofRPr, boolean createR) {
        R r = createR ? createR() : getR();

        if (noProofRPr) {
            RPr rpr = getRPr();
            rpr.setNoProof(new BooleanDefaultTrue());
        }

        Text text = myFactory.createText();
        if (value.startsWith(" ") || value.endsWith(" ")) text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        text.setValue(value);
        JAXBElement<Text> textWrapped = myFactory.createRT(text);
        r.getContent().add(textWrapped);
        return text;
    }

    @Override
    public Text addInstrText(String value, boolean noProofRPr, boolean createR) {
        // Create object for t (wrapped in JAXBElement)
        R r = createR ? createR() : getR();

        if (noProofRPr) {
            RPr rpr = getRPr();
            rpr.setNoProof(new BooleanDefaultTrue());
        }

        Text text = myFactory.createText();
        if (value.startsWith(" ") || value.endsWith(" ")) text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        text.setValue(value);
        r.getContent().add(myFactory.createRInstrText(text));
        return text;
    }

    @Override
    public FldChar addFldChar(STFldCharType charType, boolean createR) {
        R r = createR ? createR() : getR();

        FldChar fldChar = myFactory.createFldChar();
        fldChar.setFldCharType(charType);
        r.getContent().add(myFactory.createRFldChar(fldChar));
        return fldChar;
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
        ContentContainer oldRenderingContentContainer = myContentContainer;
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
        myContentContainer = oldRenderingContentContainer;
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
        createP(myRendererOptions.HORIZONTAL_LINE_STYLE);
        createR();
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
    public Style getStyle(String styleName) {
        return myDocumentPart.getStyleDefinitionsPart().getStyleById(styleName);
    }

    @Override
    public RelationshipsPart getRelationshipsPart() {
        return myContentContainer == this ? myDocumentPart.getRelationshipsPart() : myContentContainer.getRelationshipsPart();
    }

    @Override
    public Part getContainerPart() {
        return myContentContainer == this ? myDocumentPart : myContentContainer.getContainerPart();
    }

    @Override
    public Relationship getHyperlinkRelationship(String url) {
        Relationship rel;
        RelationshipsPart relationshipsPart = myContentContainer.getRelationshipsPart();
        HashMap<String, Relationship> urlRelationshipMap = myHyperlinks.get(relationshipsPart);
        if (urlRelationshipMap != null) {
            rel = urlRelationshipMap.get(url);
            if (rel != null) return rel;
        }

        // We need to add a relationship to word/_rels/document.xml.rels
        // but since its external, we don't use the
        // usual wordMLPackage.getMainDocumentPart().addTargetPart
        // mechanism
        org.docx4j.relationships.ObjectFactory factory = new org.docx4j.relationships.ObjectFactory();

        rel = factory.createRelationship();
        rel.setType(Namespaces.HYPERLINK);
        rel.setTarget(url);
        rel.setTargetMode("External");

        relationshipsPart.addRelationship(rel);
        if (urlRelationshipMap == null) {
            urlRelationshipMap = new HashMap<>();
            myHyperlinks.put(relationshipsPart, urlRelationshipMap);
        }

        urlRelationshipMap.put(url, rel);
        return rel;
    }

    @Override
    public void renderFencedCodeLines(CharSequence... lines) {
        renderFencedCodeLines(Arrays.asList(lines));
    }

    @Override
    public void renderFencedCodeLines(List<? extends CharSequence> lines) {
        contextFramed(() -> {
            setBlockFormatProvider(new FencedCodeBlockFormatProvider<>(DocxContextImpl.this));
            createP();

            int[] leadColumns = new int[lines.size()];
            int minSpaces = Integer.MAX_VALUE;
            int i = 0;
            for (CharSequence line : lines) {
                leadColumns[i] = BasedSequence.of(line).countLeadingColumns(0, CharPredicate.SPACE_TAB);
                minSpaces = Utils.min(minSpaces, leadColumns[i]);
                i++;
            }

            ArrayList<BasedSequence> trimmedLines = new ArrayList<>();
            i = 0;
            for (CharSequence line : lines) {
                StringBuilder sb = new StringBuilder();

                int spaces = leadColumns[i] - minSpaces;
                while (spaces-- > 0) sb.append(' ');
                sb.append(BasedSequence.of(line).trim());

                // Create object for p
                addTextCreateR(sb.toString());

                i++;

                if (i < lines.size()) {
                    addLineBreak();
                }
            }
        });
    }

    // @formatter:off
    //static String footnotePartXML = "<w:footnotes mc:Ignorable=\"w14 wp14\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\" xmlns:mc=\"http://schemas.openxmlformats.org/markup-compatibility/2006\">"
    //        + "<w:footnote w:id=\"-1\" w:type=\"separator\">"  // matching CTFtnDocProps below
    //            + "<w:p>"
    //                + "<w:pPr>"
    //                    + "<w:spacing w:after=\"0\" w:line=\"240\" w:lineRule=\"auto\"/>"
    //                +"</w:pPr>"
    //                + "<w:r>"
    //                    + "<w:separator/>"
    //                +"</w:r>"
    //            +"</w:p>"
    //        +"</w:footnote>"
    //        + "<w:footnote w:id=\"0\" w:type=\"continuationSeparator\">"
    //            + "<w:p>"
    //                + "<w:pPr>"
    //                    + "<w:spacing w:after=\"0\" w:line=\"240\" w:lineRule=\"auto\"/>"
    //                +"</w:pPr>"
    //                + "<w:r>"
    //                    + "<w:continuationSeparator/>"
    //                +"</w:r>"
    //            +"</w:p>"
    //        +"</w:footnote>"
    //    +"</w:footnotes>";
    // @formatter:on

    @Override
    public FootnotesPart getFootnotesPart() throws Docx4JException {
        // Setup FootnotesPart if necessary,
        // along with DocumentSettings
        FootnotesPart footnotesPart = myDocumentPart.getFootnotesPart();
        if (footnotesPart == null) { // that'll be the case in this example
            // initialise it
            footnotesPart = new FootnotesPart();
            myDocumentPart.addTargetPart(footnotesPart);

            //CTFootnotes footnotes = null;
            //footnotes = (CTFootnotes) XmlUtils.unwrap(XmlUtils.unmarshalString(footnotePartXML));
            //footnotesPart.setJaxbElement(footnotes);

            CTFootnotes footnotes = myFactory.createCTFootnotes();
            //JAXBElement<CTFootnotes> footnotesWrapped = myFactory.createFootnotes(footnotes);

            // Create object for footnote
            CTFtnEdn ftnedn = myFactory.createCTFtnEdn();
            footnotes.getFootnote().add(ftnedn);
            ftnedn.setId(BigInteger.valueOf(-1));
            ftnedn.setType(org.docx4j.wml.STFtnEdn.SEPARATOR);
            // Create object for p
            P p = myFactory.createP();
            ftnedn.getContent().add(p);
            // Create object for r
            R r = myFactory.createR();
            p.getContent().add(r);
            // Create object for separator (wrapped in JAXBElement)
            Separator rseparator = myFactory.createRSeparator();
            JAXBElement<Separator> rseparatorWrapped = myFactory.createRSeparator(rseparator);
            r.getContent().add(rseparatorWrapped);
            // Create object for footnote
            CTFtnEdn ftnedn2 = myFactory.createCTFtnEdn();
            footnotes.getFootnote().add(ftnedn2);
            ftnedn2.setId(BigInteger.valueOf(0));
            ftnedn2.setType(org.docx4j.wml.STFtnEdn.CONTINUATION_SEPARATOR);
            // Create object for p
            P p2 = myFactory.createP();
            ftnedn2.getContent().add(p2);
            // Create object for r
            R r2 = myFactory.createR();
            p2.getContent().add(r2);
            // Create object for continuationSeparator (wrapped in JAXBElement)
            ContinuationSeparator rcontinuationseparator = myFactory.createRContinuationSeparator();
            JAXBElement<ContinuationSeparator> rcontinuationseparatorWrapped = myFactory.createRContinuationSeparator(rcontinuationseparator);
            r2.getContent().add(rcontinuationseparatorWrapped);

            footnotesPart.setJaxbElement(footnotes);

            // Usually the settings part contains footnote properties;
            // so add these if not present
            DocumentSettingsPart dsp = myDocumentPart.getDocumentSettingsPart();
            if (dsp == null) {
                // create it
                dsp = new DocumentSettingsPart();
                myDocumentPart.addTargetPart(dsp);
            }

            CTSettings settings = dsp.getContents();
            if (settings == null) {
                settings = myFactory.createCTSettings();
                dsp.setJaxbElement(settings);
            }

            CTFtnDocProps ftndocprops = settings.getFootnotePr();
            if (ftndocprops == null) {
                //String openXML = "<w:footnotePr xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\">"
                //        + "<w:footnote w:id=\"-1\"/>" // these 2 numbers are special, and correspond with string footnotePartXML above
                //        + "<w:footnote w:id=\"0\"/>"
                //        + "</w:footnotePr>";
                //settings.setFootnotePr((CTFtnDocProps) XmlUtils.unmarshalString(openXML, Context.jc, CTFtnDocProps.class));

                ftndocprops = myFactory.createCTFtnDocProps();
                CTFtnEdnSepRef sepRef = myFactory.createCTFtnEdnSepRef();
                sepRef.setId(BigInteger.valueOf(-1));
                ftndocprops.getFootnote().add(sepRef);
                sepRef = myFactory.createCTFtnEdnSepRef();
                sepRef.setId(BigInteger.valueOf(0));
                ftndocprops.getFootnote().add(sepRef);
                settings.setFootnotePr(ftndocprops);
            }
        }

        return footnotesPart;
    }

    @Override
    public CTFtnEdn addFootnote(BigInteger footnoteID) throws Docx4JException {
        // Add the note number in the run
        CTFtnEdnRef ftnednref = myFactory.createCTFtnEdnRef();
        JAXBElement<CTFtnEdnRef> ftnednrefWrapped = myFactory.createRFootnoteReference(ftnednref);
        R ftnR = myFactory.createR();
        getP().getContent().add(ftnR);

        ftnR.getContent().add(ftnednrefWrapped);

        RPr ftnRPr = myFactory.createRPr();
        ftnR.setRPr(ftnRPr);

        RStyle ftnRStyle = myFactory.createRStyle();
        ftnRPr.setRStyle(ftnRStyle);

        ftnRStyle.setVal(myRendererOptions.FOOTNOTE_ANCHOR_STYLE);

        // see if we need to create a new footnote id or can re-use existing one
        boolean haveID = footnoteID.compareTo(BigInteger.ZERO) > 0;
        BigInteger i = haveID ? footnoteID : BigInteger.valueOf(myFootnoteRef++);
        ftnednref.setId(i);

        if (haveID) {
            for (CTFtnEdn ftnEdn : getFootnotesPart().getContents().getFootnote()) {
                if (ftnEdn.getId().compareTo(footnoteID) == 0) {
                    return ftnEdn;
                }
            }
        }

        // Create a footnote in the footnotesPart
        // @formatter:off
        //String openXML = "<w:footnote w:id=\"" + i + "\" xmlns:w=\"http://schemas.openxmlformats.org/wordprocessingml/2006/main\" xmlns:xml=\"http://www.w3.org/XML/1998/namespace\">"
        //        + "<w:p>"
        //            + "<w:pPr>"
        //                + "<w:pStyle w:val=\"Footnote\"/>"
        //                //+ "<w:rPr>"
        //                //    + "<w:lang w:val=\"en-AU\"/>"
        //                //+"</w:rPr>"
        //            +"</w:pPr>"
        //            + "<w:r>"
        //                + "<w:rPr>"
        //                    + "<w:rStyle w:val=\"FootnoteAnchor\"/>"
        //                +"</w:rPr>"
        //                + "<w:footnoteRef/>"
        //            +"</w:r>"
        //            + "<w:r>"
        //                + "<w:t xml:space=\"preserve\"> </w:t>"
        //            +"</w:r>"
        //            + "<w:r>"
        //                //+ "<w:rPr>"
        //                //    + "<w:lang w:val=\"en-AU\"/>"
        //                //+"</w:rPr>"
        //                + "<w:t>" + text +"</w:t>"
        //            +"</w:r>"
        //        +"</w:p>"
        //    +"</w:footnote>";
        //// @formatter:on
        //CTFtnEdn ftnedn = (CTFtnEdn) XmlUtils.unmarshalString(openXML, Context.jc, CTFtnEdn.class);

        CTFtnEdn ftnEdn = myFactory.createCTFtnEdn();
        ftnEdn.setId(i);

        P p = myFactory.createP();
        ftnEdn.getContent().add(p);

        PPr pPr = myFactory.createPPr();
        p.setPPr(pPr);

        PStyle pStyle = myFactory.createPPrBasePStyle();
        pPr.setPStyle(pStyle);

        pStyle.setVal(myRendererOptions.FOOTNOTE_STYLE);

        R r1 = myFactory.createR();
        p.getContent().add(r1);
        RPr rPr = myFactory.createRPr();
        r1.setRPr(rPr);
        RStyle rStyle = myFactory.createRStyle();
        rPr.setRStyle(rStyle);
        rStyle.setVal(myRendererOptions.FOOTNOTE_ANCHOR_STYLE);

        FootnoteRef footnoteRef = myFactory.createRFootnoteRef();
        r1.getContent().add(footnoteRef);

        R r2 = myFactory.createR();
        Text text1 = myFactory.createText();
        JAXBElement<Text> textWrapped = myFactory.createRT(text1);
        r2.getContent().add(textWrapped);
        text1.setSpace(RunFormatProvider.SPACE_PRESERVE);
        text1.setValue("\t");
        p.getContent().add(r2);

        getFootnotesPart().getContents().getFootnote().add(ftnEdn);
        return ftnEdn;
    }
}


