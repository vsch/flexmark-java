package com.vladsch.flexmark.parser;

interface PegdownExtensions {
  /** Enables anchor links in headers. */
  static final int ANCHORLINKS = 0x400;

  static final int ALL = 0x0000FFFF;
}
