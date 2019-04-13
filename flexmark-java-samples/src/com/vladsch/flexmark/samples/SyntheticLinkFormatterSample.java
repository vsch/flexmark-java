package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SyntheticLinkFormatterSample {
    public static class SyntheticLinkNodeFormatter implements PhasedNodeFormatter {
        public SyntheticLinkNodeFormatter(DataHolder options) {
        }

        @Override
        public Set<Class<?>> getNodeClasses() {
            return new HashSet<Class<?>>(Arrays.asList(
                    Text.class
            ));
        }

        @Override
        public Set<FormattingPhase> getFormattingPhases() {
            Set<FormattingPhase> set = new HashSet<FormattingPhase>();
            set.add(FormattingPhase.DOCUMENT_BOTTOM);
            return set;
        }

        @Override
        public void renderDocument(final NodeFormatterContext context, final MarkdownWriter markdown, final Document document, final FormattingPhase phase) {
            switch (phase) {
                case DOCUMENT_BOTTOM:
                    markdown.blankLine();
                    markdown.append("Some inserted text with a link [flexmark-java](https://github.com/vsch/flexmark-java) in paragraph.");
                    markdown.blankLine();
                    break;

                default:
                    break;
            }
        }

        @Override
        public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
            return new HashSet<NodeFormattingHandler<? extends Node>>(Arrays.asList(
                    // Generic unknown node formatter
                    new NodeFormattingHandler<Text>(Text.class, new CustomNodeFormatter<Text>() {
                        @Override
                        public void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
                            SyntheticLinkNodeFormatter.this.render(node, context, markdown);
                        }
                    })
            ));
        }

        private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
            BasedSequence original = node.getChars();
            ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
            BasedSequence literal = Escaping.unescape(original, textMapper);
            int lastEscaped = 0;
            boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
            TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

            Pattern pattern = Pattern.compile("\\bmarkdown\\b");
            Matcher matcher = pattern.matcher(literal);
            while (matcher.find()) {
                int startOffset = textMapper.originalOffset(matcher.start());
                int endOffset = textMapper.originalOffset(matcher.end());

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    markdown.append(escapedChars);
                }

                BasedSequence linkText = original.subSequence(startOffset, endOffset);
                BasedSequence linkAddress = PrefixedSubSequence.of("http://commonmark.org", linkText.subSequence(linkText.length()));

                markdown.append('[').append(linkText).append(']');
                markdown.append('(').append(linkAddress).append(')');
                lastEscaped = endOffset;
            }

            if (lastEscaped > 0) {
                if (lastEscaped != original.length()) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
                    markdown.append(escapedChars);
                }
            }
        }

        static class Factory implements NodeFormatterFactory {
            @Override
            public NodeFormatter create(final DataHolder options) {
                return new SyntheticLinkNodeFormatter(options);
            }
        }
    }

    public static class SyntheticLinkExtension implements Formatter.FormatterExtension {
        private SyntheticLinkExtension() {
        }

        public static Extension create() {
            return new SyntheticLinkExtension();
        }

        @Override
        public void rendererOptions(final MutableDataHolder options) {

        }

        @Override
        public void extend(final Formatter.Builder builder) {
            builder.nodeFormatterFactory(new SyntheticLinkNodeFormatter.Factory());
        }
    }

    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();

        // set optional extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(SyntheticLinkExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        Formatter formatter = Formatter.builder(options).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("Some markdown content");
        String markdown = formatter.render(document);
        System.out.println(markdown);
    }
}
