package com.itbackyard;

import com.itbackyard.Entity.WetContent;
import com.itbackyard.Helper.ContentFilter;
import com.itbackyard.Helper.FileHelper;
import com.itbackyard.Helper.LogData;

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
class App extends DataStructure {

    private final LogData LOG = LogData.getInstance();
    private final Calendar cal = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
    private final Path DOWNLOAD_FILE = Paths.get(Const.res + "/wet/files/");
    private final String SWEAR_WORDS = Const.res + "/lists/words/swearwords.txt";
    private List<String> swearWordsList;
    private final Path SAVE_SEARCH_FILEAS = Paths.get(
            "resources/output/master_" + sdf.format(cal.getTime()) + ".txt");

    /**
     * <b>NOTE:</b><br>
     * if empty all domains white, otherwise it look for link containing
     * singleton domain only like:<br>
     * bbc.co.uk<br>
     * cnn.com<br>
     */
    private static final String whiteDomain = "";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Processing...");
        new App().app();
    }

    private void app() throws IOException, URISyntaxException {
        swearWordsList = DataStructure.swearWord(SWEAR_WORDS);

        FileHelper.listSourceFiles(DOWNLOAD_FILE, "*.{warc.wet.gz}")
                .forEach(fileName -> {
                    try {
                        System.out.println(fileName);
                        doStart(fileName);
                    } catch (IOException | URISyntaxException e) {
                        LOG.write(LOG.getCurrentMethodName(), e.getMessage());
                        e.printStackTrace();
                    }
                });

    }

    private void doStart(Path fileName) throws IOException, URISyntaxException {
        // extracting wet content
        List<WetContent> wetContents = DataStructure.wetExtractor(fileName.toString());

        // filter data content
        List<WetContent> filteredContent = wetContents
                .stream()
                .filter(p -> ContentFilter.isPureAscii(p.getContent()))
                .filter(p -> ContentFilter.isPureAscii(p.getTitle()))
                .filter(p -> ContentFilter.isNotSwearWord(p.getContent(), swearWordsList))
                .filter(p -> ContentFilter.isNotSwearWord(p.getUrl(), swearWordsList))
                .filter(p -> ContentFilter.isWhiteDomain(p.getUrl(), whiteDomain))
                .collect(Collectors.toCollection(ArrayList::new));

        FileHelper.createSearchFile(SAVE_SEARCH_FILEAS, filteredContent);
    }

}
