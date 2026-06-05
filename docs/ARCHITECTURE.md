# Architecture & Design Document

## System Overview

```
┌─────────────────────────────────────────────────────────────┐
│                   Healthcare Semantic Web                   │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│   ┌──────────────────┐        ┌──────────────────────┐   │
│   │   RDF/OWL Data   │────────│  Jena Model (In-Memory) │   │
│   │ (healthcare.rdf) │        │     or Programmatic  │   │
│   └──────────────────┘        └──────────────────────┘   │
│                                          │                │
│                                          ▼                │
│   ┌─────────────────────────────────────────────────┐    │
│   │     SPARQL Query Engine (ARQ)                  │    │
│   │  - Pattern Matching                           │    │
│   │  - FILTER, OPTIONAL, UNION                    │    │
│   │  - Result Set Processing                      │    │
│   └─────────────────────────────────────────────────┘    │
│                          │                                │
│          ┌───────────────┼───────────────┐               │
│          ▼               ▼               ▼               │
│   ┌────────────┐ ┌────────────┐ ┌────────────┐          │
│   │  Query 1   │ │  Query 2   │ │  Query N   │  ...     │
│   │ (Patients) │ │ (Doctors)  │ │  (Custom)  │          │
│   └────────────┘ └────────────┘ └────────────┘          │
│          │               │               │               │
│          └───────────────┴───────────────┘               │
│                          │                                │
│                          ▼                                │
│          ┌──────────────────────────────┐                │
│          │   Result Set Processing      │                │
│          │   - Parse Results            │                │
│          │   - Format Output            │                │
│          │   - Display to User          │                │
│          └──────────────────────────────┘                │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

## Data Model

### Class Hierarchy

```
rdfs:Class
├── Person
│   ├── Patient
│   └── Doctor
├── Disease
├── Medication
├── Symptom
├── Diagnosis
└── Prescription
```

### Relationship Graph

```
Patient --hasDiagnosis--> Diagnosis --diagnosisOfDisease--> Disease
                                                              │
                                                              ├--hasSymptom--> Symptom
                                                              │
                                                              └--treatedWith--> Medication
                                                                                    │
                                                                                    └--hasContraindication--> Medication

Prescription --prescribedBy--> Doctor
             --prescribedTo--> Patient
             --prescriptionMedication--> Medication
             --dosage--> xsd:string
```

## Package Structure

```
com.healthcare.semantic/
├── Main.java
│   └── Entry point, demo execution
│
├── ontology/
│   ├── HealthcareOntology.java
│   │   └── Loads RDF from file or creates in-memory
│   │
│   └── OntologyBuilder.java
│       └── Programmatically builds ontology
│
├── query/
│   └── HealthcareQueries.java
│       └── SPARQL query methods
│
└── model/ (Optional)
    ├── Patient.java
    ├── Doctor.java
    └── Disease.java
```

## Key Classes

### Main.java
- **Purpose**: Entry point for the application
- **Responsibilities**:
  - Load ontology
  - Create query executor
  - Run demo queries
  - Display formatted output

### HealthcareOntology.java
- **Purpose**: Manages ontology loading
- **Responsibilities**:
  - Load RDF from `healthcare.rdf` file
  - Fallback to programmatic ontology builder
  - Return Jena Model instance

### OntologyBuilder.java
- **Purpose**: Builds ontology programmatically
- **Responsibilities**:
  - Define classes (Patient, Doctor, Disease, etc.)
  - Define properties and relationships
  - Create sample data instances
  - Return complete Model

### HealthcareQueries.java
- **Purpose**: Execute SPARQL queries
- **Responsibilities**:
  - Define query strings
  - Execute against model
  - Process and format results
  - Handle errors

## Query Processing Flow

```
1. User calls query method (e.g., getAllPatients())
   ↓
2. Method constructs SPARQL query string with PREFIX
   ↓
3. QueryFactory.create() parses query
   ↓
4. QueryExecutionFactory creates execution context
   ↓
5. exec.execSelect() runs query against model
   ↓
6. Results are collected in ResultSet
   ↓
7. printResults() formats and displays output
   ↓
8. exec.close() releases resources
```

## Triple Store (RDF Graph)

### Namespace
- **Prefix**: `hc:`
- **Namespace URI**: `http://healthcare.semantic/ontology/`

### Example Triples

```rdf
hc:patient1 rdf:type hc:Patient
hc:patient1 hc:name "John Doe"
hc:patient1 hc:age 45
hc:patient1 hc:hasDiagnosis hc:diagnosis1

hc:disease1 hc:hasSymptom hc:symptom1
hc:disease1 hc:treatedWith hc:medication1

hc:medication1 hc:hasContraindication hc:medication3
```

## Error Handling

```java
try {
    // Load ontology or execute query
} catch (Exception e) {
    System.err.println("Error: " + e.getMessage());
    // Fallback or graceful degradation
}
```

## Extension Points

1. **Add New Classes**: Modify `OntologyBuilder.initializeClasses()`
2. **Add New Properties**: Modify `OntologyBuilder.initializeProperties()`
3. **Add Sample Data**: Modify `OntologyBuilder.addInstances()`
4. **Add Queries**: Add methods to `HealthcareQueries`
5. **Add Tests**: Create test classes in `src/test/java/`

## Performance Considerations

- **In-Memory Model**: All data loaded to RAM for fast access
- **SPARQL Optimization**: Complex queries may take longer with large datasets
- **Memory Usage**: Estimated 1-2 MB for current ontology
- **Scalability**: Current design suitable for up to 100K triples

## Future Enhancements

1. **Persistent Storage**: Use TDB (triple database) instead of in-memory
2. **SPARQL Endpoint**: HTTP interface for remote querying
3. **Inference Engine**: Automatic reasoning and derivation
4. **Validation**: SHACL constraints for data quality
5. **Versioning**: Track changes to ontology and data
6. **Security**: Authentication and authorization

---

**Architecture Last Updated**: June 2026
