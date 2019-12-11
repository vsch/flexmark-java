package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.sequence.builder.SegmentedSequenceStats;
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
    int O_COLLECT_SEGMENTED_STATS = 0x0001;     // set if segmented stats collector key is set to non-null value
    int O_COLLECT_FIRST256_STATS = 0x0002;      // collect statistics for segments sequence on chars < code 256
    int O_NO_ANCHORS = 0x0004;                  // do not include anchors in segment builder, test only, not guaranteed to be stable for general use

    // NOTE: if neither is specified then one will be chosen, most likely tree
    //   but may be full for short sequences or ones where number of segments vs
    //   sequence length makes tree based one wasteful and slow
    int O_FULL_SEGMENTED_SEQUENCES = 0x0008;    // use non-tree based, fast access, slow create segmented sequences because they add 4 bytes per character overhead
    int O_TREE_SEGMENTED_SEQUENCES = 0x0010;    // use tree based, fast enough access and minimal overhead per segment
    int O_RESERVED = 0x0000ffff;                // reserved for library use, extensions must use data keys since there is no way to manage bit allocations
    int O_APPLICATION = 0xffff0000;             // open for user application defined use, extensions must use data keys since there is no way to manage bit allocations

    NullableDataKey<SegmentedSequenceStats> SEGMENTED_STATS = new NullableDataKey<>("SEGMENTED_STATS", (SegmentedSequenceStats) null);

    static String optionsToString(int options) {
        DelimitedBuilder out = new DelimitedBuilder(", ");
        if ((options & O_COLLECT_SEGMENTED_STATS) != 0) out.append("O_COLLECT_SEGMENTED_STATS").mark();
        if ((options & O_COLLECT_FIRST256_STATS) != 0) out.append("O_COLLECT_FIRST256_STATS").mark();
        if ((options & O_NO_ANCHORS) != 0) out.append("O_NO_ANCHORS").mark();
        if ((options & O_FULL_SEGMENTED_SEQUENCES) != 0) out.append("O_FULL_SEGMENTED_SEQUENCES").mark();
        return out.toString();
    }

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
    @Nullable <T> T getOption(DataKeyBase<T> dataKey);

    /**
     * Options holder, default has none, only available on BasedSequenceWithOptions
     *
     * @return data holder with options or null if none for this sequence
     */
    @Nullable
    DataHolder getOptions();
}
