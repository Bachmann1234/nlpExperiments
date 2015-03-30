package com.mattbachmann.nlpexperiments.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class DrugModule extends AbstractModule {
    @Override
    protected void configure() {
        Set<String> drugNames = new HashSet<>();

        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream("/naiveDrugs.txt")))
        ) {
            String line;
            while((line = reader.readLine()) != null) {
                drugNames.add(line.trim());
            }

        } catch (IOException e) {
           throw new IllegalStateException("Could not load in drugs");
        }

        bind(Set.class).annotatedWith(Names.named("drugNames")).toInstance(drugNames);
    }
}
