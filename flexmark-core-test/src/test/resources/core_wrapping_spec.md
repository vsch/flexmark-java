---
title: Wrapping and Offset Tracking Spec
author:
version:
date: '2016-06-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Formatter Spec Tests

## Wrap

```````````````````````````````` example(Wrap: 1) options(margin[30])
Paragraph with hard break and more text.

Paragraph with soft break and more text.

Paragraph with hard break and more text.

.
Paragraph with hard break and
more text.

Paragraph with soft break and
more text.

Paragraph with hard break and
more text.

````````````````````````````````


```````````````````````````````` example(Wrap: 2) options(margin[30])
Paragraph with hard break and # more text.

Paragraph with soft break and * more text.
                               
Paragraph with hard break and 1. more text.

.
Paragraph with hard break and
\# more text.

Paragraph with soft break and
\* more text.

Paragraph with hard break and
1\. more text.

````````````````````````````````


```````````````````````````````` example(Wrap: 3) options(margin[30], no-apply-escapers)
Paragraph with hard break and # more text.

Paragraph with soft break and * more text.
                               
Paragraph with hard break and 1. more text.

.
Paragraph with hard break and
# more text.

Paragraph with soft break and
* more text.

Paragraph with hard break and
1. more text.

````````````````````````````````


```````````````````````````````` example(Wrap: 4) options(margin[60], no-soft-breaks)
Paragraph with hard break and
\# more text.

Paragraph with soft break and
\* more text.

Paragraph with hard break and
1\. more text.

.
Paragraph with hard break and # more text.

Paragraph with soft break and * more text.

Paragraph with hard break and 1. more text.

````````````````````````````````


```````````````````````````````` example(Wrap: 5) options(margin[60], no-apply-escapers, no-soft-breaks)
Paragraph with hard break and
\# more text.

Paragraph with soft break and
\* more text.

Paragraph with hard break and
1\. more text.

.
Paragraph with hard break and \# more text.

Paragraph with soft break and \* more text.

Paragraph with hard break and 1\. more text.

````````````````````````````````


```````````````````````````````` example(Wrap: 6) options(margin[30])
* Paragraph with hard break and more text.

1. Paragraph with soft break and more text.
                               
.
* Paragraph with hard break
  and more text.

1. Paragraph with soft break
   and more text.

````````````````````````````````


```````````````````````````````` example(Wrap: 7) options(margin[96], insert-space, first-prefix[* ], prefix[], show-ranges, restore-tracked-spaces)
⟦* Fix: copy fixed utils from Arduino Support plugin.
* Fix: Paste Image: old crop settings out of bounds for new image caused 
⟧⟦* ⟧Fix: for ⦙#651, Drop image with dialog issues
    
.
* Fix: for ⦙#651, Drop image with dialog issues

---- Tracked Offsets ---------------------------------------------------
[0]: {138 1|0 si -> 11}

---- Ranges ------------------------------------------------------------
⟦⟧* ⟦Fix: for #651, Drop image with dialog issues
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[129, 174), s=0:1, u=2:3, t=2:3, l=48, sz=5, na=3: [129), a:'* ', [129, 174), a:'\n', [174) }
````````````````````````````````


* [ ] complete this. Space at end of previous line affect how character backspace and character
      typing reacts at the first non-blank of the next line.

```````````````````````````````` example(Wrap: 8) options(FAIL, margin[96], insert-char, first-prefix[* ], prefix[], show-ranges, restore-tracked-spaces)
⟦* ⟧Fix: for #651, Drop image with dialog issues alsdf jals;df jl;as dfjl;saj fdlaskjdf ;ls adfd 
⟦   ⟧d⦙as this is the end. 
    
.
* Fix: for #651, Drop image with dialog issues alsdf jals;df jl;as dfjl;saj fdlaskjdf ;ls adfd
d⦙as this is the end.

---- Tracked Offsets ---------------------------------------------------
[0]: { [100, i) } --> 96

---- Ranges ------------------------------------------------------------
⟦⟧* ⟦Fix: for #651, Drop image with dialog issues alsdf jals;df jl;as dfjl;saj fdlaskjdf ;ls adfd⟧⟦
⟧⟦das this is the end.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[2, 121), s=0:1, u=2:3, t=2:3, l=117, sz=8, na=6: [2), a:'* ', [2, 94), [95, 96), [99, 119), [120, 121), a:'\n', [121) }
````````````````````````````````


