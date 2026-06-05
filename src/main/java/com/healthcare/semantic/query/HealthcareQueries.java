package com.healthcare.semantic.query;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;

/**
 * SPARQL Queries for Healthcare Domain
 * Demonstrates various semantic queries on the healthcare ontology
 */
public class HealthcareQueries {

    private Model model;
    private static final String PREFIX = "PREFIX hc: <http://healthcare.semantic/ontology/> " +
                                         "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                                         "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>";

    public HealthcareQueries(Model model) {
        this.model = model;
    }

    /**
     * Query 1: Get all patients
     */
    public void getAllPatients() {
        String queryStr = PREFIX +
                " SELECT ?patient ?name ?age WHERE { " +
                "  ?patient rdf:type hc:Patient . " +
                "  ?patient hc:name ?name . " +
                "  OPTIONAL { ?patient hc:age ?age } " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Query 2: Get all doctors with their specialties
     */
    public void getAllDoctors() {
        String queryStr = PREFIX +
                " SELECT ?doctor ?name ?specialty WHERE { " +
                "  ?doctor rdf:type hc:Doctor . " +
                "  ?doctor hc:name ?name . " +
                "  ?doctor hc:specialty ?specialty . " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Query 3: Get diagnosis for a specific patient
     */
    public void getPatientDiagnosis(String patientId) {
        String queryStr = PREFIX +
                " SELECT ?disease WHERE { " +
                "  hc:" + patientId + " hc:hasDiagnosis ?diagnosis . " +
                "  ?diagnosis hc:diagnosisOfDisease ?disease . " +
                "  ?disease rdfs:label ?diseaseLabel " +
                " } ";

        executeQuery(queryStr);
    }

    /**
     * Query 4: Get all symptoms of a disease
     */
    public void getDiseaseSymptoms(String diseaseId) {
        String queryStr = PREFIX +
                " SELECT ?symptom ?symptomLabel WHERE { " +
                "  hc:" + diseaseId + " hc:hasSymptom ?symptom . " +
                "  ?symptom rdfs:label ?symptomLabel . " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Query 5: Get medications to treat a disease
     */
    public void getDiseaseMedications(String diseaseId) {
        String queryStr = PREFIX +
                " SELECT ?medication ?medicationLabel WHERE { " +
                "  hc:" + diseaseId + " hc:treatedWith ?medication . " +
                "  ?medication rdfs:label ?medicationLabel . " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Query 6: Get all prescriptions for a patient
     */
    public void getPatientPrescriptions(String patientId) {
        String queryStr = PREFIX +
                " SELECT ?doctor ?doctorName ?medication ?medicationLabel ?dosage WHERE { " +
                "  ?prescription hc:prescribedTo hc:" + patientId + " . " +
                "  ?prescription hc:prescribedBy ?doctor . " +
                "  ?doctor hc:name ?doctorName . " +
                "  ?prescription hc:prescriptionMedication ?medication . " +
                "  ?medication rdfs:label ?medicationLabel . " +
                "  ?prescription hc:dosage ?dosage . " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Query 7: Find contraindicated drugs (drug interactions)
     */
    public void getDrugContraindications(String medicationId) {
        String queryStr = PREFIX +
                " SELECT ?contraindicated ?contraindicatedLabel WHERE { " +
                "  hc:" + medicationId + " hc:hasContraindication ?contraindicated . " +
                "  ?contraindicated rdfs:label ?contraindicatedLabel . " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Query 8: Get patients diagnosed by a specific doctor
     */
    public void getPatientsByDoctor(String doctorId) {
        String queryStr = PREFIX +
                " SELECT ?patient ?patientName WHERE { " +
                "  ?prescription hc:prescribedBy hc:" + doctorId + " . " +
                "  ?prescription hc:prescribedTo ?patient . " +
                "  ?patient hc:name ?patientName . " +
                " }";

        executeQuery(queryStr);
    }

    /**
     * Use Case: Check drug safety for a patient
     * Finds all medications prescribed to a patient and checks for contraindications
     */
    public void checkDrugSafety(String patientId) {
        String queryStr = PREFIX +
                " SELECT ?medication1 ?med1Label ?medication2 ?med2Label WHERE { " +
                "  ?prescription1 hc:prescribedTo hc:" + patientId + " . " +
                "  ?prescription1 hc:prescriptionMedication ?medication1 . " +
                "  ?medication1 rdfs:label ?med1Label . " +
                "  ?prescription2 hc:prescribedTo hc:" + patientId + " . " +
                "  ?prescription2 hc:prescriptionMedication ?medication2 . " +
                "  ?medication2 rdfs:label ?med2Label . " +
                "  ?medication1 hc:hasContraindication ?medication2 . " +
                " }";

        ResultSet results = executeQueryWithResult(queryStr);
        
        if (results == null || !results.hasNext()) {
            System.out.println("  ✅ No drug interactions found for patient " + patientId);
        } else {
            System.out.println("  ⚠️  POTENTIAL DRUG INTERACTIONS DETECTED:");
            while (results.hasNext()) {
                QuerySolution solution = results.nextSolution();
                String med1 = solution.getLiteral("med1Label").getString();
                String med2 = solution.getLiteral("med2Label").getString();
                System.out.println("     • " + med1 + " <-> " + med2);
            }
        }
    }

    /**
     * Execute SPARQL query and print results
     */
    private void executeQuery(String queryStr) {
        try {
            Query query = QueryFactory.create(queryStr);
            QueryExecution exec = QueryExecutionFactory.create(query, model);
            ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
            
            if (!results.hasNext()) {
                System.out.println("  No results found.");
            } else {
                printResults(results);
            }
            
            exec.close();
        } catch (Exception e) {
            System.err.println("  ❌ Query error: " + e.getMessage());
        }
    }

    /**
     * Execute SPARQL query and return results
     */
    private ResultSet executeQueryWithResult(String queryStr) {
        try {
            Query query = QueryFactory.create(queryStr);
            QueryExecution exec = QueryExecutionFactory.create(query, model);
            ResultSet results = ResultSetFactory.copyResults(exec.execSelect());
            exec.close();
            return results;
        } catch (Exception e) {
            System.err.println("  ❌ Query error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Print query results in a formatted table
     */
    private void printResults(ResultSet results) {
        int count = 0;
        while (results.hasNext()) {
            count++;
            QuerySolution solution = results.nextSolution();
            
            System.out.print("  " + count + ". ");
            
            solution.varNames().forEachRemaining(varName -> {
                if (solution.get(varName) != null) {
                    String value = solution.get(varName).toString();
                    // Extract label if it's a URI
                    if (value.contains("/ontology/")) {
                        value = value.substring(value.lastIndexOf("/") + 1);
                    }
                    System.out.print(varName + "=" + value + " ");
                }
            });
            System.out.println();
        }
    }
}