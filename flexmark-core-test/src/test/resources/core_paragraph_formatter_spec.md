---
title: Core Paragraph Formatter Test Spec
author: Vladimir Schneider
version: 1.0
date: '2019-12-17'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# Paragraph Formatter

## Wrap

```````````````````````````````` example(Wrap: 1) options(margin[66])
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
    would be treated equivalent to task item marker prefix. That way
    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 2) options(margin[66])
Add: configuration for repeated prefixes in items, which would `be #2`        ⦙copied when adding/splitting an item. In other words they
    would be treated equivalent to task item marker prefix. That way
    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {78 8|0 -> 71}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` ⟧⟦copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` ⦙copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 3) options(margin[66], delete-space)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 >< 4|0 sd -> 128}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they⟧⟦would⟧
⟦be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they⦙would
be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 4) options(margin[66], delete-space, prefix[    ], first-prefix[], first-width-delta[0])
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 >< 4|0 sd -> 136}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
   ⟦ #2` copied when adding/splitting an item. In other words⟧
   ⟦ they⟧⟦would be treated equivalent to task item marker prefix.⟧
   ⟦ That way⟧⟦ standard: `Add: `, `Fix: `, `Break: ` and `Deprecate:⟧
   ⟦ ` prefixes would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
    #2` copied when adding/splitting an item. In other words
    they⦙would be treated equivalent to task item marker prefix.
    That way standard: `Add: `, `Fix: `, `Break: ` and `Deprecate:
    ` prefixes would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 5) options(margin[66], delete-space, prefix[    > ], first-prefix[    > ])
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 >< 4|0 sd -> 146}

---- Ranges ------------------------------------------------------------
⟦⟧    > ⟦Add: configuration for repeated prefixes in items, which⟧
    >⟦ would `be #2` copied when adding/splitting an item. In other⟧
    >⟦ words they⟧⟦would be treated equivalent to task item marker⟧
    >⟦ prefix. That way⟧⟦ standard: `Add: `, `Fix: `, `Break: ` and⟧
    >⟦ `Deprecate: ` prefixes would be automatically copied.⟧

---- Result ------------------------------------------------------------
    > Add: configuration for repeated prefixes in items, which
    > would `be #2` copied when adding/splitting an item. In other
    > words they⦙would be treated equivalent to task item marker
    > prefix. That way standard: `Add: `, `Fix: `, `Break: ` and
    > `Deprecate: ` prefixes would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 6) options(margin[66], delete-char)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 d -> 129}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 7) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {300 3|2 -> 292}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧   ⟦⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙
````````````````````````````````


```````````````````````````````` example(Wrap: 8) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 -> 129}
[1]: {300 3|2 -> 292}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧   ⟦⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙
````````````````````````````````


```````````````````````````````` example(Wrap: 9) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 -> 129}
[1]: {300 3|6 -> 292}
[2]: {303 6|3 -> 295}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧      ⟦⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
````````````````````````````````


```````````````````````````````` example(Wrap: 10) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.         
.
---- Tracked Offsets ---------------------------------------------------
[0]: {199 2|0 -> 195}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way  
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 11) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
---- Tracked Offsets ---------------------------------------------------
[0]: {133 4|0 -> 129}
[1]: {199 2|0 -> 195}
[2]: {302 3|6 -> 294}
[3]: {305 6|3 -> 297}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way  
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧      ⟦⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
````````````````````````````````


Leading space not handled for tracking for now

```````````````````````````````` example(Wrap: 12) options(margin[66], restore-tracked-spaces)
   Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent ⦙ to task item marker prefix. That way
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {164 1|1 -> 157}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent⟧ ⟦ ⟧⟦to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent ⦙ to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 13) options(margin[66], restore-tracked-spaces, running-tests)
  ⦙ Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧would be treated equivalent  to task item marker prefix. That way  
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.         
.
---- Tracked Offsets ---------------------------------------------------
[0]: {2 2|1 -> 0}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent ⟧⟦to task item marker prefix. That way  
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧

