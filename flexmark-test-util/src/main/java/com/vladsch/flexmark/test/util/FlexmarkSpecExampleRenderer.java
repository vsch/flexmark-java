package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlexmarkSpecExampleRenderer extends SpecExampleRendererBase {
    private @Nullable Node myIncludedDocument = null;
    private @Nullable Node myDocument = null;
    private @NotNull IParse myParser;
    private @NotNull IRender myRender;

    public FlexmarkSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render) {
        this(example, options, parser, render, true);
    }

    public FlexmarkSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render, boolean includeExampleCoord) {
        super(example, options, includeExampleCoord);
        myParser = parser;
        myRender = render;
    }

    @Override
    public void includeDocument(@NotNull String includedText) {
        // flexmark parser specific
        myIncludedDocument = null;

        if (!includedText.isEmpty()) {
            // need to parse and transfer references
            myIncludedDocument = getParser().parse(includedText);
            adjustParserForInclusion();
        }
    }

    @NotNull
    protected Node getIncludedDocument() {
        assert myIncludedDocument != null;
        return myIncludedDocument;
    }

    @Override
    public void parse(CharSequence input) {
        myDocument = getParser().parse(BasedSequence.of(input));
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
            getParser().transferReferences((Document) myDocument, (Document) myIncludedDocument, null);
        }
    }

    public @NotNull Node getDocument() {
        assert myDocument != null;
        return myDocument;
    }

    /**
     * Override to customize
     *
     * @return HTML string, will be cached after document is finalized to allow for timing collection iterations,
     */
    @NotNull
    @Override
    protected String renderHtml() {
        assert myDocument != null;
        return getRenderer().render(myDocument);
    }

    /**
     * Override to customize
     *
     * @return HTML string, will be cached after document is finalized to allow for timing collection iterations,
     */
    @NotNull
    @Override
    protected String renderAst() {
        assert myDocument != null;
        return TestUtils.ast(myDocument);
    }

    @Override
    public void finalizeRender() {
        super.finalizeRender();
    }

    @NotNull
    final public IParse getParser() {
        return myParser;
    }

    public void setParser(@NotNull IParse parser) {
        myParser = parser;
    }

    public void setRender(@NotNull IRender render) {
        myRender = render;
    }

    @NotNull
    final public IRender getRenderer() {
        return myRender;
    }
}
