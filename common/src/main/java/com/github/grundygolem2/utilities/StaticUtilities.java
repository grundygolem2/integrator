package com.github.grundygolem2.utilities;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;


/**
 * Created by john on 4/14/17.
 */
public class StaticUtilities {
    public static String fileToString(Logger log, File file){
        try {
            return Files.toString(file, Charsets.UTF_8);
        } catch (IOException e) {
            log.error("Unable to parse file contents to String", e);
            return null;
        }
    }
}
