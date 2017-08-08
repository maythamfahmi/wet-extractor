package com.itbackyard.Entity;

/**
 * By Maytham on 04-09-2016.
 */
public class WetContent {

    private String sUrl;
    private String sTitle;
    private String sContent;

    public WetContent(String url, String title, String content) {
        this.sUrl = url;
        this.sTitle = title;
        this.sContent = content;
    }

    public String getUrl() {
        return sUrl;
    }

    public void setUrl(String url) {
        this.sUrl = url;
    }

    public String getTitle() {
        return sTitle;
    }

    public void setTitle(String title) {
        this.sTitle = title;
    }

    public String getContent() {
        return sContent;
    }

    public void setContent(String content) {
        this.sContent = content;
    }


    /*@Override
    public String toString() {
        return oneLine("Item: ", "is here: ");
    }*/

    /*public String oneLine(String pre, String post) {
        return pre + mWhat + " " + post + mWhere;
    }*/
}
