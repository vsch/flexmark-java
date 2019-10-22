package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * A sample that demonstrates how to strip (replace) specific tokens from a parsed
 * {@link Document} prior to rendering.
 */
public class TokenReplacingPostProcessorSample {

    static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(LinkReplacingExtension.create()))
            .toImmutable();

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static class LinkReplacingPostProcessor extends NodePostProcessor {

        static class Factory extends NodePostProcessorFactory {

            public Factory(DataHolder options) {
                super(false);

                addNodes(Link.class);
                addNodes(Image.class);
            }

            @NotNull
            @Override
            public NodePostProcessor apply(@NotNull Document document) {
                return new LinkReplacingPostProcessor();
            }
        }

        @Override
        public void process(@NotNull NodeTracker state, @NotNull Node node) {
            if (node instanceof Link) { // [foo](http://example.com)
                Link link = (Link) node;
                Text text = new Text(link.getText());
                link.insertAfter(text);
                state.nodeAdded(text);

                link.unlink();
                state.nodeRemoved(link);
            } else if (node instanceof Image) { // ![bar](http://example.com)
                Image image = (Image) node;
                Text text = new Text(image.getText());
                image.insertAfter(text);
                state.nodeAdded(text);

                image.unlink();
                state.nodeRemoved(image);
            }
        }
    }

    /**
     * An extension that registers a post processor which intentionally strips (replaces)
     * specific link and image-link tokens after parsing.
     */
    static class LinkReplacingExtension implements Parser.ParserExtension {

        private LinkReplacingExtension() { }

        @Override
        public void parserOptions(MutableDataHolder options) { }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(new LinkReplacingPostProcessor.Factory(parserBuilder));
        }

        public static LinkReplacingExtension create() {
            return new LinkReplacingExtension();
        }
    }

    public static void main(String[] args) {
        String original = "[foo](http://example.com) ![bar](http://example.com) **baz**";

        Node document = PARSER.parse(original);
        String html = RENDERER.render(document);

        // <p>foo bar <strong>baz</strong></p>
        System.out.println(html);
    }
}
