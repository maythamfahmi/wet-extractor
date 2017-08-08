package com.itbackyard.FileGenerator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * By Maytham on 08-09-2016.
 */
public class SmallFiles {

    public static void main(String[] args) throws IOException, URISyntaxException {
        String input = "resources/output/master.txt";
        String tinyOutput = "resources/output/www-tiny.txt";
        String smallOutput = "resources/output/www-small.txt";
        String mediumOutput = "resources/output/www-medium.txt";
        String bigOutput = "resources/output/www-large.txt";

        //size in Kb
        generateLimitedSizeFile(input, tinyOutput, 10);
        generateLimitedSizeFile(input, smallOutput, 1000);
        generateLimitedSizeFile(input, mediumOutput, 10000);
        generateLimitedSizeFile(input, bigOutput, 250000);
    }

    private static void generateLimitedSizeFile(String readFile, String saveAs, int size) throws IOException {
        Path input = Paths.get(readFile);
        Path output = Paths.get(saveAs);
        byte[] bytes = bytesFileReader(input, size);

        Files.write(output, bytes,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );

    }

    //file size limiter
    private static byte[] bytesFileReader(Path path, int size) throws IOException {
        byte[] byteNew = new byte[size * 1000];
        byte[] byteContents = Files.readAllBytes(path);
        System.arraycopy(byteContents, 0, byteNew, 0, byteNew.length);
        return byteNew;
    }

}

