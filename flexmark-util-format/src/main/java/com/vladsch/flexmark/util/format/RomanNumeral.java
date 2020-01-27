package com.vladsch.flexmark.util.format;

/*
   taken from: http://math.hws.edu/eck/cs124/javanotes3/c9/ex-9-3-answer.html

   An object of type RomanNumeral is an integer between 1 and 3999.  It can
   be constructed either from an integer or from a string that represents
   a Roman numeral in this range.  The function toString() will return a
   standardized Roman numeral representation of the number.  The function
   toInt() will return the number as a value of type int.
*/

import java.util.regex.Pattern;

public class RomanNumeral {
    final private int num;   // The number represented by this Roman numeral.

    /*
       The following arrays are used by the toString() function to construct
       the standard Roman numeral representation of the number.  For each i,
       the number numbers[i] is represented by the corresponding string, letters[i].
    */

    // @formatter:off
    final private static int[] numbers =    { 1000, 900,  500, 400,  100, 90,   50,  40,   10,  9,    5,   4,    1 };
    final private static String[] letters = { "M",  "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
    // @formatter:on

    final public static Pattern ROMAN_NUMERAL = Pattern.compile("M{0,3}(?:CM|DC{0,3}|CD|C{1,3})?(?:XC|LX{0,3}|XL|X{1,3})?(?:IX|VI{0,3}|IV|I{1,3})?");
    final public static Pattern LOWERCASE_ROMAN_NUMERAL = Pattern.compile("m{0,3}(?:cm|dc{0,3}|cd|c{1,3})?(?:xc|lx{0,3}|xl|x{1,3})?(?:ix|vi{0,3}|iv|i{1,3})?");
    final public static Pattern LIMITED_ROMAN_NUMERAL = Pattern.compile("(?:X{1,3})?(?:IX|VI{0,3}|IV|I{1,3})?");
    final public static Pattern LIMITED_LOWERCASE_ROMAN_NUMERAL = Pattern.compile("(?:x{1,3})?(?:ix|vi{0,3}|iv|i{1,3})?");

    public RomanNumeral(int arabic) {
        // Constructor.  Creates the Roman number with the int value specified
        // by the parameter.  Throws a NumberFormatException if arabic is
        // not in the range 1 to 3999 inclusive.
        if (arabic < 1)
            throw new NumberFormatException("Value of RomanNumeral must be positive.");
        if (arabic > 3999)
            throw new NumberFormatException("Value of RomanNumeral must be 3999 or less.");
        num = arabic;
    }

    public RomanNumeral(String roman) {
        // Constructor.  Creates the Roman number with the given representation.
        // For example, RomanNumeral("xvii") is 17.  If the parameter is not a
        // legal Roman numeral, a NumberFormatException is thrown.  Both upper and
        // lower case letters are allowed.

        if (roman.length() == 0)
            throw new NumberFormatException("An empty string does not define a Roman numeral.");

        roman = roman.toUpperCase();

        int i = 0;       // A position in the string, roman;
        int arabic = 0;  // Arabic numeral equivalent of the part of the string that has been converted so far.

        while (i < roman.length()) {

            char letter = roman.charAt(i);
            int number = letterToNumber(letter);

            if (number < 0)
                throw new NumberFormatException("Illegal character \"" + letter + "\" in roman numeral.");

            i++;

            if (i == roman.length()) {
                // There is no letter in the string following the one we have just processed.
                // So just add the number corresponding to the single letter to arabic.
                arabic += number;
            } else {
                // Look at the next letter in the string.  If it has a larger Roman numeral
                // equivalent than number, then the two letters are counted together as
                // a Roman numeral with value (nextNumber - number).
                int nextNumber = letterToNumber(roman.charAt(i));
                if (nextNumber > number) {
                    // Combine the two letters to get one value, and move on to next position in string.
                    arabic += (nextNumber - number);
                    i++;
                } else {
                    // Don't combine the letters.  Just add the value of the one letter onto the number.
                    arabic += number;
                }
            }
        }

        if (arabic > 3999)
            throw new NumberFormatException("Roman numeral must have value 3999 or less.");

        num = arabic;
    }

    private int letterToNumber(char letter) {
        // Find the integer value of letter considered as a Roman numeral.  Return
        // -1 if letter is not a legal Roman numeral.  The letter must be upper case.
        switch (letter) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return -1;
        }
    }

    public String toString() {
        // Return the standard representation of this Roman numeral.
        StringBuilder roman = new StringBuilder();
        int N = num;
        for (int i = 0; i < numbers.length; i++) {
            while (N >= numbers[i]) {
                roman.append(letters[i]);
                N -= numbers[i];
            }
        }
        return roman.toString();
    }

    public int toInt() {
        // Return the value of this Roman numeral as an int.
        return num;
    }
}

