package com.vladsch.flexmark.experimental.util.sequence.managed;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public class BaseSequenceManager {
    // NOTE: baseMap will keep reference to BaseSequenceEntry as long as the underlying base object is in use
    //   baseSet relies on baseMap to keep references alive for used BasedSequence and its BaseSequenceEntry for quick fail/pass on equals
    final private @NotNull WeakHashMap<Object, WeakReference<BasedSequence>> baseMap = new WeakHashMap<>();
    final private @NotNull WeakHashMap<BasedSequence, BaseSequenceEntry> baseSet = new WeakHashMap<>();

    public BaseSequenceManager() {

    }

    /**
     * Get an equivalent existing based sequence base or a new one created by passed factory
     * <p>
     * NOTE: should only be called by base sequence which are the base for their category: {@link SubSequence} implementing managed sequence base
     * <p>
     * all others should delegate to these sequences for creating the base
     *
     * @param <T>       type of base character sequence
     * @param object    object for the underlying based sequence base
     * @param callTypes one element array for type of tests done to find result
     *                  NOTE: 0 if map lookup, 10 - set search, 20 - construct and add to map/set
     *                  with units digit giving max testEquals call type from all tests done
     * @param factory   factory to create based sequence from the object
     * @return existing equivalent base or newly created base
     */
    @NotNull
    public <T> BasedSequence getBaseSequence(@NotNull T object, @Nullable int[] callTypes, @NotNull Function<T, BasedSequence> factory) {
        WeakReference<BasedSequence> baseEntry;
        BasedSequence baseSeq;

        int callType = 0;

        synchronized (baseMap) {
            baseEntry = baseMap.get(object);

            if (baseEntry != null) {
                baseSeq = baseEntry.get();
                if (baseSeq != null) {
                    if (callTypes != null) callTypes[0] = callType;
                    return baseSeq;
                }

                baseMap.remove(object);
            }

            // see if we can find one in the set that matches
            callType = 10;
            int[] equalsCall = { 0 };
            for (Map.Entry<BasedSequence, BaseSequenceEntry> entry : baseSet.entrySet()) {
                if (entry != null) {
                    if (entry.getValue().testEquals(entry.getKey(), object, equalsCall)) {
                        callType = Math.max(callType, 10 + equalsCall[0]);
                        if (callTypes != null) callTypes[0] = callType;
                        return entry.getKey();
                    }
                    callType = Math.max(callType, 10 + equalsCall[0]);
                }
            }

            BasedSequence newBaseSeq = factory.apply(object);
            assert newBaseSeq == newBaseSeq.getBaseSequence();
            assert newBaseSeq.getBase() == object;

            // preserve entry search max call type
            callType += 10;
            if (callTypes != null) callTypes[0] = callType;
            baseMap.put(object, new WeakReference<>(newBaseSeq));
            baseSet.put(newBaseSeq, new BaseSequenceEntry());
            return newBaseSeq;
        }
    }
}
