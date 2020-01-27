package com.vladsch.flexmark.tree.iteration;

import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Stack;
import java.util.function.Predicate;

final public class IteratorInstance<N, R> implements ValueIteration<R> {
    //    final static private Logger LOG = TreeIterator.LOG;
    final static private Logger LOG_INFO = TreeIterator.LOG_INFO;
    final static private Logger LOG_TRACE = TreeIterator.LOG_TRACE;

    private Iteration<N> myIteration;               // current iteration information
    private @Nullable Stack<Iteration<N>> myRecursions;       // recursion frames
    final private @NotNull IterationConditions<N> myIterationConditions;
    final private @NotNull Predicate<? super N> myRecursionPredicate;
    final private @NotNull Predicate<? super N> myFilterPredicate;
    private int myTotalLoopCount = 0;                        // total looping count across all nesting levels, including filtered out elements
    private int myTotalAcceptCount = 0;                            // total looping count across all nesting levels, only consumed elements
    private @Nullable N myMatch;
    private @Nullable MutableDataSet myDataSet;
    final private @NotNull Object myDefaultValue;

    // ValueResult
    private @NotNull Object myResult;
    private boolean myBreak = false;
    private boolean myHadRecurse = false;          // true if current iteration had performed recurse
    private boolean myIsDefaultResult = true;
    private int myMaxRecursions = 0;

    public IteratorInstance(
            @NotNull IterationConditions<N> iterationConditions,
            @NotNull Predicate<? super N> filterPredicate,
            @NotNull Predicate<? super N> recursionPredicate,
            @NotNull N element
    ) {
        this(iterationConditions, filterPredicate, recursionPredicate, element, VoidIteration.NULL);
    }

    public IteratorInstance(
            @NotNull IterationConditions<N> iterationConditions,
            @NotNull Predicate<? super N> filterPredicate,
            @NotNull Predicate<? super N> recursionPredicate,
            @NotNull N element,
            @NotNull Object defaultValue
    ) {
        myIterationConditions = iterationConditions;
        myRecursionPredicate = recursionPredicate;
        myFilterPredicate = filterPredicate;
        myIteration = new Iteration<>(myIterationConditions.getInitializer().apply(element));
        myDefaultValue = defaultValue;
        myResult = defaultValue;
    }

    public void iterate(@NotNull VoidIterationConsumer<? super N> consumer) {
        iterate(new VoidToValueIConsumerAdapter<>(consumer));
    }

    public void iterate(@NotNull ValueIterationConsumer<? super N, R> consumer) {
        consumer.beforeStart(this);
        if (LOG_INFO.isDebugEnabled()) LOG_INFO.debug("Starting looping " + myIteration);

        if (LOG_INFO.isDebugEnabled()) LOG_INFO.debug("Start recursion " + getRecursionLevel());

        while (true) {
            if (myIteration.next == null) {
                // see if all done, or just current

                if (LOG_INFO.isDebugEnabled()) LOG_INFO.debug("End recursion " + getRecursionLevel());
                consumer.endRecursion(this);

                if (myRecursions == null || myRecursions.size() == 0) {
                    break;
                }

                dropRecursions(1, false);

                if (LOG_INFO.isDebugEnabled()) LOG_INFO.debug("Start recursion " + getRecursionLevel());
                consumer.startRecursion(this);

                continue;
            }

            myIteration.advance(myIterationConditions.getIterator().apply(myIteration.next));
            myTotalLoopCount++;

            myMatch = myIteration.current;
            if (myMatch == null) continue;

            if (myFilterPredicate.test(myMatch)) {
                myIteration.acceptCount++;
                myTotalAcceptCount++;

                myHadRecurse = false;
                consumer.accept(myMatch, this);

                if (LOG_TRACE.isDebugEnabled())
                    LOG_TRACE.debug("Consumed, recursion: " + getRecursionLevel() + " isBreak " + myBreak + " recursed: " + myHadRecurse + " " + myIteration);
                if (myBreak) break;
            } else {
                if (LOG_TRACE.isDebugEnabled())
                    LOG_TRACE.debug("Skipping, recursion: " + getRecursionLevel() + " filtered out " + myIteration);
            }

            if (!myHadRecurse && myMatch != null && myRecursionPredicate.test(myMatch)) {
                if (LOG_TRACE.isDebugEnabled()) LOG_TRACE.debug("Recursing " + myIteration);
                Recurse();
                if (LOG_INFO.isDebugEnabled()) LOG_INFO.debug("Start recursion " + getRecursionLevel());
                consumer.startRecursion(this);
            }
        }

        consumer.afterEnd(this);
        if (LOG_INFO.isDebugEnabled()) LOG_INFO.debug("Done looping, totalLoopCount: " + myTotalLoopCount + " totalCount: " + myTotalAcceptCount + " maxRecursions: " + myMaxRecursions + " " + myIteration);
    }

