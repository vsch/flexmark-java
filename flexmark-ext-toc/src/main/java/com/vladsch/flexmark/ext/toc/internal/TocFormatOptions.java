package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.ext.toc.SimTocGenerateOnFormat;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSetter;

public class TocFormatOptions implements MutableDataSetter {
    public final SimTocGenerateOnFormat updateOnFormat;
    public final TocOptions options;

    public TocFormatOptions() {
        this(null);
    }

    public TocFormatOptions(DataHolder options) {
        this.updateOnFormat = TocExtension.FORMAT_UPDATE_ON_FORMAT.getFrom(options);
        this.options = TocExtension.FORMAT_OPTIONS.getFrom(options);
    }

    @Override
    public MutableDataHolder setIn(final MutableDataHolder dataHolder) {
        dataHolder.set(TocExtension.FORMAT_UPDATE_ON_FORMAT, updateOnFormat);
        dataHolder.set(TocExtension.FORMAT_OPTIONS, options);
        return dataHolder;
    }
}
