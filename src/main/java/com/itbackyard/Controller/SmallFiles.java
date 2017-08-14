package com.itbackyard.Controller;

import com.itbackyard.Const;
import com.itbackyard.System.ISystem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * By Maytham on 08-09-2016.
 */
public class SmallFiles implements ISystem {

    private SmallFiles() {
    }

    private static class SmallFilesHelper {
        private static final SmallFiles INSTANCE = new SmallFiles();
    }

    /**
     * SmallFiles Singleton
     *
     * @return
     */
    public static SmallFiles getInstance() {
        return SmallFiles.SmallFilesHelper.INSTANCE;
    }

    public void onStart() {
        String input = Const.PATH_OUTPUT + getNewestMaster() + ".txt";
        String tinyOutput = Const.PATH_DUMMY + "/www-tiny.txt";
        String smallOutput = Const.PATH_DUMMY + "/www-small.txt";
        String mediumOutput = Const.PATH_DUMMY + "/www-medium.txt";
        String bigOutput = Const.PATH_DUMMY + "/www-large.txt";

        //size in Kb
        try {
            createLimitedSizeFile(input, tinyOutput, 10);
            createLimitedSizeFile(input, smallOutput, 1000);
            createLimitedSizeFile(input, bigOutput, 250000);
            createLimitedSizeFile(input, mediumOutput, 10000);
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * @return
     */
    private String getNewestMaster() {
        List<Path> results = null;
        try {
            results = file.listFiles(Const.RES + "/output", "*.{txt}");
        } catch (IOException e) {
            log.write(log.getCurrentMethodName(), e.getMessage());
            e.printStackTrace();
        }
        List<String> fileList = new ArrayList<>();

        for (Path result : results) {
            String str = result.toString();
            if (result.toString().contains("master")) {
                fileList.add(str.substring(str.indexOf("master_"), str.indexOf(".txt")));
            }
        }

        fileList.sort(Comparator.comparing(String::new));

        return fileList.get(fileList.size() - 1);
    }

    /**
     * @param readFile
     * @param saveAs
     * @param sizeLimit
     * @throws IOException
     */
    private void createLimitedSizeFile(String readFile, String saveAs, int sizeLimit)
            throws IOException {
        Path input = Paths.get(readFile);
        Path output = Paths.get(saveAs);
        if (!file.exist(output.getParent())) {
            file.createFolder(output.getParent());
        }
        byte[] bytes = null;
        if ((Files.size(input) / 1000) > sizeLimit) {
            bytes = bytesFileReader(input, sizeLimit);
            file.createFile(saveAs, bytes);
        }
    }

    /**
     * Create file with size limit
     *
     * @param path
     * @param size
     * @return
     * @throws IOException
     */
    private byte[] bytesFileReader(Path path, int size) throws IOException {
        byte[] byteNew = new byte[size * 1000];
        byte[] byteContents = Files.readAllBytes(path);
        System.arraycopy(byteContents, 0, byteNew, 0, byteNew.length);
        return byteNew;
    }

}

