package com.vladsch.flexmark.parser.block;

public interface BlockParserTracker {
  void blockParserAdded(BlockParser blockParser);

  void blockParserRemoved(BlockParser blockParser);
}
