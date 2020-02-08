package com.vladsch.flexmark.experimental.util.sequence.managed;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.IRichSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;
import org.jetbrains.annotations.NotNull;

import java.util.WeakHashMap;

public class BaseSequenceEntry {
    final private @NotNull WeakHashMap<Object, Boolean> quickEquals = new WeakHashMap<>();

    private int hash;

    public BaseSequenceEntry() {

    }

    /**
     * Compare object to equality of entry's base sequence
     * NOTE: if not char sequence or base of this entry's base sequence then will return false, so do not expect to pass a new instance of char[] and to get true for equivalent CharSubSequence
     *
     * @param baseSeq    base sequence
     * @param o          object to compare
     * @param equalsCall 1 element array where to return type of equals test done
     *                   equality type used, 0 - quick class and/or length, 1 - hash, 2 - quick lookup, 3 - string content comparison, 4 - char sequence comparison
     * @return true if object equivalent to this entry's base sequence, false otherwise
     */
    public boolean testEquals(@NotNull BasedSequence baseSeq, @NotNull Object o, int[] equalsCall) {
        equalsCall[0] = 0;
        if (o == baseSeq || o == baseSeq.getBase()) return true;
        if (!(o instanceof CharSequence)) return false;

        CharSequence other = (CharSequence) o;

        if (other.length() != baseSeq.length()) return false;

        if (o instanceof IRichSequence<?>) {
            IRichSequence<?> rich = (BasedSequence) o;
            equalsCall[0] = 1;
            if (rich.hashCode() != baseSeq.hashCode()) return false;

            //fall through to quickEquals tests then slow content comparison
        } else if (o instanceof String) {
            String string = (String) o;
            equalsCall[0] = 1;
            if (string.hashCode() != baseSeq.hashCode()) return false;

            //fall through to quickEquals tests then slow content comparison
        }

        // see if already have it in the weak hash map
        Boolean result;
        equalsCall[0] = 2;

        // no need to synchronize. Only called by Manager from synchronized code
        result = quickEquals.get(o);

        if (result != null) return result;

        if (baseSeq instanceof SubSequence && o instanceof String) {
            equalsCall[0] = 3;
            result = baseSeq.getBase().equals(o);
        } else if (baseSeq instanceof SubSequence && o instanceof SubSequence) {
            equalsCall[0] = 3;
            result = baseSeq.getBase().equals(((SubSequence) o).getBase());
        } else {
            equalsCall[0] = 4;
            result = baseSeq.equals(o);
        }

        quickEquals.put(o, result);

        return result;
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
