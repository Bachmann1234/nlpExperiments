package com.mattbachmann.nlpexperiments.modules;

import com.google.inject.AbstractModule;
import org.apache.ctakes.clinicalpipeline.ClinicalPipelineFactory;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AggregateBuilder;
import org.apache.uima.resource.ResourceInitializationException;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;

public class CTAKESModule extends AbstractModule {
    @Override
    protected void configure() {
        AggregateBuilder builder = new AggregateBuilder();
        // You can consider using ClinicalPipelineFactory.getDefaultPipeline();
        try {
            //if using default pipeline you need some environment variables set
            //ctakes.umlsuser and ctatkes.umlspw
            builder.add(ClinicalPipelineFactory.getFastPipeline());
            //builder.add(ClinicalPipelineFactory.getTokenProcessingPipeline());
        } catch (ResourceInitializationException e) {
            throw new IllegalStateException("failed to build pipeline", e);
        }

        try {
            bind(AnalysisEngine.class).toInstance(createEngine(builder.createAggregateDescription()));
        } catch (ResourceInitializationException e) {
            throw new IllegalStateException("Failed to create Ctakes analysis engine description", e);
        }
    }
}
