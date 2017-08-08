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

    private static LogData log = LogData.getInstance();

    public static List<Path> listSourceFiles(Path dir) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{txt}")) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException ex) {
            // I/O error encounted during the iteration, the cause is an IOException
            throw ex.getCause();
        }
        return result;
    }

    /*public static List<Path> listSourceFiles(Path path) throws IOException {
        List<Path> list = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path, "*.{warc.wet.gz}")) {
            for (Path entry : stream) {
                list.add(entry);
            }
        } catch (DirectoryIteratorException e) {
            e.printStackTrace();
        }
        return list;
    }*/

    public static Stream<String> urlList(String downloadList, int maxUrls) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(downloadList));
            return reader.lines().limit(maxUrls);
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
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

    public static void createLog(Path fileName, List<LogData> list) {

        List<String> logList = new ArrayList<>();

        list.forEach(a -> logList.add(a.toString()));

        createFile(fileName, logList);
    }

    public static void staticsLog(Path fileName, List<String> list) {
        createFile(fileName, list);
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
