package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrugDetector implements Detector {

    private Pattern drugPattern;

    @Inject
    public DrugDetector(@Named("drugPattern") Pattern drugPattern) {
        this.drugPattern = drugPattern;
    }

    @Override
    public String getLabel() {
        return "Medical Terms";
    }

    @Override
    public List<String> findAll(String text) throws DetectionException {
        List<String> results = new ArrayList<>();
        Matcher m = drugPattern.matcher(text);
        while(m.find()) {
            results.add(m.group());
        }

        return results;
    }


}
