package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import opennlp.tools.namefind.NameFinderME;

public class LocationDetector extends AbstractNameDetector {

    @Inject
    public LocationDetector(@Named("locationModel") NameFinderME nameFinder) {
        super(nameFinder);
    }

    @Override
    public String getLabel() {
        return "Locations";
    }

}
