package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import opennlp.tools.namefind.NameFinderME;

public class PersonDetector extends AbstractNameDetector {

    @Inject
    public PersonDetector(@Named("personModel") NameFinderME nameFinder) {
        super(nameFinder);
    }

    @Override
    public String getLabel() {
        return "Names";
    }
}
