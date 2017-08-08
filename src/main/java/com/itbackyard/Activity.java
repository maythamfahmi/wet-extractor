package com.itbackyard;

import com.itbackyard.Logic.Program;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * QualiFramework
 * Developer Maytham on 25-07-2017
 * 2017 © Copyright | ITBackyard ApS
 */
public class Activity {

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Starting...");
        new Program().onStart();
    }

}
