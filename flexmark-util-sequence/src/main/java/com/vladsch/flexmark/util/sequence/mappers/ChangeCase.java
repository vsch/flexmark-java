package com.vladsch.flexmark.util.sequence.mappers;

public class ChangeCase {
  public static final CharMapper toUpperCase = new ToUpperCase();
  public static final CharMapper toLowerCase = new ToLowerCase();

  private static class ToLowerCase implements CharMapper {
    ToLowerCase() {}

    @Override
    public char map(char c) {
      return Character.isUpperCase(c) ? Character.toLowerCase(c) : c;
    }
  }

  private static class ToUpperCase implements CharMapper {
    ToUpperCase() {}

    @Override
    public char map(char c) {
      return Character.isLowerCase(c) ? Character.toUpperCase(c) : c;
    }
  }
}
