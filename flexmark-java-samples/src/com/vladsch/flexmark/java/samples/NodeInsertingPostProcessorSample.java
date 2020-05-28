package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.EmbeddedAttributeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.test.util.AstCollectingVisitor;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

/**
 * A sample that demonstrates how to strip (replace) specific tokens from a parsed
 * {@link Document} prior to rendering.
 */
public class NodeInsertingPostProcessorSample {

    static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singletonList(NodeInsertingPostProcessorExtension.create()))
            .toImmutable();

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static class NodeInsertingPostProcessor extends NodePostProcessor {
        private static class NodeInsertingFactory extends NodePostProcessorFactory {
            NodeInsertingFactory(DataHolder options) {
                super(false);

                addNodes(Image.class);
                addNodes(ImageRef.class);
            }

            @NotNull
            @Override
            public NodePostProcessor apply(@NotNull Document document) {
                return new NodeInsertingPostProcessor();
            }
        }

        public static NodePostProcessorFactory Factory(DataHolder options) {
            return new NodeInsertingFactory(options);
        }

        @Override
        public void process(@NotNull NodeTracker state, @NotNull Node node) {
            BasedSequence paragraphText = BasedSequence.NULL;
            if (node instanceof ImageRef) { // [foo](http://example.com)
                ImageRef imageRef = (ImageRef) node;
                paragraphText = imageRef.isReferenceTextCombined() ? imageRef.getReference() : imageRef.getText();
            } else if (node instanceof Image) { // ![bar](http://example.com)
                Image image = (Image) node;
                paragraphText = image.getText();
            }

            if (!paragraphText.isBlank()) {
                Node paragraphParent = node.getAncestorOfType(Paragraph.class);

                // should always have a paragraph wrapper
                assert paragraphParent != null;

                // create a text element to hold the text
                Text text = new Text(PrefixedSubSequence.prefixOf(paragraphText.toString(), paragraphParent.getChars().getEmptySuffix()));

                // create a paragraph for the text
                Paragraph paragraph = new Paragraph(text.getChars());

                // this will allows us add attributes in the AST without needing to modify the attribute provider
                MutableAttributes attributes = new MutableAttributes();
                attributes.addValue("class", "caption");

                paragraph.appendChild(new EmbeddedAttributeProvider.EmbeddedNodeAttributes(paragraph, attributes));
                paragraph.appendChild(text);
                paragraph.setCharsFromContent();

                paragraphParent.insertAfter(paragraph);
                paragraphParent.setCharsFromContent();

                state.nodeAddedWithChildren(paragraph);
            }
        }
    }

    /**
     * An extension that registers a post processor which intentionally strips (replaces)
     * specific link and image-link tokens after parsing.
     */
    static class NodeInsertingPostProcessorExtension implements Parser.ParserExtension {
        private NodeInsertingPostProcessorExtension() { }

        @Override
        public void parserOptions(MutableDataHolder options) { }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(NodeInsertingPostProcessor.Factory(parserBuilder));
        }

        public static NodeInsertingPostProcessorExtension create() {
            return new NodeInsertingPostProcessorExtension();
        }
    }

    public static void main(String[] args) {
        String original = "![Figure 1. Some description here.](http://example.com/image.png) **baz**\n" +
                "\n" +
                "![bar]\n" +
                "\n" +
                "![Figure 1. Some description here.][bar]\n" +
                "\n" +
                "[bar]: http://example.com/image.png 'Image Title'\n" +
                "\n";

        Node document = PARSER.parse(original);
        String html = RENDERER.render(document);

        // <p>foo bar <strong>baz</strong></p>
        System.out.println("\n---- Markdown ------------------------\n");
        System.out.println(original);

        System.out.println("\n---- HTML ------------------------\n");
        System.out.println(html);

        System.out.println("\n---- AST ------------------------\n");
        System.out.println(new AstCollectingVisitor().collectAndGetAstText(document));
    }
}
