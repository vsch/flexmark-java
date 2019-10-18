package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SpecExampleRendererBase implements SpecExampleRenderer {
    private final @NotNull SpecExample myExample;
    private final @NotNull DataHolder myOptions;
    private @NotNull IParse myParser;
    private @NotNull IRender myRender;
    private final boolean myIncludeExampleInfo;
    private boolean myIsFinalized;

    public SpecExampleRendererBase(@NotNull SpecExample example, @Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render) {
        this(example, options, parser, render, true);
    }

    public SpecExampleRendererBase(@NotNull SpecExample example, @Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render, boolean includeExampleInfo) {
        myExample = example;
        myOptions = options == null ? new DataSet() : options.toImmutable();
        myParser = parser;
        myRender = render;
        myIncludeExampleInfo = includeExampleInfo;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isFinalized() {
        return myIsFinalized;
    }

    @Override
    public void finalizeRender() {
        myIsFinalized = true;
    }

    @Override
    public boolean includeExampleInfo() {
        return myIncludeExampleInfo;
    }

    @NotNull
    public SpecExample getExample() {
        return myExample;
    }

    @NotNull
    @Override
    public DataHolder getOptions() {
        return myOptions.toImmutable();
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
