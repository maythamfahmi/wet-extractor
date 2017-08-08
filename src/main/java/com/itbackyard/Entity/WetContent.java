package com.itbackyard.Entity;

/**
 * Wet-extractor
 * Developer Maytham on 04-09-2016
 * Updated Maytham 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class WetContent {

    private String url;
    private String title;
    private String content;

    public WetContent(String url, String title, String content) {
        this.url = url;
        this.title = title;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