```````````````````````````````` example(Wrap: 9) options(FAIL, margin[96], delete-char, first-prefix[* ], prefix[], show-ranges, restore-tracked-spaces)
⟦* ⟧Fix: for #651, Drop image with dialog issues alsdf jals;df jl;as dfjl;saj fdlaskjdf ;ls adfd 
⟦   ⟧⦙as this is the end. 
    
.
* Fix: for #651, Drop image with dialog issues alsdf jals;df jl;as dfjl;saj fdlaskjdf ;ls adf
⦙as this is the end.

---- Tracked Offsets ---------------------------------------------------
[0]: { [99, si) } --> 95

---- Ranges ------------------------------------------------------------
⟦⟧* ⟦Fix: for #651, Drop image with dialog issues alsdf jals;df jl;as dfjl;saj fdlaskjdf ;ls adfd⟧⟦
⟧⟦as this is the end.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[2, 120), s=0:1, u=2:3, t=2:3, l=116, sz=8, na=6: [2), a:'* ', [2, 94), [95, 96), [99, 118), [119, 120), a:'\n', [120) }
````````````````````````````````


### Links

do not wrap links

```````````````````````````````` example(Wrap - Links: 1) options(margin[30])
[Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
.
[Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 2) options(margin[30])
![Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
.
![Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 3) options(margin[30], explicit-links-at-start, show-ranges)
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break
  and more text.
  [Test](test) text.

1. Paragraph with soft break
   and more text.
   ![Test](test) text.

---- Ranges ------------------------------------------------------------
⟦* Paragraph with hard break⟧
⟦⟧ ⟦ and more text.⟧
⟦⟧ ⟦ [Test](test) text.⟧⟦
⟧⟦
1. Paragraph with soft break⟧
⟦⟧  ⟦ and more text.⟧
⟦⟧  ⟦ ![Test](test) text.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 133), s=4:6, u=9:11, t=9:11, l=138, sz=22, na=17: [0, 27), a:'\n', [27), a:' ', [27, 42), a:'\n', [42), a:' ', [42, 61), [62, 63), [67, 96), a:'\n', [96), a:2x' ', [96, 111), a:'\n', [111), a:2x' ', [111, 131), [132, 133), a:'\n', [133) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 4) options(margin[34], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
⟦* Paragraph with hard break and more text. [Test](test) text. 
    
⟧1. Paragraph with soft break and more text. ⦙![Test](testing) text. 
                               
.
1. Paragraph with soft break and
   more text. ⦙
   ![Test](testing) text.

---- Tracked Offsets ---------------------------------------------------
[0]: {112 1|0 si -> 47}

---- Ranges ------------------------------------------------------------
⟦1. Paragraph with soft break and⟧
⟦⟧  ⟦ more text.⟧ ⟦⟧
⟦⟧  ⟦ ![Test](testing) text.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[68, 136), s=3:5, u=6:8, t=6:8, l=75, sz=14, na=10: [68, 100), a:'\n', [100), a:2x' ', [100, 111), a:' ', [111), a:'\n', [111), a:2x' ', [111, 134), [135, 136), a:'\n', [136) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 5) options(margin[34], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
⟦* Paragraph with hard break and more text. [Test](test) text. 
    
1. ⟧Paragraph with soft break and more text. ⦙![Test](testing) text. 
⟦    ⟧with lazy continuation so we can attempt to duplicate md nav.
                               
.
Paragraph with soft break and more
text. ⦙
![Test](testing) text. with lazy
continuation so we can attempt to
duplicate md nav.

---- Tracked Offsets ---------------------------------------------------
[0]: {112 1|0 si -> 41}

---- Ranges ------------------------------------------------------------
⟦Paragraph with soft break and more⟧
⟦⟧⟦text.⟧ ⟦⟧
⟦⟧⟦![Test](testing) text. ⟧⟦with lazy⟧
⟦⟧⟦continuation so we can attempt to⟧
⟦⟧⟦duplicate md nav.
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[71, 202), s=1:1, u=6:6, t=6:6, l=128, sz=18, na=12: [71, 105), a:'\n', [105), [106, 111), a:' ', [111), a:'\n', [111), [112, 135), [140, 149), a:'\n', [149), [150, 183), a:'\n', [183), [184, 202), a:'\n', [202) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 6) options(margin[66], restore-tracked-spaces, insert-space, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated.
 ⦙[simLink](simLink.md)
.
Add: configuration for repeated. ⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: {34 1|0 si -> 33}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦
⟧⟦[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 55), s=1:1, u=2:2, t=2:2, l=56, sz=6, na=5: [0, 32), a:' ', [32, 33), [34, 55), a:'\n', [55) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 7) options(margin[66], restore-tracked-spaces, insert-space, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated.
⦙[simLink](simLink.md)
.
Add: configuration for repeated. ⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: {33 0|0 si -> 33}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦
[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 54), s=1:1, u=2:2, t=2:2, l=56, sz=5, na=4: [0, 32), a:' ', [32, 54), a:'\n', [54) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 8) options(margin[66], restore-tracked-spaces, insert-char, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated.
t⦙[simLink](simLink.md)
.
Add: configuration for repeated. t⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: {34 0|0 i -> 34}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦t⟧
⟦[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 55), s=1:1, u=3:3, t=3:3, l=57, sz=7, na=6: [0, 32), a:' ', [33, 34), a:'\n', [34, 55), a:'\n', [55) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 9) options(margin[30], show-ranges)
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break
  and more text. [Test](test)
  text.

