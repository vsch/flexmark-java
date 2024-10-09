package com.vladsch.flexmark.util.format;

class RomanNumeral {
  private final int num; // The number represented by this Roman numeral.

  /*
     The following arrays are used by the toString() function to construct
     the standard Roman numeral representation of the number.  For each i,
     the number numbers[i] is represented by the corresponding string, letters[i].
  */

  private static final int[] numbers = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
  private static final String[] letters = {
    "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
  };

  RomanNumeral(int arabic) {
    // Constructor.  Creates the Roman number with the int value specified
    // by the parameter.  Throws a NumberFormatException if arabic is
    // not in the range 1 to 3999 inclusive.
    if (arabic < 1) {
      throw new NumberFormatException("Value of RomanNumeral must be positive.");
    }
    if (arabic > 3999) {
      throw new NumberFormatException("Value of RomanNumeral must be 3999 or less.");
    }
    num = arabic;
  }

  @Override
  public String toString() {
    // Return the standard representation of this Roman numeral.
    StringBuilder roman = new StringBuilder();
    int n = num;
    for (int i = 0; i < numbers.length; i++) {
      while (n >= numbers[i]) {
        roman.append(letters[i]);
        n -= numbers[i];
      }
    }
    return roman.toString();
  }
}
