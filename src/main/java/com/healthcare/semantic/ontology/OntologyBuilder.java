package com.healthcare.semantic.ontology;

import org.apache.jena.rdf.model.*;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

/**
 * Programmatic ontology builder for Healthcare domain
 * Creates the complete healthcare ontology in-memory
 */
public class OntologyBuilder {

    private static final String NS = "http://healthcare.semantic/ontology/";
    private static final String XSD = "http://www.w3.org/2001/XMLSchema#";

    private static Model model;
    private static Property name;
    private static Property age;
    private static Property specialty;
    private static Property licenseNumber;
    private static Property hasDiagnosis;
    private static Property diagnosisOfDisease;
    private static Property hasSymptom;
    private static Property treatedWith;
    private static Property prescribedBy;
    private static Property prescribedTo;
    private static Property prescriptionMedication;
    private static Property dosage;
    private static Property hasContraindication;

    private static Resource Person;
    private static Resource Patient;
    private static Resource Doctor;
    private static Resource Disease;
    private static Resource Medication;
    private static Resource Symptom;
    private static Resource Diagnosis;
    private static Resource Prescription;

    public static Model buildOntology() {
        model = ModelFactory.createDefaultModel();
        model.setNsPrefix("hc", NS);

        initializeClasses();
        initializeProperties();
        addInstances();

        return model;
    }

    private static void initializeClasses() {
        // Define classes
        Person = model.createResource(NS + "Person");
        Patient = model.createResource(NS + "Patient");
        Doctor = model.createResource(NS + "Doctor");
        Disease = model.createResource(NS + "Disease");
        Medication = model.createResource(NS + "Medication");
        Symptom = model.createResource(NS + "Symptom");
        Diagnosis = model.createResource(NS + "Diagnosis");
        Prescription = model.createResource(NS + "Prescription");

        // Class hierarchy
        Person.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Person");

        Patient.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.subClassOf, Person)
                .addProperty(RDFS.label, "Patient");

        Doctor.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.subClassOf, Person)
                .addProperty(RDFS.label, "Doctor");

        Disease.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Disease");

        Medication.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Medication");

        Symptom.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Symptom");

        Diagnosis.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Diagnosis");

        Prescription.addProperty(RDF.type, RDFS.Class)
                .addProperty(RDFS.label, "Prescription");
    }

    private static void initializeProperties() {
        // Person properties
        name = model.createProperty(NS + "name");
        age = model.createProperty(NS + "age");

        name.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "name")
                .addProperty(RDFS.domain, Person);

        age.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "age")
                .addProperty(RDFS.domain, Person);

        // Doctor properties
        specialty = model.createProperty(NS + "specialty");
        licenseNumber = model.createProperty(NS + "licenseNumber");

        specialty.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "specialty")
                .addProperty(RDFS.domain, Doctor);

        licenseNumber.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "licenseNumber")
                .addProperty(RDFS.domain, Doctor);

        // Medical relationship properties
        hasDiagnosis = model.createProperty(NS + "hasDiagnosis");
        diagnosisOfDisease = model.createProperty(NS + "diagnosisOfDisease");
        hasSymptom = model.createProperty(NS + "hasSymptom");
        treatedWith = model.createProperty(NS + "treatedWith");
        prescribedBy = model.createProperty(NS + "prescribedBy");
        prescribedTo = model.createProperty(NS + "prescribedTo");
        prescriptionMedication = model.createProperty(NS + "prescriptionMedication");
        dosage = model.createProperty(NS + "dosage");
        hasContraindication = model.createProperty(NS + "hasContraindication");

        hasDiagnosis.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "hasDiagnosis")
                .addProperty(RDFS.domain, Patient);

        diagnosisOfDisease.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "diagnosisOfDisease")
                .addProperty(RDFS.domain, Diagnosis);

        hasSymptom.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "hasSymptom")
                .addProperty(RDFS.domain, Disease);

        treatedWith.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "treatedWith")
                .addProperty(RDFS.domain, Disease);

        dosage.addProperty(RDF.type, RDF.Property)
                .addProperty(RDFS.label, "dosage")
                .addProperty(RDFS.domain, Prescription);
    }

    private static void addInstances() {
        // Create Doctors
        Resource doctor1 = model.createResource(NS + "doctor1");
        doctor1.addProperty(RDF.type, Doctor)
                .addProperty(name, "Dr. Alice Johnson")
                .addProperty(specialty, "Cardiology")
                .addProperty(licenseNumber, "MD-001");

        Resource doctor2 = model.createResource(NS + "doctor2");
        doctor2.addProperty(RDF.type, Doctor)
                .addProperty(name, "Dr. Robert Smith")
                .addProperty(specialty, "Neurology")
                .addProperty(licenseNumber, "MD-002");

        // Create Patients
        Resource patient1 = model.createResource(NS + "patient1");
        patient1.addProperty(RDF.type, Patient)
                .addProperty(name, "John Doe")
                .addProperty(age, model.createTypedLiteral(45));

        Resource patient2 = model.createResource(NS + "patient2");
        patient2.addProperty(RDF.type, Patient)
                .addProperty(name, "Jane Smith")
                .addProperty(age, model.createTypedLiteral(38));

        // Create Diseases
        Resource disease1 = model.createResource(NS + "disease1");
        disease1.addProperty(RDF.type, Disease)
                .addProperty(RDFS.label, "Hypertension");

        Resource disease2 = model.createResource(NS + "disease2");
        disease2.addProperty(RDF.type, Disease)
                .addProperty(RDFS.label, "Migraine");

        // Create Symptoms
        Resource symptom1 = model.createResource(NS + "symptom1");
        symptom1.addProperty(RDF.type, Symptom)
                .addProperty(RDFS.label, "High Blood Pressure");

        Resource symptom2 = model.createResource(NS + "symptom2");
        symptom2.addProperty(RDF.type, Symptom)
                .addProperty(RDFS.label, "Severe Headache");

        // Create Medications
        Resource medication1 = model.createResource(NS + "medication1");
        medication1.addProperty(RDF.type, Medication)
                .addProperty(RDFS.label, "Lisinopril");

        Resource medication2 = model.createResource(NS + "medication2");
        medication2.addProperty(RDF.type, Medication)
                .addProperty(RDFS.label, "Ibuprofen");

        Resource medication3 = model.createResource(NS + "medication3");
        medication3.addProperty(RDF.type, Medication)
                .addProperty(RDFS.label, "Aspirin");

        // Disease-Symptom relationships
        disease1.addProperty(hasSymptom, symptom1);
        disease2.addProperty(hasSymptom, symptom2);

        // Disease-Treatment relationships
        disease1.addProperty(treatedWith, medication1);
        disease2.addProperty(treatedWith, medication2);

        // Drug contraindications
        medication1.addProperty(hasContraindication, medication3);

        // Create Diagnoses
        Resource diagnosis1 = model.createResource(NS + "diagnosis1");
        diagnosis1.addProperty(RDF.type, Diagnosis)
                .addProperty(diagnosisOfDisease, disease1);

        patient1.addProperty(hasDiagnosis, diagnosis1);

        // Create Prescriptions
        Resource prescription1 = model.createResource(NS + "prescription1");
        prescription1.addProperty(RDF.type, Prescription)
                .addProperty(prescribedBy, doctor1)
                .addProperty(prescribedTo, patient1)
                .addProperty(prescriptionMedication, medication1)
                .addProperty(dosage, "10mg daily");
    }
}