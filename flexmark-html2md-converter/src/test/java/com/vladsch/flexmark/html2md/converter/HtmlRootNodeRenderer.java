package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.test.util.spec.IRenderBase;
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
        htmlExtensions = HtmlConverter.getHtmlExtensions(options);
    }

    @Override
    public void render(@NotNull Node document, @NotNull Appendable output) {
        assert document instanceof HtmlConverter.RootNode;
        String text = ((HtmlConverter.RootNode) document).myRootNode;
        try {
            output.append(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
