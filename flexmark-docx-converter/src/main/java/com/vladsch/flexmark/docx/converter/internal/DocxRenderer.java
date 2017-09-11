package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.*;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.collection.NodeCollectingVisitor;
import com.vladsch.flexmark.util.collection.SubClassingBag;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.*;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.Part;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.relationships.Namespaces;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Renders a tree of nodes to docx4j API.
 */
@SuppressWarnings("WeakerAccess")
public class DocxRenderer implements IRender {
    public static final DataKey<String> STYLES_XML = new DataKey<String>("STYLES_XML", getResourceString("/styles.xml"));
    public static final DataKey<String> NUMBERING_XML = new DataKey<String>("NUMBERING_XML", getResourceString("/numbering.xml"));

    public static final DataKey<Boolean> RENDER_BODY_ONLY = new DataKey<Boolean>("RENDER_BODY_ONLY", false);
    public static final DataKey<Integer> MAX_IMAGE_WIDTH = new DataKey<Integer>("MAX_IMAGE_WIDTH", 0);

    public static final DataKey<Boolean> DEFAULT_LINK_RESOLVER = new DataKey<Boolean>("DEFAULT_LINK_RESOLVER", true);
    public static final DataKey<String> DOC_RELATIVE_URL = new DataKey<String>("DOC_RELATIVE_URL", "");
    public static final DataKey<String> DOC_ROOT_URL = new DataKey<String>("DOC_ROOT_URL", "");

    // same keys, same function also available here for convenience
    public static final DataKey<Boolean> RECHECK_UNDEFINED_REFERENCES = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES;
    public static final DataKey<Boolean> PERCENT_ENCODE_URLS = HtmlRenderer.PERCENT_ENCODE_URLS;
    public static final DataKey<Boolean> ESCAPE_HTML = HtmlRenderer.ESCAPE_HTML;
    public static final DataKey<Boolean> ESCAPE_HTML_BLOCKS = HtmlRenderer.ESCAPE_HTML_BLOCKS;
    public static final DataKey<Boolean> ESCAPE_HTML_COMMENT_BLOCKS = HtmlRenderer.ESCAPE_HTML_COMMENT_BLOCKS;
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML = HtmlRenderer.ESCAPE_INLINE_HTML;
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML_COMMENTS = HtmlRenderer.ESCAPE_INLINE_HTML_COMMENTS;
    public static final DataKey<Boolean> SUPPRESS_HTML = HtmlRenderer.SUPPRESS_HTML;
    public static final DataKey<Boolean> SUPPRESS_HTML_BLOCKS = HtmlRenderer.SUPPRESS_HTML_BLOCKS;
    public static final DataKey<Boolean> SUPPRESS_HTML_COMMENT_BLOCKS = HtmlRenderer.SUPPRESS_HTML_COMMENT_BLOCKS;
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML = HtmlRenderer.SUPPRESS_INLINE_HTML;
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML_COMMENTS = HtmlRenderer.SUPPRESS_INLINE_HTML_COMMENTS;
    public static final DataKey<Boolean> LINEBREAK_ON_INLINE_HTML_BR = new DataKey<Boolean>("LINEBREAK_ON_INLINE_HTML_BR", true);
    public static final DataKey<Boolean> TABLE_CAPTION_TO_PARAGRAPH = new DataKey<Boolean>("TABLE_CAPTION_TO_PARAGRAPH", true);
    public static final DataKey<Boolean> TABLE_CAPTION_BEFORE_TABLE = new DataKey<Boolean>("TABLE_CAPTION_BEFORE_TABLE", false);

    private final List<NodeDocxRendererFactory> nodeFormatterFactories;
    final DocxRendererOptions rendererOptions;
    private final DataHolder options;
    private final Builder builder;
    private final List<LinkResolverFactory> linkResolverFactories;

