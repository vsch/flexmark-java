package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.spec.IRenderBase;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

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
    public void render(@NotNull Node document, @NotNull Appendable output) {
        assert document instanceof HtmlParser.RootNode;
        String text = ((HtmlParser.RootNode) document).myRootNode;
        try {
            output.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
