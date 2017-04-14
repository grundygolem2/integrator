package com.github.grundygolem2.generation.messageGeneration;

import java.util.Map;

/**
 * Created by john on 4/14/17.
 */
public class GenericMessageGenerator implements MessageGenerator{
    private String template;
    public static MessageGenerator newMessageGenerator(String template) {
        return new GenericMessageGenerator(template);
    }

    @Override
    public String getMessage(Map<String, String> replacementParameters) {
        String message = template;
        for (Map.Entry<String,String> entry : replacementParameters.entrySet()){
            message = message.replace(entry.getKey(),entry.getValue());
        }
        return message;
    }

    private GenericMessageGenerator(String template){
        template = template;
    }
}
