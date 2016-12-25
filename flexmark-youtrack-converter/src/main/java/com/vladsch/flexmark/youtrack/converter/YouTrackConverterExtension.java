package com.vladsch.flexmark.youtrack.converter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.youtrack.converter.internal.YouTrackConverterNodeRenderer;

/**
 * Extension for youtrack_converters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The markdown AST is turned into YOUTRACK formatted text
 * </p>
 */
public class YouTrackConverterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // public static final DataKey<YoutrackConverterRepository> YOUTRACK_CONVERTERS = new DataKey<>("YOUTRACK_CONVERTERS", YoutrackConverterRepository::new);
    // public static final DataKey<KeepType> YOUTRACK_CONVERTERS_KEEP = new DataKey<>("YOUTRACK_CONVERTERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    // public static final DataKey<Boolean> YOUTRACK_CONVERTER_OPTION1 = new DataKey<>("YOUTRACK_CONVERTER_OPTION1", false);
    // public static final DataKey<String> YOUTRACK_CONVERTER_OPTION2 = new DataKey<>("YOUTRACK_CONVERTER_OPTION2", "default");
    // public static final DataKey<Integer> YOUTRACK_CONVERTER_OPTION3 = new DataKey<>("YOUTRACK_CONVERTER_OPTION3", Integer.MAX_VALUE);
    // public static final DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // public static final DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    public static final LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private YouTrackConverterExtension() {
    }

    public static Extension create() {
        return new YouTrackConverterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("HTML")) {
            rendererBuilder.set(HtmlRenderer.TYPE, "YOUTRACK");
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new YouTrackConverterNodeRenderer(options);
                }
            });
            // rendererBuilder.linkResolverFactory(new YoutrackConverterLinkResolver.Factory());
            // rendererBuilder.attributeProviderFactory(new YoutrackConverterAttributeProvider.Factory());
        } else if (!rendererType.equals("YOUTRACK")) {
            throw new IllegalStateException("Non HTML Renderer is already set to " + rendererType);
        }
    }
}
