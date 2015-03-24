package com.mattbachmann.nlpexperiments.detectors;

import java.util.List;

public interface Detector {
    public abstract String getLabel();
    public List<String> findAll(String text);

}
