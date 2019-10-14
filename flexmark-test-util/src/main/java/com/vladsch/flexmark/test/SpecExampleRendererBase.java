package com.vladsch.flexmark.test;

import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SpecExampleRendererBase implements SpecExampleRenderer {
    private final @NotNull DataHolder myOptions;
    private final @NotNull IParse myParser;
    private final @NotNull IRender myRender;
    private final boolean myIncludeExampleCoord;

    public SpecExampleRendererBase(@Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render) {
        this(options, parser, render, true);
    }

    public SpecExampleRendererBase(@Nullable DataHolder options, @NotNull IParse parser, @NotNull IRender render, boolean includeExampleCoord) {
        myOptions = new DataSet(options);
        myParser = parser.withOptions(options);
        myRender = render.withOptions(options);
        myIncludeExampleCoord = includeExampleCoord;
    }

    @Override
    public boolean includeExampleInfo() {
        return myIncludeExampleCoord;
    }

    @NotNull
    @Override
    public DataHolder getOptions() {
        return new DataSet(myOptions);
    }

    @NotNull
    @Override
    final public IRender renderer() {
        return myRender;
    }

    @NotNull
    @Override
    final public IParse parser() {
        return myParser;
    }
}
