package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.Attributes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class AttributeProviderSample2 {
    static class SampleExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
            rendererBuilder.attributeProviderFactory(SampleAttributeProvider.Factory());
        }

        static SampleExtension create() {
            return new SampleExtension();
        }
    }

    static class SampleAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, AttributablePart part, Attributes attributes) {
            if (node instanceof Link && part == AttributablePart.LINK) {
                Link link = (Link) node;

                if (link.getText().equals("...")) {
                    attributes.replaceValue("target", "_top");
                }
            }
        }

        static AttributeProviderFactory Factory() {
            return new IndependentAttributeProviderFactory() {
                @Override
                public AttributeProvider apply(LinkResolverContext context) {
                    return new SampleAttributeProvider();
                }
            };
        }
    }

    static String commonMark(String markdown) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(new Extension[] { SampleExtension.create() }));

        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");

        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String html = renderer.render(document);
        return html;
    }

    public static void main(String[] args) {
        String html = commonMark("[...](http://github.com/vsch/flexmark-java)");
        System.out.println(html); // output: <p><a href="http://github.com/vsch/flexmark-java" target="_top">...</a></p>
    }
}
