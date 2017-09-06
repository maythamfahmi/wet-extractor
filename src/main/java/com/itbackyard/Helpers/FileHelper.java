package com.itbackyard.Helpers;

import com.itbackyard.Const;
import com.itbackyard.System.AppSystem;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Wet-extractor
 * Developer Maytham on 06-09-2016
 * Updated Maytham 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class FileHelper extends AppSystem {

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
     * @param path
     * @param fileType
     * @return
     * @throws IOException
     */
    public List<Path> listFiles(String path, String fileType) throws IOException {

        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(filePath(path), fileType)) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException e) {
            log.error(getClassMethodName(), e);
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
            log.error(getClassMethodName(), e);
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
            log.error(getClassMethodName(), e);
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param fileName
     * @param bytes
     * @return
     */
    public boolean createFile(String fileName, byte[] bytes) {
        try {
            Files.write(Paths.get(fileName), bytes,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            return true;
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param fileName
     * @param content
     * @return
     */
    public boolean createFile(String fileName, List<String> content) {
        try {
            Files.write(Paths.get(fileName), content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param path
     * @return
     */
    public boolean createFolder(Path path) {
        try {
            Files.createDirectory(path);
            return true;
        } catch (FileAlreadyExistsException e) {
            // the directory already exists.
            return true;
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param path
     * @return
     */
    public boolean createFolder(String path) {
        return createFolder(filePath(path));
    }

    /**
     * @param path
     * @return
     */
    public boolean deleteIfExists(String path) {
        return deleteIfExists(filePath(path));
    }

    /**
     * @param path
     * @return
     */
    public boolean deleteIfExists(Path path) {
        try {
            Files.deleteIfExists(path);
            return true;
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", path);
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @param path
     * @return
     */
    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    /**
     * @param path
     * @return
     */
    public boolean isDirectory(String path) {
        return isDirectory(filePath(path));
    }

    /**
     * @param path
     * @return
     */
    public boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    /**
     * @param path
     * @return
     */
    public boolean isFile(String path) {
        return isFile(filePath(path));
    }

    /**
     * @param path
     * @return
     */
    public boolean exist(Path path) {
        boolean ret = false;
        try {
            return Files.exists(path);
        } catch (Exception e) {
            return ret;
        }
    }

    /**
     * @param path
     * @return
     */
    public boolean exist(String path) {
        return exist(filePath(path));
    }

    /**
     * @param path
     * @return
     */
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

    /**
     * @param url
     * @return
     */
    public long fileSizeOnline(String url) {
        long file_size = 0;
        try {
            URL urlObj = new URL(url);
            URLConnection urlConnection = urlObj.openConnection();
            urlConnection.connect();
            file_size = urlConnection.getContentLength();
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.printStackTrace();
        }
        return file_size;
    }

    /**
     * @param file
     * @return the file size
     */
    public long fileSizeLocal(String file) {
        long file_size = 0;
        try {
            file_size = Files.size(Paths.get(file));
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return file_size;
        }
        return file_size;
    }

    /**
     * @param url
     * @return
     */
    public String getFilenameFromUrl(String url) {
        int pos = url.lastIndexOf("wet/") + 4;
        return url.substring(pos, url.length());
    }

    /**
     * @param filepath
     * @return
     */
    public String getFilename(String filepath) {
        int pos = filepath.lastIndexOf("/") + 1;
        return filepath.substring(pos, filepath.length());
    }

    /**
     * @param fileName
     * @param fileList
     * @return
     */
    public boolean isFileDownloaded(String fileName, String fileList) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(fileList)
            );
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                if (inLine.equals(fileName)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * @param fileName
     */
    private void addToDownloaded(String fileName) {
        file.createFile(Const.FILE_DOWNLOADED, Collections.singletonList(fileName));
    }

    /**
     * Return url of latest GunZip wet file from Common Crawl Amazon S3 web page.
     * <p>
     * If Url fails it returns empty string.
     * <p>
     * It is specific method to this URL: http://commoncrawl.org/the-data/get-started
     *
     * @return url with updated download date.
     * @see <a href="http://commoncrawl.org">http://commoncrawl.org</a>*
     */
    public String getLastWetUrl() {
        try {
            Document doc = Jsoup.connect(Const.URL_CC).get();
            Elements content = doc.getElementsByClass("entry-content");
            Elements archive = content.select("li:contains(MAIN)");
            String last = archive.select("li").last().toString();
            String fileName = last.substring(last.indexOf("CC-MAIN"), last.indexOf(" ")).trim();
            return Const.URL_BASE + "crawl-data/" + fileName + "/" + Const.FILE_WET_GZ;
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            e.getStackTrace();
        }
        return Const.EMPTY_STRING;
    }

}
