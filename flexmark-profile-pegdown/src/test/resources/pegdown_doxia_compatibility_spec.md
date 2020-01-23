---
title: Pegdown with Doxia Compatibility Spec
author: Vladimir Schneider
version: 0.2
date: '2017-01-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Issue 384

Issue #384,

```````````````````````````````` example Issue 384: 1
<a href="https://doc-kurento.readthedocs.io/en/stable/user/installation.html">Install Kurento Media server</a>
<div class="bd-callout bd-callout-danger">
→It should be run under same user as OM
</div>
.
<p><a href="https://doc-kurento.readthedocs.io/en/stable/user/installation.html">Install Kurento Media server</a></p>
<div class="bd-callout bd-callout-danger">
→It should be run under same user as OM
</div>
.
Document[0, 200]
  Paragraph[0, 111]
    HtmlInline[0, 78] chars:[0, 78, "<a hr … tml\">"]
    Text[78, 106] chars:[78, 106, "Insta … erver"]
    HtmlInline[106, 110] chars:[106, 110, "</a>"]
  HtmlBlock[111, 200]
````````````````````````````````


```````````````````````````````` example(Issue 384: 2) options(no-deep-parser)
<a href="https://doc-kurento.readthedocs.io/en/stable/user/installation.html">Install Kurento Media server</a>
<div class="bd-callout bd-callout-danger">
→It should be run under same user as OM
</div>
.
<p><a href="https://doc-kurento.readthedocs.io/en/stable/user/installation.html">Install Kurento Media server</a></p>
<div class="bd-callout bd-callout-danger">
→It should be run under same user as OM
</div>
.
Document[0, 200]
  Paragraph[0, 111]
    HtmlInline[0, 78] chars:[0, 78, "<a hr … tml\">"]
    Text[78, 106] chars:[78, 106, "Insta … erver"]
    HtmlInline[106, 110] chars:[106, 110, "</a>"]
  HtmlBlock[111, 200]
````````````````````````````````


```````````````````````````````` example Issue 384: 3
<!-- 
# Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0 -->

# Media Server Installation

## Install Kurento Media server

<a href="https://doc-kurento.readthedocs.io/en/stable/user/installation.html">Install Kurento Media server</a>
<div class="bd-callout bd-callout-danger">
→It should be run under same user as OM
</div>

## Specify/Install Turn server

<div class="bd-callout bd-callout-info">Optional step</div>
.
<!-- 
# Licensed under the Apache License, Version 2.0 (the "License") http://www.apache.org/licenses/LICENSE-2.0 -->
<h1>Media Server Installation</h1>
<h2>Install Kurento Media server</h2>
<p><a href="https://doc-kurento.readthedocs.io/en/stable/user/installation.html">Install Kurento Media server</a></p>
<div class="bd-callout bd-callout-danger">
→It should be run under same user as OM
</div>
<h2>Specify/Install Turn server</h2>
<div class="bd-callout bd-callout-info">Optional step</div>
.
Document[0, 474]
  HtmlCommentBlock[0, 118]
  Heading[119, 146] textOpen:[119, 120, "#"] text:[121, 146, "Media Server Installation"]
    Text[121, 146] chars:[121, 146, "Media … ation"]
  Heading[148, 179] textOpen:[148, 150, "##"] text:[151, 179, "Install Kurento Media server"]
    Text[151, 179] chars:[151, 179, "Insta … erver"]
  Paragraph[181, 292]
    HtmlInline[181, 259] chars:[181, 259, "<a hr … tml\">"]
    Text[259, 287] chars:[259, 287, "Insta … erver"]
    HtmlInline[287, 291] chars:[287, 291, "</a>"]
  HtmlBlock[292, 382]
  Heading[383, 413] textOpen:[383, 385, "##"] text:[386, 413, "Specify/Install Turn server"]
    Text[386, 413] chars:[386, 413, "Speci … erver"]
  HtmlBlock[415, 474]
````````````````````````````````


