package com.healthcare.semantic.ontology;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.InputStream;

/**
 * Healthcare Ontology Loader
 * Loads the RDF/RDFS/OWL healthcare ontology from file
 */
public class HealthcareOntology {

    private static final String ONTOLOGY_FILE = "/healthcare.rdf";
    private Model model;

    /**
     * Load the healthcare ontology from RDF file
     * @return The loaded Jena Model
     */
    public Model loadOntology() {
        model = ModelFactory.createDefaultModel();

        try {
            InputStream in = this.getClass().getResourceAsStream(ONTOLOGY_FILE);
            if (in != null) {
                model.read(in, null, "RDF/XML");
                in.close();
                System.out.println("  • Loaded " + model.size() + " RDF triples");
            } else {
                System.err.println("  ⚠ Ontology file not found in resources");
                System.err.println("  • Creating in-memory ontology instead...");
                model = OntologyBuilder.buildOntology();
            }
        } catch (Exception e) {
            System.err.println("  ⚠ Error loading ontology: " + e.getMessage());
            System.err.println("  • Creating in-memory ontology instead...");
            model = OntologyBuilder.buildOntology();
        }

        return model;
    }

    /**
     * Get the loaded model
     * @return The Jena Model
     */
    public Model getModel() {
        if (model == null) {
            loadOntology();
        }
        return model;
    }
}