package com.itbackyard.Logic;

import com.itbackyard.Const;
import com.itbackyard.Helper.ContentFilter;
import com.itbackyard.Helper.FileHelper;
import com.itbackyard.Helper.LogData;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.ArchiveRecordHeader;
import org.archive.io.warc.WARCReaderFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Wet-extractor
 * Developer Maytham on 07-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Program {

    private final LogData LOG = LogData.getInstance();
    private final Calendar cal = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
    private final Path DOWNLOAD_FILE = Paths.get(Const.res + "/wet/files/");
    private final String SWEAR_WORDS = Const.res + "/lists/words/swearwords.txt";
    private List<String> swearWordsMap;
    private final Path SAVE_SEARCH_FILEAS = Paths.get(Const.res +
            "/output/master_" + sdf.format(cal.getTime()) + ".txt");
    private final int MIN_WORD_LENGTH = 1;
    private final int MAX_WORD_LENGTH = 30;
    private final int TITLE_WIDTH = 70;
    private int counter = 0;

    /**
     * <b>NOTE:</b><br>
     * if empty all domains white, otherwise it look for link containing
     * singleton domain only like:<br>
     * bbc.co.uk<br>
     * cnn.com<br>
     */
    private final String whiteDomain = "";

    /**
     * @throws IOException
     */
    public void onStart() throws IOException {
        swearWordsMap = swearWord(SWEAR_WORDS);

        FileHelper.listSourceFiles(DOWNLOAD_FILE, "*.{warc.wet.gz}")
                .forEach(fileName -> {
                    try {
                        counter++;
                        System.out.println(counter + ": " + fileName);
                        wetExtractor(fileName.toString());
                    } catch (IOException e) {
                        LOG.write(LOG.getCurrentMethodName(), e.getMessage());
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
        ContentFilter cf = new ContentFilter(swearWordsMap, whiteDomain);
        List<String> output = new ArrayList<>();

        ArchiveReader archiveReader = WARCReaderFactory.get(
                readDataFile, fis, true);

        for (ArchiveRecord archiveRecord : archiveReader) {

            ArchiveRecordHeader header = archiveRecord.getHeader();
            String idf = header.toString();
            String url = header.getUrl();

            if (!idf.startsWith("{reader-identifier")) {
                byte[] rawData = IOUtils.toByteArray(
                        archiveRecord, archiveRecord.available());
                String rawContent = new String(rawData);
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
        }
        createSearchFile(SAVE_SEARCH_FILEAS, output);
    }

    /**
     * @param fileName
     * @return
     */
    private TreeSet<String> swearWordTree(String fileName) {
        List<String> lst = FileHelper.linesReader(fileName);
        TreeSet<String> tree = new TreeSet<>(lst);
        /*tree = lst.stream()
                .collect(Collectors.toSet(String::new, item -> item));*/
        return tree;
    }

    /**
     * @param fileName
     * @return
     */
    private List<String> swearWord(String fileName) {
        List<String> lst = FileHelper.linesReader(fileName);
        return lst;
    }

    /**
     * @param fileName
     * @param filteredContent
     */

    private void createSearchFile(Path fileName, List<String> filteredContent) {
        FileHelper.isFolderExist(fileName);
        try {
            Files.write(fileName, filteredContent,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.write(LOG.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

}
