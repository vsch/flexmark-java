package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.html.RendererBuilder;
import com.vladsch.flexmark.html.RendererExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

public class ThreadLocalOptionsExtension implements
        Parser.ParserExtension,
        RendererExtension
{
    final private static ThreadLocal<MutableDataHolder> threadOptions = new ThreadLocal<>();

    private ThreadLocalOptionsExtension() {
    }

    /**
     * Set the per thread options for flexmark-java.
     * <p>
     * Must be called from the thread to set options to be set for
     * all flexmark-java code which uses this extension.
     * <p>
     * CAUTION: Do not try to make mutable data values shared.
     * setAll() copies values of keys so immutable data valued
     * keys: Boolean, String, Integer, enums, etc., are good to
     * go as is.
     * <p>
     * For mutable values you will need to add extra code to address
     * these or you will be hunting down overwrites of data from
     * another thread.
     *
     * @param perThreadOptions data set of the per thread options
     */
    public static void setThreadOptions(DataSet perThreadOptions) {
        threadOptions.get().setAll(perThreadOptions);
    }

    public static ThreadLocalOptionsExtension create() {
        return new ThreadLocalOptionsExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
    }

    @Override
    public void parserOptions(MutableDataHolder options) {
        // copy thread local options to builder
        options.setAll(threadOptions.get());
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {
        // copy thread local options to builder
        options.setAll(threadOptions.get());
    }

    @Override
    public void extend(@NotNull RendererBuilder rendererBuilder, @NotNull String rendererType) {

    }
}