---- Result ------------------------------------------------------------
⦙Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way  
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example(Wrap: 14) options(margin[66], restore-tracked-spaces)
  ⦙ Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
⟦    ⟧⦙would be treated equivalent ⦙ to task item marker prefix. That way  ⦙
⟦    ⟧standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.   ⦙   ⦙   
.
---- Tracked Offsets ---------------------------------------------------
[0]: {2 2|1 -> 0}
[1]: {136 4|0 -> 129}
[2]: {164 1|1 -> 157}
[3]: {203 2|0 -> 196}
[4]: {306 3|6 -> 295}
[5]: {309 6|3 -> 298}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent⟧ ⟦ ⟧⟦to task item marker prefix. That way  
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧      ⟦⟧

---- Result ------------------------------------------------------------
⦙Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
⦙would be treated equivalent ⦙ to task item marker prefix. That way  ⦙
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.   ⦙   ⦙
````````````````````````````````


```````````````````````````````` example(Wrap: 15) options(margin[66])
Add: configuration ⦙ for repeated
.
---- Tracked Offsets ---------------------------------------------------
[0]: {19 1|1 -> 19}

---- Ranges ------------------------------------------------------------
⟦Add: configuration ⟧⟦for repeated⟧

---- Result ------------------------------------------------------------
Add: configuration ⦙for repeated
````````````````````````````````


```````````````````````````````` example(Wrap: 16) options(margin[66], restore-tracked-spaces)
Add: configuration ⦙ for repeated
.
---- Tracked Offsets ---------------------------------------------------
[0]: {19 1|1 -> 19}

---- Ranges ------------------------------------------------------------
⟦Add: configuration⟧ ⟦ ⟧⟦for repeated⟧

---- Result ------------------------------------------------------------
Add: configuration ⦙ for repeated
````````````````````````````````


```````````````````````````````` example(Wrap: 17) options(margin[66], restore-tracked-spaces)
Add: configuration ⦙ for repeated.  ⦙  ⦙  
.
---- Tracked Offsets ---------------------------------------------------
[0]: {19 1|1 -> 19}
[1]: {35 2|4 -> 35}
[2]: {37 4|2 -> 37}

---- Ranges ------------------------------------------------------------
⟦Add: configuration⟧ ⟦ ⟧⟦for repeated.⟧    ⟦⟧

---- Result ------------------------------------------------------------
Add: configuration ⦙ for repeated.  ⦙  ⦙
````````````````````````````````


backspace after typing 1 char should preserve surrounding spaces

```````````````````````````````` example(Wrap: 18) options(margin[66], delete-char, restore-tracked-spaces)
Add: configuration ⦙ for repeated.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {19 1|1 d -> 19}

---- Ranges ------------------------------------------------------------
⟦Add: configuration⟧ ⟦ ⟧⟦for repeated.⟧

---- Result ------------------------------------------------------------
Add: configuration ⦙ for repeated.
````````````````````````````````


```````````````````````````````` example(Wrap: 19) options(margin[66], restore-tracked-spaces)
Add: configuration for repeated. ⦙ ➥[simLink](simLink.md)
.
---- Tracked Offsets ---------------------------------------------------
[0]: {33 1|1 -> 33}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦⟧
⟦[simLink](simLink.md)⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated. ⦙
[simLink](simLink.md)
````````````````````````````````


```````````````````````````````` example(Wrap: 20) options(margin[66], insert-space, restore-tracked-spaces)
Add: configuration for repeated.
 ⦙➥[simLink](simLink.md)
.
---- Tracked Offsets ---------------------------------------------------
[0]: {34 1|0 si -> 33}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦
⟧⟦[simLink](simLink.md)⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated. ⦙
[simLink](simLink.md)
````````````````````````````````


```````````````````````````````` example(Wrap: 21) options(margin[66], insert-char, restore-tracked-spaces)
Add: configuration for repeated.
t⦙➥[simLink](simLink.md)
.
---- Tracked Offsets ---------------------------------------------------
[0]: {34 0|0 i -> 34}

