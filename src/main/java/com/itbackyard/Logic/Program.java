package com.itbackyard.Logic;

import com.itbackyard.Activity;
import com.itbackyard.Const;
import com.itbackyard.Entity.WetContent;
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
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wet-extractor
 * Developer Maytham on 07-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Program extends Activity {

    private final LogData LOG = LogData.getInstance();
    private final Calendar cal = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYMMddHHmmss");
    private final Path DOWNLOAD_FILE = Paths.get(Const.res + "/wet/files/");
    private final String SWEAR_WORDS = Const.res + "/lists/words/swearwords.txt";
    private List<String> swearWordsList;
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
     *
     * @throws IOException
     * @throws URISyntaxException
     */
    public void onStart() throws IOException, URISyntaxException {
        swearWordsList = swearWord(SWEAR_WORDS);

        FileHelper.listSourceFiles(DOWNLOAD_FILE, "*.{warc.wet.gz}")
                .forEach(fileName -> {
                    try {
                        counter++;
                        System.out.println(counter + ": " + fileName);
                        contentFilter(fileName);
                    } catch (IOException | URISyntaxException e) {
                        LOG.write(LOG.getCurrentMethodName(), e.getMessage());
                        e.printStackTrace();
                    }
                });

    }

    /**
     * @param fileName
     * @throws IOException
     * @throws URISyntaxException
     */
    private void contentFilter(Path fileName) throws IOException, URISyntaxException {
        // extracting wet content
        List<WetContent> wetContents = wetExtractor(fileName.toString());

        // filter data content
        List<WetContent> filteredContent = wetContents
                .stream()
                .filter(p -> ContentFilter.isPureAscii(p.getContent()))
                .filter(p -> ContentFilter.isPureAscii(p.getTitle()))
                .filter(p -> ContentFilter.isNotSwearWord(p.getContent(), swearWordsList))
                .filter(p -> ContentFilter.isNotSwearWord(p.getUrl(), swearWordsList))
                .filter(p -> ContentFilter.isWhiteDomain(p.getUrl(), whiteDomain))
                .collect(Collectors.toCollection(ArrayList::new));

        createSearchFile(SAVE_SEARCH_FILEAS, filteredContent);
    }

    /**
     * @param fileName
     * @param filteredContent
     */
    private void createSearchFile(Path fileName, List<WetContent> filteredContent) {
        FileHelper.isFolderExist(fileName);

        List<String> input = new ArrayList<>();

        filteredContent.stream()
                .filter(aFilteredContent -> !aFilteredContent.getContent().isEmpty())
                .forEach(aFilteredContent -> {
                    input.add("*PAGE:" + aFilteredContent.getUrl());
                    String title = aFilteredContent.getTitle();
                    input.add(StringUtils.abbreviate(title, TITLE_WIDTH).replaceAll("\\n", ""));

                    String contentCleaner = aFilteredContent.getContent().trim().replaceAll("[^A-Za-z0-9]", " ");
                    String[] words = contentCleaner.split(" ");
                    Arrays.stream(words)
                            .filter(word -> !word.isEmpty())
                            .filter(word -> word.length() >= MIN_WORD_LENGTH)
                            .filter(word -> word.length() <= MAX_WORD_LENGTH)
                            .filter(word -> !word.startsWith("http"))
                            .forEach(word -> input.add(word.trim()));
                });

        try {
            Files.write(fileName, input,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            LOG.write(LOG.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param readDataFile
     * @return
     * @throws IOException
     */
    private List<WetContent> wetExtractor(String readDataFile) throws IOException {

        List<WetContent> wetContents = new ArrayList<>();

        FileInputStream fis = new FileInputStream(readDataFile);

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

                if (!title.isEmpty()) {
                    wetContents.add(new WetContent(url, title, content));
                }
            }
        }

        return wetContents;
    }

    /**
     * @param fileName
     * @return
     */
    private List<String> swearWord(String fileName) {
        return FileHelper.linesReader(fileName);
    }

}
