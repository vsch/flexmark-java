package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.sequence.builder.IBasedSegmentBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A BasedSequence implementation which wraps original CharSequence to provide a BasedSequence for
 * all its subsequences, a subSequence() returns a SubSequence from the original base sequence.
 * <p>
 * NOTE: '\0' changed to '\uFFFD' use {@link com.vladsch.flexmark.util.sequence.mappers.NullEncoder#decodeNull} mapper to get original null chars.
 */
final public class SubSequence extends BasedSequenceImpl {
    final private @NotNull CharSequence charSequence;
    final private @NotNull SubSequence baseSeq;
    final private int startOffset;
    final private int endOffset;

    @NotNull
    @Override
    public SubSequence getBaseSequence() {
        return baseSeq;
    }

    @Override
    public int getOptionFlags() {
        return charSequence instanceof BasedOptionsHolder ? ((BasedOptionsHolder) charSequence).getOptionFlags() : 0;
    }

    @Override
    public boolean allOptions(int options) {
        return charSequence instanceof BasedOptionsHolder && ((BasedOptionsHolder) charSequence).allOptions(options);
    }

    @Override
    public boolean anyOptions(int options) {
        return charSequence instanceof BasedOptionsHolder && ((BasedOptionsHolder) charSequence).anyOptions(options);
    }

    @Override
    public <T> T getOption(@NotNull DataKeyBase<T> dataKey) {
        return charSequence instanceof BasedOptionsHolder ? ((BasedOptionsHolder) charSequence).getOption(dataKey) : dataKey.get(null);
    }

    @Override
    public @Nullable DataHolder getOptions() {
        return charSequence instanceof BasedOptionsHolder ? ((BasedOptionsHolder) charSequence).getOptions() : null;
    }

    @NotNull
    @Override
    public CharSequence getBase() {
        return charSequence;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    private SubSequence(@NotNull CharSequence charSequence) {
        super(charSequence instanceof String ? charSequence.hashCode() : 0);
        assert !(charSequence instanceof BasedSequence);
        baseSeq = this;
        this.charSequence = charSequence;
        startOffset = 0;
        endOffset = charSequence.length();
    }

    // NOTE: called only from baseSubSequence
    private SubSequence(@NotNull SubSequence subSequence, int startIndex, int endIndex) {
        super(0);

        assert startIndex >= 0 && endIndex >= startIndex && endIndex <= subSequence.length() : String.format("SubSequence must have startIndex >= 0 && endIndex >= startIndex && endIndex <= %d, got startIndex:%d, endIndex: %d", subSequence.length(), startIndex, endIndex);

        baseSeq = subSequence;
        charSequence = subSequence.charSequence;
        startOffset = startIndex;
        endOffset = endIndex;
    }

    @Override
    public void addSegments(@NotNull IBasedSegmentBuilder<?> builder) {
        assert builder.getBaseSequence() == baseSeq || builder.getBaseSequence().equals(baseSeq);

        builder.append(startOffset, endOffset);
    }

    @Override
    public int length() {
        return endOffset - startOffset;
    }

    @NotNull
    @Override
    public Range getSourceRange() {
        return Range.of(startOffset, endOffset);
    }

    @Override
    public int getIndexOffset(int index) {
        SequenceUtils.validateIndexInclusiveEnd(index, length());
        return startOffset + index;
    }

    @Override
    public char charAt(int index) {
        SequenceUtils.validateIndex(index, length());

        char c = charSequence.charAt(index + startOffset);
        return c == SequenceUtils.NUL ? SequenceUtils.ENC_NUL : c;
    }

    @NotNull
    @Override
    public SubSequence subSequence(int startIndex, int endIndex) {
        SequenceUtils.validateStartEnd(startIndex, endIndex, length());
        return baseSubSequence(startOffset + startIndex, startOffset + endIndex);
    }

    @NotNull
    @Override
    public SubSequence baseSubSequence(int startIndex, int endIndex) {
        SequenceUtils.validateStartEnd(startIndex, endIndex, baseSeq.length());
        return startIndex == startOffset && endIndex == endOffset ? this : baseSeq != this ? baseSeq.baseSubSequence(startIndex, endIndex) : new SubSequence(this, startIndex, endIndex);
    }

    static BasedSequence create(@Nullable CharSequence charSequence) {
        if (charSequence == null) return BasedSequence.NULL;
        else if (charSequence instanceof BasedSequence) return (BasedSequence) charSequence;
        else return new SubSequence(charSequence);
    }

    @Deprecated
    public static BasedSequence of(@Nullable CharSequence charSequence) {
        return BasedSequence.of(charSequence);
    }

    @Deprecated
    public static BasedSequence of(@Nullable CharSequence charSequence, int startIndex) {
        return BasedSequence.of(charSequence).subSequence(startIndex, charSequence == null ? 0 : charSequence.length());
    }

    @Deprecated
    public static BasedSequence of(@Nullable CharSequence charSequence, int startIndex, int endIndex) {
        return BasedSequence.of(charSequence).subSequence(startIndex, endIndex);
    }
}
