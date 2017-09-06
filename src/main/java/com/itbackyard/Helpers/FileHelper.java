package com.itbackyard.Helpers;

import com.itbackyard.Conf;
import com.itbackyard.System.AppSystem;
import com.itbackyard.System.IAppSystem;
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
 * Class {@code FileHelper} singleton class contains file methods
 *
 * @author Maytham Fahmi
 * @see IAppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class FileHelper extends AppSystem {

    private FileHelper() {
    }

    private static class ClassHelper {
        private static final FileHelper INSTANCE = new FileHelper();
    }

    /**
     * FileHelper Singleton
     *
     * @return FileHelper instance
     */
    public static FileHelper getInstance() {
        return FileHelper.ClassHelper.INSTANCE;
    }

    /**
     * To process filePath with text extension than pass "*.{txt}"<br>
     * to <code>fileType</code>.
     * <br>
     * Txt: "*.{txt}"<br>
     * Wet: "*.{warc.wet.gz}"
     *
     * @param path     path source
     * @param fileType file extension
     * @return List of files in specific path
     * @throws IOException throws exception
     */
    public List<Path> listFiles(String path, String fileType) throws IOException {

        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(filePath(path), fileType)) {
            for (Path entry : stream) {
                result.add(entry);
            }
        } catch (DirectoryIteratorException e) {
            log.error(getClassMethodName(), e);
        }
        return result;
    }

    /**
     * Read download list with limiting the urls.
     * <br>
     * Set <code>maxUrls</code> to limit the number of urls
     *
     * @param downloadList download list
     * @param maxUrls      maximum urls to be downloaded
     * @return stream of url list
     */
    public Stream<String> urlList(String downloadList, int maxUrls) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(downloadList));
            return reader.lines().limit(maxUrls);
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return Stream.empty();
        }
    }

    /**
     * Return list of string from filePath lines
     *
     * @param filename file name
     * @return list of string from file
     */
    public List<String> linesReader(String filename) {
        List<String> list = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                list.add(inLine);
            }
            reader.close();
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
        }
        return list;
    }

    /**
     * @param filename file name
     * @param bytes    bytes
     * @return boolean
     */
    public boolean createFile(String filename, byte[] bytes) {
        try {
            Files.write(Paths.get(filename), bytes,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
            return true;
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return false;
        }
    }

    /**
     * @param filename file name
     * @param content  content
     * @return boolean
     */
    public boolean createFile(String filename, List<String> content) {
        try {
            Files.write(Paths.get(filename), content,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return false;
        }
    }

    /**
     * @param path path source
     * @return boolean
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
            return false;
        }
    }

    /**
     * @param path path source
     * @return boolean
     */
    public boolean createFolder(String path) {
        return createFolder(filePath(path));
    }

    /**
     * @param path path source
     * @return boolean
     */
    public boolean deleteIfExists(String path) {
        return deleteIfExists(filePath(path));
    }

    /**
     * @param path path source
     * @return boolean
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
            return false;
        }
        return false;
    }

    /**
     * @param path path source
     * @return boolean
     */
    public boolean isDirectory(Path path) {
        return Files.isDirectory(path);
    }

    /**
     * @param path path source
     * @return boolean
     */
    public boolean isDirectory(String path) {
        return isDirectory(filePath(path));
    }

    /**
     * @param path path source
     * @return boolean
     */
    public boolean isFile(Path path) {
        return Files.isRegularFile(path);
    }

    /**
     * @param path path source
     * @return boolean
     */
    public boolean isFile(String path) {
        return isFile(filePath(path));
    }

    /**
     * @param path path source
     * @return boolean
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
     * @param path path source
     * @return boolean
     */
    public boolean exist(String path) {
        return exist(filePath(path));
    }

    /**
     * @param path path source
     * @return boolean
     */
    public Path filePath(String path) {
        return new File(path).toPath();
    }

    /**
     * @param filename file name
     * @return boolean
     */
    public TreeSet<String> fileToTree(String filename) {
        List<String> lst = file.linesReader(filename);
        TreeSet<String> tree = new TreeSet<>(lst);
        return tree;
    }

    /**
     * @param filename file name
     * @return boolean
     */
    public Map<String, String> fileToMap(String filename) {
        return file.linesReader(filename).stream()
                .collect(Collectors.toMap(String::new, item -> item));
    }

    /**
     * @param filename file name
     * @return convert file to list of String
     */
    public List<String> fileToList(String filename) {
        return file.linesReader(filename);
    }

    /**
     * @param url url
     * @return file size online
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
        }
        return file_size;
    }

    /**
     * @param filename file name
     * @return file size local
     */
    public long fileSizeLocal(String filename) {
        long file_size = 0;
        try {
            file_size = Files.size(Paths.get(filename));
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return file_size;
        }
        return file_size;
    }

    /**
     * @param url url
     * @return file name
     */
    public String getFilenameFromUrl(String url) {
        int pos = url.lastIndexOf("wet/") + 4;
        return url.substring(pos, url.length());
    }

    /**
     * @param filepath full path source
     * @return get file name
     */
    public String getFilename(String filepath) {
        int pos = filepath.lastIndexOf("/") + 1;
        return filepath.substring(pos, filepath.length());
    }

    /**
     * @param filename file name
     * @param fileList file list
     * @return boolean
     */
    public boolean isFileDownloaded(String filename, String fileList) {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(fileList)
            );
            String inLine;
            while ((inLine = reader.readLine()) != null) {
                if (inLine.equals(filename)) {
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
            return false;
        }
        return false;
    }

    /**
     * @param filename file name
     */
    public void addToDownloaded(String filename) {
        file.createFile(Conf.FILE_DOWNLOADED, Collections.singletonList(filename));
    }

    /**
     * Return url of latest GunZip wet file from Common Crawl Amazon S3 web page.
     * <p>
     * If Url fails it returns empty string.
     * <p>
     * It is specific method to this URL: http://commoncrawl.org/the-data/get-started
     *
     * @return url with updated download date.
     */
    public String getLastWetUrl() {
        try {
            Document doc = Jsoup.connect(Conf.URL_CC).get();
            Elements content = doc.getElementsByClass("entry-content");
            Elements archive = content.select("li:contains(MAIN)");
            String last = archive.select("li").last().toString();
            String fileName = last.substring(last.indexOf("CC-MAIN"), last.indexOf(" ")).trim();
            return Conf.URL_BASE + "crawl-data/" + fileName + "/" + getFilename(Conf.FILE_WET_GZ);
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
        }
        return Conf.EMPTY_STRING;
    }

}
