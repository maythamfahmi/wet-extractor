package com.itbackyard.System;

import com.itbackyard.Helpers.FileHelper;
import com.itbackyard.Helpers.GZip;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Calendar;

public interface IAppSystem {
    Log log = LogFactory.getLog("Logs");
    FileHelper file = FileHelper.getInstance();
    Calendar cal = Calendar.getInstance();
    GZip gzip = GZip.getInstance();
}