    private DocxRenderer(Builder builder) {
        this.builder = new Builder(builder); // take a copy to avoid after creation side effects
        this.options = new DataSet(builder);
        this.rendererOptions = new DocxRendererOptions(this.options);
        this.nodeFormatterFactories = new ArrayList<NodeDocxRendererFactory>(builder.nodeDocxRendererFactories.size() + 1);
        this.nodeFormatterFactories.addAll(builder.nodeDocxRendererFactories);

        // Add as last. This means clients can override the rendering of core nodes if they want.
        this.nodeFormatterFactories.add(new NodeDocxRendererFactory() {
            @Override
            public NodeDocxRenderer create(DataHolder options) {
                return new CoreNodeDocxRenderer(options);
            }
        });

        this.linkResolverFactories = FlatDependencyHandler.computeDependencies(builder.linkResolverFactories);
    }

    /**
     * Create a new builder for configuring the DocxRenderer.
     *
     * @return a builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Create a new builder for configuring the DocxRenderer.
     *
     * @param options initialization options
     * @return a builder
     */
    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    public static WordprocessingMLPackage getDefaultTemplate() {
        return getDefaultTemplate("/empty.xml");
    }

    public static WordprocessingMLPackage getDefaultTemplate(String emptyXMLResourcePath) {
        final InputStream inputStream = getResourceInputStream(emptyXMLResourcePath);
        return getDefaultTemplate(inputStream);
    }

