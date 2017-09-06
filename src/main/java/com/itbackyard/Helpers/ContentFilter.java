package com.itbackyard.Helpers;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.*;

/**
 * Class {@code ContentFilter} content filtering methods
 *
 * @author Maytham Fahmi
 * @since WET-EXTRACTOR 3.0
 */
public class ContentFilter {

    private TreeSet<String> swearWordsTree;
    private String whiteDomain;

    /**
     * Constructor
     *
     * @param swearWordsTree Swear words data structure
     * @param whiteDomain    Domain name
     */
    public ContentFilter(TreeSet<String> swearWordsTree, String whiteDomain) {
        this.swearWordsTree = swearWordsTree;
        this.whiteDomain = whiteDomain;
    }

    private CharsetEncoder asciiEncoder =
            Charset.forName("US-ASCII").newEncoder();

    /**
     * Prevent none English content
     *
     * @param content text content
     * @return boolean
     */
    public boolean isPureAscii(String content) {
        return asciiEncoder.canEncode(content);
    }

    /**
     * Prevent swear word content
     *
     * @param wordIn single word
     * @return boolean
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
     * @param url url address
     * @return boolean
     */
    public boolean isWhiteDomain(String url) {
        return whiteDomain.equals("") || url.contains(whiteDomain);
    }

}
