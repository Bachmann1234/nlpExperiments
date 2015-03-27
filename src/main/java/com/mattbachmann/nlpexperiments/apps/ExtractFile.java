package com.mattbachmann.nlpexperiments.apps;

import com.mattbachmann.nlpexperiments.extraction.ExtractionException;
import com.mattbachmann.nlpexperiments.extraction.TextExtractor;

import java.io.File;

public class ExtractFile {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("I only take one arg, which is the file to extract text from");
            return;
        }

        try {
            System.out.println(TextExtractor.extractText(new File(args[0])));
        } catch (ExtractionException e) {
            System.out.println("Failed to extract text from file");
        }
    }
}
