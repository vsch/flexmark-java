package com.vladsch.flexmark.util.sequence;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;

public class BaseSequenceManager {
    final public static BaseSequenceManager INSTANCE = new BaseSequenceManager();

    // NOTE: baseMap will keep reference to BaseSequenceEntry as long as the underlying base object is in use
    //   baseSet relies on baseMap to keep references alive for used BasedSequence and its BaseSequenceEntry for quick fail/pass on equals
    final private @NotNull WeakHashMap<Object, WeakReference<BasedSequence>> baseMap = new WeakHashMap<>();
    final private @NotNull WeakHashMap<BasedSequence, BaseSequenceEntry> baseSet = new WeakHashMap<>();

    @TestOnly
    private int callType;

    private BaseSequenceManager() {

    }

    /**
     * Get type of getBaseSequence impact
     *
     * @return 0 if map lookup, 10 - set search, with units digit giving max testEquals call type from all tests done, 20 - construct and add to map/set, 30 - construct but was added in anther thread, so dropped
     */
    @TestOnly
    public int getCallType() {
        return callType;
    }

    /**
     * Get an equivalent existing based sequence base or a new one created by passed factory
     * <p>
     * NOTE: should only be called by base sequence which are the base for their category: {@link CharSubSequence} and {@link SubSequence} for now
     * <p>
     * all others should delegate to these sequences for creating the base
     *
     * @param object  object for the underlying based sequence base
     * @param factory factory to create based sequence from the object
     * @return existing equivalent base or newly created base
     */
    @NotNull
    public <T> BasedSequence getBaseSequence(@NotNull T object, @NotNull Function<T, BasedSequence> factory) {
        WeakReference<BasedSequence> baseEntry;
        BasedSequence baseSeq;

        callType = 0;

        synchronized (baseMap) {
            baseEntry = baseMap.get(object);

            if (baseEntry != null) {
                baseSeq = baseEntry.get();
                if (baseSeq != null) {
                    return baseSeq;
                }

                baseMap.remove(object);
            }
        }

        // see if we can find one in the set that matches
        callType = 10;
        for (Map.Entry<BasedSequence, BaseSequenceEntry> entry : baseSet.entrySet()) {
            if (entry != null) {
                if (entry.getValue().testEquals(entry.getKey(), object)) {
                    callType = Math.max(callType, 10 + entry.getValue().getEqualsCall());
                    return entry.getKey();
                }
                callType = Math.max(callType, 10 + entry.getValue().getEqualsCall());
            }
        }

        BasedSequence newBaseSeq = factory.apply(object);
        assert newBaseSeq == newBaseSeq.getBaseSequence();

        synchronized (baseMap) {
            baseEntry = baseMap.get(object);

            if (baseEntry != null) {
                baseSeq = baseEntry.get();
                if (baseSeq != null) {
                    // preserve entry search max call type
                    callType += 20;
                    return baseSeq;
                }

                baseMap.remove(object);
            }

            // preserve entry search max call type
            callType += 10;
            baseMap.put(object, new WeakReference<>(newBaseSeq));
            baseSet.put(newBaseSeq, new BaseSequenceEntry());
            return newBaseSeq;
        }
    }
}
