package com.mattbachmann.nlpexperiments.extraction;

public class ExtractionException extends Throwable {
    public ExtractionException(String message, Throwable exception) {
        super(message, exception);
    }
}
