package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.escaped.character.internal.EscapedCharacterNodePostProcessor;
import com.vladsch.flexmark.ext.escaped.character.internal.EscapedCharacterNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.DataHolder;

/**
 * Extension for escaped_characters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed escaped_character text is turned into {@link EscapedCharacter} nodes.
 * </p>
 */
public class EscapedCharacterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // public static final DataKey<EscapedCharacterRepository> ESCAPED_CHARACTERS = new DataKey<>("ESCAPED_CHARACTERS", EscapedCharacterRepository::new);
    // public static final DataKey<KeepType> ESCAPED_CHARACTERS_KEEP = new DataKey<>("ESCAPED_CHARACTERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    //public static final DataKey<Boolean> ESCAPED_CHARACTER_OPTION1 = new DataKey<>("ESCAPED_CHARACTER_OPTION1", false);
    //public static final DataKey<String> ESCAPED_CHARACTER_OPTION2 = new DataKey<>("ESCAPED_CHARACTER_OPTION2", "default");
    //public static final DataKey<Integer> ESCAPED_CHARACTER_OPTION3 = new DataKey<>("ESCAPED_CHARACTER_OPTION3", Integer.MAX_VALUE);

    private EscapedCharacterExtension() {
    }

    public static Extension create() {
        return new EscapedCharacterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.postProcessorFactory(new EscapedCharacterNodePostProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
        } else if (rendererType.equals("HTML")) {
            rendererBuilder.nodeRendererFactory(new NodeRendererFactory() {
                @Override
                public NodeRenderer create(DataHolder options) {
                    return new EscapedCharacterNodeRenderer(options);
                }
            });
            // rendererBuilder.linkResolverFactory(new EscapedCharacterLinkResolver.Factory());
        }
    }
}
