package com.itbackyard.Helper;

import com.itbackyard.Entity.WetContent;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * By Maytham on 06-09-2016.
 */
public class FileHelper {

    private final static LogData LOG = LogData.getInstance();

    /**
     * To process file with text extention than pass "*.{txt}"<br>
     * to <code>fileType</code>.
     * <br>
     * Txt: "*.{txt}"<br>
     * Wet: "*.{warc.wet.gz}"
     *
     * @param dir
     * @param fileType
     * @return
     * @throws IOException
     */
    public static List<Path> listSourceFiles(Path dir, String fileType) throws IOException {

        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, fileType)) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException e) {
            LOG.write(LOG.getCurrentMethodName(), e.getMessage());
            //throw e.getCause();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Read download list with limiting the urls.
     * <br>
     * Set <code>maxUrls</code> to limit the number of urls
     *
     * @param downloadList
     * @param maxUrls
     * @return
     */
    public static Stream<String> urlList(String downloadList, int maxUrls) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(downloadList));
            return reader.lines().limit(maxUrls);
        } catch (IOException e) {
            LOG.write(LOG.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
            return Stream.empty();
        }
    }

    public static List<String> linesReader(String fileName) {
        List<String> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                list.add(inLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void createFile(Path fileName, List<String> list) {
        isFolderExist(fileName);

        try {
            Files.write(fileName, list,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createSearchFile(Path fileName, List<WetContent> filteredContent) {
        int minWordLenght = 1;
        int maxWordLenght = 30;

        isFolderExist(fileName);

        List<String> input = new ArrayList<>();

        filteredContent.stream()
                .filter(aFilteredContent -> !aFilteredContent.getContent().isEmpty())
                .forEach(aFilteredContent -> {
                    input.add("*PAGE:" + aFilteredContent.getUrl());
                    String title = aFilteredContent.getTitle();
                    input.add(StringUtils.abbreviate(title, 70).replaceAll("\\n", ""));

                    String contentCleaner = aFilteredContent.getContent().trim().replaceAll("[^A-Za-z0-9]", " ");
                    String[] words = contentCleaner.split(" ");
                    Arrays.stream(words)
                            .filter(word -> !word.isEmpty())
                            .filter(word -> word.length() >= minWordLenght)
                            .filter(word -> word.length() <= maxWordLenght)
                            .filter(word -> !word.startsWith("http"))
                            .forEach(word -> input.add(word.trim()));
                });

        try {
            Files.write(fileName, input,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void isFolderExist(Path fileName) {
        Path path = fileName.getParent();
        try {
            Files.createDirectory(path);
        } catch (FileAlreadyExistsException e) {
            // the directory already exists.
        } catch (IOException e) {
            //something else went wrong
            e.printStackTrace();
        }
    }

}
