package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
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

    public SpecExampleRendererBase(@NotNull SpecExample example, @Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render) {
        this(example, options, parser, render, true);
    }

    public SpecExampleRendererBase(@NotNull SpecExample example, @Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render, boolean includeExampleInfo) {
        myExample = example;
        myOptions = new DataSet(options);
        myParser = parser.withOptions(options);
        myRender = render.withOptions(options);
        myIncludeExampleInfo = includeExampleInfo;
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
        return new DataSet(myOptions);
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
