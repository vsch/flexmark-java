# Segment List

Is a list of parts used to construct a sequence of characters. Each part represents a range of
from the original sequence or text outside the original sequence implemented by `SegmentList`
with properties:
* `range`: range of the resulting sequence in original sequence. `Range.NULL` for a list which
  does not contain such information. :information_source: null range has start =
  `Integer.MAX_VALUE` and end = `Integer.MIN_VALUE`
* `startOffset`: `range` start,  start offset of the resulting sequence in original sequence,
* `endOffset`: `range` end,  end offset of the resulting sequence in original sequence,
* `lastSegment`: last segment contained in the list, is null for empty list.
* `segments`: list of segments describing the resulting sequence and their position in the
  original sequence.
* `length`: length of resulting sequence.
* `textLength`: length of all text in the resulting sequence which is out of the original
  sequence.

Each segment part consists of a range in the original sequence that it represents and text from
out of original sequence. It has `span` and `length` properties, with `length` being the length
of text it is contributing to the sequence being constructed. `span` is the number of characters
in the original sequence this segment represents or replaces. Either the `span` or `length` can
be 0. If both are zero then this is a `NULL` segment with no effect on the resulting sequence
and should not be part of the segment list.

A segment list can contain the following segment types:
* `NULL`: null segment, should not exist in a normalized list. Ignored when building a list
* `ANCHOR`: range of span =0, text is empty, `span` is span of range, `length` and `span` = 0,
  position is the offset of this anchor in the original sequence. This segment does not exist in
  a normalized list. Used only to provide range information to `STRING` and list while building
  a list.
* `STRING`: range is null, text is not empty, `length` is length of text, `span` is 0
* `TEXT`: range of span >= 0, text is not empty, `length` is length of text, `span` is span of
  range. Start is `startOffset` of list if it is the first element in the list. Otherwise it is
  equal to previous `BASE` end. End offset is equal `endOffset` of list when the last segment in
  a  list. Otherwise it is equal to start of following `BASE`.
* `BASE`: range of span >0, text is empty, `span` is span of range, `length` is `span`

Only `ANCHOR`, `BASE` or `STRING` types can be appended to a segment list and always results in
a normalized segment list. Concrete `ANCHOR` and `BASE` are `Range` instances with the former
having `Range.getSpan()` == 0. Concrete `STRING` is `String`.

`ANCHOR` is can only set `startOffset` of a list which has no offset information or extend its
end offset if it is less than the `ANCHOR` end. It is only used while building list to mark the
start/end extent of a segment list in original sequence. It adds no content and is ignored when
it does not affect the extent of the resulting sequence.

#### Segment List Invariants

* `lastSegment` is the last segment in the list or null for empty list
* `endOffset`, `range` end are equal, if `lastSegment` is not null then its end is equal to
  `endOffset`
* `startOffset`, `range` start are equal, list is not empty then the first segment start is
  equal to `startOffset`
* `length` equals the sum of `length` of segments in a list
* `textLength` equals the sum of `length` of non `BASE` segments in the list.
* `STRING` segment can only be the the only element in a list with null range.
* `TEXT` start will always be `startOffset` if first element or end of previous `BASE`. end
  offset will be `endOffset` if last segment in list or start of next `BASE`.

### Appending Rules

Result of an appending operation are determined only by the list `range` and `lastSegment`
properties, the segment being appended.

Only appending of a `BASE` can be: `disjoint`, `adjacent` or `overlapped` and only if the list
has original sequence offset information.

* `disjoint`: appended segment has no range information, list has no offset information, or
  segment start > `endOffset`.
* `adjacent`: appended segment start == `endOffset`
* `overlapped`: appended segment start < `endOffset`

The overlap requires resolving and depends on type of sequence and has the following outcomes
based on result of the overlap resolution:
* null: appending range is thrown out, list unchanged, for example if the last segment contains
  the appending range.
* `BASE`: last segment end set to appending range end. Overlap is removed from appending range
  and resulting adjacent range merged into the last segment.
* `TEXT`: overlap converted to text of original sequence with start set to last segment end. The
  overlap contains the appending range and is converted to original sequence characters.
* `TEXT`, `BASE`: converted to text of original sequence characters and non-overlapping range.
  The intersection of appending range and last segment is converted to original sequence
  characters and a range. with start == end of last segment. The range of `TEXT` segment is the
  end of last segment and start of converted non-overlapped range.

#### Disjoint or Adjacent Append Rules

|   Last   | Appended | Result (= replaces last, + appended) |                                     Description                                      |
|----------|----------|--------------------------------------|--------------------------------------------------------------------------------------|
|          | `BASE`   | +`BASE`                              |                                                                                      |
|          | `STRING` | +`STRING`                            |                                                                                      |
|          | `ANCHOR` |                                      | `startOffset`/`endOffset` set to anchor pos                                          |
| `STRING` | `ANCHOR` | =`TEXT`                              | `startOffset`/`endOffset` and `TEXT` start/end = anchor position                     |
| `STRING` | `STRING` | =`STRING`                            | `STRING` appended text                                                               |
| `STRING` | `BASE`   | =`TEXT` +`BASE`                      | `startOffset` and `TEXT` start/end set to `BASE` start, `endOffset` set to range end |
| `TEXT`   | `STRING` | =`TEXT`                              | `STRING` appended to `TEXT` text                                                     |
| `TEXT`   | `BASE`   | =`TEXT` +`BASE`                      | `TEXT` end, `endOffset` = range start                                                |
| `BASE`   | `BASE`   | =`BASE`                              | appending range start == `endOffset`, `BASE` end = appending range end               |
| `BASE`   | `STRING` | +`TEXT`                              | `TEXT` start = range end, text = `STRING` text                                       |
| `BASE`   | `BASE`   | +`BASE`                              | appended range start is > `endOffset`                                                |
| `BASE`   | `BASE`   | see overlapped append                | appending range start < `endOffset`, overlap resolution required                     |

