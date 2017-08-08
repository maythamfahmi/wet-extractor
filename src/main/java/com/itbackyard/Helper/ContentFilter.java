package com.itbackyard.Helper;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.List;

/**
 * By Maytham on 07-09-2016.
 */
public class ContentFilter {

    private static CharsetEncoder asciiEncoder =
            Charset.forName("US-ASCII").newEncoder();

    //remove none English content
    public static boolean isPureAscii(String v) {
        return asciiEncoder.canEncode(v);
    }

    //remove content with SwearWords
    public static boolean isNotSwearWord(String content, List<String> swearWordsList) {

        String[] words = content.replaceAll("\"^[a-zA-Z0-9]*$\"", "").trim().split(" ");

        for (String word : words) {
            for (int j = 0; j < swearWordsList.size(); j++) {
                if (word.toLowerCase()
                        .contains(swearWordsList.get(j).toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    //Create blacklist domains data structure
    public static boolean isWhiteDomain(String url, String whiteDomain) {
        return whiteDomain.equals("") || url.contains(whiteDomain);
    }
}
