package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.test.util.spec.IRenderBase;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.pegdown.LinkRenderer;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.RootNode;

import java.io.IOException;

class PegdownRenderer extends IRenderBase {
    final private ToHtmlSerializer mySerializer;
    final private int pegdownExtensions;

    public PegdownRenderer() {
        this(null);
    }

    public PegdownRenderer(DataHolder options) {
        super(options);
        pegdownExtensions = PegdownParser.getPegdownExtensions(options);
        mySerializer = new ToHtmlSerializer(new LinkRenderer());
    }

    @Override
    public void render(@NotNull Node document, @NotNull Appendable output) {
        assert document instanceof PegdownParser.PegdownRootNode;

        RootNode rootNode = ((PegdownParser.PegdownRootNode) document).myRootNode;
        String html = mySerializer.toHtml(rootNode);
        try {
            output.append(html);
            output.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
