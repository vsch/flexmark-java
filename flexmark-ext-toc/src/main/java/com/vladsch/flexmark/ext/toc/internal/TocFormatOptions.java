package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocGenerateOnFormat;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import org.jetbrains.annotations.NotNull;

public class TocFormatOptions implements MutableDataSetter {
    final public SimTocGenerateOnFormat updateOnFormat;
    final public TocOptions options;

    public TocFormatOptions() {
        this(null);
    }

    public TocFormatOptions(DataHolder options) {
        this.updateOnFormat = TocExtension.FORMAT_UPDATE_ON_FORMAT.get(options);
        this.options = TocExtension.FORMAT_OPTIONS.get(options);
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(TocExtension.FORMAT_UPDATE_ON_FORMAT, updateOnFormat);
        dataHolder.set(TocExtension.FORMAT_OPTIONS, options);
        return dataHolder;
    }
}
