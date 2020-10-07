package io.hamed.htepubreadr.app.util;

public class StringUtils {

    public static String getBaseName(String filename) {
        return removeExtension(getName(filename));
    }

    public static int indexOfLastSeparator(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int lastUnixPos = filename.lastIndexOf(47);
            int lastWindowsPos = filename.lastIndexOf(92);
            return Math.max(lastUnixPos, lastWindowsPos);
        }
    }

    public static String getName(String filename) {
        if (filename == null) {
            return null;
        } else {
            int index = indexOfLastSeparator(filename);
            return filename.substring(index + 1);
        }
    }

    public static String removeExtension(String filename) {
        if (filename == null) {
            return null;
        } else {
            int index = indexOfExtension(filename);
            return index == -1 ? filename : filename.substring(0, index);
        }
    }

    public static int indexOfExtension(String filename) {
        if (filename == null) {
            return -1;
        } else {
            int extensionPos = filename.lastIndexOf(46);
            int lastSeparator = indexOfLastSeparator(filename);
            return lastSeparator > extensionPos ? -1 : extensionPos;
        }
    }
}