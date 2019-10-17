package com.vladsch.flexmark.spec;

import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public abstract class IRenderBase implements IRender {
    public static final IRender NullRenderer = new IRenderBase() {
        @Override
        public void render(@NotNull Node node, @NotNull Appendable output) {

        }
    };

    public static final IRender TextRenderer = new IRenderBase() {
        @Override
        public void render(@NotNull Node node, @NotNull Appendable output) {
            try {
                output.append(node.getChars());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private final DataHolder myOptions;

    public IRenderBase() {
        this(null);
    }

    public IRenderBase(DataHolder options) {
        myOptions = options;
    }

    @NotNull
    @Override
    public String render(@NotNull Node document) {
        StringBuilder out = new StringBuilder();
        render(document, out);
        return out.toString();
    }

    @Nullable
    public DataHolder getOptions() {
        return myOptions;
    }
}
