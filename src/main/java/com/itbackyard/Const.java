package com.itbackyard;

import com.itbackyard.System.IAppSystem;

import java.text.SimpleDateFormat;

/**
 * Wet-extractor
 * Developer Maytham on 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Const implements IAppSystem {

    public static final String EMPTY_STRING = "";

    /**
     * URLS
     **/
    public static final String URL_CC = "http://commoncrawl.org/the-data/get-started/";
    public static final String URL_BASE = "https://commoncrawl.s3.amazonaws.com/";
    public static final String URL_SWEAR_WORDS = "http://www.bannedwordlist.com/lists/swearWords.txt";

    /**
     * FOLDERS
     **/
    public static String P_ROOT = "src/main/resources/";
    public static String P_ROOT_TEST = "src/test/resources/";

    public static final String P_SYS = P_ROOT + "system/";
    public static final String P_OUTPUT = P_ROOT + "output/";
    public static final String P_WET_FILES = P_ROOT + "wetfiles/";

    public static String[] folderStructure = {
            P_ROOT, P_ROOT_TEST, P_SYS, P_OUTPUT, P_WET_FILES
    };

    /**
     * FILES
     **/
    public static final String FILE_WET_GZ = P_SYS + "wet.paths.gz";
    public static final String FILE_URL_LIST = P_SYS + "url_list.txt";
    public static final String FILE_DOWNLOADED = P_SYS + "downloaded.txt";
    public static final String FILE_SWEAR_WORDS = P_SYS + "swearwords.txt";
    private static final SimpleDateFormat SDF =
            new SimpleDateFormat("YYMMddHHmmss");
    public static final String FILE_OUTPUT_SAVE_AS = P_OUTPUT + "master_" +
            SDF.format(cal.getTime()) + ".txt";

    /**
     * Colors
     **/
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

}