package com.itbackyard;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * By Maytham on 07-09-2016.
 */
class ContentFilter {

    private static CharsetEncoder asciiEncoder =
            Charset.forName("US-ASCII").newEncoder();

    //remove none English content
    static boolean isPureAscii(String v) {
        return asciiEncoder.canEncode(v);
    }

    //remove content with SwearWords
    static boolean isNotSwearWord(String content) {

        String[] words = content.replaceAll("\"^[a-zA-Z0-9]*$\"", "").trim().split(" ");

        for (String word : words) {
            for (int j = 0; j < Program.swearWordsList.size(); j++) {
                if (word.toLowerCase()
                        .contains(Program.swearWordsList.get(j).toLowerCase())) {
                    return false;
                }
            }
        }
        return true;
    }

    //Create blacklist domains data structure
    static boolean isWhiteDomain(String url, String whiteDomain) {
        return whiteDomain.equals("") || url.contains(whiteDomain);
    }
}
