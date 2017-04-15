package com.github.grundygolem2.integrator.generation;

import com.github.grundygolem2.integrator.GlobalConfigurations;
import com.github.grundygolem2.integrator.generation.fieldGeneration.FieldGenerationFunctions;
import com.github.grundygolem2.integrator.generation.fieldGeneration.FieldGenerator;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.github.grundygolem2.integrator.utilities.StaticUtilities.*;

/**
 * Created by john on 4/14/17.
 */
@Configuration
@Import(GlobalConfigurations.class)
public class GenerationConfiguration {
    private static final Logger log = LoggerFactory.getLogger(GenerationConfiguration.class);

    @Value("${generation.templateToReplacement}")
    String templateToReplacement;

    @Value("${generation.replacementMaps}")
    String replacementMaps;

    @Value("${generation.templates}")
    String templates;

    @Bean
    Map<String, FieldGenerator> fieldGeneratorMap() {
        return ImmutableMap.<String, FieldGenerator>builder()
                .put("IDENTIFIER", FieldGenerationFunctions.getIdentifierGenerator())
                .put("TIMESTAMP", FieldGenerationFunctions.getTimestampGenerator())
                .build();
    }

    @Bean
    public Set<Pair<String, Map<String, String>>> templateToReplacements() {
        Class clazz = this.getClass();
        URL url = clazz.getResource(templateToReplacement);
        Set<Pair<String, String>> referencePairs = parseCsvToPairs(log, url);
        Set<Pair<String, Map<String, String>>> parsedPairs = new HashSet<>();
        referencePairs.forEach(pair -> {
            Pair<String, Map<String, String>> parsedPair = Pair.of(
                    urlToString(log, clazz.getResource(templates + pair.getLeft())),
                    parseCsvToMap(log, clazz.getResource(replacementMaps + pair.getRight()))
            );
            parsedPairs.add(parsedPair);
        });
        return parsedPairs;
    }


}
