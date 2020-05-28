package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListItem;
import com.vladsch.flexmark.html.AttributeProvider;
import com.vladsch.flexmark.html.AttributeProviderFactory;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.IndependentAttributeProviderFactory;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.html.renderer.CoreNodeRenderer;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class TaskListAttributeProviderSample {
    static class SampleExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.attributeProviderFactory(TaskListAttributeProvider.Factory());
        }

        static SampleExtension create() {
            return new SampleExtension();
        }
    }

    static class TaskListAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(@NotNull Node node, @NotNull AttributablePart part, @NotNull MutableAttributes attributes) {
            if (node instanceof TaskListItem && (part == CoreNodeRenderer.TIGHT_LIST_ITEM || part == CoreNodeRenderer.LOOSE_LIST_ITEM)) {
                if (((TaskListItem) node).isItemDoneMarker()) {
                    attributes.addValue("class", "closed-list-item");
                } else {
                    attributes.addValue("class", "open-list-item");
                }
            }
        }

        static AttributeProviderFactory Factory() {
            return new IndependentAttributeProviderFactory() {
                @NotNull
                @Override
                public AttributeProvider apply(@NotNull LinkResolverContext context) {
                    return new TaskListAttributeProvider();
                }
            };
        }
    }

    static String commonMark(String markdown) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(TaskListExtension.create(), SampleExtension.create()));

        options.set(TaskListExtension.ITEM_DONE_MARKER, "");
        options.set(TaskListExtension.ITEM_NOT_DONE_MARKER, "");

        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String html = renderer.render(document);
        return html;
    }

    public static void main(String[] args) {
        String html = commonMark("" +
                "* [ ] Open item\n" +
                "* [x] Closed item\n" +
                "\n");
        System.out.println(html); // output: <p><a href="http://github.com/vsch/flexmark-java" target="_top">...</a></p>
    }
}
