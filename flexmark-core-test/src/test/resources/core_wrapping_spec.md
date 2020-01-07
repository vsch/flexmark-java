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
[0]: { [138, si) } --> 11

---- Ranges ------------------------------------------------------------
⟦⟧* ⟦Fix: for #651, Drop image with dialog issues
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[129, 174), s=0:1, u=2:3, t=2:3, l=48, sz=5, na=3: [129), a:'* ', [129, 174), a:'\n', [174) }
````````````````````````````````


do not wrap links

```````````````````````````````` example(Wrap: 8) options(margin[30])
[Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
.
[Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
````````````````````````````````


```````````````````````````````` example(Wrap: 9) options(margin[30])
![Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
.
![Issue #Tests-Fail-JavaSwingTimers](https://github.com/vsch/idea-multimarkdown/issues/Tests-Fail-JavaSwingTimers)
````````````````````````````````


```````````````````````````````` example(Wrap: 10) options(margin[30], explicit-links-at-start, show-ranges)
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


```````````````````````````````` example(Wrap: 11) options(margin[96], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
⟧* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line elements. ⦙
.
* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line
  elements. ⦙

---- Tracked Offsets ---------------------------------------------------
[0]: { [151, si) } --> 109

---- Ranges ------------------------------------------------------------
⟦* Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line⟧
⟦⟧ ⟦ elements.⟧ ⟦⟧
⟦⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[44, 150), s=2:2, u=5:5, t=5:5, l=111, sz=11, na=7: [44, 140), a:'\n', [140), a:' ', [140, 150), a:' ', [150), a:'\n', [150), a:'\n', [150) }
````````````````````````````````


```````````````````````````````` example(Wrap: 12) options(margin[34], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
⟦* Paragraph with hard break and more text. [Test](test) text. 
    
⟧1. Paragraph with soft break and more text. ⦙![Test](testing) text. 
                               
.
1. Paragraph with soft break and
   more text. ⦙
   ![Test](testing) text.

---- Tracked Offsets ---------------------------------------------------
[0]: { [112, si) } --> 47

---- Ranges ------------------------------------------------------------
⟦1. Paragraph with soft break and⟧
⟦⟧  ⟦ more text.⟧ ⟦⟧
⟦⟧  ⟦ ![Test](testing) text.⟧⟦
⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[68, 136), s=3:5, u=6:8, t=6:8, l=75, sz=14, na=10: [68, 100), a:'\n', [100), a:2x' ', [100, 111), a:' ', [111), a:'\n', [111), a:2x' ', [111, 134), [135, 136), a:'\n', [136) }
````````````````````````````````


```````````````````````````````` example(Wrap: 13) options(margin[34], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
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
[0]: { [112, si) } --> 41

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


```````````````````````````````` example(Wrap: 14) options(margin[96], insert-char, restore-tracked-spaces, first-prefix[* ], prefix[  ], show-ranges)
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
[0]: { [233, i) } --> 187

---- Ranges ------------------------------------------------------------
⟦⟧* ⟦Fix: Jekyll `{% include "" %}` completions would not work unless there was an `.html`
  extension between the strings. a;lsdfj ladsfj dlsf; jlasdfj l;asdfj lads;fj lasdfj l;dsaj⟧
⟦⟧ ⟦ fladd⟧⟦ asdfasfdsaffdsa⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 251), s=1:2, u=4:5, t=4:5, l=204, sz=10, na=7: [50), a:'* ', [50, 227), a:'\n', [227), a:' ', [227, 233), [235, 251), a:'\n', [251) }
````````````````````````````````


delete space to previous non-blank

```````````````````````````````` example(Wrap: 15) options(margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the
      need⦙keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: { [147, sd) } --> 102

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the⟧
⟦⟧     ⟦ need⟧⟦keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 167), s=1:8, u=4:13, t=4:13, l=123, sz=10, na=7: [50), a:'* [ ] ', [50, 135), a:'\n', [135), a:5x' ', [135, 140), [147, 167), a:'\n', [167) }
````````````````````````````````


```````````````````````````````` example(Wrap: 16) options(margin[96], delete-char, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙ keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
      keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: { [147, d) } --> 97

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need⟧ ⟦
   ⟧⟦   keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 168), s=1:4, u=3:8, t=3:8, l=125, sz=8, na=6: [50), a:'* [ ] ', [50, 140), a:' ', [140, 144), [145, 168), a:'\n', [168) }
````````````````````````````````


```````````````````````````````` example(Wrap: 17) options(margin[66], restore-tracked-spaces)
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


```````````````````````````````` example(Wrap: 18) options(margin[96], insert-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙to keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      ⦙to keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: { [147, si) } --> 103

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      to keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 170), s=0:3, u=2:7, t=2:7, l=127, sz=5, na=3: [50), a:'* [ ] ', [50, 170), a:'\n', [170) }
````````````````````````````````


splice when deleting at indent

