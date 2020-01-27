package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ext.toc.internal.SimTocOptionsParser;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.toc.internal.TocOptionsParser;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.spec.IParseBase;
import com.vladsch.flexmark.test.util.spec.IRenderBase;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.options.OptionsParser;
import com.vladsch.flexmark.util.options.ParsedOption;
import com.vladsch.flexmark.util.options.ParsedOptionStatus;
import com.vladsch.flexmark.util.options.ParserMessage;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TocOptionsParserTest extends ComboSpecTestCase {
    final private static String SPEC_RESOURCE = "/toc_options_parser_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            //.set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.RENDER_HEADER_ID, true)
            //.set(Parser.EXTENSIONS, Collections.singletonList(SimTocExtension.create()))
            ;

    static DataKey<TocOptions> TOC_OPTIONS = new DataKey<>("TOC_OPTIONS", TocOptions.DEFAULT);
    static DataKey<Boolean> SIM_TOC = new DataKey<>("SIM_TOC", false);

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("sim-toc", new MutableDataSet().set(SIM_TOC, true));
        //optionsMap.put("text-only", new MutableDataSet().set(SimTocExtension.HEADER_TEXT_ONLY, true));
    }

    interface ParserVisitor {
        void visit(ParserNode node);
    }

    static class ParserVisitorExt {
        static <V extends ParserVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
            return new VisitHandler<?>[] {
                    new VisitHandler<>(ParserNode.class, visitor::visit),
            };
        }
    }

    private static class ParserNode extends Node {
        final private String nodeType;
        final private ParsedOptionStatus status;
        final private String message;
        final private MutableDataHolder options = new MutableDataSet();
        private TocOptions tocOptions = null;

        public TocOptions getTocOptions() {
            return tocOptions;
        }

        public void setTocOptions(TocOptions tocOptions) {
            this.tocOptions = tocOptions;
        }

        public ParserNode(String nodeType, String message) {
            this.nodeType = nodeType;
            this.message = message;
            this.status = null;
        }

        public ParserNode(String nodeType, ParsedOptionStatus status) {
            this.nodeType = nodeType;
            this.message = null;
            this.status = status;
        }

        public ParserNode(String nodeType, BasedSequence chars, ParsedOptionStatus status) {
            this(nodeType, chars, status, null);
        }

        public ParserNode(String nodeType, BasedSequence chars, String message) {
            this(nodeType, chars, null, message);
        }

        public ParserNode(String nodeType, BasedSequence chars, ParsedOptionStatus status, String message) {
            super(chars);
            this.nodeType = nodeType;
            this.status = status;
            this.message = message;
        }

        public MutableDataHolder getOptions() {
            return options;
        }

        @NotNull
        @Override
        public BasedSequence[] getSegments() {
            return EMPTY_SEGMENTS;
        }

        @Override
        public void getAstExtra(@NotNull StringBuilder out) {
            if (status != null) out.append(" status:").append(status.name());
            if (message != null) out.append(" message:").append(message);
        }

        @NotNull
        @Override
        public String getNodeName() {
            return nodeType;
        }
    }

    private static class Parser extends IParseBase {
        final private OptionsParser<TocOptions> myParser;

        public Parser() {
            this(null);
        }

        public Parser(DataHolder options) {
            super(options);
            myParser = SIM_TOC.get(options) ? new SimTocOptionsParser() : new TocOptionsParser();
        }

        @Override
        public @NotNull Node parse(@NotNull BasedSequence input) {
            // here we make the lexer parse the input sequence from start to finish and accumulate everything in custom nodes
            BasedSequence[] lines = input.split("\n");
            ParserNode example = new ParserNode("Example", input, null, null);
            for (BasedSequence line : lines) {
                Pair<TocOptions, List<ParsedOption<TocOptions>>> pair = myParser.parseOption(line, TOC_OPTIONS.get(getOptions()), null);
                ParserNode root = new ParserNode("Style", line, null, null);
                root.setTocOptions(pair.getFirst());
                example.appendChild(root);

                if (pair.getSecond() != null) {
                    for (ParsedOption<TocOptions> option : pair.getSecond()) {
                        ParserNode part = new ParserNode(option.getOptionParser().getOptionName(), option.getSource(), option.getOptionResult());
                        root.appendChild(part);

                        if (option.getMessages() != null) {
                            for (ParserMessage message : option.getMessages()) {
                                ParserNode messageNode = new ParserNode("Message", message.getSource(), null, message.getMessage());
                                part.appendChild(messageNode);
                            }
                            part.setCharsFromContent();
                        }
                    }
                }
                root.setCharsFromContent();
            }
            example.setCharsFromContent();

            return example;
        }
    }

    public static class Renderer extends IRenderBase {
        public Renderer() {
        }

        public Renderer(DataHolder options) {
            super(options);
        }

        class RenderingVisitor implements ParserVisitor {
            final HtmlWriter html;
            final TocOptions defaultOptions;
            final NodeVisitor myVisitor;

            public RenderingVisitor(HtmlWriter html, TocOptions defaultOptions) {
                myVisitor = new NodeVisitor(ParserVisitorExt.VISIT_HANDLERS(this));
                this.html = html;
                this.defaultOptions = defaultOptions;
            }

            @Override
            public void visit(ParserNode node) {
                ParserNode parserNode = node;
                TocOptions nodeTocOptions = parserNode.getTocOptions();
                if (nodeTocOptions != null) {
                    html.raw("'").raw(parserNode.getChars().toString()).raw("' => ");
                    html.raw(nodeTocOptions.toString()).line();
                    html.indent();

                    if (SIM_TOC.get(getOptions())) {
                        html.raw("diff: ").raw(TocUtils.getSimTocPrefix(nodeTocOptions, defaultOptions)).line();
                        html.raw("full: ").raw(TocUtils.getSimTocPrefix(nodeTocOptions, null)).line();
                    } else {
                        html.raw("diff: ").raw(TocUtils.getTocPrefix(nodeTocOptions, defaultOptions)).line();
                        html.raw("full: ").raw(TocUtils.getTocPrefix(nodeTocOptions, null)).line();
                    }

                    myVisitor.visitChildren(node);
                    html.unIndent();
                } else {
                    myVisitor.visitChildren(node);
                }
            }

            public void render(Node node) {
                myVisitor.visit(node);
            }
        }

        @Override
        public void render(@NotNull Node document, @NotNull Appendable output) {
            assert document instanceof ParserNode;
            TocOptions tocOptions = TOC_OPTIONS.get(getOptions());
            HtmlWriter html = new HtmlWriter(2, 0);
            RenderingVisitor visitor = new RenderingVisitor(html, tocOptions);
            visitor.render(document);
            try {
                html.appendTo(output, 0, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public TocOptionsParserTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder OPTIONS = aggregate(TocOptionsParserTest.OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, OPTIONS, new Parser(OPTIONS), new Renderer(OPTIONS), true);
    }
}
