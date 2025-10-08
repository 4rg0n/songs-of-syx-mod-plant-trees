package com.github.argon.sos.planttree.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtil {

    /**
     * Transforms multiple objects into a string.
     *
     * @param objects to transform into a string
     * @return build string
     */
    public static String toString(Object[] objects) {
        return Arrays.toString(objects);
    }

    /**
     * Transforms a key value map into a string.
     *
     * @param map to transform into a string
     * @return build string
     */
    public static String toString(Map<?, ?> map) {
        return map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    /**
     * Will transform primitive array types into strings.
     *
     * @param arg the primitive array
     * @return built string
     */
    public static String toStringPrimitiveArray(Object arg) {
        if (arg == null) {
            return "null";
        } else if (arg instanceof int[]) {
            return Arrays.toString((int[]) arg);
        } else if (arg instanceof long[]) {
            return Arrays.toString((long[]) arg);
        } else if (arg instanceof short[]) {
            return Arrays.toString((short[]) arg);
        } else if (arg instanceof double[]) {
            return Arrays.toString((double[]) arg);
        } else if (arg instanceof float[]) {
            return Arrays.toString((float[]) arg);
        } else if (arg instanceof byte[]) {
            return Arrays.toString((byte[]) arg);
        } else if (arg instanceof boolean[]) {
            return Arrays.toString((boolean[]) arg);
        } else if (arg instanceof char[]) {
            return Arrays.toString((char[]) arg);
        }

        return arg.toString();
    }

    /**
     * Will transform an array of any object to a string.
     *
     * @param args array with object to transform
     * @return built string
     */
    public static Object[] stringifyValues(Object[] args) {
        Object[] stringArgs = new Object[args.length];

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            stringArgs[i] = stringify(arg);
        }

        return stringArgs;
    }

    /**
     * Will transform a single value into a string.
     *
     * @param arg to transform
     * @return built string
     */
    public static String stringify(Object arg) {
        if (arg == null) {
            return "null";
        } else if (arg instanceof String) {
            return (String) arg;
        } if (arg instanceof Double) {
            return String.format(Locale.US, "%.4f", (Double) arg);
        } else if (arg instanceof Float) {
            return String.format(Locale.US, "%.4f", (Float) arg);
        } else if (arg instanceof Map) {
            return StringUtil.toString((Map<?, ?>) arg);
        } else if (arg instanceof Object[]) {
            return StringUtil.toString((Object[]) arg);
        } else if (arg.getClass().isArray()) {
            return StringUtil.toStringPrimitiveArray(arg);
        } else {
            return arg.toString();
        }
    }

    /**
     * Will transform a {@link Throwable} into a string with stacktrace.
     *
     * @param throwable to transform
     * @return built string
     */
    public static String stringify(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);

        return stringWriter.toString();
    }

    /**
     * Will replace the packages with a single character in a class name.
     * E.g. "java.lang.String" will be transformed to "j.l.String"
     *
     * @param clazz to read the class name from
     * @return shortened class name
     */
    public static String shortenClassName(Class<?> clazz) {
        return shortenPackageName(clazz.getPackage().getName()) + '.' + clazz.getSimpleName();
    }

    /**
     * Will replace the packages with a single character in a package name.
     * E.g. "java.lang" will be transformed to "j.l"
     *
     * @param packageName to shorten
     * @return shortened class name
     */
    public static String shortenPackageName(String packageName) {
        if (packageName.isEmpty()) {
            return packageName;
        }

        return Arrays.stream(packageName.split("\\."))
                .filter(string -> !string.isEmpty())
                .map(segment -> Character.toString(segment.charAt(0)))
                .collect(Collectors.joining("."));
    }

    /**
     * Cuts a given text to a max length or fills it up with spaces to the max length.
     *
     *
     * @param string to cut to max length or fill with spaces
     * @param maxLength to fill or cut
     * @param cutTail whether to cut from the start or the end
     * @return cur ot filled string
     */
    public static String cutOrFill(String string, int maxLength, boolean cutTail) {
        if (string.length() == maxLength) {
            return string;
        }

        if (!cutTail && string.length() > maxLength) {
            return string.substring(string.length() - maxLength);
        } else if (cutTail && string.length() > maxLength) {
            return string.substring(0, maxLength);
        }

        int spaceLength = maxLength - string.length();
        String spacesString = repeat(' ', spaceLength);
        return string + spacesString;
    }

    /**
     * E.g. 'a' with amount 3 would be "aaa".
     *
     * @param character to repeat
     * @param amount how often to repeat
     * @return repeated character
     */
    public static String repeat(char character, int amount) {
        char[] chars = new char[amount];
        Arrays.fill(chars, character);

        return new String(chars);
    }
}
