package com.github.grundygolem2.integrator.generation;


import com.github.grundygolem2.integrator.GlobalConfigurations;
import com.github.grundygolem2.integrator.generation.fieldGeneration.FieldGenerator;
import com.github.grundygolem2.integrator.generation.messageGeneration.GenericMessageGenerator;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by john on 4/14/17.
 */
@Controller
public class GenerationRun {
    private static final Logger log = LoggerFactory.getLogger(GenerationRun.class);

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(GlobalConfigurations.class, GenerationConfiguration.class);
        ctx.refresh();

        Set<Pair<String, Map<String, String>>> templateToReplacements = (Set<Pair<String, Map<String, String>>>) ctx.getBean("templateToReplacements");
        Map<String, FieldGenerator> fieldGeneratorMap = (Map<String, FieldGenerator>) ctx.getBean("fieldGeneratorMap");

        runGeneration(templateToReplacements, fieldGeneratorMap);
    }

    private static void runGeneration(Set<Pair<String, Map<String, String>>> templateToReplacements, Map<String, FieldGenerator> fieldGeneratorMap) {
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
