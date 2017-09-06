package com.itbackyard.Helpers;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.*;

/**
 * By Maytham on 07-09-2016.
 */
public class ContentFilter {

    private List<String> swearWords;
    private TreeSet<String> swearWordsTree;
    private String whiteDomain;

    public ContentFilter() {
    }

    public ContentFilter(List<String> swearWords, String whiteDomain) {
        this.swearWords = swearWords;
        this.whiteDomain = whiteDomain;
    }

    public ContentFilter(TreeSet<String> swearWordsTree, String whiteDomain) {
        this.swearWordsTree = swearWordsTree;
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
        for (String swearWord : swearWordsTree) {
            if (word.contains(swearWord)) {
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
