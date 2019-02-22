package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.*;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.test.AstCollectingVisitor;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

import java.util.Arrays;

/**
 * A sample that demonstrates how to strip (replace) specific tokens from a parsed
 * {@link Document} prior to rendering.
 */
public class NodeInsertingPostProcessorSample {

    static final MutableDataSet OPTIONS = new MutableDataSet();
    static {
        OPTIONS.set(Parser.EXTENSIONS, Arrays.asList(NodeInsertingPostProcessorExtension.create()));
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    static class NodeInsertingPostProcessor extends NodePostProcessor {
        private static class NodeInsertingFactory extends NodePostProcessorFactory {
            private NodeInsertingFactory(DataHolder options) {
                super(false);

                addNodes(Image.class);
                addNodes(ImageRef.class);
            }

            @Override
            public NodePostProcessor create(Document document) {
                return new NodeInsertingPostProcessor();
            }
        }

        public static NodePostProcessorFactory Factory(DataHolder options) {
            return new NodeInsertingFactory(options);
        }

        @Override
        public void process(NodeTracker state, Node node) {
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
                Text text = new Text(PrefixedSubSequence.of(paragraphText.toString(), paragraphParent.getChars().subSequence(paragraphParent.getTextLength())));

                // create a paragraph for the text
                Paragraph paragraph = new Paragraph(text.getChars());
                
                // this will allows us add attributes in the AST without needing to modify the attribute provider
                Attributes attributes = new Attributes();
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

    // NOTE: EmbeddedAttributeProvider is part of flexmark core HtmlRenderer as of version 0.40.18 but provided here pre 0.40.18 release
    public static class EmbeddedAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(final Node node, final AttributablePart part, final Attributes attributes) {
            if (part == AttributablePart.NODE) {
                Node firstChild = node.getChildOfType(EmbeddedNodeAttributes.class);
                if (firstChild instanceof EmbeddedNodeAttributes) {
                    attributes.addValues(((EmbeddedNodeAttributes) firstChild).attributes);
                }
            }
        }

        public static AttributeProviderFactory Factory() {
            return new IndependentAttributeProviderFactory() {
                @Override
                public AttributeProvider create(LinkResolverContext context) {
                    //noinspection ReturnOfInnerClass
                    return new EmbeddedAttributeProvider();
                }
            };
        }

        // so we can attach attributes to any node in the AST and have a generic attribute provider serve them up
        static class EmbeddedNodeAttributes extends Node {
            final Attributes attributes;

            public EmbeddedNodeAttributes(Node parent, Attributes attributes) {
                super(parent.getChars().subSequence(0,0));
                this.attributes = attributes;
            }

            @Override
            public BasedSequence[] getSegments() {
                return Node.EMPTY_SEGMENTS;
            }

            @Override
            public void astString(final StringBuilder out, final boolean withExtra) {
                out.append(EmbeddedNodeAttributes.class.getSimpleName());
                out.append("[").append(getStartOffset()).append(", ").append(getEndOffset()).append("]");
                out.append(", attributes: ").append(attributes.toString());

                if (withExtra) getAstExtra(out);
            }

            @Override
            public void astExtraChars(final StringBuilder out) {
            }
        }
    }
    
    /**
     * An extension that registers a post processor which intentionally strips (replaces)
     * specific link and image-link tokens after parsing.
     */
    static class NodeInsertingPostProcessorExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
        private NodeInsertingPostProcessorExtension() { }

        @Override
        public void rendererOptions(final MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
            rendererBuilder.attributeProviderFactory(EmbeddedAttributeProvider.Factory());
        }

        @Override
        public void parserOptions(MutableDataHolder options) { }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(NodeInsertingPostProcessor.Factory(parserBuilder));
        }

        public static Extension create() {
            return new NodeInsertingPostProcessorExtension();
        }
    }

    public static void main(String[] args) {
        final String original = "![Figure 1. Some description here.](http://example.com/image.png) **baz**\n" +
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
