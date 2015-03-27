package com.mattbachmann.nlpexperiments.extraction;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public interface TextExtractor {
    /**
     * Takes in a file and returns the text contained in that stream
     * @param file
     *  The file to extract from.
     * @return
     *  The text we were able to pull out
     */
    static String extractText(File file) throws ExtractionException {
        Parser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        StringWriter writer = new StringWriter();
        try {
            parser.parse(TikaInputStream.get(file),
                    new WriteOutContentHandler(writer),
                    metadata,
                    new ParseContext());
        } catch (IOException | SAXException | TikaException e) {
            throw new ExtractionException("Failed at extracting text from stream", e);
        }
        return writer.toString().trim();
    }
}
