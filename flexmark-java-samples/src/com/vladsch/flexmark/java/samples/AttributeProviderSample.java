package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.AutoLink;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.misc.Extension;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class AttributeProviderSample {
    static class SampleExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.attributeProviderFactory(SampleAttributeProvider.Factory());
        }

        static SampleExtension create() {
            return new SampleExtension();
        }
    }

    static class SampleAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(@NotNull Node node, @NotNull AttributablePart part, @NotNull MutableAttributes attributes) {
            if (node instanceof AutoLink && part == AttributablePart.LINK) {

/*
                // test any link resolver status if needed
                final Attribute linkResolverStatus = attributes.get(Attribute.LINK_STATUS_ATTR);
                if (linkResolverStatus != null) {
                    String status = linkResolverStatus.getValue();
                    if (status.equals(LinkStatus.UNKNOWN.getName())) {

                    }
                    if (status.equals(LinkStatus.VALID.getName())) {

                    }
                    if (status.equals(LinkStatus.UNCHECKED.getName())) {

                    }
                    if (status.equals(LinkStatus.NOT_FOUND.getName())) {

                    }
                }
*/

/*
                // check properties of the node if needed
                AutoLink autoLink = (AutoLink) node;
*/

                // Put info in custom attribute instead
                attributes.replaceValue("class", "my-autolink-class");
            }
        }

        static AttributeProviderFactory Factory() {
            return new IndependentAttributeProviderFactory() {
                @NotNull
                @Override
                public AttributeProvider apply(@NotNull LinkResolverContext context) {
                    return new SampleAttributeProvider();
                }
            };
        }
    }

    static String commonMark(String markdown) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(new Extension[] { AutolinkExtension.create(), SampleExtension.create() }));

        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");

        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String html = renderer.render(document);
        return html;
    }

    public static void main(String[] args) {
        String html = commonMark("http://github.com/vsch/flexmark-java");
        System.out.println(html); // output: <p><a href="http://github.com/vsch/flexmark-java" class="my-autolink-class">http://github.com/vsch/flexmark-java</a></p>

        html = commonMark("hello\nworld");
        System.out.println(html); // output: <p>hello<br/>world</p>
    }
}
