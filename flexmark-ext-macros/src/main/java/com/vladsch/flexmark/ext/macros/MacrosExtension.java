package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.macros.internal.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for macross
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed macros text is turned into {@link MacroReference} nodes.
 * </p>
 */
public class MacrosExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension
        , Parser.ReferenceHoldingExtension
        , Formatter.FormatterExtension
{
    public static final DataKey<KeepType> MACRO_DEFINITIONS_KEEP = new DataKey<>("MACRO_DEFINITIONS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    public static final DataKey<MacroDefinitionRepository> MACRO_DEFINITIONS = new DataKey<>("MACRO_DEFINITIONS", new DataValueFactory<MacroDefinitionRepository>() {
        @Override
        public MacroDefinitionRepository create(DataHolder options) { return new MacroDefinitionRepository(options); }
    });
    //public static final DataKey<Boolean> MACROS_OPTION1 = new DataKey<>("MACROS_OPTION1", false);
    //public static final DataKey<String> MACROS_OPTION2 = new DataKey<>("MACROS_OPTION2", "default");
    //public static final DataKey<Integer> MACROS_OPTION3 = new DataKey<>("MACROS_OPTION3", Integer.MAX_VALUE);
    // public static final DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // public static final DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    // formatter options
    public static final DataKey<ElementPlacement> MACRO_DEFINITIONS_PLACEMENT = new DataKey<>("MACRO_DEFINITIONS_PLACEMENT", ElementPlacement.AS_IS);
    public static final DataKey<ElementPlacementSort> MACRO_DEFINITIONS_SORT = new DataKey<>("MACRO_DEFINITIONS_SORT", ElementPlacementSort.AS_IS);
    public static final DataKey<Boolean> SOURCE_WRAP_MACRO_REFERENCES = new DataKey<>("SOURCE_WRAP_MACRO_REFERENCES", false);

    private MacrosExtension() {
    }

    public static Extension create() {
        return new MacrosExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(final MutableDataHolder document, final DataHolder included) {
        // cannot optimize based on macros in this document, repository is not accessed until rendering
        if (/*document.contains(MACRO_DEFINITIONS) &&*/ included.contains(MACRO_DEFINITIONS)) {
            return Parser.transferReferences(MACRO_DEFINITIONS.getFrom(document), MACRO_DEFINITIONS.getFrom(included), MACRO_DEFINITIONS_KEEP.getFrom(document) == KeepType.FIRST);
        }
        return false;
    }

    @Override
    public void extend(final Formatter.Builder builder) {
        builder.nodeFormatterFactory(new MacrosNodeFormatter.Factory());
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new MacroDefinitionBlockParser.Factory());
        parserBuilder.customInlineParserExtensionFactory(new MacrosInlineParserExtension.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        if (rendererBuilder.isRendererType("HTML")) {
            rendererBuilder.nodeRendererFactory(new MacrosNodeRenderer.Factory());
        } else if (rendererBuilder.isRendererType("JIRA")) {
        }
    }
}
