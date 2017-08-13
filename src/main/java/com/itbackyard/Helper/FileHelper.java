package com.itbackyard.Helper;

import com.itbackyard.System.ISystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Wet-extractor
 * Developer Maytham on 06-09-2016
 * Updated Maytham 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class FileHelper implements ISystem {

    private FileHelper() {
    }

    private static class FileHelperHelper {
        private static final FileHelper INSTANCE = new FileHelper();
    }

    /**
     * FileHelper Singleton
     *
     * @return
     */
    public static FileHelper getInstance() {
        return FileHelper.FileHelperHelper.INSTANCE;
    }

    /**
     * To process filePath with text extention than pass "*.{txt}"<br>
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
    public List<Path> listFiles(Path dir, String fileType) throws IOException {

        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, fileType)) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
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
    public Stream<String> urlList(String downloadList, int maxUrls) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(downloadList));
            return reader.lines().limit(maxUrls);
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
            return Stream.empty();
        }
    }

    /**
     * Return list of string from filePath lines
     *
     * @param fileName
     * @return
     */
    public List<String> linesReader(String fileName) {
        List<String> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                list.add(inLine);
            }
            reader.close();
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public boolean createFile(String fileName, List<String> content) {
        try {
            Files.write(Paths.get(fileName), content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean createFolder(Path path) {
        try {
            Files.createDirectory(path);
            return true;
        } catch (FileAlreadyExistsException e) {
            // the directory already exists.
            return true;
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean createFolder(String path) {
        return createFolder(filePath(path));
    }

    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    public boolean isDirectory(String path) {
        return isDirectory(filePath(path));
    }

    public boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    public boolean isFile(String path) {
        return isFile(filePath(path));
    }

    public boolean exist(Path path) {
        boolean ret = false;
        try {
            return Files.exists(path);
        } catch (Exception e) {
            return ret;
        }
    }

    public boolean exist(String path) {
        return exist(filePath(path));
    }

    public Path filePath(String path) {
        return new File(path).toPath();
    }

    /**
     * @param fileName
     * @return
     */
    public TreeSet<String> fileToTree(String fileName) {
        List<String> lst = file.linesReader(fileName);
        TreeSet<String> tree = new TreeSet<>(lst);
        return tree;
    }

    /**
     * @param fileName
     * @return
     */
    public Map<String, String> fileToMap(String fileName) {
        return file.linesReader(fileName).stream()
                .collect(Collectors.toMap(String::new, item -> item));
    }

    /**
     * @param fileName
     * @return
     */
    public List<String> fileToList(String fileName) {
        return file.linesReader(fileName);
    }

    public static void main(String[] args) {

        FileHelper f = new FileHelper();

        System.out.println(f.isDirectory("src/main/resources/log"));
        System.out.println(f.isDirectory("src/main/resources/log/log.txt"));
        System.out.println(f.isFile("src/main/resources/log"));
        System.out.println(f.isFile("src/main/resources/log/log.txt"));
        System.out.println(f.exist("src/main/resources/log/log.txt"));
        System.out.println(f.exist("src/main/resources/lo"));
        System.out.println(f.exist("src/main/resources/log/"));

    }


}
