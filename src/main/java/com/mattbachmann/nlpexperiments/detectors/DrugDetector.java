package com.mattbachmann.nlpexperiments.detectors;

import com.google.inject.Inject;
import org.apache.ctakes.typesystem.type.textsem.IdentifiedAnnotation;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import java.util.ArrayList;
import java.util.List;

public class DrugDetector implements Detector {
    private AnalysisEngine analysisEngine;

    @Inject
    public DrugDetector(AnalysisEngine analysisEngine) {
        this.analysisEngine = analysisEngine;
    }

    @Override
    public String getLabel() {
        return "Medical Terms";
    }

    @Override
    public List<String> findAll(String text) throws DetectionException {
        List<String> results = new ArrayList<>();
        JCas jcas;
        try {
            jcas = JCasFactory.createJCas();
        } catch (UIMAException e) {
            throw new DetectionException("Failed to create document container", e);
        }
        jcas.setDocumentText(text);


        try {
            SimplePipeline.runPipeline(jcas, analysisEngine);
        } catch (Exception e) {
            throw new DetectionException("Failed to run pipeline", e);
        }
        for(IdentifiedAnnotation entity : JCasUtil.select(jcas, IdentifiedAnnotation.class)){
            String type = entity.getType().toString();
            if(type.contains("Medication")
                    ) {
                results.add(entity.getCoveredText());
            }

        }
        return results;
    }


}