1. Paragraph with soft break
   and more text.
   ![Test](test) text.

---- Ranges ------------------------------------------------------------
⟦* Paragraph with hard break⟧
⟦⟧ ⟦ and more text. [Test](test)⟧
⟦⟧ ⟦ text.⟧⟦
⟧⟦
1. Paragraph with soft break⟧
⟦⟧  ⟦ and more text.⟧
⟦⟧  ⟦ ![Test](test) text.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 133), s=4:6, u=9:11, t=9:11, l=138, sz=22, na=17: [0, 27), a:'\n', [27), a:' ', [27, 55), a:'\n', [55), a:' ', [55, 61), [62, 63), [67, 96), a:'\n', [96), a:2x' ', [96, 111), a:'\n', [111), a:2x' ', [111, 131), [132, 133), a:'\n', [133) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 10) options(margin[30], image-links-at-start, show-ranges)
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break
  and more text. [Test](test)
  text.

1. Paragraph with soft break
   and more text.
   ![Test](test) text.

---- Ranges ------------------------------------------------------------
⟦* Paragraph with hard break⟧
⟦⟧ ⟦ and more text. [Test](test)⟧
⟦⟧ ⟦ text.⟧⟦
⟧⟦
1. Paragraph with soft break⟧
⟦⟧  ⟦ and more text.⟧
⟦⟧  ⟦ ![Test](test) text.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 133), s=4:6, u=9:11, t=9:11, l=138, sz=22, na=17: [0, 27), a:'\n', [27), a:' ', [27, 55), a:'\n', [55), a:' ', [55, 61), [62, 63), [67, 96), a:'\n', [96), a:2x' ', [96, 111), a:'\n', [111), a:2x' ', [111, 131), [132, 133), a:'\n', [133) }
````````````````````````````````


