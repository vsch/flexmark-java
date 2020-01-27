package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.format.MarkdownWriterBase;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class HtmlMarkdownWriter extends MarkdownWriterBase<HtmlMarkdownWriter, Node, HtmlNodeConverterContext> {
    public HtmlMarkdownWriter() {
        this(null, 0);
    }

    public HtmlMarkdownWriter(int formatOptions) {
        this(null, formatOptions);
    }

    public HtmlMarkdownWriter(@Nullable Appendable builder, int formatOptions) {
        super(builder, formatOptions);
    }

    @NotNull
    @Override
    public HtmlMarkdownWriter getEmptyAppendable() {
        return new HtmlMarkdownWriter(appendable, appendable.getOptions());
    }

    @Override
    public @NotNull BasedSequence lastBlockQuoteChildPrefix(BasedSequence prefix) {
        Node node = context.getCurrentNode();

        if (node instanceof Element) {
            Element element = (Element) node;

            while (element.nextElementSibling() == null) {
                Element parent = element.parent();
                if (parent == null) break;
                if (parent.nodeName().toLowerCase().equals(FlexmarkHtmlConverter.BLOCKQUOTE_NODE)) {
                    int pos = prefix.lastIndexOf('>');
                    if (pos >= 0) {
                        prefix = prefix.getBuilder().append(prefix.subSequence(0, pos)).append(' ').append(prefix.subSequence(pos + 1)).toSequence();
//                } else {
//                    // NOTE: occurs if continuation block prefix is remove
                    }
                }
                element = parent;
            }
        }
        return prefix;
    }
}

