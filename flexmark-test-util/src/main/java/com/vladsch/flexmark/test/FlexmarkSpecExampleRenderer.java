package com.vladsch.flexmark.test;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlexmarkSpecExampleRenderer extends SpecExampleRendererBase {
    private @Nullable Node myIncludedDocument = null;
    private @Nullable Node myDocument = null;
    private @NotNull IParse myParserWithOptions;
    private final @NotNull IRender myRendererWithOptions;
    private @Nullable String myRenderedHtml;
    private @Nullable String myRenderedAst;

    public FlexmarkSpecExampleRenderer(@Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render) {
        super(options, parser, render);
        myParserWithOptions = parser().withOptions(options);
        myRendererWithOptions = renderer().withOptions(options);
    }

    public FlexmarkSpecExampleRenderer(@Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render, boolean includeExampleCoord) {
        super(options, parser, render, includeExampleCoord);
        myParserWithOptions = parser().withOptions(options);
        myRendererWithOptions = renderer().withOptions(options);
    }

    @Override
    public void includeDocument(@NotNull String includedText) {
        // flexmark parser specific
        myIncludedDocument = null;

        if (!includedText.isEmpty()) {
            // need to parse and transfer references
            myIncludedDocument = myParserWithOptions.parse(includedText);
            adjustParserForInclusion();
        }
    }

    @NotNull
    protected Node getIncludedDocument() {
        assert myIncludedDocument != null;
        return myIncludedDocument;
    }

    @NotNull
    protected IParse getParserWithOptions() {
        return myParserWithOptions;
    }

    protected void setParserWithOptions(@NotNull IParse parserWithOptions) {
        myParserWithOptions = parserWithOptions;
    }

    @Override
    public void parse(CharSequence input) {
        myDocument = myParserWithOptions.parse(BasedSequenceImpl.of(input));
    }

    @Override
    public void finalizeDocument() {
        assert myDocument != null;

        if (myIncludedDocument != null) {
            adjustParserForInclusion();
        }
    }

    protected void adjustParserForInclusion() {
        if (myDocument instanceof Document && myIncludedDocument instanceof Document) {
            myParserWithOptions.transferReferences((Document) myDocument, (Document) myIncludedDocument, null);
        }
    }

    public @NotNull Node getDocument() {
        assert myDocument != null;
        return myDocument;
    }

    @Override
    public @NotNull String renderHtml() {
        assert myDocument != null;
        if (myRenderedHtml == null) {
            myRenderedHtml = myRendererWithOptions.render(myDocument);
        }
        return myRenderedHtml;
    }

    @Override
    public @NotNull String getAst() {
        assert myDocument != null;
        if (myRenderedAst == null) {
            myRenderedAst = TestUtils.ast(myDocument);
        }
        return myRenderedAst;
    }

    @Override
    public void finalizeRender() {

    }
}
