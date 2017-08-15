package com.itbackyard.System;

import com.itbackyard.Controller.ListFetcher;
import com.itbackyard.Controller.Program;
import com.itbackyard.Controller.Downloader;
import com.itbackyard.Controller.SmallFiles;
import com.itbackyard.Helper.FileHelper;
import com.itbackyard.Helper.GZip;
import com.itbackyard.Helper.LogData;
import com.itbackyard.View.ConsoleView;

import java.util.Calendar;

/**
 * Wet-extractor
 * Developer Maytham on 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public interface ISystem {
    LogData log = LogData.getInstance();
    FileHelper file = FileHelper.getInstance();
    Calendar cal = Calendar.getInstance();
    GZip gzip = GZip.getInstance();
    ConsoleView c = ConsoleView.getInstance();
    ListFetcher listfetcher = ListFetcher.getInstance();
    Downloader downloader = Downloader.getInstance();
    Program program = Program.getInstance();
    System system = System.getInstance();
    SmallFiles small = SmallFiles.getInstance();
}
