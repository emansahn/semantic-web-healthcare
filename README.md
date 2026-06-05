# Semantic Web - Healthcare Domain Project

## Project Overview
A semantic web application modeling a **Healthcare Domain** using RDF, RDFS, and OWL ontologies with SPARQL queries in Java.

### Domain: Healthcare
This project focuses on building a knowledge graph for healthcare with:
- Patients, Doctors, Diseases, Medications, Symptoms
- Medical relationships (diagnoses, prescriptions, treatments)
- Real-world use cases (patient diagnosis, drug interactions, treatment recommendations)

## Project Structure
```
semantic-web-healthcare/
├── README.md
├── pom.xml
├── src/
│   ├── main/java/com/healthcare/semantic/
│   │   ├── Main.java
│   │   ├── ontology/
│   │   │   ├── HealthcareOntology.java
│   │   │   └── OntologyBuilder.java
│   │   └── query/
│   │       └── HealthcareQueries.java
├── ontology/
│   └── healthcare.rdf
└── docs/
    └── PROJECT_REPORT.md
```

## Technologies
- **RDF/RDFS/OWL**: Semantic data representation
- **Apache Jena**: Java RDF framework
- **SPARQL**: Graph querying language
- **Maven**: Build management

## Key Features
✅ Complete Healthcare Ontology (RDF)
✅ SPARQL Queries (Find diagnoses, medications, symptoms)
✅ Real Use Cases (Patient lookup, drug interactions)
✅ Knowledge Graph Visualization
✅ CRUD Operations on healthcare data

## Getting Started
```bash
# 1. Clone the repository
git clone https://github.com/emansahn/semantic-web-healthcare.git
cd semantic-web-healthcare

# 2. Build the project
mvn clean install

# 3. Run the application
mvn exec:java
```

## Deliverables
- ✅ Project Report (docs/PROJECT_REPORT.md)
- ✅ Ontology Files (ontology/healthcare.rdf)
- ✅ SPARQL Queries (src/main/java/.../HealthcareQueries.java)
- ✅ Knowledge Graph Visualization
- ✅ Concrete Use Case Examples

## Quick Demo Output
```
╔════════════════════════════════════════════════════════════╗
║   Semantic Web - Healthcare Domain Project                 ║
║   Using RDF, RDFS, OWL and SPARQL                          ║
╚════════════════════════════════════════════════════════════╝

📂 Loading Healthcare Ontology...
✅ Ontology loaded successfully!

📋 [Query 1] List all patients:
  1. patient=patient1 name=John Doe age=45
  2. patient=patient2 name=Jane Smith age=38

📋 [Query 2] List all doctors and their specialties:
  1. doctor=doctor1 name=Dr. Alice Johnson specialty=Cardiology
  2. doctor=doctor2 name=Dr. Robert Smith specialty=Neurology

✅ All queries executed successfully!
```

## Documentation
- See **[PROJECT_REPORT.md](docs/PROJECT_REPORT.md)** for detailed documentation
- See **[SETUP.md](SETUP.md)** for installation and troubleshooting

---
**Author**: Healthcare Semantic Web Team
**Date**: 2026