package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkNode;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntheticLinkSample {
    public static class SyntheticLinkPostProcessor extends NodePostProcessor {
        public SyntheticLinkPostProcessor(DataHolder options) {
        }

        @Override
        public void process(@NotNull NodeTracker state, @NotNull Node node) {
            BasedSequence original = node.getChars();
            ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
            BasedSequence literal = Escaping.unescape(original, textMapper);
            int lastEscaped = 0;
            boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
            TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

            Pattern pattern = Pattern.compile("\\bmarkdown\\b");
            Matcher matcher = pattern.matcher(literal);
            while (matcher.find()) {
                BasedSequence linkText = literal.subSequence(matcher.start(), matcher.end());

                int startOffset = textMapper.originalOffset(matcher.start());

                if (wrapInTextBase) {
                    wrapInTextBase = false;
                    textBase = new TextBase(original);
                    node.insertBefore(textBase);
                    state.nodeAdded(textBase);
                }

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    Node node1 = new Text(escapedChars);
                    textBase.appendChild(node1);
                    state.nodeAdded(node1);
                }

                Text contentNode = new Text(linkText);
                LinkNode linkNode;

                BasedSequence linkAddress = PrefixedSubSequence.prefixOf("http://commonmark.org", linkText.getEmptySuffix());

                linkNode = new Link(BasedSequence.NULL, linkText, BasedSequence.NULL, BasedSequence.NULL, linkAddress, BasedSequence.NULL);
                linkNode.setCharsFromContent();

                linkNode.appendChild(contentNode);
                textBase.appendChild(linkNode);
                state.nodeAddedWithChildren(linkNode);

                lastEscaped = textMapper.originalOffset(matcher.start() + linkText.length());
            }

            if (lastEscaped > 0) {
                if (lastEscaped != original.length()) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
                    Node node1 = new Text(escapedChars);
                    textBase.appendChild(node1);
                    state.nodeAdded(node1);
                }

                node.unlink();
                state.nodeRemoved(node);
            }
        }

        public static class Factory extends NodePostProcessorFactory {
            public Factory() {
                super(false);
                addNodeWithExclusions(Text.class, DoNotLinkDecorate.class);
            }

            @NotNull
            @Override
            public NodePostProcessor apply(@NotNull Document document) {
                return new SyntheticLinkPostProcessor(document);
            }
        }
    }

    public static class AppendedMarkdownPostProcessor extends DocumentPostProcessor {
        public AppendedMarkdownPostProcessor(DataHolder options) {
        }

        @NotNull
        @Override
        public Document processDocument(@NotNull Document document) {
            // here you can append some markdown text but keep it based on original input by
            // using PrefixedSubSequence with only prefix without any characters from input string

            // parse using the same options as the document but remove the SyntheticLinkExtension to prevent infinite recursion
            MutableDataHolder options = Parser.removeExtensions(new MutableDataSet(document), SyntheticLinkExtension.class);
            
            // here we create a based sequence of "inserted" text with offset set to end of input string
            String insertedText = "Some inserted text with a link [flexmark-java](https://github.com/vsch/flexmark-java) in paragraph.";
            
            Node insertedDocument = Parser.builder(options).build().parse(document.getChars().append(insertedText).toString());

            // now can append nodes from inserted to document
            document.takeChildren(insertedDocument);
            return document;
        }

        public static class Factory extends DocumentPostProcessorFactory {
            @NotNull
            @Override
            public PostProcessor apply(@NotNull Document document) {
                return new AppendedMarkdownPostProcessor(document);
            }

            @Nullable
            @Override
            public Set<Class<?>> getAfterDependents() {
                // run this post processor after synthetic link processor
                Set<Class<?>> set = new HashSet<>();
                set.add(SyntheticLinkPostProcessor.Factory.class);
                return set;
            }
        }
    }

    public static class SyntheticLinkExtension implements Parser.ParserExtension {
        private SyntheticLinkExtension() {
        }

        public static SyntheticLinkExtension create() {
            return new SyntheticLinkExtension();
        }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(new SyntheticLinkPostProcessor.Factory());
            parserBuilder.postProcessorFactory(new AppendedMarkdownPostProcessor.Factory());
        }

        @Override
        public void parserOptions(MutableDataHolder options) {

        }
    }

    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();

        // set optional extensions
        options.set(Parser.EXTENSIONS, Collections.singletonList(SyntheticLinkExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("Some markdown content");
        String html = renderer.render(document);
        System.out.println(html);
        
        String markdown = Formatter.builder(options).build().render(document);
        System.out.println(markdown);
    }
}