    @Override
    public boolean getHaveNext() {
        return myIteration.next != null;
    }

    @Override
    public boolean getHaveAcceptableNext() {
        return myIteration.next != null && myFilterPredicate.test(myIteration.next);
    }

    @Override
    public void setResult(@NotNull Object value) {
        myResult = value;
        myIsDefaultResult = false;
    }

    @NotNull
    @Override
    public R getResult() {
        //noinspection unchecked
        return (R) myResult;
    }

    @Override
    public void Return(@NotNull Object value) {
        myResult = value;
        myBreak = true;
        myMatch = null;
        myIsDefaultResult = false;
    }

    /**
     * Unconditionally recurse into current element
     */
    private void Recurse() {
        if (myMatch != null && !myHadRecurse) {
            if (myRecursions == null) {
                myRecursions = new Stack<>();
            }

            myHadRecurse = true;
            myRecursions.push(myIteration);
            myIteration = new Iteration<>(myIterationConditions.getInitializer().apply(myMatch));
            myMaxRecursions = Integer.max(myMaxRecursions, myRecursions.size());
            myMatch = null;
        }
    }

    @Override
    public void doReturn() {
        myBreak = true;
        myMatch = null;
    }

    private void dropRecursions(int iteration, boolean inclusive) {
        if (inclusive) iteration++;

        if (iteration > 0) {
            if (myRecursions == null || iteration > myRecursions.size()) {
                throw new IllegalArgumentException("Recursion level " + getRecursionLevel() + " is less than requested break/continue level " + iteration);
            }

            int i = iteration;
            while (i-- > 0) {
                myIteration = myRecursions.pop();
            }
        }
    }

    @Override
    public void doContinue(int recursionLevel) {
        if (myHadRecurse)
            throw new IllegalStateException("Continue(" + recursionLevel + ") called after Recurse() in current iteration");

        dropRecursions(recursionLevel, false);
        myMatch = null;
    }

    @Override
    public void doBreak(int recursionLevel) {
        if (myHadRecurse)
            throw new IllegalStateException("Break(" + recursionLevel + ") called after Recurse() in current iteration");

        if (recursionLevel == getRecursionLevel()) {
            // we are breaking out completely of all iterations and returning
            myBreak = true;
        } else {
            dropRecursions(recursionLevel, true);
        }
        myMatch = null;
    }

    @Override
    public MutableDataHolder getData() {
        if (myDataSet == null) {
            myDataSet = new MutableDataSet();
        }
        return myDataSet;
    }

    @Nullable
    public N getMatch() {
        return myMatch;
    }

    @Override
    public boolean isComplete() {
        return myMatch == null;
    }

    @Override
    public boolean isIncomplete() {
        return myMatch != null;
    }

    @Override
    public void ifIncomplete(@NotNull Runnable runnable) {
        if (isIncomplete()) {
            runnable.run();
        }
    }

    @Override
    public void doComplete() {
        myMatch = null;
    }

    @Override
    public boolean isTerminated() {
        return myBreak;
    }

    @Override
    public int getLoopCount() {
        return myIteration.loopCount;
    }

    @Override
    public int getAcceptCount() {
        return myIteration.acceptCount;
    }

    @Override
    public int getTotalLoopCount() {
        return myTotalLoopCount;
    }

    @Override
    public int getTotalAcceptCount() {
        return myTotalAcceptCount;
    }

    @Override
    public int getRecursionLevel() {
        return myRecursions == null ? 0 : myRecursions.size();
    }

    @Override
    public boolean isDefaultResult() {
        return myIsDefaultResult;
    }

    @NotNull
    @Override
    public R getDefaultValue() {
        //noinspection unchecked
        return (R) myDefaultValue;
    }

    public static class Iteration<V> {
        @Nullable V current; // current looping variable
        @Nullable V next;    // current looping variable
        int acceptCount;           // iteration count of valid filtered elements
        int loopCount;       // iteration count of all advance ops

        Iteration(@Nullable V next) {
            this.next = next;
            this.current = next;
            acceptCount = 0;
            loopCount = 0;
        }

        void advance(@Nullable V nextValue) {
            current = next;
            next = nextValue;
            loopCount++;
        }

        @Override
        public String toString() {
            String currentText = current == null ? "null" : current.toString();
            String nextText = next == null ? "null" : next.toString();
            return "Iteration {" +
                    ", count=" + acceptCount +
                    ", current=" + currentText.subSequence(0, Integer.min(currentText.length(), 30)) +
                    ", next=" + nextText.subSequence(0, Integer.min(nextText.length(), 30)) +
                    ", loopCount=" + loopCount +
                    '}';
        }
    }
}
