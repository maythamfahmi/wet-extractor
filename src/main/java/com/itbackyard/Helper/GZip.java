package com.itbackyard.Helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Wet-extractor
 * Developer Maytham on 04-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class GZip {

    private GZip() {
    }

    private static class GZipHelper {
        private static final GZip INSTANCE = new GZip();
    }

    /**
     * <code>getInstance</code> return threadsafe instance of GZip method.
     * this is becuase our INSTANCE declared as final.
     * Resources http://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
     *
     * @return
     */
    public static GZip getInstance() {
        return GZipHelper.INSTANCE;
    }

    /**
     * Unzip gz (gunzip) file format
     * Resources https://www.mkyong.com/java/how-to-decompress-file-from-gzip-file
     *
     * @param inputFile  gz file
     * @param outputFile txt file
     * @return if unzip correctly will be true, else false
     */
    public void gzUnzip(String inputFile, String outputFile) {
        byte[] buffer = new byte[1024];
        try {
            GZIPInputStream gzis =
                    new GZIPInputStream(new FileInputStream(inputFile));
            FileOutputStream out =
                    new FileOutputStream(outputFile);
            int len;
            while ((len = gzis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            gzis.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
