package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.util.format.MarkdownWriterBase;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

public class HtmlMarkdownWriter extends MarkdownWriterBase<HtmlMarkdownWriter, Node, HtmlNodeConverterContext> {
    public HtmlMarkdownWriter() {
        this(0);
    }

    public HtmlMarkdownWriter(int formatOptions) {
        super(formatOptions);
    }

    @Override
    public boolean isLastBlockQuoteChild() {
        Node node = context.getCurrentNode();

        if (node instanceof Element) {
            Element element = (Element)node;

            while (element.nextElementSibling() == null) {
                Element parent = element.parent();
                if (parent == null) break;
                if (parent.nodeName().toLowerCase().equals(FlexmarkHtmlConverter.BLOCKQUOTE_NODE)) return true;
                element = parent;
            }
        }
        return false;
    }

    @Override
    public HtmlMarkdownWriter tailBlankLine(int count) {
        if (isLastBlockQuoteChild()) {
            // Needed to not add block quote prefix to trailing blank lines
            CharSequence prefix = getPrefix();
            int pos = prefix.toString().lastIndexOf('>');
            if (pos != -1) {
                setPrefix(prefix.subSequence(0, pos));
            } else {
                setPrefix("");
            }
            blankLine(count);
            setPrefix(prefix, false);
        } else {
            blankLine(count);
        }
        return this;
    }
}

