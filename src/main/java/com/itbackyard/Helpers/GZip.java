package com.itbackyard.Helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * Class {@code GZip} GunZip extractor
 *
 * @author Maytham Fahmi
 * @since WET-EXTRACTOR 3.0
 */
public class GZip {

    private GZip() {
    }

    private static class GZipHelper {
        private static final GZip INSTANCE = new GZip();
    }

    /**
     * <code>getInstance</code> return thread-safe instance of GZip method.
     * this is because our INSTANCE declared as final.
     *
     * @return GZip instance
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