```````````````````````````````` example(Wrap: 19) options(margin[96], delete-space, restore-tracked-spaces, first-prefix[* [ ] ], prefix[      ], show-ranges)
⟦### Next 2.9.0.227/2.9.7.227 - Dev Build
  
* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧keep duplicate code.
⟦      ⟧⦙keep duplicate code.
.
* [ ] Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      keep duplicate code.⦙keep duplicate code.
---- Tracked Offsets ---------------------------------------------------
[0]: { [174, sd) } --> 123

---- Ranges ------------------------------------------------------------
⟦⟧* [ ] ⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need
      keep duplicate code.⟧⟦keep duplicate code.⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[50, 194), s=0:3, u=2:7, t=2:7, l=144, sz=6, na=4: [50), a:'* [ ] ', [50, 167), [174, 194), a:'\n', [194) }
````````````````````````````````


```````````````````````````````` example(Wrap: 20) options(margin[29], insert-space, restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
 ⦙text should wrap onto the next line at right margin of 30
.
⦙text should wrap onto the
next line at right margin of
30
---- Tracked Offsets ---------------------------------------------------
[0]: { [1, si) } --> 0

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the⟧
⟦⟧⟦next line at right margin of⟧
⟦⟧⟦30⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[1, 58), s=0:0, u=3:3, t=3:3, l=58, sz=9, na=6: [1, 26), a:'\n', [26), [27, 55), a:'\n', [55), [56, 58), a:'\n', [58) }
````````````````````````````````


```````````````````````````````` example(Wrap: 21) options(margin[66], restore-tracked-spaces, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated. ⦙ [simLink](simLink.md)
.
Add: configuration for repeated. ⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: { [33) } --> 33

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦⟧
⟦⟧⟦[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 55), s=1:1, u=3:3, t=3:3, l=56, sz=8, na=5: [0, 32), a:' ', [32), a:'\n', [32), [34, 55), a:'\n', [55) }
````````````````````````````````


```````````````````````````````` example(Wrap: 22) options(margin[66], restore-tracked-spaces, insert-space, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated.
 ⦙[simLink](simLink.md)
.
Add: configuration for repeated. ⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: { [34, si) } --> 33

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦
⟧⟦[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 55), s=1:1, u=2:2, t=2:2, l=56, sz=6, na=5: [0, 32), a:' ', [32, 33), [34, 55), a:'\n', [55) }
````````````````````````````````


```````````````````````````````` example(Wrap: 23) options(margin[66], restore-tracked-spaces, insert-space, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated.
⦙[simLink](simLink.md)
.
Add: configuration for repeated. ⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: { [33, si) } --> 33

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦
[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 54), s=1:1, u=2:2, t=2:2, l=56, sz=5, na=4: [0, 32), a:' ', [32, 54), a:'\n', [54) }
````````````````````````````````


```````````````````````````````` example(Wrap: 24) options(margin[66], restore-tracked-spaces, insert-char, explicit-links-at-start, image-links-at-start, show-ranges)
Add: configuration for repeated.
t⦙[simLink](simLink.md)
.
Add: configuration for repeated. t⦙
[simLink](simLink.md)
---- Tracked Offsets ---------------------------------------------------
[0]: { [34, i) } --> 34

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦t⟧
⟦[simLink](simLink.md)⟧
⟦⟧
---- Segments ----------------------------------------------------------
BasedSegmentBuilder{[0, 55), s=1:1, u=3:3, t=3:3, l=57, sz=7, na=6: [0, 32), a:' ', [33, 34), a:'\n', [34, 55), a:'\n', [55) }
````````````````````````````````


```````````````````````````````` example(Wrap: 25) options(margin[30], show-ranges)
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


```````````````````````````````` example(Wrap: 26) options(margin[30], image-links-at-start, show-ranges)
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


```````````````````````````````` example(Wrap: 27) options(margin[72])
* [#697, Autoscroll from source does not work in simplified structure view]
.
* [#697, Autoscroll from source does not work in simplified structure view]

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
[0]: { [0) } --> 0
[1]: { [1) } --> 1
[2]: { [2) } --> 2
[3]: { [31) } --> 31
[4]: { [32) } --> 34
[5]: { [42) } --> 44
[6]: { [43) } --> 47
[7]: { [61) } --> 65
[8]: { [68) } --> 67
[9]: { [69) } --> 68
[10]: { [70) } --> 69
[11]: { [71) } --> 70
[12]: { [96) } --> 95
[13]: { [97) } --> 99
[14]: { [111) } --> 113
[15]: { [112) } --> 117
[16]: { [131) } --> 136

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

```````````````````````````````` example(Tracked Offset - Paragraph: 1) options(margin[66], restore-tracked-spaces)
⟦    ⟧configuration.   ⦙  
.
configuration.   ⦙
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 2) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙  
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 3) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙  
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 4) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 5) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.         
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Tracked Offset - Paragraph: 6) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
Add: configuration for repeated prefixes in items, which would `be
\#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
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


