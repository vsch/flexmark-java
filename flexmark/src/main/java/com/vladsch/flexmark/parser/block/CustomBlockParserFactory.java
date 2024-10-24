package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.Dependent;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import java.util.function.Function;

/** Custom block parser factory to create parser instance specific block parser factory */
public interface CustomBlockParserFactory
    extends Function<DataHolder, BlockParserFactory>, Dependent {
  @Override
  BlockParserFactory apply(DataHolder options);

  /**
   * @param options options for this parser session
   * @return special lead in character handler for the block parser elements
   */
  default SpecialLeadInHandler getLeadInHandler(DataHolder options) {
    return null;
  }
}
