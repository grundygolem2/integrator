package com.github.grundygolem2.generation.messageGeneration;

import java.util.Map;

/**
 * Created by john on 4/14/17.
 */
public interface MessageGenerator {
    public String getMessage(Map<String,String> replacementParameters);
}
