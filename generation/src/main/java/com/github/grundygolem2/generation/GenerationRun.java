package com.github.grundygolem2.generation;


import com.github.grundygolem2.generation.fieldGeneration.FieldGenerator;
import com.github.grundygolem2.generation.messageGeneration.GenericMessageGenerator;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by john on 4/14/17.
 */
@Import(GenerationConfiguration.class)
public class GenerationRun {
    private static final Logger log = LoggerFactory.getLogger(GenerationRun.class);
    @Autowired
    private static Map<String, FieldGenerator> fieldGeneratorMap;

    @Autowired
    private static Set<Pair<String, Map<String, String>>> templateToReplacements;

    public static void main(String[] args) {
        GenerationConfiguration configuration = new GenerationConfiguration();
        templateToReplacements = configuration.templateToReplacements();
        fieldGeneratorMap = configuration.fieldGeneratorMap();
        templateToReplacements.forEach(pair -> {
            GenericMessageGenerator messageGenerator = GenericMessageGenerator.newMessageGenerator(pair.getLeft());
            Map<String, String> replacementMap = pair.getRight();
            replacementMap.putAll(computeFields(fieldGeneratorMap));
            log.info(messageGenerator.getMessage(replacementMap));
        });
    }

    private static Map<String, String> computeFields(Map<String, FieldGenerator> generatorMap) {
        Map<String, String> computedMap = new HashMap<>();
        generatorMap.forEach((key, value) -> {
            computedMap.put(key, value.getField());
        });
        return computedMap;
    }
}
