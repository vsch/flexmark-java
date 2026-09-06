package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import org.jspecify.annotations.Nullable;

public abstract class SpecExampleRendererBase implements SpecExampleRenderer {
    protected final SpecExample myExample;
    protected final DataHolder myOptions;
    protected final boolean myIncludeExampleInfo;
    private boolean myIsFinalized;
    private @Nullable String myRenderedHtml;
    private @Nullable String myRenderedAst;

    public SpecExampleRendererBase(SpecExample example, @Nullable DataHolder options) {
        this(example, options, true);
    }

    public SpecExampleRendererBase(SpecExample example, @Nullable DataHolder options, boolean includeExampleInfo) {
        myExample = example;
        myOptions = options == null ? new DataSet() : options.toImmutable();
        myIncludeExampleInfo = includeExampleInfo;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isFinalized() {
        return myIsFinalized;
    }

    @Override
    final public String getHtml() {
        if (myRenderedHtml == null || !isFinalized()) {
            myRenderedHtml = renderHtml();
        }
        return myRenderedHtml;
    }

    @Override
    final public String getAst() {
        if (myRenderedAst == null || !isFinalized()) {
            myRenderedAst = renderAst();
        }
        return myRenderedAst;
    }

    protected abstract String renderHtml();

    protected abstract String renderAst();

    @Override
    public void finalizeRender() {
        myIsFinalized = true;
    }

    @Override
    public boolean includeExampleInfo() {
        return myIncludeExampleInfo;
    }

    public SpecExample getExample() {
        return myExample;
    }

    @Override
    public DataHolder getOptions() {
        return myOptions.toImmutable();
    }
}
