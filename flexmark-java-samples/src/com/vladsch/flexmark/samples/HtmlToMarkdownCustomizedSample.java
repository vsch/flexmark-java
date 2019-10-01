package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.html2md.converter.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HtmlToMarkdownCustomizedSample {
    static class CustomLinkResolver implements HtmlLinkResolver {
        public CustomLinkResolver(HtmlNodeConverterContext context) {
        }

        @Override
        public ResolvedLink resolveLink(Node node, HtmlNodeConverterContext context, ResolvedLink link) {
            // convert all links from http:// to https://
            if (link.getUrl().startsWith("http:")) {
                return link.withUrl("https:" + link.getUrl().substring("http:".length()));
            }
            return link;
        }

        static class Factory implements HtmlLinkResolverFactory {
            @Override
            public Set<Class<? extends HtmlLinkResolverFactory>> getAfterDependents() {
                return null;
            }

            @Override
            public Set<Class<? extends HtmlLinkResolverFactory>> getBeforeDependents() {
                return null;
            }

            @Override
            public boolean affectsGlobalScope() {
                return false;
            }

            @Override
            public HtmlLinkResolver apply(HtmlNodeConverterContext context) {
                return new CustomLinkResolver(context);
            }
        }
    }

    static class HtmlConverterTextExtension implements FlexmarkHtmlConverter.HtmlConverterExtension {
        public static HtmlConverterTextExtension create() {
            return new HtmlConverterTextExtension();
        }

        @Override
        public void rendererOptions(MutableDataHolder options) {

        }

        @Override
        public void extend(FlexmarkHtmlConverter.Builder builder) {
            builder.linkResolverFactory(new CustomLinkResolver.Factory());
            builder.htmlNodeRendererFactory(new CustomHtmlNodeConverter.Factory());
        }
    }

    static class CustomHtmlNodeConverter implements HtmlNodeRenderer {
        public CustomHtmlNodeConverter(DataHolder options) {

        }

        @Override
        public Set<HtmlNodeRendererHandler<?>> getHtmlNodeRendererHandlers() {
            return new HashSet<>(Collections.singletonList(
                    new HtmlNodeRendererHandler<>("kbd", Element.class, this::processKbd)
            ));
        }

        private void processKbd(Element node, HtmlNodeConverterContext context, HtmlMarkdownWriter out) {
            out.append("<<");
            context.renderChildren(node, false, null);
            out.append(">>");
        }

        static class Factory implements HtmlNodeRendererFactory {
            @Override
            public HtmlNodeRenderer apply(DataHolder options) {
                return new CustomHtmlNodeConverter(options);
            }
        }
    }

    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet()
                .set(Parser.EXTENSIONS, Collections.singletonList(HtmlConverterTextExtension.create()));

        String html = "<ul>\n" +
                "  <li>\n" +
                "    <p>Add: live templates starting with <code>.</code> <kbd>Kbd</kbd> <a href='http://example.com'>link</a></p>\n" +
                "    <table>\n" +
                "      <thead>\n" +
                "        <tr><th> Element       </th><th> Abbreviation    </th><th> Expansion                                               </th></tr>\n" +
                "      </thead>\n" +
                "      <tbody>\n" +
                "        <tr><td> Abbreviation  </td><td> <code>.abbreviation</code> </td><td> <code>*[]:</code>                                                 </td></tr>\n" +
                "        <tr><td> Code fence    </td><td> <code>.codefence</code>    </td><td> ``` ... ```                                       </td></tr>\n" +
                "        <tr><td> Explicit link </td><td> <code>.link</code>         </td><td> <code>[]()</code>                                                  </td></tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "  </li>\n" +
                "</ul>";
        String markdown = FlexmarkHtmlConverter.builder(options).build().convert(html);

        System.out.println("HTML:");
        System.out.println(html);

        System.out.println("\nMarkdown:");
        System.out.println(markdown);
    }
}
