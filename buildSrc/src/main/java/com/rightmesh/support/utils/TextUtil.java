package com.rightmesh.support.utils;

public class TextUtil {

    /**
     * Return true if the text is empty
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() < 1;
    }

    /**
     * If any item is empty returns true
     * @param texts
     * @return
     */
    public static boolean isEmpty(String... texts) {

        if(texts == null || texts.length == 0) {
            return true;
        }

        for(String text : texts) {
            if(isEmpty(text)) {
                return true;
            }
        }

        return false;
    }

}
