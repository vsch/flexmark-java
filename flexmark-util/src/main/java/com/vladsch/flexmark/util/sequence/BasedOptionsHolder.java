package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.SegmentedSequenceStats;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.data.NullableDataKey;
import org.jetbrains.annotations.Nullable;

/**
 * Implemented by BasedOptionsSequence, use instance of it to pass to {@link BasedSequence#of(CharSequence)} and options enabled in it
 * will be accessible to all based sequences or uses of these for testing for options or getting options.
 * <p>
 * Only works with SubSequence base not CharArraySequence
 */
public interface BasedOptionsHolder {
    int O_COLLECT_SEGMENTED_STATS = 0x0001;

    static NullableDataKey<SegmentedSequenceStats> SEGMENTED_STATS = new NullableDataKey<>("SEGMENTED_STATS", (SegmentedSequenceStats) null);

    /**
     * Options test for options for this sequence
     * <p>
     * default reports true for global default options (if any) , variation available on BasedSequenceWithOptions
     *
     * @param option option flags
     * @return true if option is set for this sequence
     */
    boolean isOption(int option);

    /**
     * Options holder, default has none, only available on BasedSequenceWithOptions
     *
     * @param dataKey in options
     * @return true if data key is available
     */
    @Nullable
    <T> T getOption(DataKeyBase<T> dataKey);

    /**
     * Options holder, default has none, only available on BasedSequenceWithOptions
     *
     * @return data holder with options or null if none for this sequence
     */
    @Nullable
    DataHolder getOptions();
}
