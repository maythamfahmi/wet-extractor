package com.itbackyard;

import com.itbackyard.Entity.WetContent;
import com.itbackyard.Helper.FileHelper;
import org.apache.commons.io.IOUtils;
import org.archive.io.ArchiveReader;
import org.archive.io.ArchiveRecord;
import org.archive.io.ArchiveRecordHeader;
import org.archive.io.warc.WARCReaderFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Wet-extractor
 * Developer Maytham on 01-09-2017
 * 2017 © Copyright | ITBackyard ApS
 */
class DataStructure {

    static List<WetContent> wetExtractor(String readDataFile) throws IOException {

        List<WetContent> wetContents = new ArrayList<>();

        FileInputStream fis = new FileInputStream(readDataFile);

        ArchiveReader archiveReader = WARCReaderFactory.get(
                readDataFile, fis, true);

        for (ArchiveRecord archiveRecord : archiveReader) {

            ArchiveRecordHeader header = archiveRecord.getHeader();
            String idf = header.toString();
            String url = header.getUrl();

            if (!idf.startsWith("{reader-identifier")) {
                byte[] rawData = IOUtils.toByteArray(
                        archiveRecord, archiveRecord.available());
                String rawContent = new String(rawData);
                String title = rawContent.substring(0, rawContent.indexOf("\n") + 1);
                String content = rawContent.substring(rawContent.indexOf("\n") + 1);

                if (!title.isEmpty()) {
                    wetContents.add(new WetContent(url, title, content));
                }
            }
        }

        return wetContents;
    }

    static List<String> swearWord(String fileName) {
        return FileHelper.linesReader(fileName);
    }

}