---- Ranges ------------------------------------------------------------
⟦Add: configuration for repeated.⟧ ⟦t⟧
⟦[simLink](simLink.md)⟧

---- Result ------------------------------------------------------------
Add: configuration for repeated. t⦙
[simLink](simLink.md)
````````````````````````````````


```````````````````````````````` example(Wrap: 22) options(margin[30], insert-char, restore-tracked-spaces)
text should wrap onto the next t⦙
line at right margin of 30
.
---- Tracked Offsets ---------------------------------------------------
[0]: {32 0|0 i -> 32}

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the next⟧
⟦t⟧ ⟦line at right margin of 30⟧

---- Result ------------------------------------------------------------
text should wrap onto the next
t⦙ line at right margin of 30
````````````````````````````````


```````````````````````````````` example(Wrap: 23) options(margin[32], insert-char, restore-tracked-spaces)
text should wrap onto the next tt⦙
line at right margin of 30
.
---- Tracked Offsets ---------------------------------------------------
[0]: {33 0|0 i -> 33}

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the next⟧
⟦tt⟧ ⟦line at right margin of 30⟧

---- Result ------------------------------------------------------------
text should wrap onto the next
tt⦙ line at right margin of 30
````````````````````````````````


```````````````````````````````` example(Wrap: 24) options(margin[32], delete-char, restore-tracked-spaces)
text should wrap onto the next
t⦙ line at right margin of 30
.
---- Tracked Offsets ---------------------------------------------------
[0]: {32 0|1 d -> 32}

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the next⟧ ⟦t⟧
⟦line at right margin of 30⟧

---- Result ------------------------------------------------------------
text should wrap onto the next t⦙
line at right margin of 30
````````````````````````````````


```````````````````````````````` example(Wrap: 25) options(margin[30], insert-space, restore-tracked-spaces)
text should wrap onto the next ⦙.
line at right margin of 30
.
---- Tracked Offsets ---------------------------------------------------
[0]: {31 1|0 si -> 31}

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the next⟧
⟦.⟧ ⟦line at right margin of 30⟧

---- Result ------------------------------------------------------------
text should wrap onto the next
⦙. line at right margin of 30
````````````````````````````````


```````````````````````````````` example(Wrap: 26) options(margin[32], insert-space, restore-tracked-spaces)
text should wrap onto the next ⦙\\. 
line at right margin of 30
.
---- Tracked Offsets ---------------------------------------------------
[0]: {31 1|0 si -> 31}

---- Ranges ------------------------------------------------------------
⟦text should wrap onto the next⟧
⟦\\. ⟧⟦line at right margin of 30⟧

---- Result ------------------------------------------------------------
text should wrap onto the next
⦙\\. line at right margin of 30
````````````````````````````````


```````````````````````````````` example(Wrap: 27) options(margin[96], delete-char, restore-tracked-spaces, prefix[      ], first-prefix[* [ ] ])
⟦* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧⦙ keep duplicate code.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {103 6|1 d -> 91}

---- Ranges ------------------------------------------------------------
⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need⟧ ⟦
⟧⟦keep duplicate code.⟧

---- Result ------------------------------------------------------------
Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
keep duplicate code.
````````````````````````````````


In tests, `prefix` and `first-prefix` with `restore-tracked-spaces` is only used to adjust right
margin on first/following lines. Not to add actual prefix to result.

```````````````````````````````` example(Wrap: 28) options(margin[96], delete-char, restore-tracked-spaces, prefix[        ], first-prefix[  * [ ] ])
⟦  * [ ] ⟧d ⦙ classes contained in `util` directory to `misc` sub-directory and not have any
⟦        ⟧dependencies on classes in other directories.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {10 1|1 d -> 2}

---- Ranges ------------------------------------------------------------
⟦d⟧ ⟦ ⟧⟦classes contained in `util` directory to `misc` sub-directory and not have any
⟧⟦dependencies on classes in other directories.⟧

