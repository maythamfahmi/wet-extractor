package com.itbackyard.Helper;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * By Maytham on 07-09-2016.
 */
public class ContentFilter {

    private List<String> swearWords;
    private String whiteDomain;

    public ContentFilter() {
    }

    public ContentFilter(List<String> swearWords, String whiteDomain) {
        this.swearWords = swearWords;
        this.whiteDomain = whiteDomain;
    }

    private CharsetEncoder asciiEncoder =
            Charset.forName("US-ASCII").newEncoder();

    /**
     * Prevent none English content
     *
     * @param content
     * @return
     */
    public boolean isPureAscii(String content) {
        return asciiEncoder.canEncode(content);
    }

    /**
     * Prevent swear word content
     *
     * @param wordIn
     * @return
     */
    public boolean isNotSwearWord(String wordIn) {
        String word = wordIn.toLowerCase();
        for (String swearWord : swearWords) {
            if(word.contains(swearWord)){
                return false;
            }
        }
        return true;
    }

    /**
     * White list domains
     *
     * @param url
     * @return
     */
    public boolean isWhiteDomain(String url) {
        return whiteDomain.equals("") || url.contains(whiteDomain);
    }
}
