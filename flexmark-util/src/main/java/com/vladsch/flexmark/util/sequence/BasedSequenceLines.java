package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * A Builder for Segmented BasedSequences of lines. EOLs are automatically added when missing between lines.
 * Only the last line is allowed to have no EOL
 * <p>
 * toSequence() returns segmented sequence, any based sequence lines that are out of order or have different base are converted to String prefixes to put them in order
 * <p>
 * getLines() returns an array of individual based sequence lines. No conversion to string or checking that they are in order is done at this point
 * <p>
 * toCharSequence() returns a string of concatenated lines.
 * <p>
 * This is a list of based sequences but to allow plain {@link CharSequence} to be added as elements it is
 * a List&lt;CharSequence&gt;. To get contained based sequence of lines use getLines()
 */
public class BasedSequenceLines implements List<CharSequence> {
    private final @NotNull BasedSequence base;
    private @Nullable ArrayList<OffsetTracker> offsetTrackers;
    private final ArrayList<CharSequence> lines = new ArrayList<>();

    private final ArrayList<CharSequence> updateLines = new ArrayList<>();  // used to take a snapshot of lines at time of segments update so no mod tracking during updates is needed
    private final ArrayList<BasedSequence> segments = new ArrayList<>();

    private BasedSequenceLines(@NotNull BasedSequence base) {
        this.base = base.getBaseSequence();
    }

    private void addOffsetTracker(@Nullable OffsetTracker tracker) {
        if (offsetTrackers == null) offsetTrackers = new ArrayList<>();
        offsetTrackers.add(tracker);
    }

    @NotNull
    public BasedSequence getBaseSequence() {
        return base;
    }

    @NotNull
    public BasedSequenceLines subContext() {
        return new BasedSequenceLines(base);
    }

    /**
     * Get updated segments
     *
     * @param sequences array list to store result
     *                  forSequence true if  segments != this.segments, update is for SegmentedSequence and all segments should be in order
     *                  false if this is only for individual lines
     */
    private void updateSegments(@NotNull ArrayList<BasedSequence> sequences) {
        boolean forSequence = segments != sequences;

        if (forSequence || !Objects.equals(lines, updateLines)) {
            // convert non-based and out of sequence based to string suffix to previous based or prefix to next based
            sequences.clear();

            if (forSequence) offsetTrackers = null;
            else updateLines.clear();

            if (!lines.isEmpty()) {
                int[] basedIndices = new int[lines.size()];
                Arrays.fill(basedIndices, 0, basedIndices.length, -1);

                BasedSequence lastBased = null;

                int i = 0;
                int lastBasedIndex = -1;
                for (CharSequence sequence : lines) {
                    if (sequence instanceof BasedSequence) {
                        if (((BasedSequence) sequence).getBaseSequence() == base) {
                            if (lastBased == null) {
                                lastBased = (BasedSequence) sequence;
                                Arrays.fill(basedIndices, lastBasedIndex + 1, i, i);
                                lastBasedIndex = i;
                            } else {
                                if (!forSequence || lastBased.getEndOffset() <= ((BasedSequence) sequence).getStartOffset()) {
                                    // in sequence or only for lines, new based anchor
                                    lastBased = (BasedSequence) sequence;
                                    Arrays.fill(basedIndices, lastBasedIndex + 1, i, i);
                                    lastBasedIndex = i;
                                }
                            }

                            if (forSequence && sequence instanceof BasedTrackedSequence) {
                                addOffsetTracker(((BasedTrackedSequence) sequence).getOffsetTracker());
                            }
                        }
                    }

                    i++;
                }

                //noinspection StatementWithEmptyBody
                if (lastBasedIndex == -1) {
                    // no based sequences, this is not right
                } else if (lastBasedIndex + 1 < basedIndices.length) {
                    // tail end lines become suffixes of last based line
                    Arrays.fill(basedIndices, lastBasedIndex + 1, basedIndices.length, lastBasedIndex);
                }

                i = 0;
                lastBased = null;
                int lastLine = lines.size() - 1;

                for (CharSequence sequence : lines) {
                    int basedIndex = basedIndices[i];

                    if (i != basedIndex) {
                        String s = sequence.toString();
                        if (i < lastLine && !s.endsWith("\n")) {
                            s = s + "\n";
                        }

                        // QUERY: is it ever necessary to test/add tracking to non based lines?
                        if (basedIndex == -1) {
                            // all are strings
                            sequences.add(PrefixedSubSequence.prefixOf(s, base.getEmptyPrefix()));
                        } else {
                            // when basedIndices[i] != i, means it is a sequence which should be converted to string and added as
                            // a prefix/suffix to based sequence at i in the lines list
                            BasedSequence basedSequence = (BasedSequence) lines.get(basedIndex);
                            sequences.add(PrefixedSubSequence.prefixOf(s, basedIndex > i ? basedSequence.getEmptySuffix() : basedSequence.getEmptyPrefix()));
                        }
                    } else {
                        BasedSequence basedSequence = (BasedSequence) sequence;
                        assert basedSequence.getBaseSequence() == base;
                        assert lastBased == null || lastBased.getEndOffset() <= basedSequence.getStartOffset();

                        if (i < lastLine && !basedSequence.endsWithEOL()) {
                            basedSequence = basedSequence.suffixWithEOL();
                        }

                        lastBased = basedSequence;
                        sequences.add(basedSequence);
                    }
                    i++;
                }

                assert sequences.size() == lines.size();

                if (!forSequence) {
                    updateLines.addAll(lines);
                }
            }
        }
    }