```````````````````````````````` example(Wrap - Links: 11) options(margin[72])
* [#697, Autoscroll from source does not work in simplified structure view]
.
* [#697, Autoscroll from source does not work in simplified structure view]

````````````````````````````````


### Delete Indent

delete space to previous non-blank

```````````````````````````````` example(Wrap - Delete Indent: 1) options(margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the
      need⦙keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {147 >< 6|0 sd -> 102}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the⟧
⟦⟧     ⟦ need⟧⟦keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 167), s=1:8, u=4:13, t=4:13, l=123, sz=10, na=7: [50), a:'* [ ] ', [50, 135), a:'\n', [135), a:5x' ', [135, 140), [147, 167), a:'\n', [167) }
````````````````````````````````


```````````````````````````````` example(Wrap - Delete Indent: 2) options(FAIL, margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges, explicit-links-at-start)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the
      need ⦙[example.com](http://example.com) keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
      [example.com](http://example.com) keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {147 1|0 sd -> 97}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the⟧⟦ need ⟧
⟦⟧     ⟦ [example.com](http://example.com) keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 201), s=1:8, u=4:13, t=4:13, l=159, sz=10, na=7: [50), a:'* [ ] ', [50, 135), [140, 146), a:'\n', [146), a:5x' ', [146, 201), a:'\n', [201) }
````````````````````````````````


This makes the caret jump to previous word because of the kludged way paragraphs resolve
offsets. There is no way to fix it in the current implementation without more kludges which will
break other kludges. This is the reason for needing to rewrite it using more algorithmic
approach by processing all unwrapped and wrapped segments to get tracked offset context for
determining its index in the wrapped sequence.

```````````````````````````````` example(Wrap - Delete Indent: 3) options(FAIL, margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges, explicit-links-at-start)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the
      need⦙[example.com](http://example.com) keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need⦙
      [example.com](http://example.com) keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {146 0|0 sd -> 96}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the⟧⟦ need⟧
⟦⟧     ⟦ [example.com](http://example.com) keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 201), s=1:8, u=4:13, t=4:13, l=159, sz=10, na=7: [50), a:'* [ ] ', [50, 135), [140, 146), a:'\n', [146), a:5x' ', [146, 201), a:'\n', [201) }
````````````````````````````````


```````````````````````````````` example(Wrap - Delete Indent: 4) options(margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges, explicit-links-at-start)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the
      ⦙[example.com](http://example.com) keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the⦙
      [example.com](http://example.com) keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {142 6|0 sd -> 91}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the
      [example.com](http://example.com) keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 196), s=0:3, u=2:7, t=2:7, l=153, sz=5, na=3: [50), a:'* [ ] ', [50, 196), a:'\n', [196) }
````````````````````````````````


```````````````````````````````` example(Wrap - Delete Indent: 5) options(margin[96], delete-char, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙ keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
      keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {147 6|1 d -> 97}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need⟧ ⟦
   ⟧⟦   keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 168), s=1:4, u=3:8, t=3:8, l=125, sz=8, na=6: [50), a:'* [ ] ', [50, 140), a:' ', [140, 144), [145, 168), a:'\n', [168) }
````````````````````````````````


```````````````````````````````` example(Wrap - Delete Indent: 6) options(margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧keep duplicate code.
⟦      ⟧⦙keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      keep duplicate code.⦙keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {174 >< 6|0 sd -> 123}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      keep duplicate code.⟧⟦keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 194), s=0:3, u=2:7, t=2:7, l=144, sz=6, na=4: [50), a:'* [ ] ', [50, 167), [174, 194), a:'\n', [194) }
````````````````````````````````


### Restore Spaces

```````````````````````````````` example(Wrap - Restore Spaces: 1) options(margin[66], restore-tracked-spaces)
⟦   ⟧Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent ⦙ to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
would be treated equivalent ⦙ to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap - Restore Spaces: 2) options(margin[96], insert-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙to keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      ⦙to keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: {147 6|0 si -> 103}

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      to keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 170), s=0:3, u=2:7, t=2:7, l=127, sz=5, na=3: [50), a:'* [ ] ', [50, 170), a:'\n', [170) }
````````````````````````````````


```````````````````````````````` example(Wrap - Restore Spaces: 3) options(margin[29], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
 ⦙text should wrap onto the next line at right margin of 30
.
⦙text should wrap onto the
next line at right margin of
30
---- Tracked Offsets ---------------------------------------------------
[0]: {1 1|0 si -> 0}

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the⟧
⟦⟧⟦next line at right margin of⟧
⟦⟧⟦30⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[1, 58), s=0:0, u=3:3, t=3:3, l=58, sz=9, na=6: [1, 26), a:'\n', [26), [27, 55), a:'\n', [55), [56, 58), a:'\n', [58) }
````````````````````````````````


```````````````````````````````` example(Wrap - Restore Spaces: 4) options(margin[66], restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated. ⦙ [simLink](simLink.md)
.
Add: configuration for repeated. ⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: {33 1|1 -> 33}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦⟧
⟦⟧⟦[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 55), s=1:1, u=3:3, t=3:3, l=56, sz=8, na=5: [0, 32), a:' ', [32), a:'\n', [32), [34, 55), a:'\n', [55) }
````````````````````````````````


```````````````````````````````` example(Wrap - Restore Spaces: 5) options(margin[96], insert-char, restore-tracked-spaces, first-prefix[* ], prefix[  ], show-ranges)
⟦## Some Heading
    
Text to shift offset 
    
* ⟧Fix: Jekyll `{% include "" %}` completions would not work unless there was an `.html`
⟦  ⟧extension between the strings. a;lsdfj ladsfj dlsf; jlasdfj l;asdfj lads;fj lasdfj l;dsaj fladd⦙
⟦  ⟧asdfasfdsaffdsa
.
* Fix: Jekyll `{% include "" %}` completions would not work unless there was an `.html`
  extension between the strings. a;lsdfj ladsfj dlsf; jlasdfj l;asdfj lads;fj lasdfj l;dsaj
  fladd⦙ asdfasfdsaffdsa
---- Tracked Offsets ---------------------------------------------------
[0]: {233 0|0 i -> 187}

---- Ranges ------------------------------------------------------------
⟦⟧* ⟦Fix: Jekyll `{% include "" %}` completions would not work unless there was an `.html`
  extension between the strings. a;lsdfj ladsfj dlsf; jlasdfj l;asdfj lads;fj lasdfj l;dsaj⟧
⟦⟧ ⟦ fladd⟧⟦ asdfasfdsaffdsa⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 251), s=1:2, u=4:5, t=4:5, l=204, sz=10, na=7: [50), a:'* ', [50, 227), a:'\n', [227), a:' ', [227, 233), [235, 251), a:'\n', [251) }
````````````````````````````````


```````````````````````````````` example(Wrap - Restore Spaces: 6) options(margin[96], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
⟧* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line elements. ⦙
.
* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line
  elements. ⦙

---- Tracked Offsets ---------------------------------------------------
[0]: {151 1|0 si -> 109}

---- Ranges ------------------------------------------------------------
⟦* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line⟧
⟦⟧ ⟦ elements.⟧ ⟦⟧
⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[44, 150), s=2:2, u=5:5, t=5:5, l=111, sz=11, na=7: [44, 140), a:'\n', [140), a:' ', [140, 150), a:' ', [150), a:'\n', [150), a:'\n', [150) }
````````````````````````````````


## Tracked Offset

```````````````````````````````` example(Tracked Offset: 1) options(margin[31])
* Paragraph with hard break ⦙and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break ⦙and
  more text. [Test](test) text.

1. Paragraph with soft break
   and more text. ![Test](test)
   text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 2) options(margin[31])
* Paragraph with hard break⦙ and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break⦙ and
  more text. [Test](test) text.

1. Paragraph with soft break
   and more text. ![Test](test)
   text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 3) options(margin[31])
* Paragraph with hard break and more text. [Test](test) text. 

some text
    
1. Paragraph with soft break ⦙and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text. [Test](test) text.

some text

1. Paragraph with soft break
   ⦙and more text. ![Test](test)
   text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 4) options(margin[31])
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break⦙ and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text. [Test](test) text.

1. Paragraph with soft break⦙
   and more text. ![Test](test)
   text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 5) options(margin[31], explicit-links-at-start)
