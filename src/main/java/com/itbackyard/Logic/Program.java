package com.itbackyard.Logic;

import com.itbackyard.Const;
import com.itbackyard.Helper.ContentFilter;
import com.itbackyard.System.ISystem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.ArchiveRecordHeader;
import org.archive.io.warc.WARCReaderFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wet-extractor
 * Developer Maytham on 07-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Program implements ISystem {

    /**
     * system config
     **/
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
    private final Path DOWNLOAD_FILE = Paths.get(Const.res + "/wet/files/");
    private final String SWEAR_WORDS = Const.res + "/lists/words/swearwords.txt";
    private final String SAVE_SEARCH_FILE_AS = Const.res + "/output/master_" +
            sdf.format(cal.getTime()) + ".txt";
    private TreeSet<String> swearWordsTree;

    /**
     * user config
     **/
    private int MIN_WORD_LENGTH = 1;
    private int MAX_WORD_LENGTH = 30;
    private int TITLE_WIDTH = 70;
    private int counter = 0;

    /**
     * <b>NOTE:</b><br>
     * if empty all domains white, otherwise it look for link containing
     * singleton domain only like:<br>
     * bbc.co.uk<br>
     * cnn.com<br>
     */
    private String whiteDomain = "";

    /**
     * @throws IOException
     */
    public void onStart() throws IOException {
        swearWordsTree = file.fileToTree(SWEAR_WORDS);

        file.listFiles(DOWNLOAD_FILE, "*.{warc.wet.gz}")
                .forEach(fileName -> {
                    try {
                        counter++;
                        System.out.println(counter + ": " + fileName);
                        wetExtractor(fileName.toString());
                    } catch (IOException e) {
                        log.write(log.getCurrentMethodName(), e.getMessage());
                        e.printStackTrace();
                    }
                });

    }

    /**
     * Convert wet file content to List of String.<br>
     * <br>
     * Order of<br>
     * - Url<br>
     * - Title<br>
     * - Words<br>
     * ...<br>
     * repeatedly<br>
     *
     * @param readDataFile
     * @return
     * @throws IOException
     */
    private void wetExtractor(String readDataFile) throws IOException {
        FileInputStream fis = new FileInputStream(readDataFile);
        ContentFilter cf = new ContentFilter(swearWordsTree, whiteDomain);
        List<String> output = new ArrayList<>();

        ArchiveReader archiveReader = WARCReaderFactory.get(
                readDataFile, fis, true);

        archiveReader.spliterator().forEachRemaining((ArchiveRecord elm) -> {

            ArchiveRecordHeader header = elm.getHeader();
            String idf = header.toString();
            String url = header.getUrl();

            if (!idf.startsWith("{reader-identifier")) {
                String rawContent = null;
                try {
                    byte[] rawData = IOUtils.toByteArray(elm, elm.available());
                    rawContent = new String(rawData);
                } catch (Exception e) {
                    e.getStackTrace();
                }
                String title = rawContent.substring(0, rawContent.indexOf("\n") + 1);
                String content = rawContent.substring(rawContent.indexOf("\n") + 1);

                if (!title.isEmpty() && !content.isEmpty()) {
                    if (cf.isPureAscii(content) && cf.isPureAscii(title)) {
                        if (cf.isNotSwearWord(url) && cf.isNotSwearWord(title)) {
                            if (cf.isWhiteDomain(url)) {
                                output.add("*PAGE:" + url);
                                output.add(StringUtils.abbreviate(title, TITLE_WIDTH).replaceAll("\\n", ""));
                                String contentCleaner = content.trim().replaceAll("[^A-Za-z0-9]", " ");
                                Arrays.stream(contentCleaner.split(" "))
                                        .filter(word -> !word.isEmpty())
                                        .filter(cf::isNotSwearWord)
                                        .filter(word -> word.length() >= MIN_WORD_LENGTH)
                                        .filter(word -> word.length() <= MAX_WORD_LENGTH)
                                        .filter(word -> !word.startsWith("http"))
                                        .forEach(word -> output.add(word.trim()));
                            }
                        }
                    }
                }
            }
        });
        createSearchFile(SAVE_SEARCH_FILE_AS, output);
    }

    /**
     * @param fileName
     * @param filteredContent
     */
    private void createSearchFile(String fileName, List<String> filteredContent) {
        Path path = Paths.get(fileName).getParent();
        if (!file.exist(path)) {
            file.createFolder(path);
        }
        file.createFile(fileName, filteredContent);
    }

}
