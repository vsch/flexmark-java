/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.IParse;
import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.toc.internal.SimTocOptionsParser;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.toc.internal.TocOptionsParser;
import com.vladsch.flexmark.ext.toc.internal.TocUtils;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.spec.IParseBase;
import com.vladsch.flexmark.spec.IRenderBase;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.options.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TocOptionsParserTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/toc_options_parser_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            //.set(HtmlRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.RENDER_HEADER_ID, true)
            //.set(Parser.EXTENSIONS, Collections.singletonList(SimTocExtension.create()))
            ;

    private static DataKey<TocOptions> TOC_OPTIONS = new DataKey<>("TOC_OPTIONS", TocOptions.DEFAULT);
    private static DataKey<Boolean> SIM_TOC = new DataKey<>("SIM_TOC", false);

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("sim-toc", new MutableDataSet().set(SIM_TOC, true));
        //optionsMap.put("text-only", new MutableDataSet().set(SimTocExtension.HEADER_TEXT_ONLY, true));
    }

    private static final IParse PARSER = new Parser(OPTIONS);
    private static final IRender RENDERER = new Renderer(OPTIONS);

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    interface ParserVisitor {
        void visit(ParserNode node);
    }

    static class ParserVisitorExt {
        static <V extends ParserVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
            return new VisitHandler<?>[] {
                    new VisitHandler<>(ParserNode.class, new Visitor<ParserNode>() {
                        @Override
                        public void visit(ParserNode node) {visitor.visit(node);
                        }
                    }),
            };
        }
    }

    private static class ParserNode extends CustomNode {
        private final String nodeType;
        private final ParsedOptionStatus status;
        private final String message;
        private final MutableDataHolder options = new MutableDataSet();
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

        @Override
        public BasedSequence[] getSegments() {
            return EMPTY_SEGMENTS;
        }

        @Override
        public void getAstExtra(StringBuilder out) {
            if (status != null) out.append(" status:").append(status.name());
            if (message != null) out.append(" message:").append(message);
        }

        @Override
        public String getNodeName() {
            return nodeType;
        }
    }

    private static class Parser extends IParseBase {
        private final OptionsParser<TocOptions> myParser;

        public Parser() {
            this(null);
        }

        public Parser(DataHolder options) {
            super(options);
            myParser = SIM_TOC.getFrom(options) ? new SimTocOptionsParser() : new TocOptionsParser();
        }

        @Override
        public ParserNode parse(BasedSequence input) {
            // here we make the lexer parse the input sequence from start to finish and accumulate everything in custom nodes
            List<BasedSequence> lines = input.split('\n');
            ParserNode example = new ParserNode("Example", input, null, null);
            for (BasedSequence line : lines) {
                Pair<TocOptions, List<ParsedOption<TocOptions>>> pair = myParser.parseOption(line, getOptions().get(TOC_OPTIONS), null);
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

        @Override
        public IParse withOptions(DataHolder options) {
            return new Parser(options);
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
                ParserNode parserNode = (ParserNode) node;
                TocOptions nodeTocOptions = parserNode.getTocOptions();
                if (nodeTocOptions != null) {
                    html.raw("'").raw(parserNode.getChars().toString()).raw("' => ");
                    html.raw(nodeTocOptions.toString()).line();
                    html.indent();

                    if (getOptions().get(SIM_TOC)) {
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
        public void render(Node node, Appendable output) {
            assert node instanceof ParserNode;
            TocOptions tocOptions = getOptions().get(TOC_OPTIONS);
            final HtmlWriter html = new HtmlWriter(output, 2);
            RenderingVisitor visitor = new RenderingVisitor(html, tocOptions);
            visitor.render(node);
            html.flush();
        }

        @Override
        public IRender withOptions(DataHolder options) {
            return new Renderer(options);
        }
    }

    public TocOptionsParserTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected IParse parser() {
        return PARSER;
    }

    @Override
    protected IRender renderer() {
        return RENDERER;
    }
}