    public static ArrayList<BasedSequence> adjustLastLineEOL(@NotNull ArrayList<BasedSequence> sequences, @NotNull DiscretionaryText lastLineEOL) {
        if (!sequences.isEmpty()) {
            BasedSequence lastLine = sequences.get(sequences.size() - 1);
            BasedSequence originalLine = sequences.get(sequences.size() - 1);

            lastLine = adjustLineEOL(lastLine, lastLineEOL);

            if (lastLine != originalLine) {
                sequences.set(sequences.size() - 1, lastLine);
            }
        }
        return sequences;
    }

    public static BasedSequence adjustLineEOL(BasedSequence lastLine, @NotNull DiscretionaryText lastLineEOL) {
        switch (lastLineEOL) {
            case AS_IS:
                break;
            case ADD:
                lastLine = lastLine.suffixOnceWithEOL();
                break;
            case REMOVE:
                lastLine = lastLine.removeSuffix(IRichSequence.EOL);
                break;
        }
        return lastLine;
    }

    @NotNull
    public ArrayList<BasedSequence> getLines() {
        return toLines(DiscretionaryText.AS_IS);
    }

    @NotNull
    public ArrayList<BasedSequence> toLines(DiscretionaryText lastLineEOL) {
        updateSegments(segments);
        return adjustLastLineEOL(new ArrayList<>(segments), lastLineEOL);
    }

    @NotNull
    String toCharSequence(DiscretionaryText lastLineEOL) {
        updateSegments(segments);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int lastLine = segments.size() - 1;
        for (BasedSequence sequence : segments) {
            if (i == lastLine) {
                sequence = adjustLineEOL(sequence, lastLineEOL);
            }
            sb.append(sequence);
            i++;
        }

        return sb.toString();
    }

    /**
     * Convert lines to segmented sequence and apply any offset trackers from contained lines
     * to the result
     *
     * @return resulting based sequence
     */
    @NotNull
    public BasedSequence toSequence(DiscretionaryText lastLineEOL) {
        ArrayList<BasedSequence> sequences = new ArrayList<>(lines.size());
        updateSegments(sequences);
        adjustLastLineEOL(sequences, lastLineEOL);

        BasedSequence modifiedSeq = SegmentedSequence.of(segments);
        if (offsetTrackers != null) {
            OffsetTracker modifiedTracker = null;
            for (OffsetTracker offsetTracker : offsetTrackers) {
                modifiedTracker = offsetTracker.modifiedTracker(modifiedSeq, modifiedTracker);
            }

            if (modifiedTracker != null) {
                return BasedTrackedSequence.create(modifiedSeq, modifiedTracker);
            }
        }
        return modifiedSeq;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int line = 0;
        boolean hadEOL = true;
        sb.append("BasedSequenceLines:\n");
        for (CharSequence s : lines) {
            if (!hadEOL) sb.append(RichSequence.EOL);
            sb.append("  [").append(line).append("]: {");
            if (s.length() > 0 && s.charAt(s.length() - 1) == '\n') {
                hadEOL = true;
                sb.append(s.subSequence(0, s.length() - 1));
            } else {
                hadEOL = false;
                sb.append(s);
            }
            sb.append("}\n");
            line++;
        }

        return sb.toString();
    }

    public void trimToSize() {lines.trimToSize();}

    public void ensureCapacity(int minCapacity) {lines.ensureCapacity(minCapacity);}

    @Override
    public int size() {return lines.size();}

    @Override
    public boolean isEmpty() {return lines.isEmpty();}

    @Override
    public boolean contains(Object o) {return lines.contains(o);}

    @Override
    public int indexOf(Object o) {return lines.indexOf(o);}

    @Override
    public int lastIndexOf(Object o) {return lines.lastIndexOf(o);}

    @NotNull
    @Override
    public Object[] toArray() {return lines.toArray();}

    @NotNull
    @Override
    public <T> T[] toArray(T[] a) {return lines.toArray(a);}

    @Override
    public CharSequence get(int index) {return lines.get(index);}

    @Override
    public CharSequence set(int index, CharSequence element) {return lines.set(index, element);}

    @Override
    public boolean add(CharSequence sequence) {return lines.add(sequence);}

    @Override
    public void add(int index, CharSequence element) {lines.add(index, element);}

    @Override
    public CharSequence remove(int index) {return lines.remove(index);}

    @Override
    public boolean remove(Object o) {return lines.remove(o);}

    @Override
    public void clear() {lines.clear();}

    @Override
    public boolean addAll(Collection<? extends CharSequence> c) {return lines.addAll(c);}

    @Override
    public boolean addAll(int index, Collection<? extends CharSequence> c) {return lines.addAll(index, c);}

    @Override
    public boolean removeAll(Collection<?> c) {return lines.removeAll(c);}

    @Override
    public boolean retainAll(Collection<?> c) {return lines.retainAll(c);}

    @NotNull
    @Override
    public ListIterator<CharSequence> listIterator(int index) {return lines.listIterator(index);}

    @NotNull
    @Override
    public ListIterator<CharSequence> listIterator() {return lines.listIterator();}

    @NotNull
    @Override
    public Iterator<CharSequence> iterator() {return lines.iterator();}

    @NotNull
    @Override
    public List<CharSequence> subList(int fromIndex, int toIndex) {return lines.subList(fromIndex, toIndex);}

    @Override
    public boolean containsAll(Collection<?> c) {return lines.containsAll(c);}
}
