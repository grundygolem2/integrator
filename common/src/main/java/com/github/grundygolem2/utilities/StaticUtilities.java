package com.github.grundygolem2.utilities;

import com.google.common.base.Charsets;
import com.google.common.base.Splitter;
import com.google.common.io.Files;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;


/**
 * Created by john on 4/14/17.
 */
public class StaticUtilities {
    public static String urlToString(Logger log, URL url) {
        try {
            return Files.toString(new File(url.toURI()), Charsets.UTF_8);
        } catch (URISyntaxException e) {
            //shouldn't happen
            return null;
        } catch (IOException e) {
            log.error("Unable to parse file contents to String", e);
            return null;
        }
    }

    public static Set<Pair<String, String>> parseCsvToPairs(Logger log, URL url) {
        List<String> rawPairs;
        try {
            File file = new File(url.toURI());
            rawPairs = Files.readLines(file, Charsets.UTF_8);
        } catch (URISyntaxException e) {
            //shouldn't happen
            return null;
        } catch (IOException e) {
            log.error("Unable to read file", e);
            return null;
        }
        Set<Pair<String, String>> pairs = new HashSet<>();
        for (String rawPair : rawPairs) {
            List<String> splitPair = Splitter.on(',').splitToList(rawPair);
            Pair<String, String> pair = Pair.of(splitPair.get(0), splitPair.get(1));
            pairs.add(pair);
        }
        return pairs;
    }

    public static Map<String, String> parseCsvToMap(Logger log, URL url) {
        List<String> rawPairs;
        try {
            File file = new File(url.toURI());
            rawPairs = Files.readLines(file, Charsets.UTF_8);
        } catch (URISyntaxException e) {
            //shouldn't happen
            return null;
        } catch (IOException e) {
            log.error("Unable to read file", e);
            return null;
        }
        Map<String, String> pairs = new HashMap<>();
        for (String rawPair : rawPairs) {
            List<String> splitPair = Splitter.on(',').splitToList(rawPair);
            pairs.put(splitPair.get(0), splitPair.get(1));
        }
        return pairs;
    }
}
