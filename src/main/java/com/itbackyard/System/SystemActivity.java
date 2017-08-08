package com.itbackyard.System;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Wet-extractor
 * Developer Maytham on 08-08-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public abstract class SystemActivity {

    protected abstract void onStart() throws IOException, URISyntaxException;

    protected abstract void onExit();

}