* Paragraph with hard break and more text.⦙ [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text.⦙
  [Test](test) text.

1. Paragraph with soft break
   and more text. ![Test](test)
   text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 6) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and more text.⦙ [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text.⦙
  [Test](test) text.

1. Paragraph with soft break
   and more text.
   ![Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 7) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and more text. ⦙[Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text.
  ⦙[Test](test) text.

1. Paragraph with soft break
   and more text.
   ![Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 8) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and more text. [Test](test) text.⦙ 
    
1. Paragraph with soft break and more text. ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text.
  [Test](test) text.⦙

1. Paragraph with soft break
   and more text.
   ![Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 9) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text.⦙ ![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text.
  [Test](test) text.

1. Paragraph with soft break
   and more text.⦙
   ![Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 10) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ⦙![Test](test) text. 
                               
.
* Paragraph with hard break and
  more text.
  [Test](test) text.

1. Paragraph with soft break
   and more text.
   ⦙![Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 11) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and more text. [Test](test) text. 
    
1. Paragraph with soft break and more text. ![Test](test) text.⦙ 
                               
.
* Paragraph with hard break and
  more text.
  [Test](test) text.

1. Paragraph with soft break
   and more text.
   ![Test](test) text.⦙

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 12) options(margin[31], explicit-links-at-start, image-links-at-start)
* Paragraph with hard break and⦙ ⦙more text.⦙ ⦙[Test](test) text.⦙ 
    
1. Paragraph with soft break⦙ ⦙and more text.⦙ ⦙![Test](test) text.⦙ 
                               
.
* Paragraph with hard break and⦙
  ⦙more text.⦙
  ⦙[Test](test) text.⦙

1. Paragraph with soft break⦙
   ⦙and more text.⦙
   ⦙![Test](test) text.⦙

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 13) options(margin[31], explicit-links-at-start, image-links-at-start)
⦙* Paragraph with hard break and more text. [Test](test) text. 
    
.
⦙* Paragraph with hard break and
  more text.
  [Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 14) options(margin[31], explicit-links-at-start, image-links-at-start)
*⦙ Paragraph with hard break and more text. [Test](test) text. 
    
.
*⦙ Paragraph with hard break and
  more text.
  [Test](test) text.

````````````````````````````````


```````````````````````````````` example(Tracked Offset: 15) options(margin[31], explicit-links-at-start, image-links-at-start, show-ranges)
⦙*⦙ ⦙Paragraph with hard break and⦙ ⦙more text.⦙ ⦙[Test](test) text.⦙ 
    
⦙1⦙.⦙ ⦙Paragraph with soft break⦙ ⦙and more text.⦙ ⦙![Test](test) text.⦙ 
                               
.
⦙*⦙ ⦙Paragraph with hard break and⦙
  ⦙more text.⦙
  ⦙[Test](test) text.⦙

⦙1⦙.⦙ ⦙Paragraph with soft break⦙
   ⦙and more text.⦙
   ⦙![Test](test) text.⦙

---- Tracked Offsets ---------------------------------------------------
[0]: {0 0|0 -> 0}
[1]: {1 0|1 -> 1}
[2]: {2 1|0 -> 2}
[3]: {31 0|1 -> 31}
[4]: {32 1|0 -> 34}
[5]: {42 0|1 -> 44}
[6]: {43 1|0 -> 47}
[7]: {61 0|1 -> 65}
[8]: {68 0|0 -> 67}
[9]: {69 0|0 -> 68}
[10]: {70 0|1 -> 69}
[11]: {71 1|0 -> 70}
[12]: {96 0|1 -> 95}
[13]: {97 1|0 -> 99}
[14]: {111 0|1 -> 113}
[15]: {112 1|0 -> 117}
[16]: {131 0|1 -> 136}

---- Ranges ------------------------------------------------------------
⟦* Paragraph with hard break and⟧
⟦⟧ ⟦ more text.⟧
⟦⟧ ⟦ [Test](test) text.⟧⟦
⟧⟦
1. Paragraph with soft break⟧
⟦⟧  ⟦ and more text.⟧
⟦⟧  ⟦ ![Test](test) text.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 133), s=4:6, u=9:11, t=9:11, l=138, sz=22, na=17: [0, 31), a:'\n', [31), a:' ', [31, 42), a:'\n', [42), a:' ', [42, 61), [62, 63), [67, 96), a:'\n', [96), a:2x' ', [96, 111), a:'\n', [111), a:2x' ', [111, 131), [132, 133), a:'\n', [133) }
````````````````````````````````


