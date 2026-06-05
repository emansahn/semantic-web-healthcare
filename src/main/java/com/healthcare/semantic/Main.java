package com.healthcare.semantic;

import com.healthcare.semantic.ontology.HealthcareOntology;
import com.healthcare.semantic.query.HealthcareQueries;
import org.apache.jena.rdf.model.Model;

/**
 * Main entry point for the Healthcare Semantic Web Application
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   Semantic Web - Healthcare Domain Project                 ║");
        System.out.println("║   Using RDF, RDFS, OWL and SPARQL                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        try {
            // Load ontology
            System.out.println("📂 Loading Healthcare Ontology...");
            HealthcareOntology ontology = new HealthcareOntology();
            Model model = ontology.loadOntology();
            System.out.println("✅ Ontology loaded successfully!\n");

            // Create queries instance
            HealthcareQueries queries = new HealthcareQueries(model);

            // Execute demo queries
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.println("EXECUTING SPARQL QUERIES");
            System.out.println("════════════════════════════════════════════════════════════\n");

            // Query 1: List all patients
            System.out.println("📋 [Query 1] List all patients:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getAllPatients();
            System.out.println();

            // Query 2: List all doctors and their specialties
            System.out.println("📋 [Query 2] List all doctors and their specialties:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getAllDoctors();
            System.out.println();

            // Query 3: Find patient diagnoses
            System.out.println("📋 [Query 3] Find diagnosis for patient John Doe:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getPatientDiagnosis("patient1");
            System.out.println();

            // Query 4: Find symptoms of a disease
            System.out.println("📋 [Query 4] Find symptoms of Hypertension:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getDiseaseSymptoms("disease1");
            System.out.println();

            // Query 5: Find medications for a disease
            System.out.println("📋 [Query 5] Find medications for Hypertension:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getDiseaseMedications("disease1");
            System.out.println();

            // Query 6: Find prescriptions for a patient
            System.out.println("📋 [Query 6] Find prescriptions for patient John Doe:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getPatientPrescriptions("patient1");
            System.out.println();

            // Query 7: Find drug contraindications
            System.out.println("📋 [Query 7] Find contraindicated drugs for Lisinopril:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getDrugContraindications("medication1");
            System.out.println();

            // Query 8: Find all diseases treated by a doctor
            System.out.println("📋 [Query 8] Find patients diagnosed by Dr. Alice Johnson:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.getPatientsByDoctor("doctor1");
            System.out.println();

            // Use Case: Check drug safety
            System.out.println("════════════════════════════════════════════════════════════");
            System.out.println("USE CASE: DRUG SAFETY CHECK");
            System.out.println("════════════════════════════════════════════════════════════\n");
            System.out.println("🔍 Checking drug interactions for patient:");
            System.out.println("─────────────────────────────────────────────────────");
            queries.checkDrugSafety("patient1");

            System.out.println("\n════════════════════════════════════════════════════════════");
            System.out.println("✅ All queries executed successfully!");
            System.out.println("════════════════════════════════════════════════════════════\n");

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}