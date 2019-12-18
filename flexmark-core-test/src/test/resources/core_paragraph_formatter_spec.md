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

### 13

Issue #13, Hard line breaks do not work if markdown text/files uses CR LF as line separator

Escape crlf

```````````````````````````````` example Core Issues Tests - 13: 1 options(margin[66])
Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they
    would be treated equivalent to task item marker prefix. That way
    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧
-----------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


```````````````````````````````` example Core Issues Tests - 13: 1 options(margin[66])
Add: configuration for repeated prefixes in items, which would `be #2` ⦙copied when adding/splitting an item. In other words they
    would be treated equivalent to task item marker prefix. That way
    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.  
.
⟦Add: configuration for repeated prefixes in items, which would `be⟧
⟦#2` copied when adding/splitting an item. In other words they
⟧⟦would be treated equivalent to task item marker prefix. That way
⟧⟦standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes⟧
⟦would be automatically copied.⟧
-----------------------------------------------------
Add: configuration for repeated prefixes in items, which would `be
#2` copied when adding/splitting an item. In other words they
would be treated equivalent to task item marker prefix. That way
standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes
would be automatically copied.
````````````````````````````````