### Paragraph

```````````````````````````````` example(Tracked Offset - Paragraph: 1) options(margin[66], restore-tracked-spaces, show-ranges)
⟦    ⟧configuration.   ⦙  
.
configuration.   ⦙
---- Tracked Offsets ---------------------------------------------------
[0]: {21 3|2 -> 17}

---- Ranges ------------------------------------------------------------
⟦configuration.⟧   ⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[4, 18), s=1:3, u=2:4, t=2:4, l=18, sz=5, na=3: [4, 18), a:3x' ', [18), a:'\n', [18) }
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 2) options(margin[66], restore-tracked-spaces, show-ranges)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙  
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙
---- Tracked Offsets ---------------------------------------------------
[0]: {300 3|2 -> 293}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦⟧\⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦⟧⟦would be automatically copied.⟧   ⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 297), s=1:3, u=5:7, t=5:7, l=294, sz=14, na=10: [0, 66), a:'\n', [66), a:'\', [67, 129), [133, 198), [202, 266), a:'\n', [266), [267, 297), a:3x' ', [297), a:'\n', [297) }
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 3) options(margin[66], restore-tracked-spaces, show-ranges)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙  
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 -> 130}
[1]: {300 3|2 -> 293}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦⟧\⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦⟧⟦would be automatically copied.⟧   ⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 297), s=1:3, u=5:7, t=5:7, l=294, sz=14, na=10: [0, 66), a:'\n', [66), a:'\', [67, 129), [133, 198), [202, 266), a:'\n', [266), [267, 297), a:3x' ', [297), a:'\n', [297) }
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 4) options(margin[66], restore-tracked-spaces, show-ranges)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 -> 130}
[1]: {300 3|6 -> 293}
[2]: {303 6|3 -> 296}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦⟧\⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦⟧⟦would be automatically copied.⟧      ⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 297), s=1:6, u=5:10, t=5:10, l=297, sz=14, na=10: [0, 66), a:'\n', [66), a:'\', [67, 129), [133, 198), [202, 266), a:'\n', [266), [267, 297), a:6x' ', [297), a:'\n', [297) }
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 5) options(margin[66], restore-tracked-spaces, show-ranges)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.         
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
---- Tracked Offsets ---------------------------------------------------
[0]: {199 2|0 -> 196}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦⟧\⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way⟧  ⟦⟧⟦
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦⟧⟦would be automatically copied.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 299), s=1:2, u=5:6, t=5:6, l=293, sz=15, na=11: [0, 66), a:'\n', [66), a:'\', [67, 129), [133, 197), a:2x' ', [197), [199, 200), [204, 268), a:'\n', [268), [269, 299), a:'\n', [299) }
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 6) options(margin[66], restore-tracked-spaces, show-ranges)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 -> 130}
[1]: {199 2|0 -> 196}
[2]: {302 3|6 -> 295}
[3]: {305 6|3 -> 298}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦⟧\⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way⟧  ⟦⟧⟦
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦⟧⟦would be automatically copied.⟧      ⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 299), s=2:8, u=6:12, t=6:12, l=299, sz=17, na=12: [0, 66), a:'\n', [66), a:'\', [67, 129), [133, 197), a:2x' ', [197), [199, 200), [204, 268), a:'\n', [268), [269, 299), a:6x' ', [299), a:'\n', [299) }
````````````````````````````````


