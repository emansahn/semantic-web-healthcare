# Healthcare Semantic Web Project - Report

## 1. Introduction

This project implements a **Semantic Web application for the Healthcare domain** using RDF, RDFS, OWL, and SPARQL. The goal is to create a knowledge graph that models healthcare concepts and their relationships, allowing for complex semantic queries and real-world use cases.

### Project Objectives
- ✅ Create a complete healthcare ontology
- ✅ Implement semantic relationships between medical concepts
- ✅ Build SPARQL queries for healthcare data interrogation
- ✅ Demonstrate real-world use cases (drug safety, patient diagnosis)
- ✅ Provide a working Java implementation using Apache Jena

## 2. Ontology Design

### 2.1 Classes (RDFS)

The healthcare ontology defines the following main classes:

| Class | Description | Parent Class |
|-------|-------------|──────────────|
| **Person** | Base class for all individuals in healthcare system | rdfs:Class |
| **Patient** | A person receiving medical care | Person |
| **Doctor** | A medical professional providing care | Person |
| **Disease** | A medical disease or condition | - |
| **Medication** | A pharmaceutical drug or medication | - |
| **Symptom** | A symptom manifestation of a disease | - |
| **Diagnosis** | A medical diagnosis linking patient to disease | - |
| **Prescription** | A prescription of medication for a patient | - |

### 2.2 Properties (Relationships)

#### Person Properties
- `name` (xsd:string): Name of the person
- `age` (xsd:integer): Age of the person

#### Doctor Properties
- `specialty` (xsd:string): Medical specialty
- `licenseNumber` (xsd:string): Medical license number

#### Semantic Relationships
- `hasDiagnosis`: Patient → Diagnosis
- `diagnosisOfDisease`: Diagnosis → Disease
- `hasSymptom`: Disease → Symptom
- `treatedWith`: Disease → Medication
- `prescribedBy`: Prescription → Doctor
- `prescribedTo`: Prescription → Patient
- `prescriptionMedication`: Prescription → Medication
- `dosage`: Prescription → xsd:string
- `hasContraindication`: Medication → Medication

## 3. SPARQL Queries

### Query 1: List All Patients
```sparql
SELECT ?patient ?name ?age WHERE {
    ?patient rdf:type hc:Patient .
    ?patient hc:name ?name .
    OPTIONAL { ?patient hc:age ?age }
}
```

### Query 2: Get All Doctors with Specialties
```sparql
SELECT ?doctor ?name ?specialty WHERE {
    ?doctor rdf:type hc:Doctor .
    ?doctor hc:name ?name .
    ?doctor hc:specialty ?specialty .
}
```

### Query 3: Patient Diagnosis
```sparql
SELECT ?disease WHERE {
    hc:patient1 hc:hasDiagnosis ?diagnosis .
    ?diagnosis hc:diagnosisOfDisease ?disease .
    ?disease rdfs:label ?diseaseLabel
}
```

### Query 4: Disease Symptoms
```sparql
SELECT ?symptom ?symptomLabel WHERE {
    hc:disease1 hc:hasSymptom ?symptom .
    ?symptom rdfs:label ?symptomLabel .
}
```

### Query 5: Treatment Options
```sparql
SELECT ?medication ?medicationLabel WHERE {
    hc:disease1 hc:treatedWith ?medication .
    ?medication rdfs:label ?medicationLabel .
}
```

### Query 6: Patient Prescriptions
```sparql
SELECT ?doctor ?doctorName ?medication ?medicationLabel ?dosage WHERE {
    ?prescription hc:prescribedTo hc:patient1 .
    ?prescription hc:prescribedBy ?doctor .
    ?doctor hc:name ?doctorName .
    ?prescription hc:prescriptionMedication ?medication .
    ?medication rdfs:label ?medicationLabel .
    ?prescription hc:dosage ?dosage .
}
```

### Query 7: Drug Contraindications
```sparql
SELECT ?contraindicated ?contraindicatedLabel WHERE {
    hc:medication1 hc:hasContraindication ?contraindicated .
    ?contraindicated rdfs:label ?contraindicatedLabel .
}
```

## 4. Real-World Use Cases

### Use Case 1: Drug Safety Check
Automatically detects dangerous drug interactions for patients taking multiple medications.

### Use Case 2: Treatment Recommendation
Finds recommended medications for a patient's diagnosed disease.

### Use Case 3: Patient History Summary
Generates comprehensive health summaries with diagnoses, prescriptions, and doctors.

## 5. Implementation

**Technologies:**
- Apache Jena 4.9.0
- Java 11+
- Maven
- SPARQL Query Engine (ARQ)

## 6. Semantic Web Concepts Demonstrated

✅ **RDF**: Triplet representation of medical data
✅ **RDFS**: Class hierarchy and property constraints
✅ **OWL**: Restriction and contraindication relationships
✅ **SPARQL**: Complex medical data querying

## 7. Advantages for Healthcare

1. **Data Interoperability**: Different systems can share standardized RDF data
2. **Intelligent Querying**: Ask complex questions about patient data
3. **Knowledge Representation**: Capture medical expertise in machine-readable form
4. **Clinical Decision Support**: Automatic drug interaction checking
5. **Research Analytics**: Identify disease patterns and treatment effectiveness

## 8. Future Extensions

- Temporal data (dates, timelines)
- Risk assessment algorithms
- FHIR/HL7 integration
- Web-based query interface
- Real-time clinical alerts

---

**Status**: Complete ✅
**Date**: June 2026