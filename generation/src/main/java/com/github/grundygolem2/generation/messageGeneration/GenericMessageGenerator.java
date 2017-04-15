package com.github.grundygolem2.generation.messageGeneration;

import java.util.Map;

/**
 * Created by john on 4/14/17.
 */
public class GenericMessageGenerator implements MessageGenerator {
    private String template;

    private GenericMessageGenerator(final String template) {
        this.template = template;
    }

    public static GenericMessageGenerator newMessageGenerator(final String template) {
        return new GenericMessageGenerator(template);
    }

    @Override
    public String getMessage(final Map<String, String> replacementParameters) {
        String message = template;
        for (Map.Entry<String, String> entry : replacementParameters.entrySet()) {
            message = message.replaceAll(entry.getKey(), entry.getValue());
        }
        return message;
    }
}
