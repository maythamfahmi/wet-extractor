package com.itbackyard.System;

import com.itbackyard.Helpers.FileHelper;
import com.itbackyard.Helpers.GZip;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

/**
 * Class {@code IAppSystem} system interface
 *
 * @author Maytham Fahmi
 * @since WET-EXTRACTOR 3.0
 */
public interface IAppSystem {
    Log log = LogFactory.getLog("Logs");
    FileHelper file = FileHelper.getInstance();
    Calendar cal = Calendar.getInstance();
    GZip gzip = GZip.getInstance();
}
