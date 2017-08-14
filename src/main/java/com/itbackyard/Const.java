package com.itbackyard;

import com.itbackyard.System.ISystem;

import java.text.SimpleDateFormat;

/**
 * Wet-extractor
 * Developer Maytham on 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Const implements ISystem {

    public static final String RES = "src/main/resources/";
    public static final String RES_TEST = "src/test/resources/";

    public static final String URL_CC = "http://commoncrawl.org/the-data/get-started/";
    public static final String URL_BASE = "https://commoncrawl.s3.amazonaws.com/";
    public static final String URL_SWEAR_WORDS = "http://www.bannedwordlist.com/lists/swearWords.txt";
    public static final String FILENAME_GZ = "wet.paths.gz";
    public static final String EMPTY_STRING = "";
    public static final String PATH_WET = RES + "wet/";
    public static final String FILES_WET_PATH = PATH_WET + "files/";
    public static final String FILE_LOG = RES + "/log/log.txt";
    public static final String FILE_DOWNLOADED = PATH_WET + "downloaded.txt";
    public static final String FILE_URL_DOWNLOAD_LIST = PATH_WET + "url_download_list.txt";
    public static final String FILE_GZ_DOWNLOAD_LIST = PATH_WET + FILENAME_GZ;
    public static final String SWEAR_WORDS = RES + "/lists/words/swearwords.txt";
    public static final SimpleDateFormat SDF = new SimpleDateFormat("YYMMddHHmmss");
    public static final String SAVE_SEARCH_FILE_AS = RES + "/output/master_" +
            SDF.format(cal.getTime()) + ".txt";

}
