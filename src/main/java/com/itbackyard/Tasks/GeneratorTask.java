package com.itbackyard.Tasks;

import com.itbackyard.System.AppSystem;
import com.itbackyard.Conf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class {@code GeneratorTask} generator class for creating small searchable files
 *
 * @author Maytham Fahmi
 * @see AppSystem
 * @since WET-EXTRACTOR 3.0
 */
public class GeneratorTask extends AppSystem {

    /**
     * Wrapper method to create 4 searchable files with 4 different sizes
     * File size in Kb
     */
    public void createSmallFiles() {
        String input = Conf.P_OUTPUT + getNewestMaster() + ".txt";

        Conf.output().forEach((k, v) -> {
            try {
                createLimitedSizeFile(input, Conf.P_OUTPUT + k, v);
            } catch (IOException e) {
                log.error(getClassMethodName(), e);
            }
        });

    }

    /**
     * Select the latest dummy file
     *
     * @return
     */
    private String getNewestMaster() {
        List<Path> results = null;
        try {
            results = file.listFiles(Conf.P_OUTPUT, "*.{txt}");
        } catch (IOException e) {
            log.error(getClassMethodName(), e);
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
     * Create files with limited size
     *
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

