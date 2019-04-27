package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.spec.IRenderBase;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

import java.io.IOException;

class HtmlRootNodeRenderer extends IRenderBase {
    final private int htmlExtensions;

    public HtmlRootNodeRenderer() {
        this(null);
    }

    public HtmlRootNodeRenderer(DataHolder options) {
        super(options);
        htmlExtensions = HtmlParser.getHtmlExtensions(options);
    }

    @Override
    public void render(Node node, Appendable output) {
        assert node instanceof HtmlParser.RootNode;
        String text = ((HtmlParser.RootNode) node).myRootNode;
        try {
            output.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IRender withOptions(DataHolder options) {
        return new HtmlRootNodeRenderer(options);
    }
}
