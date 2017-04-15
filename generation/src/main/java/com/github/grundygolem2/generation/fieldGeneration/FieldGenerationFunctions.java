package com.github.grundygolem2.generation.fieldGeneration;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Created by john on 4/14/17.
 */

public class FieldGenerationFunctions {
    public static FieldGenerator getTimestampGenerator() {
        return (() -> Long.toString(Instant.now().toEpochMilli()));
    }

    public static FieldGenerator getFormattedTimeGenerator(final String format) {
        return (() -> {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date());
        });
    }

    public static FieldGenerator getIdentifierGenerator() {
        return (() -> UUID.randomUUID().toString());
    }
}
