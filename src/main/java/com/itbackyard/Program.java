package com.itbackyard;

import com.itbackyard.Entity.WetContent;
import com.itbackyard.Helper.FileHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * By Maytham on 07-09-2016.
 */
class Program extends DataStructure {

    //static final String DATA_WET_SOURCE_PATH = "resources/wet";
    private static final Path DATA_WET_SOURCE_PATH = Paths.get(Const.res + "/wet");
    private static final String SWEAR_WORDS = Const.res + "/lists/words/swearwords.txt";
    private static final Path PROCESSED_FILES = Paths.get(Const.res + "/processed/already.txt");
    private static final Path STATISTICS_LOG = Paths.get(Const.res + "/log/statics.txt");
    private static final Path PROCESS_LOG = Paths.get(Const.res + "/log/log.txt");
    static List<String> swearWordsList;
    private static Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");

    // Customized area
    private static final Path SAVE_SEARCH_FILEAS = Paths.get(
            "resources/output/master_" + sdf.format(cal.getTime()) + ".txt");
    // if empty all domains white, otherwise it look for link containing
    // singleton domain only like:
    // bbc.co.uk/
    // cnn.com/
    private static final String whiteDomain = "";

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Swear word list crates onces
        swearWordsList = DataStructure.swearWord(SWEAR_WORDS);

        FileHelper.listSourceFiles(DATA_WET_SOURCE_PATH)
                .forEach(fileName -> {
                    try {
                        String fn = fileName.getFileName().toString();
                        if (!FileHelper.isFileAlreadyProcessed(fn, PROCESSED_FILES)) {
                            doStart(fileName);
                        }
                    } catch (IOException | URISyntaxException e) {
                        e.printStackTrace();
                    }
                });

    }

    private static void doStart(Path fileName) throws IOException, URISyntaxException {

        String fn = fileName.getFileName().toString();

        //LogHelper.doLog(LogHelper.getCurrentMethodName(), "Extracting Started: " + fn);

        // extracting wet content
        List<WetContent> wetContents = DataStructure.wetExtractor(fileName.toString());

        // filter data content
        List<WetContent> filteredContent = wetContents
                .stream()
                .filter(p -> ContentFilter.isPureAscii(p.getContent()))
                .filter(p -> ContentFilter.isPureAscii(p.getTitle()))
                .filter(p -> ContentFilter.isNotSwearWord(p.getContent()))
                .filter(p -> ContentFilter.isNotSwearWord(p.getUrl()))
                .filter(p -> ContentFilter.isWhiteDomain(p.getUrl(), whiteDomain))
                .collect(Collectors.toCollection(ArrayList::new));

        FileHelper.createSearchFile(SAVE_SEARCH_FILEAS, filteredContent);

        //LogHelper.doLog(LogHelper.getCurrentMethodName(), "Extracting Ended: " + fn);

        //FileHelper.staticsLog(STATISTICS_LOG, LogHelper.staticsList);
        //FileHelper.createLog(PROCESS_LOG, LogHelper.logList);
        FileHelper.addToAlreadyProcessed(fn, PROCESSED_FILES);
    }

}
