package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.WeakHashMap;

class BaseSequenceEntry {
    final private @NotNull WeakHashMap<Object, Boolean> quickEquals = new WeakHashMap<>();

    @TestOnly
    private int equalsCall; // 0 - quick class and/or length, 1 - hash, 2 - quick lookup, 3 - content

    private int hash;

    BaseSequenceEntry() {

    }

    /**
     * Get type of equality used to figure out result
     * only valid right after equals call and in tests
     *
     * @return equality type used, 0 - quick class and/or length, 1 - hash, 2 - quick lookup, 3 - content comparison
     */
    @TestOnly
    int getEqualsCall() {
        return equalsCall;
    }

    /**
     * Compare object to equality of entry's base sequence
     * NOTE: if not char sequence or base of this entry's base sequence then will return false, so do not expect to pass a new instance of char[] and to get true for equivalent CharSubSequence
     *
     * @param o object to compare
     * @return true if object equivalent to this entry's base sequence, false otherwise
     */
    boolean testEquals(@NotNull BasedSequence baseSeq, @NotNull Object o) {
        equalsCall = 0;
        if (o == baseSeq || o == baseSeq.getBase()) return true;
        if (!(o instanceof CharSequence)) return false;

        CharSequence other = (CharSequence) o;

        if (other.length() != baseSeq.length()) return false;

        if (o instanceof IRichSequence<?>) {
            IRichSequence<?> rich = (BasedSequence) o;
            equalsCall = 1;
            if (rich.hashCode() != baseSeq.hashCode()) return false;

            //fall through to quickEquals tests then slow content comparison
        } else if (o instanceof String) {
            String string = (String) o;
            equalsCall = 1;
            if (string.hashCode() != baseSeq.hashCode()) return false;

            //fall through to quickEquals tests then slow content comparison
        }

        // see if already have it in the weak hash map
        Boolean result;
        equalsCall = 2;

        synchronized (quickEquals) {
            result = quickEquals.get(o);
        }

        if (result != null) return result;

        result = baseSeq.equals(o);
        equalsCall = 3;

        synchronized (quickEquals) {
            quickEquals.put(o, result);
        }

        return result;
    }

    public int getHashCode(@NotNull BasedSequence baseSeq) {
        int h = hash;
        if (h == 0 && baseSeq.length() > 0) {
            h = baseSeq.hashCode();
            hash = h;
        }
        return h;
    }

    @Override
    public boolean equals(Object o) {
        throw new IllegalStateException("Not Supported, use testEquals()");
    }

    @Override
    public int hashCode() {
        throw new IllegalStateException("Not Supported, use getHashCode()");
    }
}
