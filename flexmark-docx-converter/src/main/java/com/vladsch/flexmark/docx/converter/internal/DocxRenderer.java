package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.collection.DynamicDefaultKey;
import com.vladsch.flexmark.util.collection.NodeCollectingVisitor;
import com.vladsch.flexmark.util.collection.SubClassingBag;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.*;
import org.docx4j.Docx4J;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
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

    public static final DataKey<Integer> MAX_IMAGE_WIDTH = new DataKey<Integer>("MAX_IMAGE_WIDTH", 0);

    public static final DataKey<Boolean> XHTML_IMPORT = new DataKey<Boolean>("XHTML_IMPORT", false);
    public static final DataKey<String> XHTML_IMPORT_PREFIX = new DataKey<String>("XHTML_IMPORT_PREFIX", "" +
            "\n" +
            "<body>\n");
    public static final DataKey<String> XHTML_IMPORT_SUFFIX = new DataKey<String>("XHTML_IMPORT_SUFFIX", "\n</body>");
    public static final DataKey<String> DOC_URL = new DataKey<String>("DOC_URL", "");

    public static final DataKey<Boolean> RECHECK_UNDEFINED_REFERENCES = new DataKey<Boolean>("RECHECK_UNDEFINED_REFERENCES", false);
    public static final DataKey<Boolean> PERCENT_ENCODE_URLS = new DataKey<Boolean>("PERCENT_ENCODE_URLS", false);
    public static final DataKey<Boolean> ESCAPE_HTML = new DataKey<Boolean>("ESCAPE_HTML", false);
    public static final DataKey<Boolean> ESCAPE_HTML_BLOCKS = new DynamicDefaultKey<Boolean>("ESCAPE_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_HTML.getFrom(holder);
        }
    });

    public static final DataKey<Boolean> ESCAPE_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<Boolean>("ESCAPE_HTML_COMMENT_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_HTML_BLOCKS.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML = new DynamicDefaultKey<Boolean>("ESCAPE_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> ESCAPE_INLINE_HTML_COMMENTS = new DynamicDefaultKey<Boolean>("ESCAPE_INLINE_HTML_COMMENTS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return ESCAPE_INLINE_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_HTML = new DataKey<Boolean>("SUPPRESS_HTML", false);
    public static final DataKey<Boolean> SUPPRESS_HTML_BLOCKS = new DynamicDefaultKey<Boolean>("SUPPRESS_HTML_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SUPPRESS_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_HTML_COMMENT_BLOCKS = new DynamicDefaultKey<Boolean>("SUPPRESS_HTML_COMMENT_BLOCKS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return holder != null && (holder.contains(SUPPRESS_HTML) || holder.contains(SUPPRESS_HTML_BLOCKS)) ? SUPPRESS_HTML_BLOCKS.getFrom(holder) : true;
        }
    });
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML = new DynamicDefaultKey<Boolean>("SUPPRESS_INLINE_HTML", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return SUPPRESS_HTML.getFrom(holder);
        }
    });
    public static final DataKey<Boolean> SUPPRESS_INLINE_HTML_COMMENTS = new DynamicDefaultKey<Boolean>("SUPPRESS_INLINE_HTML_COMMENTS", new DataValueFactory<Boolean>() {
        @Override
        public Boolean create(DataHolder holder) {
            return holder != null && (holder.contains(SUPPRESS_HTML) || holder.contains(SUPPRESS_INLINE_HTML)) ? SUPPRESS_INLINE_HTML.getFrom(holder) : true;
        }
    });

    private final List<NodeDocxRendererFactory> nodeFormatterFactories;
    private final DocxRendererOptions rendererOptions;
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

    public static String exportToDocx(String html, String url) {
        return "";
    }

    public static void exportToDocx(final OutputStream os, final String html, final String url, final DataHolder options) {
        //exportToDocx(os, html, url, options.get(DEFAULT_TEXT_DIRECTION));
    }

    public static void exportToDocx(final WordprocessingMLPackage out, final DataHolder options, final String html, final String url) {
        try {
            setDefaultStyleAndNumbering(out, options);
            MainDocumentPart documentPart = out.getMainDocumentPart();
            XHTMLImporterImpl importer = new XHTMLImporterImpl(out);
            importer.convert(html, url);
        } catch (Exception e) {
            e.printStackTrace();
            // LOG exception
        }
    }

    public static WordprocessingMLPackage getDefaultTemplate() {
        try {
            final InputStream inputStream = getResourceInputStream("/empty.xml");
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
        if (XHTML_IMPORT.getFrom(options)) {
            String html = HtmlRenderer.builder(options).build().render(node);
            StringBuilder sb = new StringBuilder();
            sb.append(XHTML_IMPORT_PREFIX.getFrom(options));
            sb.append(html);
            sb.append(XHTML_IMPORT_SUFFIX.getFrom(options));
            exportToDocx(output, options, sb.toString(), DOC_URL.getFrom(options));
        } else {
            MainDocxRenderer renderer = new MainDocxRenderer(options, output, node.getDocument());
            renderer.render(node);
        }
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
            return outputStream.toString("UTF-8") + "\n";
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
        private final HashSet<FormatterExtension> loadedExtensions = new HashSet<FormatterExtension>();
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
         * @param extensions extensions to use on this HTML renderer
         * @return {@code this}
         */
        public Builder extensions(Iterable<? extends Extension> extensions) {
            // first give extensions a chance to modify options
            for (Extension extension : extensions) {
                if (extension instanceof FormatterExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        FormatterExtension formatterExtension = (FormatterExtension) extension;
                        formatterExtension.rendererOptions(this);
                    }
                }
            }

            for (Extension extension : extensions) {
                if (extension instanceof FormatterExtension) {
                    if (!loadedExtensions.contains(extension)) {
                        FormatterExtension formatterExtension = (FormatterExtension) extension;
                        formatterExtension.extend(this);
                        loadedExtensions.add(formatterExtension);
                    }
                }
            }
            return this;
        }
    }

    /**
     * Extension for {@link DocxRenderer}.
     */
    public interface FormatterExtension extends Extension {
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

    private final static Iterable<? extends Node> NULL_ITERABLE = new Iterable<Node>() {
        @Override
        public Iterator<Node> iterator() {
            return null;
        }
    };

    private class MainDocxRenderer implements DocxRendererContext {
        private final Document document;
        private final Map<Class<?>, NodeDocxRendererHandler> renderers;
        private final SubClassingBag<Node> collectedNodes;

        private final List<PhasedNodeDocxRenderer> phasedFormatters;
        private final Set<DocxRendererPhase> renderingPhases;
        private final DataHolder options;
        private DocxRendererPhase phase;
        private Node renderingNode;
        private final LinkResolver[] myLinkResolvers;
        private final HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<LinkType, HashMap<String, ResolvedLink>>();

        // docx stuff
        final HashMap<String, Style> styles;
        final HashMap<String, Relationship> hyperlinks;
        private final WordprocessingMLPackage wordprocessingPackage;
        private final MainDocumentPart mainDocumentPart;
        private final Body body;
        private final ObjectFactory wmlObjectFactory;
        private ArrayList<ValueRunnable<PPr>> pInitializerList;
        private ArrayList<ValueRunnable<RPr>> rInitializerList;
        private P p;
        private ValueRunnable<R> rAdopter;
        private Stack<Runnable> beforeP;
        private Stack<Runnable> afterP;
        private boolean inBeforeAfterP;

        MainDocxRenderer(DataHolder options, WordprocessingMLPackage out, Document document) {
            this.options = new ScopedDataSet(options, document);
            this.document = document;
            this.renderers = new HashMap<Class<?>, NodeDocxRendererHandler>(32);
            this.renderingPhases = new HashSet<DocxRendererPhase>(DocxRendererPhase.values().length);
            final Set<Class> collectNodeTypes = new HashSet<Class>(100);
            this.phasedFormatters = new ArrayList<PhasedNodeDocxRenderer>(nodeFormatterFactories.size());
            this.styles = new HashMap<String, Style>();
            this.hyperlinks = new HashMap<String, Relationship>();
            this.myLinkResolvers = new LinkResolver[linkResolverFactories.size()];
            this.beforeP = new Stack<Runnable>();
            this.afterP = new Stack<Runnable>();

            this.wmlObjectFactory = new ObjectFactory();
            this.wordprocessingPackage = out;

            setDefaultStyleAndNumbering(out, this.options);

            this.mainDocumentPart = out.getMainDocumentPart();
            this.body = ((org.docx4j.wml.Document)this.mainDocumentPart.getJaxbElement()).getBody();
            this.rInitializerList = new ArrayList<ValueRunnable<RPr>>();
            this.pInitializerList = new ArrayList<ValueRunnable<PPr>>();

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                myLinkResolvers[i] = linkResolverFactories.get(i).create(this);
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
        public String encodeUrl(CharSequence url) {
            if (rendererOptions.percentEncodeUrls) {
                return Escaping.percentEncodeUrl(url);
            } else {
                return url instanceof String ? (String) url : String.valueOf(url);
            }
        }

        @Override
        public ObjectFactory getObjectFactory() {
            return wmlObjectFactory;
        }

        @Override
        public void pushRPrInitializer(ValueRunnable<RPr> runnable) {
            if (rInitializerList == null) {
                rInitializerList = new ArrayList<ValueRunnable<RPr>>();
            }
            rInitializerList.add(runnable);
        }

        @Override
        public void popRPrInitializer(ValueRunnable<RPr> runnable) {
            if (rInitializerList == null || rInitializerList.isEmpty()) {
                throw new IllegalStateException("popRPrInitializer called with empty initializer list");
            }
            if (rInitializerList.get(rInitializerList.size() - 1) != runnable) {
                throw new IllegalStateException("popRPrInitializer called with " + runnable + " when last initializer is " + rInitializerList.get(rInitializerList.size() - 1));
            }
            rInitializerList.remove(rInitializerList.size() - 1);
        }

        void initializeRPr(RPr rPr) {
            for (ValueRunnable<RPr> initializer : rInitializerList) {
                initializer.run(rPr);
            }
        }

        @Override
        public void pushPPrInitializer(ValueRunnable<PPr> runnable) {
            if (pInitializerList == null) {
                pInitializerList = new ArrayList<ValueRunnable<PPr>>();
            }
            pInitializerList.add(runnable);
            startNewP();
        }

        @Override
        public void popPPrInitializer(ValueRunnable<PPr> runnable) {
            if (pInitializerList == null || pInitializerList.isEmpty()) {
                throw new IllegalStateException("popPPrInitializer called with empty initializer list");
            }
            if (pInitializerList.get(pInitializerList.size() - 1) != runnable) {
                throw new IllegalStateException("popPPrInitializer called with " + runnable + " when last initializer is " + pInitializerList.get(pInitializerList.size() - 1));
            }
            pInitializerList.remove(pInitializerList.size() - 1);
            startNewP();
        }

        void initializePPr(PPr pPr) {
            for (ValueRunnable<PPr> initializer : pInitializerList) {
                initializer.run(pPr);
            }
        }

        public boolean haveRPrInitializers() {
            return !rInitializerList.isEmpty();
        }

        public boolean havePPrInitializers() {
            return !pInitializerList.isEmpty();
        }

        @Override
        public P createP() {
            // reset all rInitializers, if we are in a new paragraph with one previously existing
            if (this.p != null) startNewP();

            if (!beforeP.isEmpty() && !inBeforeAfterP) {
                try {
                    inBeforeAfterP = true;
                    beforeP.peek().run();
                } finally {
                    inBeforeAfterP = false;
                }
            }

            P p = wmlObjectFactory.createP();
            mainDocumentPart.getContent().add(p);
            PPr pPr = wmlObjectFactory.createPPr();
            p.setPPr(pPr);

            if (havePPrInitializers()) {
                initializePPr(pPr);
            }

            this.p = p;
            return p;
        }

        private void startNewP() {
            if (this.p != null && !afterP.isEmpty() && !inBeforeAfterP) {
                try {
                    inBeforeAfterP = true;
                    afterP.peek().run();
                } finally {
                    inBeforeAfterP = false;
                    this.p = null;
                }
            }
            rInitializerList.clear();
            rAdopter = null;
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
            R r = wmlObjectFactory.createR();
            RPr rPr = wmlObjectFactory.createRPr();
            r.setRPr(rPr);

            if (haveRPrInitializers()) {
                initializeRPr(rPr);
            }
            if (rAdopter != null) {
                rAdopter.run(r);
            } else {
                p.getContent().add(r);
            }
            return r;
        }

        @Override
        public PPrBase.Ind setPPrIndent(final PPr pPr, final BigInteger left, final BigInteger right, final BigInteger hanging, final int flags) {
            PPrBase.Ind prInd = pPr.getInd();

            if (left != null || right != null || hanging != null) {
                if (prInd == null) {
                    prInd = wmlObjectFactory.createPPrBaseInd();
                    pPr.setInd(prInd);
                }

                if (left != null) {
                    BigInteger value = prInd.getLeft();
                    if ((flags & ADD_HANGING_TO_LEFT) != 0) {
                        BigInteger hang = prInd.getHanging();
                        if (hang != null) {
                            value = value == null ? hang : value.add(hang);
                        }
                        if (hanging != null) {
                            value = value == null ? hanging : value.add(hang);
                        }
                    }
                    prInd.setLeft((flags & ADD_LEFT) == 0 || value == null ? left : left.add(value));
                }

                if (right != null) {
                    final BigInteger value = prInd.getRight();
                    prInd.setRight((flags & ADD_LEFT) == 0 || value == null ? right : right.add(value));
                }

                if ((flags & ADD_HANGING_TO_LEFT) == 0) {
                    if (hanging != null) {
                        final BigInteger value = prInd.getHanging();
                        prInd.setHanging((flags & ADD_HANGING) == 0 || value == null ? hanging : hanging.add(value));
                    }
                } else {
                    prInd.setHanging(BigInteger.ZERO);
                }
            }

            return prInd;
        }

        @Override
        public void withRPrInitializer(final ValueRunnable<RPr> initializer, final Runnable runnable) {
            pushRPrInitializer(initializer);
            runnable.run();
            popRPrInitializer(initializer);
        }

        @Override
        public void setAdopterR(final ValueRunnable<R> adopter) {
            if (rAdopter != null) {
                throw new IllegalStateException("setAdopterR called for " + adopter + " when one is already set to " + rAdopter);
            }
            rAdopter = adopter;
        }

        @Override
        public void clearAdopterR(final ValueRunnable<R> adopter) {
            if (rAdopter == null) {
                throw new IllegalStateException("clearAdopterR called for " + adopter + " when one is not set");
            }
            if (rAdopter != adopter) {
                throw new IllegalStateException("clearAdopterR called for " + adopter + " when one is set to " + rAdopter);
            }
            rAdopter = null;
        }

        @Override
        public void setBeforeP(final Runnable runnable) {
            beforeP.push(runnable);
        }

        @Override
        public void clearBeforeP(final Runnable runnable) {
            if (beforeP.isEmpty()) {
                throw new IllegalStateException("clearBeforeP called with empty list");
            }
            if (beforeP.peek() != runnable) {
                throw new IllegalStateException("clearBeforeP called with " + runnable + " when last is " + beforeP.peek());
            }
            beforeP.pop();
        }

        @Override
        public Runnable getBeforeP() {
            return beforeP.isEmpty() ? null : beforeP.peek();
        }

        @Override
        public Runnable getAfterP() {
            return afterP.isEmpty() ? null : afterP.peek();
        }

        @Override
        public void setAfterP(final Runnable runnable) {
            afterP.push(runnable);
        }

        @Override
        public void clearAfterP(final Runnable runnable) {
            if (afterP.isEmpty()) {
                throw new IllegalStateException("clearAfterP called with empty list");
            }
            if (afterP.peek() != runnable) {
                throw new IllegalStateException("clearAfterP called with " + runnable + " when last is " + afterP.peek());
            }
            afterP.pop();
        }

        @Override
        public void withPPrInitializer(final ValueRunnable<PPr> initializer, final Runnable runnable) {
            pushPPrInitializer(initializer);
            runnable.run();
            popPPrInitializer(initializer);
        }

        @Override
        public org.docx4j.wml.Text addWrappedText(final R r) {
            // Create object for t (wrapped in JAXBElement)
            org.docx4j.wml.Text text = wmlObjectFactory.createText();
            JAXBElement<org.docx4j.wml.Text> textWrapped = wmlObjectFactory.createRT(text);
            r.getContent().add(textWrapped);
            return text;
        }

        @Override
        public org.docx4j.wml.Text text(final String text) {
            R r = createR();
            org.docx4j.wml.Text textElem = addWrappedText(r);
            textElem.setValue(text);
            textElem.setSpace("preserve");
            return textElem;
        }

        @Override
        public BooleanDefaultTrue getBooleanDefaultTrue() {
            return wmlObjectFactory.createBooleanDefaultTrue();
        }

        @Override
        public BooleanDefaultFalse getBooleanDefaultFalse() {
            return wmlObjectFactory.createBooleanDefaultFalse();
        }

        @Override
        public WordprocessingMLPackage getWordprocessingPackage() {
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
                    Node currentNode = getCurrentNode();

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
        public void render(Node node) {
            if (node instanceof Document) {
                // here we render multiple phases
                for (DocxRendererPhase phase : DocxRendererPhase.values()) {
                    if (phase != DocxRendererPhase.DOCUMENT && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;
                    // here we render multiple phases
                    if (getPhase() == DocxRendererPhase.DOCUMENT) {
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
                    Node oldNode = this.renderingNode;
                    renderingNode = node;
                    nodeRenderer.render(node, this);
                    renderingNode = oldNode;
                } else {
                    // default behavior is controlled by generic Node.class that is implemented in CoreNodeDocxRenderer
                    throw new IllegalStateException("Core Node DocxRenderer should implement generic Node renderer");
                }
            }
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(getResourceInputStream(resourcePath), Charset.forName("UTF-8")));
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
