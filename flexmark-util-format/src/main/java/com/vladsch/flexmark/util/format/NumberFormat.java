package com.vladsch.flexmark.util.format;

public enum NumberFormat {
    NONE,
    ARABIC,
    LETTERS,
    ROMAN,
    CUSTOM,
    ;

    public static String getFormat(NumberFormat format, int count) {
        switch (format) {
            case NONE:
                return "";
            case ARABIC:
                return String.valueOf(count);
            case LETTERS:
                if (count < 1) throw new NumberFormatException("Letter format count must be > 0, actual " + count);
                return getFormat(count - 1, "abcdefghijklmnopqrstuvwxyz");
            case ROMAN:
                return new RomanNumeral(count).toString();
            case CUSTOM:
                throw new IllegalStateException("CounterFormat.CUSTOM has to use custom conversion, possibly by calling getFormat(int count, CharSequence digitSet)");
        }
        return "";
    }

    public static String getFormat(int count, CharSequence digitSet) {
        StringBuilder sb = new StringBuilder(10);
        int base = digitSet.length();
        do {
            int next = count / base;
            int dig = count - next * base;
            sb.append(digitSet.charAt(dig));
            count = next;
        } while (count > 0);

        int iMax = sb.length();
        StringBuilder out = new StringBuilder(iMax);
        for (int i = iMax; i-- > 0; ) {
            out.append(sb.charAt(i));
        }

        return out.toString();
    }
}