    public static WordprocessingMLPackage getDefaultTemplate(InputStream inputStream) {
        try {
            final WordprocessingMLPackage mlPackage = WordprocessingMLPackage.load(inputStream);
            return mlPackage;
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void setDefaultStyleAndNumbering(WordprocessingMLPackage out, final DataHolder options) {
        try {
            // (main doc part it if necessary)
            MainDocumentPart documentPart = out.getMainDocumentPart();
            if (documentPart == null) {
                try {
                    documentPart = new MainDocumentPart();
                    out.addTargetPart(documentPart);
                } catch (InvalidFormatException e) {
                    e.printStackTrace();
                }
            }

            if (documentPart.getStyleDefinitionsPart() == null) {
                Part stylesPart = new org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart();
                final Styles styles = (Styles) XmlUtils.unmarshalString(STYLES_XML.getFrom(options));
                ((org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart) stylesPart).setJaxbElement(styles);
                documentPart.addTargetPart(stylesPart); // NB - add it to main doc part, not package!
                assert documentPart.getStyleDefinitionsPart() != null : "Styles failed to set";
            }

            if (documentPart.getNumberingDefinitionsPart() == null) {
                // add it
                Part numberingPart = new org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart();
                final Numbering numbering = (Numbering) XmlUtils.unmarshalString(NUMBERING_XML.getFrom(options));
                ((org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart) numberingPart).setJaxbElement(numbering);
                documentPart.addTargetPart(numberingPart); // NB - add it to main doc part, not package!
                assert documentPart.getNumberingDefinitionsPart() != null : "Numbering failed to set";
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * Render a node to the appendable
     *
     * @param node   node to render
     * @param output appendable to use for the output
     */
    public void render(Node node, WordprocessingMLPackage output) {
        DocxRenderer.MainDocxRenderer renderer = new DocxRenderer.MainDocxRenderer(options, output, node.getDocument());
        renderer.render(node);
    }

    /**
     * Render the tree of nodes to DocX.
     *
     * @param node the root node
     * @return the rendered HTML
     */
    public String render(Node node) {
        WordprocessingMLPackage mlPackage = getDefaultTemplate();
        render(node, mlPackage);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            mlPackage.save(outputStream, Docx4J.FLAG_SAVE_FLAT_XML);
            final String s = options.get(RENDER_BODY_ONLY) ? XmlFormatter.formatDocumentBody(outputStream.toString("UTF-8"))
                    : XmlFormatter.format(outputStream.toString("UTF-8"));
            return s;
        } catch (Docx4JException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void render(final Node node, final Appendable output) {
        String docx = render(node);
        try {
            output.append(docx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DocxRenderer withOptions(DataHolder options) {
        return options == null ? this : new DocxRenderer(new Builder(builder, options));
    }

    /**
     * Builder for configuring an {@link DocxRenderer}. See methods for default configuration.
     */
    public static class Builder extends MutableDataSet {
        List<AttributeProviderFactory> attributeProviderFactories = new ArrayList<AttributeProviderFactory>();
        List<NodeDocxRendererFactory> nodeDocxRendererFactories = new ArrayList<NodeDocxRendererFactory>();
        List<LinkResolverFactory> linkResolverFactories = new ArrayList<LinkResolverFactory>();
        private final HashSet<RendererExtension> loadedExtensions = new HashSet<RendererExtension>();
        HeaderIdGeneratorFactory htmlIdGeneratorFactory = null;

        public Builder() {
            super();
        }

        public Builder(DataHolder options) {
            super(options);

            if (options.contains(Parser.EXTENSIONS)) {
                extensions(get(Parser.EXTENSIONS));
            }
        }

        public Builder(Builder other) {
            super(other);

            this.attributeProviderFactories.addAll(other.attributeProviderFactories);
            this.nodeDocxRendererFactories.addAll(other.nodeDocxRendererFactories);
            this.linkResolverFactories.addAll(other.linkResolverFactories);
            this.loadedExtensions.addAll(other.loadedExtensions);
            this.htmlIdGeneratorFactory = other.htmlIdGeneratorFactory;
        }

        public Builder(Builder other, DataHolder options) {
            super(other);

            List<Extension> extensions = new ArrayList<Extension>();
            for (Extension extension : get(Parser.EXTENSIONS)) {
                extensions.add(extension);
            }

            if (options != null) {
                for (DataKey key : options.keySet()) {
                    if (key == Parser.EXTENSIONS) {
                        for (Extension extension : options.get(Parser.EXTENSIONS)) {
                            extensions.add(extension);
                        }
                    } else {
                        set(key, options.get(key));
                    }
                }
            }

            set(Parser.EXTENSIONS, extensions);
            extensions(extensions);
        }

        /**
         * @return the configured {@link DocxRenderer}
         */
        public DocxRenderer build() {
            return new DocxRenderer(this);
        }

        /**
         * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
         * of node types or define rendering for custom node types.
         * <p>
         * If multiple node renderers for the same node type are created, the one from the factory that was added first
         * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
         *
         * @param nodeDocxRendererFactory the factory for creating a node renderer
         * @return {@code this}
         */
        @SuppressWarnings("UnusedReturnValue")
        public Builder nodeFormatterFactory(NodeDocxRendererFactory nodeDocxRendererFactory) {
            this.nodeDocxRendererFactories.add(nodeDocxRendererFactory);
            return this;
        }

        /**
         * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
         * of node types or define rendering for custom node types.
         * <p>
         * If multiple node renderers for the same node type are created, the one from the factory that was added first
         * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
         *
         * @param linkResolverFactory the factory for creating a node renderer
         * @return {@code this}
         */
        public Builder linkResolverFactory(LinkResolverFactory linkResolverFactory) {
            this.linkResolverFactories.add(linkResolverFactory);
            return this;
        }

        /**
         * @param extensions extensions to use on this HTML renderer
         * @return {@code this}
         */
        public Builder extensions(Iterable<? extends Extension> extensions) {
            // first give extensions a chance to modify options
            for (Extension extension : extensions) {
                if (extension instanceof RendererExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        RendererExtension rendererExtension = (RendererExtension) extension;
                        rendererExtension.rendererOptions(this);
                    }
                }
            }

            for (Extension extension : extensions) {
                if (extension instanceof RendererExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        RendererExtension rendererExtension = (RendererExtension) extension;
                        rendererExtension.extend(this);
                        loadedExtensions.add(rendererExtension);
                    }
                }
            }
            return this;
        }
    }

    /**
     * Extension for {@link DocxRenderer}.
     */
    public interface RendererExtension extends Extension {
        /**
         * This method is called first on all extensions so that they can adjust the options.
         *
         * @param options option set that will be used for the builder
         */
        void rendererOptions(MutableDataHolder options);

        void extend(Builder builder);
    }

    private final static Iterator<? extends Node> NULL_ITERATOR = new Iterator<Node>() {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Node next() {
            return null;
        }

        @Override
        public void remove() {
        }
    };

    final static Iterable<? extends Node> NULL_ITERABLE = new Iterable<Node>() {
        @Override
        public Iterator<Node> iterator() {
            return null;
        }
    };

    private class MainDocxRenderer implements DocxRendererContext, BlockFormatProvider, ParaContainer, RunFormatProvider, RunContainer {
        private final Document document;
        private final Map<Class<?>, NodeDocxRendererHandler> renderers;
        private final SubClassingBag<Node> collectedNodes;

        private final List<PhasedNodeDocxRenderer> phasedFormatters;
        private final Set<DocxRendererPhase> renderingPhases;
        private final DataHolder options;
        private DocxRendererPhase phase;
        Node renderingNode;
        private final LinkResolver[] myLinkResolvers;
        private final HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<LinkType, HashMap<String, ResolvedLink>>();

        // docx stuff
        final HashMap<String, Style> styles;
        final HashMap<String, Relationship> hyperlinks;
        private final WordprocessingMLPackage wordprocessingPackage;
        private final MainDocumentPart mainDocumentPart;
        private final ObjectFactory myFactory;
        private final DocxHelper myDocxHelper;
        private P p;

        private HashMap<Node, BlockFormatProvider> blockFormatProviders;
        private BlockFormatProvider renderingBlockFormatProvider;
        private ParaContainer renderingParaContainer;
        private HashMap<Node, RunFormatProvider> runFormatProviders;
        private RunFormatProvider renderingRunFormatProvider;
        private RunContainer renderingRunContainer;

        MainDocxRenderer(DataHolder options, WordprocessingMLPackage out, Document document) {
            this.options = new ScopedDataSet(options, document);
            this.document = document;
            this.renderers = new HashMap<Class<?>, NodeDocxRendererHandler>(32);
            this.renderingPhases = new HashSet<DocxRendererPhase>(DocxRendererPhase.values().length);
            final Set<Class> collectNodeTypes = new HashSet<Class>(100);
            this.phasedFormatters = new ArrayList<PhasedNodeDocxRenderer>(nodeFormatterFactories.size());
            this.styles = new HashMap<String, Style>();
            this.hyperlinks = new HashMap<String, Relationship>();
            final Boolean defaultLinkResolver = DEFAULT_LINK_RESOLVER.getFrom(options);
            this.myLinkResolvers = new LinkResolver[linkResolverFactories.size() + (defaultLinkResolver ? 1 : 0)];

            this.wordprocessingPackage = out;
            this.myFactory = new ObjectFactory();
            this.mainDocumentPart = out.getMainDocumentPart();
            this.myDocxHelper = new DocxHelper(wordprocessingPackage, myFactory);

            setDefaultStyleAndNumbering(out, this.options);

            this.blockFormatProviders = new HashMap<Node, BlockFormatProvider>();
            this.runFormatProviders = new HashMap<Node, RunFormatProvider>();

            // we are top level provider
            this.blockFormatProviders.put(document, this);
            this.runFormatProviders.put(document, this);
            this.renderingBlockFormatProvider = this;
            this.renderingRunFormatProvider = this;
            this.renderingParaContainer = this;
            this.renderingRunContainer = this;

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                myLinkResolvers[i] = linkResolverFactories.get(i).create(this);
            }

            if (defaultLinkResolver) {
                // add the default link resolver
                myLinkResolvers[linkResolverFactories.size()] = new DocxLinkResolver.Factory().create(this);
            }

            // The first node renderer for a node type "wins".
            for (int i = nodeFormatterFactories.size() - 1; i >= 0; i--) {
                NodeDocxRendererFactory nodeDocxRendererFactory = nodeFormatterFactories.get(i);
                NodeDocxRenderer nodeDocxRenderer = nodeDocxRendererFactory.create(this.getOptions());
                final Set<NodeDocxRendererHandler<?>> formattingHandlers = nodeDocxRenderer.getNodeFormattingHandlers();
                if (formattingHandlers == null) continue;

                for (NodeDocxRendererHandler nodeType : formattingHandlers) {
                    // Overwrite existing renderer
                    renderers.put(nodeType.getNodeType(), nodeType);
                }

                // get nodes of interest
                Set<Class<?>> nodeClasses = nodeDocxRenderer.getNodeClasses();
                if (nodeClasses != null) {
                    collectNodeTypes.addAll(nodeClasses);
                }

                if (nodeDocxRenderer instanceof PhasedNodeDocxRenderer) {
                    Set<DocxRendererPhase> phases = ((PhasedNodeDocxRenderer) nodeDocxRenderer).getFormattingPhases();
                    if (phases != null) {
                        if (phases.isEmpty()) throw new IllegalStateException("PhasedNodeDocxRenderer with empty Phases");
                        this.renderingPhases.addAll(phases);
                        this.phasedFormatters.add((PhasedNodeDocxRenderer) nodeDocxRenderer);
                    } else {
                        throw new IllegalStateException("PhasedNodeDocxRenderer with null Phases");
                    }
                }
            }

            // collect nodes of interest from document
            if (!collectNodeTypes.isEmpty()) {
                NodeCollectingVisitor collectingVisitor = new NodeCollectingVisitor(collectNodeTypes);
                collectingVisitor.collect(document);
                collectedNodes = collectingVisitor.getSubClassingBag();
            } else {
                collectedNodes = null;
            }
        }

        @Override
        public void setParaContainer(final ParaContainer container) {
            renderingParaContainer = container;
        }

        @Override
        public void setBlockFormatProvider(final BlockFormatProvider formatProvider) {
            blockFormatProviders.put(getCurrentNode(), formatProvider);
            renderingBlockFormatProvider = formatProvider;

            // let it initialize, closing done after rendering
            formatProvider.open();
        }

        @Override
        public BlockFormatProvider getBlockFormatProvider(final Node node) {
            return blockFormatProviders.get(node);
        }

        @Override
        public BlockFormatProvider getBlockFormatProvider() {
            return renderingBlockFormatProvider;
        }

        @Override
        public void setRunContainer(final RunContainer container) {
            renderingRunContainer = container;
        }

        @Override
        public void setRunFormatProvider(final RunFormatProvider formatProvider) {
            runFormatProviders.put(getCurrentNode(), formatProvider);
            renderingRunFormatProvider = formatProvider;

            // let it initialize, closing done after rendering
            formatProvider.open();
        }

        @Override
        public RunFormatProvider getRunFormatProvider(final Node node) {
            return runFormatProviders.get(node);
        }

        @Override
        public RunFormatProvider getRunFormatProvider() {
            return renderingRunFormatProvider;
        }

        // document format provider
        @Override
        public void open() {

        }

        @Override
        public void close() {

        }

        @Override
        public BlockFormatProvider getParent() {
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
            mainDocumentPart.getContent().add(p);
        }

        @Override
        public void addR(final R r) {
            p.getContent().add(r);
        }

        @Override
        public void getRPr(final RPr rPr) {

        }

        @Override
        public Node getNode() {
            return document;
        }

        @Override
        public P createP() {
            P p = myFactory.createP();
            PPr pPr = myFactory.createPPr();

            p.setPPr(pPr);

            renderingParaContainer.addP(p);
            this.p = p;

            renderingBlockFormatProvider.getPPr(pPr);
            renderingBlockFormatProvider.adjustPPrForFormatting(pPr);

            if (StyleUtil.isEmpty(p.getPPr())) {
                p.setPPr(null);
            } else if (StyleUtil.isEmpty(p.getPPr().getRPr())) {
                p.getPPr().setRPr(null);
            }

            return p;
        }

        @Override
        public P getP() {
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

            renderingRunContainer.addR(r);

            RPr blockRPr = myFactory.createRPr();
            renderingBlockFormatProvider.getParaRPr(blockRPr);

            renderingRunFormatProvider.getRPr(rPr);
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

            final PPrBase.PStyle pStyle = p.getPPr().getPStyle();
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
            return r;
        }

        @Override
        public R getR() {
            if (p == null || p.getContent().isEmpty() || !(p.getContent().get(p.getContent().size() - 1) instanceof R)) {
                return createR();
            } else {
                return (R) p.getContent().get(p.getContent().size() - 1);
            }
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

                renderingParaContainer.addP(p);
                this.p = p;

                if (styleId != null && !styleId.isEmpty()) {
                    if (pPr.getPStyle() == null) {
                        PPrBase.PStyle pStyle = myDocxHelper.myFactory.createPPrBasePStyle();
                        pPr.setPStyle(pStyle);
                    }
                    pPr.getPStyle().setVal(styleId);
                }

                // Create new Spacing which we override
                PPrBase.Spacing spacing = myFactory.createPPrBaseSpacing();
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
                renderingBlockFormatProvider.getPPr(pPr);

                PPr explicitPPr = myDocxHelper.getExplicitPPr(pPr);
                final ParaRPr rPr = explicitPPr.getRPr();
                BigInteger size = rPr.getSz().getVal().max(rPr.getSzCs().getVal());

                addBlankLine(size.multiply(BigInteger.valueOf(count)), null);
            }
        }

        @Override
        public org.docx4j.wml.Text addWrappedText(final R r) {
            // Create object for t (wrapped in JAXBElement)
            org.docx4j.wml.Text text = myFactory.createText();
            JAXBElement<org.docx4j.wml.Text> textWrapped = myFactory.createRT(text);
            r.getContent().add(textWrapped);
            return text;
        }

        @Override
        public void addLineBreak() {
            addBreak(null);
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

            renderingRunContainer.addR(r);
        }

        @Override
        public org.docx4j.wml.Text text(final String text) {
            R r = createR();
            org.docx4j.wml.Text textElem = addWrappedText(r);
            textElem.setValue(text);
            textElem.setSpace(RunFormatProvider.SPACE_PRESERVE);
            return textElem;
        }

        @Override
        public BooleanDefaultTrue getBooleanDefaultTrue() {
            return myFactory.createBooleanDefaultTrue();
        }

        @Override
        public BooleanDefaultFalse getBooleanDefaultFalse() {
            return myFactory.createBooleanDefaultFalse();
        }

        @Override
        public WordprocessingMLPackage getPackage() {
            return wordprocessingPackage;
        }

        @Override
        public MainDocumentPart getDocxDocument() {
            return mainDocumentPart;
        }

        @Override
        public Style getStyle(final String styleName) {
            Style style = styles.get(styleName);
            if (style != null) return style;

            style = mainDocumentPart.getStyleDefinitionsPart().getStyleById(styleName);
            styles.put(styleName, style);
            return style;
        }

        @Override
        public Relationship getHyperlinkRelationship(String url) {
            Relationship rel = hyperlinks.get(url);
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

            mainDocumentPart.getRelationshipsPart().addRelationship(rel);
            hyperlinks.put(url, rel);
            return rel;
        }

        @Override
        public String encodeUrl(CharSequence url) {
            if (rendererOptions.percentEncodeUrls) {
                return Escaping.percentEncodeUrl(url);
            } else {
                return url instanceof String ? (String) url : String.valueOf(url);
            }
        }

        @Override
        public Node getCurrentNode() {
            return renderingNode;
        }

        @Override
        public DataHolder getOptions() {
            return options;
        }

        @Override
        public DocxRendererOptions getDocxRendererOptions() {
            return rendererOptions;
        }

        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public DocxRendererPhase getPhase() {
            return phase;
        }

        @Override
        public final Iterable<? extends Node> nodesOfType(final Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @Override
        public final Iterable<? extends Node> nodesOfType(final Collection<Class<?>> classes) {
            //noinspection unchecked
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.itemsOfType(Node.class, classes);
        }

        @Override
        public final Iterable<? extends Node> reversedNodesOfType(final Class<?>[] classes) {
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @Override
        public final Iterable<? extends Node> reversedNodesOfType(final Collection<Class<?>> classes) {
            //noinspection unchecked
            return collectedNodes == null ? NULL_ITERABLE : collectedNodes.reversedItemsOfType(Node.class, classes);
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
            return resolveLink(linkType, url, (Attributes) null, urlEncode);
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
            HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.get(linkType);
            if (resolvedLinks == null) {
                resolvedLinks = new HashMap<String, ResolvedLink>();
                resolvedLinkMap.put(linkType, resolvedLinks);
            }

            String urlSeq = url instanceof String ? (String) url : String.valueOf(url);
            ResolvedLink resolvedLink = resolvedLinks.get(urlSeq);
            if (resolvedLink == null) {
                resolvedLink = new ResolvedLink(linkType, urlSeq, attributes);

                if (!urlSeq.isEmpty()) {
                    Node currentNode = renderingNode;

                    for (LinkResolver linkResolver : myLinkResolvers) {
                        resolvedLink = linkResolver.resolveLink(currentNode, this, resolvedLink);
                        if (resolvedLink.getStatus() != LinkStatus.UNKNOWN) break;
                    }

                    if (urlEncode == null && rendererOptions.percentEncodeUrls || urlEncode != null && urlEncode) {
                        resolvedLink = resolvedLink.withUrl(Escaping.percentEncodeUrl(resolvedLink.getUrl()));
                    }
                }

                // put it in the map
                resolvedLinks.put(urlSeq, resolvedLink);
            }

            return resolvedLink;
        }

        @Override
        public void render(final Node node) {
            if (node instanceof Document) {
                // here we render multiple phases
                for (DocxRendererPhase phase : DocxRendererPhase.values()) {
                    if (phase != DocxRendererPhase.DOCUMENT && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;
                    // here we render multiple phases
                    if (this.phase == DocxRendererPhase.DOCUMENT) {
                        NodeDocxRendererHandler nodeRenderer = renderers.get(node.getClass());
                        if (nodeRenderer != null) {
                            renderingNode = node;
                            nodeRenderer.render(node, this);
                            renderingNode = null;
                        }
                    } else {
                        // go through all renderers that want this phase
                        for (PhasedNodeDocxRenderer phasedFormatter : phasedFormatters) {
                            if (phasedFormatter.getFormattingPhases().contains(phase)) {
                                renderingNode = node;
                                phasedFormatter.renderDocument(this, (Document) node, phase);
                                renderingNode = null;
                            }
                        }
                    }
                }
            } else {
                NodeDocxRendererHandler nodeRenderer = renderers.get(node.getClass());

                if (nodeRenderer == null) {
                    nodeRenderer = renderers.get(Node.class);
                }

                if (nodeRenderer != null) {
                    final NodeDocxRendererHandler finalNodeRenderer = nodeRenderer;
                    final Node oldNode = MainDocxRenderer.this.renderingNode;
                    renderingNode = node;

                    contextFramed(new Runnable() {
                        @Override
                        public void run() {
                            finalNodeRenderer.render(renderingNode, MainDocxRenderer.this);

                            renderingNode = oldNode;
                        }
                    });

                } else {
                    // default behavior is controlled by generic Node.class that is implemented in CoreNodeDocxRenderer
                    throw new IllegalStateException("Core Node DocxRenderer should implement generic Node renderer");
                }
            }
        }

        @Override
        public void contextFramed(Runnable runnable) {
            blockFormatProviders.put(renderingNode, renderingBlockFormatProvider);
            runFormatProviders.put(renderingNode, renderingRunFormatProvider);
            BlockFormatProvider oldRenderingBlockFormatProvider = renderingBlockFormatProvider;
            RunFormatProvider oldRenderingRunFormatProvider = renderingRunFormatProvider;
            ParaContainer oldRenderingParaContainer = renderingParaContainer;
            RunContainer oldRenderingRunContainer = renderingRunContainer;
            Node oldNode = renderingNode;

            runnable.run();

            if (oldNode != renderingNode) {
                RunFormatProvider runFormatProvider = runFormatProviders.remove(oldNode);

                if (runFormatProvider != oldRenderingRunFormatProvider) {
                    runFormatProvider.close();
                }
            }
            renderingRunFormatProvider = oldRenderingRunFormatProvider;

            if (oldNode != renderingNode) {
                BlockFormatProvider blockFormatProvider = blockFormatProviders.remove(oldNode);
                if (blockFormatProvider != oldRenderingBlockFormatProvider) {
                    blockFormatProvider.close();
                }
            }

            renderingBlockFormatProvider = oldRenderingBlockFormatProvider;
            renderingRunContainer = oldRenderingRunContainer;
            renderingParaContainer = oldRenderingParaContainer;
        }

        public void renderChildren(Node parent) {
            Node node = parent.getFirstChild();
            while (node != null) {
                Node next = node.getNext();
                render(node);
                node = next;
            }
        }
    }

    public static String getResourceString(String resourcePath) {
        try {
            InputStream stream = getResourceInputStream(resourcePath);
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getResourceInputStream(String resourcePath) {
        String specPath = resourcePath != null ? resourcePath : "/spec.txt";
        InputStream stream = DocxRenderer.class.getResourceAsStream(specPath);
        if (stream == null) {
            throw new IllegalStateException("Could not load " + resourcePath + " classpath resource");
        }
        return stream;
    }
}