Leading space on first line not handled for now.

```````````````````````````````` example(Tracked Offset - Paragraph: 7) options(IGNORE, margin[66], restore-tracked-spaces)
   Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent ⦙ to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.
.
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent ⦙ to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 8) options(margin[96], delete-char, restore-tracked-spaces, prefix[      ], first-prefix[* [ ] ])
⟦* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙ keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
      keep duplicate code.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 9) options(margin[96], delete-char, restore-tracked-spaces, prefix[        ], first-prefix[  * [ ] ])
⟦  * [ ] ⟧d ⦙ classes contained in `util` directory to `misc` sub-directory and not have any
⟦        ⟧dependencies on classes in other directories.
.
  * [ ] d ⦙ classes contained in `util` directory to `misc` sub-directory and not have any
        dependencies on classes in other directories.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 10) options(margin[96], insert-space, restore-tracked-spaces, prefix[      ], first-prefix[* [ ] ])
⟦* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧ ⦙to keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      ⦙to keep duplicate code.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 11) options(margin[96], insert-space, restore-tracked-spaces, prefix[      ], first-prefix[* [ ] ])
⟦* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙ to keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
      to keep duplicate code.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 12) options(margin[96], insert-space, restore-tracked-spaces, prefix[  ], first-prefix[* ])
⟦* ⟧Fix: conversion from Smart to based to extract more source information from segmented
⟦  ⟧sequence ⦙and mapped sequence.
.
* Fix: conversion from Smart to based to extract more source information from segmented sequence
  ⦙and mapped sequence.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 13) options(margin[96], insert-space, restore-tracked-spaces, prefix[  ], first-prefix[* ])
⟦* ⟧Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line elements. ⦙
.
* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line
  elements. ⦙
````````````````````````````````


