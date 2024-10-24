package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class IParseBase implements IParse {
  @Override
  public Node parse(String input) {
    return parse(BasedSequence.of(input));
  }

  @Override
  public boolean transferReferences(Document document, Document included, Boolean onlyIfUndefined) {
    return false;
  }

  @Override
  public Node parseReader(Reader input) throws IOException {
    BufferedReader bufferedReader;
    if (input instanceof BufferedReader) {
      bufferedReader = (BufferedReader) input;
    } else {
      bufferedReader = new BufferedReader(input);
    }

    StringBuilder file = new StringBuilder();
    char[] buffer = new char[16384];

    while (true) {
      int charsRead = bufferedReader.read(buffer);
      if (charsRead < 0) {
        break;
      }
      file.append(buffer, 0, charsRead);
    }

    BasedSequence source = BasedSequence.of(file.toString());
    return parse(source);
  }
}
