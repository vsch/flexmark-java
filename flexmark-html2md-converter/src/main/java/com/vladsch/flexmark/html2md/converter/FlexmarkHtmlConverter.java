package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.html.renderer.HeaderIdGeneratorFactory;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.html2md.converter.internal.HtmlConverterCoreNodeRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.dependency.DependencyHandler;
import com.vladsch.flexmark.util.dependency.FlatDependencyHandler;
import com.vladsch.flexmark.util.dependency.ResolvedDependencies;
import com.vladsch.flexmark.util.format.RomanNumeral;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.CellAlignment;
import com.vladsch.flexmark.util.html.LineFormattingAppendable;
import com.vladsch.flexmark.util.html.LineFormattingAppendableImpl;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Renders a tree of nodes to HTML.
 * <p>
 * Start with the {@link #builder} method to configure the renderer. Example:
 * <pre><code>
 * HtmlRenderer renderer = builder().escapeHtml(true).build();
 * renderer.render(node);
 * </code></pre>
 */
@SuppressWarnings("WeakerAccess")
public class FlexmarkHtmlConverter {
    /**
     * output control for FormattingAppendable, see {@link LineFormattingAppendable#setOptions(int)}
     */
    public static final DataKey<Integer> FORMAT_FLAGS = new DataKey<>("FORMAT_FLAGS", LineFormattingAppendable.SUPPRESS_TRAILING_WHITESPACE | LineFormattingAppendable.COLLAPSE_WHITESPACE | LineFormattingAppendable.PREFIX_PRE_FORMATTED);
    public static final DataKey<Integer> MAX_BLANK_LINES = new DataKey<>("MAX_BLANK_LINES", 2);
    public static final DataKey<Integer> MAX_TRAILING_BLANK_LINES = new DataKey<>("MAX_TRAILING_BLANK_LINES", 1);

    public static final DataKey<Boolean> LIST_CONTENT_INDENT = new DataKey<>("LIST_CONTENT_INDENT", true);
    public static final DataKey<Boolean> SETEXT_HEADINGS = new DataKey<>("SETEXT_HEADINGS", true);
    public static final DataKey<Boolean> OUTPUT_UNKNOWN_TAGS = new DataKey<>("OUTPUT_UNKNOWN_TAGS", false);
    public static final DataKey<Boolean> TYPOGRAPHIC_QUOTES = new DataKey<>("TYPOGRAPHIC_QUOTES", true);
    public static final DataKey<Boolean> TYPOGRAPHIC_SMARTS = new DataKey<>("TYPOGRAPHIC_SMARTS", true);
    public static final DataKey<Boolean> EXTRACT_AUTO_LINKS = new DataKey<>("EXTRACT_AUTO_LINKS", true);
    public static final DataKey<Boolean> OUTPUT_ATTRIBUTES_ID = new DataKey<>("OUTPUT_ATTRIBUTES_ID", true);
    public static final DataKey<String> OUTPUT_ATTRIBUTES_NAMES_REGEX = new DataKey<>("OUTPUT_ATTRIBUTES_NAMES_REGEX", "");
    public static final DataKey<Boolean> WRAP_AUTO_LINKS = new DataKey<>("WRAP_AUTO_LINKS", true);
    public static final DataKey<Boolean> RENDER_COMMENTS = new DataKey<>("RENDER_COMMENTS", false);
    public static final DataKey<Boolean> DOT_ONLY_NUMERIC_LISTS = new DataKey<>("DOT_ONLY_NUMERIC_LISTS", true);
    public static final DataKey<Boolean> COMMENT_ORIGINAL_NON_NUMERIC_LIST_ITEM = new DataKey<>("COMMENT_ORIGINAL_NON_NUMERIC_LIST_ITEM", false);
    public static final DataKey<Boolean> PRE_CODE_PRESERVE_EMPHASIS = new DataKey<>("PRE_CODE_PRESERVE_EMPHASIS", false);
    public static final DataKey<Character> ORDERED_LIST_DELIMITER = new DataKey<>("ORDERED_LIST_DELIMITER", '.');
    public static final DataKey<Character> UNORDERED_LIST_DELIMITER = new DataKey<>("UNORDERED_LIST_DELIMITER", '*');
    public static final DataKey<Integer> DEFINITION_MARKER_SPACES = new DataKey<>("DEFINITION_MARKER_SPACES", 3);
    public static final DataKey<Integer> MIN_SETEXT_HEADING_MARKER_LENGTH = new DataKey<>("MIN_SETEXT_HEADING_MARKER_LENGTH", 3);
    public static final DataKey<String> CODE_INDENT = new DataKey<>("CODE_INDENT", "    ");
    public static final DataKey<String> NBSP_TEXT = new DataKey<>("NBSP_TEXT", " ");
    public static final DataKey<String> EOL_IN_TITLE_ATTRIBUTE = new DataKey<>("EOL_IN_TITLE_ATTRIBUTE", " ");
    public static final DataKey<String> THEMATIC_BREAK = new DataKey<>("THEMATIC_BREAK", "*** ** * ** ***");

    // Render HTML contents - UNWRAPPED
    public static final DataKey<String[]> UNWRAPPED_TAGS = new DataKey<>("UNWRAPPED_TAGS", new String[] {
            "article",
            "address",
            "frameset",
            "section",
            "small",
            "iframe",
    });

    // Render HTML contents - WRAPPED in original HTML tag
    public static final DataKey<String[]> WRAPPED_TAGS = new DataKey<>("WRAPPED_TAGS", new String[] {
            "kbd",
            "var",
    });

    // regex to use for processing id attributes, if matched then will concatenate all groups which are not empty, if result string is empty after trimming then no id will be generated
    // if value empty then no processing is done
    public static final DataKey<String> OUTPUT_ID_ATTRIBUTE_REGEX = new DataKey<>("OUTPUT_ID_ATTRIBUTE_REGEX", "^user-content-(.*)$");

    public static final DataKey<Integer> TABLE_MIN_SEPARATOR_COLUMN_WIDTH = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_COLUMN_WIDTH;
    public static final DataKey<Integer> TABLE_MIN_SEPARATOR_DASHES = TableFormatOptions.FORMAT_TABLE_MIN_SEPARATOR_DASHES;
    public static final DataKey<Boolean> TABLE_LEAD_TRAIL_PIPES = TableFormatOptions.FORMAT_TABLE_LEAD_TRAIL_PIPES;
    public static final DataKey<Boolean> TABLE_SPACE_AROUND_PIPES = TableFormatOptions.FORMAT_TABLE_SPACE_AROUND_PIPES;
    public static final DataKey<TableCaptionHandling> TABLE_CAPTION = TableFormatOptions.FORMAT_TABLE_CAPTION;
    public static final DataKey<Boolean> LISTS_END_ON_DOUBLE_BLANK = new DataKey<>("LISTS_END_ON_DOUBLE_BLANK", false);
    public static final DataKey<Boolean> DIV_AS_PARAGRAPH = new DataKey<>("DIV_AS_PARAGRAPH", false);
    public static final DataKey<Boolean> BR_AS_PARA_BREAKS = new DataKey<>("BR_AS_PARA_BREAKS", true);
    public static final DataKey<Boolean> BR_AS_EXTRA_BLANK_LINES = new DataKey<>("BR_AS_EXTRA_BLANK_LINES", true);

    public static final DataKey<Boolean> ADD_TRAILING_EOL = new DataKey<>("ADD_TRAILING_EOL", true);

    public static final DataKey<Boolean> SKIP_HEADING_1 = new DataKey<>("SKIP_HEADING_1", false);
    public static final DataKey<Boolean> SKIP_HEADING_2 = new DataKey<>("SKIP_HEADING_2", false);
    public static final DataKey<Boolean> SKIP_HEADING_3 = new DataKey<>("SKIP_HEADING_3", false);
    public static final DataKey<Boolean> SKIP_HEADING_4 = new DataKey<>("SKIP_HEADING_4", false);
    public static final DataKey<Boolean> SKIP_HEADING_5 = new DataKey<>("SKIP_HEADING_5", false);
    public static final DataKey<Boolean> SKIP_HEADING_6 = new DataKey<>("SKIP_HEADING_6", false);
    public static final DataKey<Boolean> SKIP_ATTRIBUTES = new DataKey<>("SKIP_ATTRIBUTES", false);
    public static final DataKey<Boolean> SKIP_FENCED_CODE = new DataKey<>("SKIP_FENCED_CODE", false);
    public static final DataKey<Boolean> SKIP_CHAR_ESCAPE = new DataKey<>("SKIP_CHAR_ESCAPE", false);

    public static final DataKey<ExtensionConversion> EXT_INLINE_STRONG = new DataKey<>("EXT_INLINE_STRONG", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_INLINE_EMPHASIS = new DataKey<>("EXT_INLINE_EMPHASIS", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_INLINE_CODE = new DataKey<>("EXT_INLINE_CODE", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_INLINE_DEL = new DataKey<>("EXT_INLINE_DEL", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_INLINE_INS = new DataKey<>("EXT_INLINE_INS", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_INLINE_SUB = new DataKey<>("EXT_INLINE_SUB", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_INLINE_SUP = new DataKey<>("EXT_INLINE_SUP", ExtensionConversion.MARKDOWN);
    public static final DataKey<ExtensionConversion> EXT_MATH = new DataKey<>("EXT_MATH", ExtensionConversion.HTML);

    public static final DataKey<ExtensionConversion> EXT_TABLES = new DataKey<>("EXT_TABLES", ExtensionConversion.MARKDOWN);

    public static final DataKey<LinkConversion> EXT_INLINE_LINK = new DataKey<>("EXT_INLINE_LINK", LinkConversion.MARKDOWN_EXPLICIT);
    public static final DataKey<LinkConversion> EXT_INLINE_IMAGE = new DataKey<>("EXT_INLINE_IMAGE", LinkConversion.MARKDOWN_EXPLICIT);
    public static final DataKey<Ref<com.vladsch.flexmark.util.ast.Document>> FOR_DOCUMENT = new DataKey<>("FOR_DOCUMENT", new Ref<>((com.vladsch.flexmark.util.ast.Document) null));
    public static final DataKey<? extends Map<String, String>> TYPOGRAPHIC_REPLACEMENT_MAP = new DataKey<>("TYPOGRAPHIC_REPLACEMENT_MAP", new HashMap<>());

    /**
     * if true then will dump HTML tree of body element to console when using {@link #convert(String, Appendable)}(String)
     */
    public static final DataKey<Boolean> DUMP_HTML_TREE = new DataKey<>("DUMP_HTML_TREE", false);

    /**
     * If true then will ignore rows with th columns after rows with td columns have been
     * emitted to the table.
     * <p>
     * If false then will convert these to regular columns.
     */
    public static final DataKey<Boolean> IGNORE_TABLE_HEADING_AFTER_ROWS = new DataKey<>("IGNORE_TABLE_HEADING_AFTER_ROWS", true);

    // HTML node names (all lowercase)
    public static final String A_NODE = "a";
    public static final String ABBR_NODE = "abbr";
    public static final String ASIDE_NODE = "aside";
    public static final String BR_NODE = "br";
    public static final String BLOCKQUOTE_NODE = "blockquote";
    public static final String CODE_NODE = "code";
    public static final String IMG_NODE = "img";
    public static final String DEL_NODE = "del";
    public static final String STRIKE_NODE = "strike";
    public static final String DIV_NODE = "div";
    public static final String DD_NODE = "dd";
    public static final String DL_NODE = "dl";
    public static final String DT_NODE = "dt";
    public static final String I_NODE = "i";
    public static final String EM_NODE = "em";
    public static final String B_NODE = "b";
    public static final String STRONG_NODE = "strong";
    public static final String EMOJI_NODE = "g-emoji";
    public static final String INPUT_NODE = "input";
    public static final String INS_NODE = "ins";
    public static final String U_NODE = "u";
    public static final String SUB_NODE = "sub";
    public static final String SUP_NODE = "sup";
    public static final String HR_NODE = "hr";
    public static final String OL_NODE = "ol";
    public static final String UL_NODE = "ul";
    public static final String LI_NODE = "li";
    public static final String TABLE_NODE = "table";
    public static final String TBODY_NODE = "tbody";
    public static final String TD_NODE = "td";
    public static final String TH_NODE = "th";
    public static final String THEAD_NODE = "thead";
    public static final String TR_NODE = "tr";
    public static final String CAPTION_NODE = "caption";
    public static final String SVG_NODE = "svg";
    public static final String P_NODE = "p";
    public static final String PRE_NODE = "pre";
    public static final String MATH_NODE = "math";
    public static final String SPAN_NODE = "span";
    public static final String TEXT_NODE = "#text";
    public static final String COMMENT_NODE = "#comment";
    public static final String H1_NODE = "h1";
    public static final String H2_NODE = "h2";
    public static final String H3_NODE = "h3";
    public static final String H4_NODE = "h4";
    public static final String H5_NODE = "h5";
    public static final String H6_NODE = "h6";
    public static final String DEFAULT_NODE = "";

    public static String[] EXPLICIT_LINK_TEXT_TAGS = new String[] { IMG_NODE };

    private static final Map<Object, CellAlignment> TABLE_CELL_ALIGNMENTS = new LinkedHashMap<>();
    private static final String EMOJI_ALT_PREFIX = "emoji ";
    static {
        TABLE_CELL_ALIGNMENTS.put(Pattern.compile("\\bleft\\b"), CellAlignment.LEFT);
        TABLE_CELL_ALIGNMENTS.put(Pattern.compile("\\bcenter\\b"), CellAlignment.CENTER);
        TABLE_CELL_ALIGNMENTS.put(Pattern.compile("\\bright\\b"), CellAlignment.RIGHT);
        TABLE_CELL_ALIGNMENTS.put("text-left", CellAlignment.LEFT);
        TABLE_CELL_ALIGNMENTS.put("text-center", CellAlignment.CENTER);
        TABLE_CELL_ALIGNMENTS.put("text-right", CellAlignment.RIGHT);
    }

    private static final Map<String, String> SPECIAL_CHARS_MAP = new HashMap<>();
    private static final String TYPOGRAPHIC_QUOTES_PIPED = "“|”|‘|’|«|»|&ldquo;|&rdquo;|&lsquo;|&rsquo;|&apos;|&laquo;|&raquo;";
    private static final String TYPOGRAPHIC_SMARTS_PIPED = "…|–|—|&hellip;|&endash;|&emdash;";
    static {
        SPECIAL_CHARS_MAP.put("“", "\"");
        SPECIAL_CHARS_MAP.put("”", "\"");
        SPECIAL_CHARS_MAP.put("&ldquo;", "\"");
        SPECIAL_CHARS_MAP.put("&rdquo;", "\"");
        SPECIAL_CHARS_MAP.put("‘", "'");
        SPECIAL_CHARS_MAP.put("’", "'");
        SPECIAL_CHARS_MAP.put("&lsquo;", "'");
        SPECIAL_CHARS_MAP.put("&rsquo;", "'");
        SPECIAL_CHARS_MAP.put("&apos;", "'");
        SPECIAL_CHARS_MAP.put("«", "<<");
        SPECIAL_CHARS_MAP.put("&laquo;", "<<");
        SPECIAL_CHARS_MAP.put("»", ">>");
        SPECIAL_CHARS_MAP.put("&raquo;", ">>");
        SPECIAL_CHARS_MAP.put("…", "...");
        SPECIAL_CHARS_MAP.put("&hellip;", "...");
        SPECIAL_CHARS_MAP.put("–", "--");
        SPECIAL_CHARS_MAP.put("&endash;", "--");
        SPECIAL_CHARS_MAP.put("—", "---");
        SPECIAL_CHARS_MAP.put("&emdash;", "---");
    }

    private static final Pattern NUMERIC_DOT_LIST = Pattern.compile("^(\\d+)\\.\\s*$");
    private static final Pattern NUMERIC_PAREN_LIST = Pattern.compile("^(\\d+)\\)\\s*$");
    private static final Pattern NON_NUMERIC_DOT_LIST = Pattern.compile("^((?:(?:" + RomanNumeral.ROMAN_NUMERAL.pattern() + ")|(?:" + RomanNumeral.LOWERCASE_ROMAN_NUMERAL.pattern() + ")|[a-z]+|[A-Z]+))\\.\\s*$");
    private static final Pattern NON_NUMERIC_PAREN_LIST = Pattern.compile("^((?:[a-z]+|[A-Z]+))\\)\\s*$");
    private static final Pattern BULLET_LIST = Pattern.compile("^([·])\\s*$");
    private static final Pattern ALPHA_NUMERAL = Pattern.compile("^[a-z]+|[A-Z]+$");

    public static final DataKey<Map<Object, CellAlignment>> TABLE_CELL_ALIGNMENT_MAP = new DataKey<>("TABLE_CELL_ALIGNMENT_MAP", TABLE_CELL_ALIGNMENTS);

    final List<HtmlNodeRendererFactory> nodeConverterFactories;
    final HtmlConverterOptions htmlConverterOptions;
    private final DataHolder options;
    private final List<DelegatingNodeRendererFactoryWrapper> nodeRendererFactories;
    private final List<HtmlLinkResolverFactory> linkResolverFactories;

    FlexmarkHtmlConverter(Builder builder) {
        this.options = builder.toImmutable();
        this.htmlConverterOptions = new HtmlConverterOptions(this.options);
        this.nodeConverterFactories = new ArrayList<>(builder.nodeRendererFactories.size() + 1);
        this.nodeConverterFactories.addAll(builder.nodeRendererFactories);

        // resolve renderer dependencies
        List<DelegatingNodeRendererFactoryWrapper> nodeRenderers = new ArrayList<>(builder.nodeRendererFactories.size());

        for (int i = builder.nodeRendererFactories.size() - 1; i >= 0; i--) {
            HtmlNodeRendererFactory nodeRendererFactory = builder.nodeRendererFactories.get(i);
            Set<Class<? extends DelegatingNodeRendererFactoryWrapper>>[] myDelegates = new Set[] { null };

            nodeRenderers.add(new DelegatingNodeRendererFactoryWrapper(nodeRenderers, nodeRendererFactory));
        }

        // Add as last. This means clients can override the rendering of core nodes if they want by default
        HtmlConverterCoreNodeRendererFactory nodeRendererFactory = new HtmlConverterCoreNodeRendererFactory();
        nodeRenderers.add(new DelegatingNodeRendererFactoryWrapper(nodeRenderers, nodeRendererFactory));

        FlexmarkHtmlConverter.RendererDependencyHandler resolver = new FlexmarkHtmlConverter.RendererDependencyHandler();
        nodeRendererFactories = resolver.resolveDependencies(nodeRenderers).getNodeRendererFactories();

        // Add as last. This means clients can override the rendering of core nodes if they want.
        this.nodeConverterFactories.add(HtmlConverterCoreNodeRenderer::new);

        this.linkResolverFactories = FlatDependencyHandler.computeDependencies(builder.linkResolverFactories);
    }

    public DataHolder getOptions() {
        return options;
    }

    /**
     * Create a new builder for configuring an {@link FlexmarkHtmlConverter}.
     *
     * @return a builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Create a new builder for configuring an {@link FlexmarkHtmlConverter}.
     *
     * @param options initialization options
     * @return a builder
     */
    public static Builder builder(DataHolder options) {
        return new Builder(options);
    }

    /**
     * Render a node to the appendable
     *
     * @param html   html to convert to markdown
     * @param output appendable to use for the output
     */
    public void convert(String html, Appendable output) {
        Document document = Jsoup.parse(html);

        if (DUMP_HTML_TREE.getFrom(getOptions())) {
            LineFormattingAppendableImpl trace = new LineFormattingAppendableImpl(0);
            trace.setIndentPrefix("  ");
            dumpHtmlTree(trace, document.body());
            System.out.println(trace.toString(0));
        }

        MainHtmlConverter converter = new MainHtmlConverter(options, new HtmlMarkdownWriter(htmlConverterOptions.formatFlags), document, null);
        converter.render(document);
        converter.flushTo(output, htmlConverterOptions.maxTrailingBlankLines);
    }

    /**
     * Parse HTML with default options
     *
     * @param html html to be parsed
     * @return resulting markdown string
     */
    public String convert(String html) {
        return convert(html, 1);
    }

    /**
     * Parse HTML with given options and max trailing blank lines
     *
     * @param html          html to be parsed
     * @param maxBlankLines max trailing blank lines, -1 will suppress trailing EOL
     * @return resulting markdown string
     */
    public String convert(String html, int maxBlankLines) {
        Document document = Jsoup.parse(html);

        if (DUMP_HTML_TREE.getFrom(getOptions())) {
            LineFormattingAppendableImpl trace = new LineFormattingAppendableImpl(0);
            trace.setIndentPrefix("  ");
            dumpHtmlTree(trace, document.body());
            System.out.println(trace.toString(0));
        }

        MainHtmlConverter converter = new MainHtmlConverter(options, new HtmlMarkdownWriter(htmlConverterOptions.formatFlags), document, null);
        converter.render(document);

        boolean eolEnd = maxBlankLines >= 0 && converter.getMarkdown().getPendingEOL() > 0;
        String s = converter.getMarkdown().toString(maxBlankLines);
        return eolEnd ? s : Utils.removeSuffix(s, "\n");
    }

    public static void dumpHtmlTree(LineFormattingAppendable out, Node node) {
        out.line().append(node.nodeName());
        for (org.jsoup.nodes.Attribute attribute : node.attributes().asList()) {
            out.append(' ').append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\"");
        }

        out.line().indent();

        for (Node child : node.childNodes()) {
            dumpHtmlTree(out, child);
        }

        out.unIndent();
    }

    /**
     * Render a node to the appendable
     *
     * @param node                  node to render
     * @param output                appendable to use for the output
     * @param maxTrailingBlankLines max blank lines allowed at end of output
     */
    public void convert(Node node, Appendable output, int maxTrailingBlankLines) {
        MainHtmlConverter renderer = new MainHtmlConverter(options, new HtmlMarkdownWriter(htmlConverterOptions.formatFlags), node.ownerDocument(), null);
        renderer.render(node);
        renderer.flushTo(output, maxTrailingBlankLines);
    }

    /**
     * Render the tree of nodes to markdown
     *
     * @param node the root node
     * @return the formatted markdown
     */
    public String convert(Node node) {
        StringBuilder sb = new StringBuilder();
        convert(node, sb, 0);
        return sb.toString();
    }

    /**
     * Builder for configuring an {@link FlexmarkHtmlConverter}. See methods for default configuration.
     */
    public static class Builder extends BuilderBase<Builder> {
        List<HtmlNodeRendererFactory> nodeRendererFactories = new ArrayList<>();
        List<HtmlLinkResolverFactory> linkResolverFactories = new ArrayList<>();
        HeaderIdGeneratorFactory htmlIdGeneratorFactory = null;

        public Builder() {
            super();
        }

        public Builder(DataHolder options) {
            super(options);
            loadExtensions();
        }

        /**
         * @return the configured {@link FlexmarkHtmlConverter}
         */
        @NotNull
        public FlexmarkHtmlConverter build() {
            return new FlexmarkHtmlConverter(this);
        }

        @Override
        protected void removeApiPoint(Object apiPoint) {
            if (apiPoint instanceof HtmlNodeRendererFactory) this.nodeRendererFactories.remove(apiPoint);
            else if (apiPoint instanceof HtmlLinkResolverFactory) this.linkResolverFactories.remove(apiPoint);
            else if (apiPoint instanceof HeaderIdGeneratorFactory) this.htmlIdGeneratorFactory = null;
            else {
                throw new IllegalStateException("Unknown data point type: " + apiPoint.getClass().getName());
            }
        }

        @Override
        protected void preloadExtension(Extension extension) {
            if (extension instanceof HtmlConverterExtension) {
                HtmlConverterExtension htmlConverterExtension = (HtmlConverterExtension) extension;
                htmlConverterExtension.rendererOptions(this);
            }
        }

        @Override
        protected boolean loadExtension(Extension extension) {
            if (extension instanceof HtmlConverterExtension) {
                HtmlConverterExtension htmlConverterExtension = (HtmlConverterExtension) extension;
                htmlConverterExtension.extend(this);
                return true;
            }
            return false;
        }

        /**
         * Add a factory for instantiating a node renderer (done when rendering). This allows to override the rendering
         * of node types or define rendering for custom node types.
         * <p>
         * If multiple node renderers for the same node type are created, the one from the factory that was added first
         * "wins". (This is how the rendering for core node types can be overridden; the default rendering comes last.)
         *
         * @param htmlNodeRendererFactory the factory for creating a node renderer
         * @return {@code this}
         */
        @SuppressWarnings("UnusedReturnValue")
        public Builder htmlNodeRendererFactory(HtmlNodeRendererFactory htmlNodeRendererFactory) {
            this.nodeRendererFactories.add(htmlNodeRendererFactory);
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
        public Builder linkResolverFactory(HtmlLinkResolverFactory linkResolverFactory) {
            this.linkResolverFactories.add(linkResolverFactory);
            addExtensionApiPoint(linkResolverFactory);
            return this;
        }
    }

    /**
     * Extension for {@link FlexmarkHtmlConverter}.
     */
    public interface HtmlConverterExtension extends Extension {
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

    final public static Iterable<? extends Node> NULL_ITERABLE = (Iterable<Node>) () -> null;

    public static class RendererDependencyStage {
        private final List<DelegatingNodeRendererFactoryWrapper> dependents;

        public RendererDependencyStage(List<DelegatingNodeRendererFactoryWrapper> dependents) {
            this.dependents = dependents;
        }
    }

    public static class RendererDependencies extends ResolvedDependencies<RendererDependencyStage> {
        private final List<DelegatingNodeRendererFactoryWrapper> nodeRendererFactories;

        public RendererDependencies(List<RendererDependencyStage> dependentStages) {
            super(dependentStages);
            List<DelegatingNodeRendererFactoryWrapper> blockPreProcessorFactories = new ArrayList<>();
            for (RendererDependencyStage stage : dependentStages) {
                blockPreProcessorFactories.addAll(stage.dependents);
            }
            this.nodeRendererFactories = blockPreProcessorFactories;
        }

        public List<DelegatingNodeRendererFactoryWrapper> getNodeRendererFactories() {
            return nodeRendererFactories;
        }
    }

    private static class RendererDependencyHandler extends DependencyHandler<DelegatingNodeRendererFactoryWrapper, RendererDependencyStage, RendererDependencies> {
        @Override
        protected Class getDependentClass(DelegatingNodeRendererFactoryWrapper dependent) {
            return dependent.getFactory().getClass();
        }

        @Override
        protected RendererDependencies createResolvedDependencies(List<RendererDependencyStage> stages) {
            return new RendererDependencies(stages);
        }

        @Override
        protected RendererDependencyStage createStage(List<DelegatingNodeRendererFactoryWrapper> dependents) {
            return new RendererDependencyStage(dependents);
        }
    }

    private class MainHtmlConverter extends HtmlNodeConverterSubContext {
        private final Document document;
        private final com.vladsch.flexmark.util.ast.Document myForDocument;
        private final Map<String, HtmlNodeRendererHandler> renderers;

        private final List<PhasedHtmlNodeRenderer> phasedFormatters;
        private final Set<HtmlConverterPhase> renderingPhases;
        private final DataHolder myOptions;
        private HtmlConverterPhase phase;

        private final HtmlConverterOptions myHtmlConverterOptions;
        private final Pattern specialCharsPattern;

        private Stack<HtmlConverterState> myStateStack;
        private Map<String, String> mySpecialCharsMap;
        private HtmlConverterState myState;
        private boolean myTrace;
        private boolean myInlineCode;
        private Parser myParser = null;
        private HashMap<LinkType, HashMap<String, ResolvedLink>> resolvedLinkMap = new HashMap<>();
        private HtmlLinkResolver[] myHtmlLinkResolvers;
        private HashMap<String, Reference> myReferenceUrlToReferenceMap;  // map of URL to reference node
        private HashSet<Reference> myExternalReferences;  // map of URL to reference node

        @Override
        public HtmlConverterState getState() {
            return myState;
        }

        MainHtmlConverter(DataHolder options, HtmlMarkdownWriter out, Document document, DataHolder parentOptions) {
            super(out);
            this.myOptions = new ScopedDataSet(parentOptions, options);
            this.renderers = new HashMap<>(32);
            this.renderingPhases = new HashSet<>(HtmlConverterPhase.values().length);
            this.phasedFormatters = new ArrayList<>(nodeConverterFactories.size());
            this.resolvedLinkMap = null;
            this.myHtmlLinkResolvers = new HtmlLinkResolver[linkResolverFactories.size()];

            out.setContext(this);

            myHtmlConverterOptions = new HtmlConverterOptions(myOptions);

            if (myHtmlConverterOptions.typographicQuotes && myHtmlConverterOptions.typographicSmarts) {
                specialCharsPattern = Pattern.compile(TYPOGRAPHIC_QUOTES_PIPED + "|" + TYPOGRAPHIC_SMARTS_PIPED);
            } else if (myHtmlConverterOptions.typographicQuotes) {
                specialCharsPattern = Pattern.compile(TYPOGRAPHIC_QUOTES_PIPED);
            } else if (myHtmlConverterOptions.typographicSmarts) {
                specialCharsPattern = Pattern.compile(TYPOGRAPHIC_SMARTS_PIPED);
            } else {
                specialCharsPattern = null;
            }

            //myTrace = true;
            myStateStack = new Stack<>();
            myReferenceUrlToReferenceMap = new HashMap<>();
            myExternalReferences = new HashSet<>();
            myState = null;

            Map<String, String> typographicReplacementMap = TYPOGRAPHIC_REPLACEMENT_MAP.getFrom(myOptions);
            if (!typographicReplacementMap.isEmpty()) {
                mySpecialCharsMap = typographicReplacementMap;
            } else {
                mySpecialCharsMap = SPECIAL_CHARS_MAP;
            }

            // The first node renderer for a node type "wins".
            for (int i = nodeConverterFactories.size() - 1; i >= 0; i--) {
                HtmlNodeRendererFactory htmlNodeRendererFactory = nodeConverterFactories.get(i);
                HtmlNodeRenderer htmlNodeRenderer = htmlNodeRendererFactory.apply(this.myOptions);
                Set<HtmlNodeRendererHandler<?>> formattingHandlers = htmlNodeRenderer.getHtmlNodeRendererHandlers();
                if (formattingHandlers == null) continue;

                for (HtmlNodeRendererHandler nodeType : formattingHandlers) {
                    // Overwrite existing renderer
                    renderers.put(nodeType.getTagName(), nodeType);
                }

                if (htmlNodeRenderer instanceof PhasedHtmlNodeRenderer) {
                    Set<HtmlConverterPhase> phases = ((PhasedHtmlNodeRenderer) htmlNodeRenderer).getHtmlConverterPhases();
                    if (phases != null) {
                        if (phases.isEmpty()) throw new IllegalStateException("PhasedNodeFormatter with empty Phases");
                        this.renderingPhases.addAll(phases);
                        this.phasedFormatters.add((PhasedHtmlNodeRenderer) htmlNodeRenderer);
                    } else {
                        throw new IllegalStateException("PhasedNodeFormatter with null Phases");
                    }
                }
            }

            for (int i = 0; i < linkResolverFactories.size(); i++) {
                myHtmlLinkResolvers[i] = linkResolverFactories.get(i).apply(this);
            }

            this.document = document;
            this.myForDocument = FlexmarkHtmlConverter.FOR_DOCUMENT.getFrom(options).value;
        }

        @Override
        public HashMap<String, Reference> getReferenceUrlToReferenceMap() {
            return myReferenceUrlToReferenceMap;
        }

        @Override
        public HashSet<Reference> getExternalReferences() {
            return myExternalReferences;
        }

        @Override
        public boolean isTrace() {
            return myTrace;
        }

        @Override
        public Stack<HtmlConverterState> getStateStack() {
            return myStateStack;
        }

        @Override
        public void setTrace(boolean trace) {
            myTrace = trace;
        }

        @Override
        public com.vladsch.flexmark.util.ast.Node parseMarkdown(String markdown) {
            if (myParser == null) {
                myParser = Parser.builder(myOptions).build();
            }
            return myParser.parse(markdown);
        }

        @Override
        public Reference getOrCreateReference(String url, String text, String title) {
            Reference reference = myReferenceUrlToReferenceMap.get(url);
            if (reference != null) {
                if (title != null && !title.trim().isEmpty()) {
                    if (reference.getTitle().isBlank()) {
                        // just add it to the existing reference
                        reference.setTitle(BasedSequenceImpl.of(title));
                        return reference;
                    } else if (reference.getTitle().equals(title.trim())) {
                        return reference;
                    }
                }
                return reference;
            }

            // create a new one with URL and if no conflict with text as id
            String referenceId = text;

            if (myReferenceUrlToReferenceMap.containsKey(referenceId)) {
                for (int i = 1; ; i++) {
                    referenceId = text + "_" + i;
                    if (!myReferenceUrlToReferenceMap.containsKey(referenceId)) {
                        break;
                    }
                }
            }

            StringBuilder sb = new StringBuilder().append("[").append(referenceId).append("]: ").append(url);

            if (title != null && !title.trim().isEmpty()) {
                sb.append(" '").append(title.replace("'", "\\'")).append("'");
            }

            com.vladsch.flexmark.util.ast.Node document = parseMarkdown(sb.toString());
            com.vladsch.flexmark.util.ast.Node firstChild = document.getFirstChild();
            if (firstChild instanceof Reference) {
                reference = (Reference) firstChild;
                myReferenceUrlToReferenceMap.put(url, reference);
                return reference;
            }
            return null;
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
            return resolveLink(linkType, url, (Attributes) null, urlEncode);
        }

        @Override
        public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
            // Resolved links not cached to allow resolving to different targets by more than URL
            //HashMap<String, ResolvedLink> resolvedLinks = resolvedLinkMap.computeIfAbsent(linkType, k -> new HashMap<String, ResolvedLink>());

            String urlSeq = String.valueOf(url);
            //ResolvedLink resolvedLink = resolvedLinks.get(urlSeq);
            ResolvedLink resolvedLink;
            //if (resolvedLink == null) {
            resolvedLink = new ResolvedLink(linkType, urlSeq, attributes);

            if (!urlSeq.isEmpty()) {
                Node currentNode = getCurrentNode();

                for (HtmlLinkResolver htmlLinkResolver : myHtmlLinkResolvers) {
                    resolvedLink = htmlLinkResolver.resolveLink(currentNode, this, resolvedLink);
                    if (resolvedLink.getStatus() != LinkStatus.UNKNOWN) break;
                }
            }

            // put it in the map
            //resolvedLinks.put(urlSeq, resolvedLink);
            //}

            return resolvedLink;
        }

        @Override
        public Node getCurrentNode() {
            return myRenderingNode;
        }

        @Override
        public DataHolder getOptions() {
            return myOptions;
        }

        @Override
        public HtmlConverterOptions getHtmlConverterOptions() {
            return htmlConverterOptions;
        }

        @Override
        public Document getDocument() {
            return document;
        }

        @Override
        public com.vladsch.flexmark.util.ast.Document getForDocument() {
            return myForDocument;
        }

        @Override
        public HtmlConverterPhase getFormattingPhase() {
            return phase;
        }

        @Override
        public void render(Node node) {
            renderNode(node, this);
        }

        @Override
        public void delegateRender() {
            renderByPreviousHandler(this);
        }

        void renderByPreviousHandler(HtmlNodeConverterSubContext subContext) {
            if (subContext.getRenderingNode() != null) {
                NodeRenderingHandlerWrapper nodeRenderer = subContext.renderingHandlerWrapper.myPreviousRenderingHandler;

                if (nodeRenderer != null) {
                    Node oldNode = subContext.getRenderingNode();
                    NodeRenderingHandlerWrapper prevWrapper = subContext.renderingHandlerWrapper;
                    try {
                        subContext.renderingHandlerWrapper = nodeRenderer;
                        nodeRenderer.myRenderingHandler.render(oldNode, subContext, subContext.getMarkdown());
                    } finally {
                        subContext.setRenderingNode(oldNode);
                        subContext.renderingHandlerWrapper = prevWrapper;
                    }
                }
            } else {
                throw new IllegalStateException("renderingByPreviousHandler called outside node rendering code");
            }
        }

        @Override
        public HtmlNodeConverterContext getSubContext() {
            HtmlMarkdownWriter writer = new HtmlMarkdownWriter(getMarkdown().getOptions());
            //writer.setContext(this); // set this temporarily
            return new SubHtmlNodeConverter(this, writer);
        }

        void renderNode(Node node, HtmlNodeConverterSubContext subContext) {
            if (node instanceof Document) {
                // here we render multiple phases
                for (HtmlConverterPhase phase : HtmlConverterPhase.values()) {
                    if (phase != HtmlConverterPhase.DOCUMENT && !renderingPhases.contains(phase)) { continue; }
                    this.phase = phase;

                    // here we render multiple phases
                    if (this.phase == HtmlConverterPhase.DOCUMENT) {
                        // rendering starts here with rendering of the body element children
                        processHtmlTree(subContext, document.body(), false, null);

                        //HtmlNodeRendererHandler nodeRenderer = renderers.get(node.getClass());
                        //if (nodeRenderer != null) {
                        //    subContext.myRenderingNode = node;
                        //    nodeRenderer.render(node, subContext, subContext.markdown);
                        //    subContext.myRenderingNode = null;
                        //}
                    } else {
                        // go through all renderers that want this phase
                        for (PhasedHtmlNodeRenderer phasedFormatter : phasedFormatters) {
                            if (phasedFormatter.getHtmlConverterPhases().contains(phase)) {
                                subContext.myRenderingNode = node;
                                phasedFormatter.renderDocument(subContext, subContext.markdown, (Document) node, phase);
                                subContext.myRenderingNode = null;
                            }
                        }
                    }
                }
            } else {
                HtmlNodeRendererHandler nodeRenderer = renderers.get(node.nodeName().toLowerCase());

                if (nodeRenderer == null) {
                    nodeRenderer = renderers.get(DEFAULT_NODE); // get default renderer
                }

                if (nodeRenderer != null) {
                    Node oldNode = this.myRenderingNode;
                    subContext.myRenderingNode = node;
                    nodeRenderer.render(node, subContext, subContext.markdown);
                    subContext.myRenderingNode = oldNode;
                } else {
                    // default behavior is controlled by empty "" tag that is implemented in CoreNodeFormatter
                    throw new IllegalStateException("Core Node Formatter should implement generic empty tag renderer");
                }
            }
        }

        @Override
        public void renderChildren(Node parent, boolean outputAttributes, Runnable prePopAction) {
            processHtmlTree(this, parent, outputAttributes, prePopAction);
        }

        @Override
        public void pushState(Node parent) {
            myStateStack.push(myState);
            myState = new HtmlConverterState(parent);
            processAttributes(parent);
        }

        @Override
        public void excludeAttributes(String... excludes) {
            for (String exclude : excludes) {
                myState.myAttributes.remove(exclude);
            }
        }

        @Override
        public void processAttributes(Node node) {
            Attributes attributes = myState.myAttributes;

            if (myHtmlConverterOptions.outputAttributesIdAttr || !myHtmlConverterOptions.outputAttributesNamesRegex.isEmpty()) {
                org.jsoup.nodes.Attributes nodeAttributes = node.attributes();
                boolean idDone = false;
                if (myHtmlConverterOptions.outputAttributesIdAttr) {
                    String id = nodeAttributes.get("id");
                    if (id == null || id.isEmpty()) {
                        id = nodeAttributes.get("name");
                    }

                    if (id != null && !id.isEmpty()) {
                        attributes.replaceValue("id", id);
                        idDone = true;
                    }
                }

                if (!myHtmlConverterOptions.outputAttributesNamesRegex.isEmpty()) {
                    for (org.jsoup.nodes.Attribute attribute : nodeAttributes) {
                        if (idDone && (attribute.getKey().equals("id") || attribute.getKey().equals("name"))) {
                            continue;
                        }
                        if (attribute.getKey().matches(myHtmlConverterOptions.outputAttributesNamesRegex)) {
                            attributes.replaceValue(attribute.getKey(), attribute.getValue());
                        }
                    }
                }
            }
        }

        @Override
        public int outputAttributes(LineFormattingAppendable out, String initialSep) {
            Attributes attributes = myState.myAttributes;
            int startOffset = out.offsetWithPending();

            if (!attributes.isEmpty() && !myHtmlConverterOptions.skipAttributes) {
                // have some
                String sep = "";
                out.append(initialSep);
                out.append("{");

                for (String attrName : attributes.keySet()) {
                    String value = attributes.getValue(attrName);
                    out.append(sep);

                    if (attrName.equals("id") || attrName.equals("name")) {
                        // process it first
                        boolean handled = false;
                        if (!myHtmlConverterOptions.outputIdAttributeRegex.isEmpty()) {
                            Matcher matcher = myHtmlConverterOptions.outputIdAttributeRegexPattern.matcher(value);
                            if (matcher.matches()) {
                                StringBuilder sb = new StringBuilder();
                                int iMax = matcher.groupCount();
                                for (int i = 0; i < iMax; i++) {
                                    String group = matcher.group(i + 1);
                                    if (group != null && !group.isEmpty()) {
                                        sb.append(group);
                                    }
                                }

                                value = sb.toString().trim();
                                handled = value.isEmpty();
                            }
                        }

                        if (!handled) {
                            out.append("#").append(value);
                        }
                    } else if (attrName.equals("class")) {
                        out.append(".").append(value);
                    } else {
                        out.append(attrName).append("=");

                        if (!value.contains("\"")) {
                            out.append('"').append(value).append('"');
                        } else if (!value.contains("\'")) {
                            out.append('\'').append(value).append('\'');
                        } else {
                            out.append('"').append(value.replace("\"", "\\\"")).append('"');
                        }
                    }

                    sep = " ";
                }
                out.append("}");
                myState.myAttributes.clear();
            }

            return out.offsetWithPending() - startOffset;
        }

        @Override
        public void transferIdToParent() {
            if (myStateStack.isEmpty())
                throw new IllegalStateException("transferIdToParent with an empty stack");
            Attribute attribute = myState.myAttributes.get("id");
            myState.myAttributes.remove("id");
            if (attribute != null && !attribute.getValue().isEmpty()) {
                HtmlConverterState state = myStateStack.peek();
                if (state != null) {
                    state.myAttributes.addValue("id", attribute.getValue());
                }
            }
        }

        @Override
        public void transferToParentExcept(String... excludes) {
            if (myStateStack.isEmpty())
                throw new IllegalStateException("transferIdToParent with an empty stack");
            Attributes attributes = new Attributes(myState.myAttributes);
            myState.myAttributes.clear();

            for (String exclude : excludes) {
                myState.myAttributes.addValue(attributes.get(exclude));
                attributes.remove(exclude);
            }

            if (!attributes.isEmpty()) {
                HtmlConverterState parentState = myStateStack.peek();
                for (String attrName : attributes.keySet()) {
                    parentState.myAttributes.addValue(attributes.get(attrName));
                }
            }
        }

        @Override
        public void transferToParentOnly(String... includes) {
            if (myStateStack.isEmpty())
                throw new IllegalStateException("transferIdToParent with an empty stack");
            Attributes attributes = new Attributes();

            for (String include : includes) {
                Attribute attribute = myState.myAttributes.get(include);
                if (attribute != null) {
                    myState.myAttributes.remove(include);
                    attributes.addValue(attribute);
                }
            }

            if (!attributes.isEmpty()) {
                HtmlConverterState parentState = myStateStack.peek();
                for (String attrName : attributes.keySet()) {
                    parentState.myAttributes.addValue(attributes.get(attrName));
                }
            }
        }

        @Override
        public void popState(LineFormattingAppendable out) {
            if (myStateStack.isEmpty())
                throw new IllegalStateException("popState with an empty stack");
            if (out != null) outputAttributes(out, "");
            myState = myStateStack.pop();
        }

        @Override
        public Node peek() {
            if (myState.myIndex < myState.myElements.size())
                return myState.myElements.get(myState.myIndex);
            return null;
        }

        @Override
        public Node peek(int skip) {
            if (myState.myIndex + skip >= 0 && myState.myIndex + skip < myState.myElements.size())
                return myState.myElements.get(myState.myIndex + skip);
            return null;
        }

        @Override
        public Node next() {
            Node next = peek();
            if (next != null) myState.myIndex++;
            return next;
        }

        @Override
        public void skip() {
            Node next = peek();
            if (next != null) myState.myIndex++;
        }

        @Override
        public Node next(int skip) {
            if (skip > 0) {
                Node next = peek(skip - 1);
                if (next != null) myState.myIndex += skip;
                return next;
            }
            return peek();
        }

        @SuppressWarnings("SameParameterValue")
        @Override
        public void skip(int skip) {
            if (skip > 0) {
                Node next = peek(skip - 1);
                if (next != null) myState.myIndex += skip;
            }
        }

        private String dumpState() {
            if (!myStateStack.isEmpty()) {
                StringBuilder sb = new StringBuilder();

                while (!myStateStack.isEmpty()) {
                    HtmlConverterState state = myStateStack.pop();
                    sb.append("\n").append(state == null ? "null" : state.toString());
                }

                return sb.toString();
            }
            return "";
        }

        // processing related helpers
        @Override
        public void processUnwrapped(Node element) {
            processUnwrapped(this, element);
        }

        void processUnwrapped(HtmlNodeConverterSubContext context, Node element) {
            // unwrap and process content
            processHtmlTree(context, element, false, null);
        }

        @Override
        public void processWrapped(Node node, Boolean isBlock, boolean escapeMarkdown) {
            FlexmarkHtmlConverter.processWrapped(this, node, isBlock, escapeMarkdown);
        }

        @Override
        public void processTextNodes(Node node, boolean stripIdAttribute) {
            processTextNodes(node, stripIdAttribute, null, null);
        }

        @Override
        public void processTextNodes(Node node, boolean stripIdAttribute, CharSequence wrapText) {
            processTextNodes(node, stripIdAttribute, wrapText, wrapText);
        }

        @Override
        public void processTextNodes(Node node, boolean stripIdAttribute, CharSequence textPrefix, CharSequence textSuffix) {
            FlexmarkHtmlConverter.processTextNodes(this, node, stripIdAttribute, textPrefix, textSuffix);
        }

        @Override
        public void wrapTextNodes(Node node, CharSequence wrapText, boolean needSpaceAround) {
            FlexmarkHtmlConverter.wrapTextNodes(this, node, wrapText, needSpaceAround);
        }

        @Override
        public String processTextNodes(Node node) {
            pushState(node);

            Node child;
            HtmlNodeConverterContext subContext = getSubContext();

            while ((child = next()) != null) {
                if (child instanceof TextNode) {
                    String text = ((TextNode) child).getWholeText();
                    subContext.getMarkdown().append(prepareText(text));
                } else if (child instanceof Element) {
                    subContext.render(child);
                }
            }

            transferIdToParent();
            popState(null);
            return subContext.getMarkdown().toString(-1);
        }

        @Override
        public void appendOuterHtml(Node node) {
            FlexmarkHtmlConverter.appendOuterHtml(this, node);
        }

        @Override
        public boolean isInlineCode() {
            return myInlineCode;
        }

        @Override
        public void setInlineCode(boolean inlineCode) {
            myInlineCode = inlineCode;
        }

        @Override
        public void inlineCode(Runnable inlineRunnable) {
            boolean oldInlineCode = myInlineCode;
            myInlineCode = true;
            try {
                inlineRunnable.run();
            } finally {
                myInlineCode = oldInlineCode;
            }
        }

        @Override
        public String prepareText(String text) {
            return prepareText(text, myInlineCode);
        }

        @Override
        public String prepareText(String text, boolean inCode) {
            if (specialCharsPattern != null) {
                Matcher matcher = specialCharsPattern.matcher(text);
                int length = text.length();
                StringBuilder sb = new StringBuilder(length * 2);
                int lastPos = 0;

                while (matcher.find()) {
                    if (lastPos < matcher.start()) {
                        sb.append(text, lastPos, matcher.start());
                    }

                    String raw = matcher.group();
                    String mapped = mySpecialCharsMap.get(raw);
                    if (mapped != null) {
                        sb.append(mapped);
                    } else {
                        sb.append(raw);
                    }
                    lastPos = matcher.end();
                }

                if (lastPos < length) {
                    sb.append(text, lastPos, length);
                }

                text = sb.toString();
            }

            if (!inCode) {
                // replace < > [ ] | ` by escaped versions
                text = escapeSpecialChars(text);
            } else {
                text = text.replace("\u00A0", " ");
            }

            return text;
        }

        @Override
        public String escapeSpecialChars(String text) {
            if (!myHtmlConverterOptions.skipCharEscape) {
                text = text.replace("\\", "\\\\");
                text = text.replace("*", "\\*");
                text = text.replace("~", "\\~");
                text = text.replace("^", "\\^");
                text = text.replace("&", "\\&");
                text = text.replace("<", "\\<").replace(">", "\\>");
                text = text.replace("[", "\\[").replace("]", "\\]");
                text = text.replace("|", "\\|").replace("`", "\\`");
                text = text.replace("\u00A0", myHtmlConverterOptions.nbspText);
            }
            return text;
        }

        @Override
        public void processConditional(ExtensionConversion extensionConversion, Node node, Runnable processNode) {
            FlexmarkHtmlConverter.processConditional(this, extensionConversion, node, processNode);
        }

        @Override
        public void renderDefault(Node node) {
            FlexmarkHtmlConverter.processDefault(this, node, getHtmlConverterOptions().outputUnknownTags);
        }

        @SuppressWarnings("WeakerAccess")
        private class SubHtmlNodeConverter extends HtmlNodeConverterSubContext implements HtmlNodeConverterContext {
            private final MainHtmlConverter myMainNodeRenderer;

            public SubHtmlNodeConverter(MainHtmlConverter mainNodeRenderer, HtmlMarkdownWriter out) {
                super(out);
                myMainNodeRenderer = mainNodeRenderer;
            }

            @Override
            public DataHolder getOptions() {return myMainNodeRenderer.getOptions();}

            @Override
            public HtmlConverterOptions getHtmlConverterOptions() {return myMainNodeRenderer.getHtmlConverterOptions();}

            @Override
            public Document getDocument() {return myMainNodeRenderer.getDocument();}

            @Override
            public HtmlConverterPhase getFormattingPhase() {return myMainNodeRenderer.getFormattingPhase();}

            @Override
            public void render(Node node) {
                myMainNodeRenderer.renderNode(node, this);
            }

            @Override
            public Node getCurrentNode() {
                return myRenderingNode;
            }

            @Override
            public HtmlNodeConverterContext getSubContext() {
                HtmlMarkdownWriter htmlWriter = new HtmlMarkdownWriter(this.markdown.getOptions());
                htmlWriter.setContext(this);
                //noinspection ReturnOfInnerClass
                return new SubHtmlNodeConverter(myMainNodeRenderer, htmlWriter);
            }

            @Override
            public void renderChildren(Node parent, boolean outputAttributes, Runnable prePopAction) {
                FlexmarkHtmlConverter.processHtmlTree(this, parent, outputAttributes, prePopAction);
            }

            @Override
            public com.vladsch.flexmark.util.ast.Document getForDocument() {
                return myMainNodeRenderer.getForDocument();
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, urlEncode);
            }

            @Override
            public ResolvedLink resolveLink(LinkType linkType, CharSequence url, Attributes attributes, Boolean urlEncode) {
                return myMainNodeRenderer.resolveLink(linkType, url, attributes, urlEncode);
            }

            @Override
            public void pushState(Node parent) {
                myMainNodeRenderer.pushState(parent);
            }

            @Override
            public void popState(LineFormattingAppendable out) {
                myMainNodeRenderer.popState(out);
            }

            @Override
            public void processAttributes(Node node) {
                myMainNodeRenderer.processAttributes(node);
            }

            @Override
            public int outputAttributes(LineFormattingAppendable out, String initialSep) {
                return myMainNodeRenderer.outputAttributes(out, initialSep);
            }

            @Override
            public void transferIdToParent() {
                myMainNodeRenderer.transferIdToParent();
            }

            @Override
            public void transferToParentExcept(String... excludes) {
                myMainNodeRenderer.transferToParentExcept(excludes);
            }

            @Override
            public void transferToParentOnly(String... includes) {
                myMainNodeRenderer.transferToParentOnly(includes);
            }

            @Override
            public Node peek() {
                return myMainNodeRenderer.peek();
            }

            @Override
            public Node peek(int skip) {
                return myMainNodeRenderer.peek(skip);
            }

            @Override
            public Node next() {
                return myMainNodeRenderer.next();
            }

            @Override
            public void skip() {
                myMainNodeRenderer.skip();
            }

            @Override
            public Node next(int skip) {
                return myMainNodeRenderer.next(skip);
            }

            @Override
            public void skip(int skip) {
                myMainNodeRenderer.skip(skip);
            }

            @Override
            public void delegateRender() {
                myMainNodeRenderer.renderByPreviousHandler(this);
            }

            @Override
            public HashMap<String, Reference> getReferenceUrlToReferenceMap() {
                return myMainNodeRenderer.getReferenceUrlToReferenceMap();
            }

            @Override
            public HashSet<Reference> getExternalReferences() {
                return myMainNodeRenderer.getExternalReferences();
            }

            @Override
            public Reference getOrCreateReference(String url, String text, String title) {
                return myMainNodeRenderer.getOrCreateReference(url, text, title);
            }

            @Override
            public com.vladsch.flexmark.util.ast.Node parseMarkdown(String markdown) {
                return myMainNodeRenderer.parseMarkdown(markdown);
            }

            @Override
            public void processUnwrapped(Node element) {
                myMainNodeRenderer.processUnwrapped(this, element);
            }

            @Override
            public void processWrapped(Node node, Boolean isBlock, boolean escapeMarkdown) {
                FlexmarkHtmlConverter.processWrapped(this, node, isBlock, escapeMarkdown);
            }

            @Override
            public void appendOuterHtml(Node node) {
                FlexmarkHtmlConverter.appendOuterHtml(this, node);
            }

            @Override
            public boolean isInlineCode() {
                return myMainNodeRenderer.isInlineCode();
            }

            @Override
            public void setInlineCode(boolean inlineCode) {
                myMainNodeRenderer.setInlineCode(inlineCode);
            }

            @Override
            public void inlineCode(Runnable inlineRunnable) {
                myMainNodeRenderer.inlineCode(inlineRunnable);
            }

            @Override
            public String escapeSpecialChars(String text) {
                return myMainNodeRenderer.escapeSpecialChars(text);
            }

            @Override
            public String prepareText(String text) {
                return myMainNodeRenderer.prepareText(text);
            }

            @Override
            public String prepareText(String text, boolean inCode) {
                return myMainNodeRenderer.prepareText(text, inCode);
            }

            @Override
            public String processTextNodes(Node node) {
                return myMainNodeRenderer.processTextNodes(node);
            }

            @Override
            public void excludeAttributes(String... excludes) {
                myMainNodeRenderer.excludeAttributes(excludes);
            }

            @Override
            public void processTextNodes(Node node, boolean stripIdAttribute) {
                processTextNodes(node, stripIdAttribute, null, null);
            }

            @Override
            public void processTextNodes(Node node, boolean stripIdAttribute, CharSequence wrapText) {
                processTextNodes(node, stripIdAttribute, wrapText, wrapText);
            }

            @Override
            public void processTextNodes(Node node, boolean stripIdAttribute, CharSequence textPrefix, CharSequence textSuffix) {
                FlexmarkHtmlConverter.processTextNodes(this, node, stripIdAttribute, textPrefix, textSuffix);
            }

            @Override
            public void wrapTextNodes(Node node, CharSequence wrapText, boolean needSpaceAround) {
                FlexmarkHtmlConverter.wrapTextNodes(this, node, wrapText, needSpaceAround);
            }

            @Override
            public void processConditional(ExtensionConversion extensionConversion, Node node, Runnable processNode) {
                FlexmarkHtmlConverter.processConditional(this, extensionConversion, node, processNode);
            }

            @Override
            public void renderDefault(Node node) {
                FlexmarkHtmlConverter.processDefault(this, node, getHtmlConverterOptions().outputUnknownTags);
            }

            @Override
            public HtmlConverterState getState() {
                return myMainNodeRenderer.getState();
            }

            @Override
            public boolean isTrace() {
                return myMainNodeRenderer.isTrace();
            }

            @Override
            public void setTrace(boolean trace) {
                myMainNodeRenderer.setTrace(trace);
            }

            @Override
            public Stack<HtmlConverterState> getStateStack() {
                return myMainNodeRenderer.getStateStack();
            }
        }
    }

    static void processTextNodes(HtmlNodeConverterContext context, Node node, boolean stripIdAttribute, CharSequence textPrefix, CharSequence textSuffix) {
        context.pushState(node);

        Node child;

        LineFormattingAppendable markdown = context.getMarkdown();

        while ((child = context.next()) != null) {
            if (child instanceof TextNode) {
                if (textPrefix != null && textPrefix.length() > 0) markdown.append(textPrefix);
                String text = ((TextNode) child).getWholeText();
                String preparedText = context.prepareText(text);
                markdown.append(preparedText);
                if (textSuffix != null && textSuffix.length() > 0) markdown.append(textSuffix);
            } else if (child instanceof Element) {
                context.render(child);
            }
        }

        if (stripIdAttribute) {
            context.excludeAttributes("id");
        }

        // last text node gives up id to parent
        int nodeCount = node.parent().childNodeSize();
        if (node.parent().childNode(nodeCount - 1) == node) {
            context.transferIdToParent();
        }
        context.popState(markdown);
    }

    static void wrapTextNodes(HtmlNodeConverterContext context, Node node, CharSequence wrapText, boolean needSpaceAround) {
        String text = context.processTextNodes(node);
        String prefixBefore = null;
        String appendAfter = null;
        boolean addSpaceBefore = false;
        boolean addSpaceAfter = false;
        HtmlMarkdownWriter out = context.getMarkdown();

        if (!text.isEmpty() && needSpaceAround) {
            if ("\u00A0 \t\n".indexOf(text.charAt(0)) != -1) {
                prefixBefore = context.prepareText(text.substring(0, 1));
                text = text.substring(1);
            } else if (text.startsWith("&nbsp;")) {
                prefixBefore = "&nbsp;";
                text = text.substring(prefixBefore.length());
            } else {
                // if we already have space or nothing before us
                addSpaceBefore = !(out.getPendingEOL() == 0 || out.isPendingSpace() || out.offsetWithPending() == 0 || out.getPendingEOL() > 0);
            }

            if (!text.isEmpty() && "\u00A0 \t\n".indexOf(text.charAt(text.length() - 1)) != -1) {
                appendAfter = context.prepareText(text.substring(text.length() - 1));
                text = text.substring(0, text.length() - 1);
            } else if (text.endsWith("&nbsp;")) {
                appendAfter = "&nbsp;";
                text = text.substring(0, text.length() - appendAfter.length());
            } else {
                // if next is not text space
                Node next = context.peek();
                addSpaceAfter = true;

                if (next instanceof TextNode) {
                    String nextText = ((TextNode) next).getWholeText();
                    if (!nextText.isEmpty() && Character.isWhitespace(nextText.charAt(0))) {
                        addSpaceAfter = false;
                    }
                }
            }
        }

        if (!text.isEmpty()) {
            // need to trim end of string
            int pos = text.length() - 1;
            while (pos >= 0 && Character.isWhitespace(text.charAt(pos))) pos--;
            pos++;

            if (pos > 0) {
                if (prefixBefore != null) out.append(prefixBefore);
                if (addSpaceBefore) out.append(' ');

                text = text.substring(0, pos);
                out.append(wrapText);
                out.append(text);
                out.append(wrapText);

                if (appendAfter != null) out.append(appendAfter);
                if (addSpaceAfter) out.append(' ');
            }
        }
    }

    static void processConditional(HtmlNodeConverterContext context, ExtensionConversion extensionConversion, Node node, Runnable processNode) {
        if (extensionConversion.isParsed()) {
            if (!extensionConversion.isSuppressed()) {
                processNode.run();
            }
        } else {
            context.processWrapped(node, null, true);
        }
    }

    static void appendOuterHtml(HtmlNodeConverterSubContext context, Node node) {
        String text = node.outerHtml();
        int head = text.indexOf(">");
        int tail = text.lastIndexOf("</");
        if (head != -1 && tail != -1) {
            context.markdown.append(text.substring(0, head + 1));

            int iMax = node.childNodeSize();
            if (iMax > 0) {
                for (int i = 0; i < iMax; i++) {
                    appendOuterHtml(context, node.childNode(i));
                }
            } else {
                // this text we escape
                context.markdown.append(context.escapeSpecialChars(text.substring(head + 1, tail)));
            }
            context.markdown.append(text.substring(tail));
        } else {
            if (head == -1) {
                context.markdown.append(context.escapeSpecialChars(text));
            } else {
                // this text we don't escape it is a single tag
                context.markdown.append(text);
            }
        }
    }

    static public void processWrapped(HtmlNodeConverterSubContext context, Node node, Boolean isBlock, boolean escapeMarkdown) {
        if (node instanceof Element && (isBlock == null && ((Element) node).isBlock() || isBlock != null && isBlock)) {
            String s = node.toString();
            int pos = s.indexOf(">");
            context.markdown.lineIf(isBlock != null).append(s.substring(0, pos + 1)).lineIf(isBlock != null);

            processHtmlTree(context, node, false, null);

            int endPos = s.lastIndexOf("<");
            context.markdown.lineIf(isBlock != null).append(s.substring(endPos)).lineIf(isBlock != null);
        } else {
            if (escapeMarkdown) {
                appendOuterHtml(context, node);
            } else {
                context.markdown.append(node.toString());
            }
        }
    }

    static void processHtmlTree(HtmlNodeConverterSubContext context, Node parent, boolean outputAttributes, Runnable prePopAction) {
        context.pushState(parent);
        HtmlConverterState oldState = context.getState();

        if (prePopAction != null) {
            oldState.addPrePopAction(prePopAction);
        }

        Node node;

        while ((node = context.next()) != null) {
            context.render(node);
        }

        if (oldState != context.getState()) {
            throw new IllegalStateException("State not equal after process " + dumpState(context));
        }

        oldState.runPrePopActions();
        context.popState(outputAttributes ? context.markdown : null);
    }

    static String dumpState(HtmlNodeConverterContext context) {
        Stack<HtmlConverterState> stateStack = context.getStateStack();

        if (!stateStack.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            while (!stateStack.isEmpty()) {
                HtmlConverterState state = stateStack.pop();
                sb.append("\n").append(state == null ? "null" : state.toString());
            }

            return sb.toString();
        }
        return "";
    }

    static void processDefault(HtmlNodeConverterSubContext subContext, Node node, boolean outputUnknownTags) {
        if (outputUnknownTags) {
            subContext.processWrapped(node, null, false);
        } else {
            subContext.processUnwrapped(node);
        }
    }
}
