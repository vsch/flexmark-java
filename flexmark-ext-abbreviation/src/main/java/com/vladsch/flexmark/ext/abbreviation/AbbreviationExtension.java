package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationNodePostProcessor;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationNodeRenderer;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationParagraphPreProcessor;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationRepository;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.collection.DataValueFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 * Extension for adding abbreviations to markdown
 * <p>
 * Create it with {@link #create()} then configure builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed abbreviations are turned into abbr tags by default or a links as an option by setting the {@link AbbreviationExtension#USE_LINKS} key to true in option used to create the {@link Parser.Builder} via {@code Parser.builder(options)}
 * </p>
 */
public class AbbreviationExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension, Parser.ReferenceHoldingExtension {
    /**
     * A {@link DataKey} that is used to get the document's Node repository holding all the abbreviations defined in the current document.
     */
    public static final DataKey<AbbreviationRepository> ABBREVIATIONS = new DataKey<>("ABBREVIATIONS", new DataValueFactory<AbbreviationRepository>() {
        @Override
        public AbbreviationRepository create(DataHolder options) {
            return new AbbreviationRepository(options);
        }
    });

    /**
     * A {@link DataKey} that is used to set the behavior of the abbreviations repository when duplicates are defined. {@link KeepType}
     */
    public static final DataKey<KeepType> ABBREVIATIONS_KEEP = new DataKey<>("ABBREVIATIONS_KEEP", KeepType.FIRST);

    /**
     * A {@link DataKey} that is used to set the use links option when true, default is false and abbr tag will be used in the rendered HTML.
     */
    public static final DataKey<Boolean> USE_LINKS = new DataKey<>("USE_LINKS", false);

    public static Extension create() {
        return new AbbreviationExtension();
    }

    @Override
    public void rendererOptions(final MutableDataHolder options) {

    }

    @Override
    public void parserOptions(final MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(final MutableDataHolder document, final DataHolder included) {
        if (document.contains(ABBREVIATIONS) && included.contains(ABBREVIATIONS)) {
            return Parser.transferReferences(ABBREVIATIONS.getFrom(document), ABBREVIATIONS.getFrom(included), ABBREVIATIONS_KEEP.getFrom(document) == KeepType.FIRST);
        }
        return false;
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.paragraphPreProcessorFactory(AbbreviationParagraphPreProcessor.Factory());
        parserBuilder.postProcessorFactory(new AbbreviationNodePostProcessor.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
        switch (rendererType) {
            case "HTML":
                rendererBuilder.nodeRendererFactory(new AbbreviationNodeRenderer.Factory());
                break;

            case "JIRA":
            case "YOUTRACK":
                break;
        }
    }
}
