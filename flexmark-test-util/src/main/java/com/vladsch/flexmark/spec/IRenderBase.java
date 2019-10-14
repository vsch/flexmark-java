package com.vladsch.flexmark.spec;

import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public abstract class IRenderBase implements IRender {
    public static final IRender NullRenderer = new IRenderBase() {
        @Override
        public void render(Node node, @NotNull Appendable output) {

        }

        @NotNull
        @Override
        public IRender withOptions(@Nullable DataHolder options) {
            return this;
        }
    };

    public static final IRender TextRenderer = new IRenderBase() {
        @Override
        public void render(Node node, @NotNull Appendable output) {
            try {
                output.append(node.getChars());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @NotNull
        @Override
        public IRender withOptions(@Nullable DataHolder options) {
            return this;
        }
    };

    private final MutableDataSet myOptions;

    public IRenderBase() {
        this(null);
    }

    public IRenderBase(DataHolder options) {
        myOptions = options != null ? new MutableDataSet(options) : new MutableDataSet();
    }

    @NotNull
    @Override
    public String render(Node document) {
        StringBuilder out = new StringBuilder();
        render(document, out);
        return out.toString();
    }

    @Nullable
    public MutableDataSet getOptions() {
        return myOptions;
    }
}