---- Result ------------------------------------------------------------
d ⦙ classes contained in `util` directory to `misc` sub-directory and not have any
dependencies on classes in other directories.
````````````````````````````````


```````````````````````````````` example(Wrap: 29) options(margin[96], delete-char, restore-tracked-spaces, prefix[        ], first-prefix[  * [ ] ])
⟦  * [ ] ⟧ ⦙ classes contained in `util` directory to `misc` sub-directory and not have any
⟦        ⟧dependencies on classes in other directories.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {9 2|1 d -> 0}

---- Ranges ------------------------------------------------------------
⟦⟧ ⟦classes contained in `util` directory to `misc` sub-directory and not have any
⟧⟦dependencies on classes in other directories.⟧

---- Result ------------------------------------------------------------
⦙ classes contained in `util` directory to `misc` sub-directory and not have any
dependencies on classes in other directories.
````````````````````````````````


```````````````````````````````` example(Wrap: 30) options(margin[96], delete-char, restore-tracked-spaces, prefix[        ], first-prefix[  * [ ] ])
⟦* item 1
  * [ ] ⟧ ⦙ classes contained in `util` directory to `misc` sub-directory and not have any
⟦        ⟧dependencies on classes in other directories.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {18 2|1 d -> 0}

---- Ranges ------------------------------------------------------------
⟦⟧ ⟦classes contained in `util` directory to `misc` sub-directory and not have any
⟧⟦dependencies on classes in other directories.⟧

---- Result ------------------------------------------------------------
⦙ classes contained in `util` directory to `misc` sub-directory and not have any
dependencies on classes in other directories.
````````````````````````````````


```````````````````````````````` example(Wrap: 31) options(margin[96], insert-space, restore-tracked-spaces, prefix[      ], first-prefix[* [ ] ])
⟦* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟦      ⟧ ⦙to keep duplicate code.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {104 7|0 si -> 91}

---- Ranges ------------------------------------------------------------
⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⟧⟦to keep duplicate code.⟧

---- Result ------------------------------------------------------------
Fix: remove formatter and use flexmark formatter for document format to eliminate the need
⦙to keep duplicate code.
````````````````````````````````


```````````````````````````````` example(Wrap: 32) options(margin[96], insert-space, restore-tracked-spaces, prefix[      ], first-prefix[* [ ] ])
⟦* [ ] ⟧Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙ to keep duplicate code.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {97 1|1 si -> 91}

---- Ranges ------------------------------------------------------------
⟦Fix: remove formatter and use flexmark formatter for document format to eliminate the need⟧ ⟦⟧
⟦to keep duplicate code.⟧

---- Result ------------------------------------------------------------
Fix: remove formatter and use flexmark formatter for document format to eliminate the need ⦙
to keep duplicate code.
````````````````````````````````


```````````````````````````````` example(Wrap: 33) options(margin[96], insert-space, restore-tracked-spaces, prefix[  ], first-prefix[* ])
⟦* ⟧Fix: conversion from Smart to based to extract more source information from segmented
⟦  ⟧sequence ⦙and mapped sequence.
.
---- Tracked Offsets ---------------------------------------------------
[0]: {99 1|0 si -> 95}

---- Ranges ------------------------------------------------------------
⟦Fix: conversion from Smart to based to extract more source information from segmented⟧⟦ sequence⟧
⟦and mapped sequence.⟧

---- Result ------------------------------------------------------------
Fix: conversion from Smart to based to extract more source information from segmented sequence
⦙and mapped sequence.
````````````````````````````````


```````````````````````````````` example(Wrap: 34) options(margin[96], insert-space, restore-tracked-spaces, prefix[  ], first-prefix[* ])
⟦* ⟧Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line elements. ⦙
.
---- Tracked Offsets ---------------------------------------------------
[0]: {107 1|0 si -> 105}

---- Ranges ------------------------------------------------------------
⟦Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line⟧
⟦elements.⟧ ⟦⟧

---- Result ------------------------------------------------------------
Fix: wrap on typing caret adjustment on space after non-space and before keep at start of line
elements. ⦙
````````````````````````````````


