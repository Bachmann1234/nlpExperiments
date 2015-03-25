package com.mattbachmann.nlpexperiments.detectors;

import java.util.List;

public interface Detector {
    public String getLabel();
    public List<String> findAll(String text) throws DetectionException;

}
