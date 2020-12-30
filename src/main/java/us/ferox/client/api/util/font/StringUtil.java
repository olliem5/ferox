package us.ferox.client.api.util.font;

import java.util.regex.Pattern;

public class StringUtil {
    public static final char COLOR_CHAR = '\u00A7';

    public static String stripColor(final String input) {
        return input == null ? null : Pattern.compile("(?i)" + COLOR_CHAR + "[0-9A-FK-OR]").matcher(input).replaceAll("");
    }
}
