package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DrugDetector implements Detector {

    Set<String> drugNames;

    @Inject
    public DrugDetector(@Named("drugNames") Set drugNames) {
        this.drugNames = drugNames;
    }

    @Override
    public String getLabel() {
        return "Medical Terms";
    }

    @Override
    public List<String> findAll(String text) throws DetectionException {
        List<String> results = new ArrayList<>();
        String[] tokens = text.toLowerCase().split("\\s+");
        for(String token : tokens) {
            if(drugNames.contains(token)) {
                results.add(token);
            }
        }
        return results;
    }


}
