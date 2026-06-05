# Sample SPARQL Query Examples

## Running SPARQL Queries Manually

You can run SPARQL queries against the healthcare ontology using the query editor.

### Example 1: Find All Patients with Their Ages

```sparql
PREFIX hc: <http://healthcare.semantic/ontology/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?patient ?name ?age
WHERE {
    ?patient rdf:type hc:Patient .
    ?patient hc:name ?name .
    ?patient hc:age ?age .
}
```

### Example 2: Find All Medications and Their Contraindications

```sparql
PREFIX hc: <http://healthcare.semantic/ontology/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?medication ?medName ?contraindicated ?contraName
WHERE {
    ?medication rdf:type hc:Medication .
    ?medication rdfs:label ?medName .
    ?medication hc:hasContraindication ?contraindicated .
    ?contraindicated rdfs:label ?contraName .
}
```

### Example 3: Find Complete Treatment Plan for Disease

```sparql
PREFIX hc: <http://healthcare.semantic/ontology/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?disease ?symptom ?medication
WHERE {
    hc:disease1 rdf:type hc:Disease .
    hc:disease1 rdfs:label ?disease .
    hc:disease1 hc:hasSymptom ?symptom .
    hc:disease1 hc:treatedWith ?medication .
    ?symptom rdfs:label ?symptomLabel .
    ?medication rdfs:label ?medicationLabel .
}
ORDER BY ?disease
```

### Example 4: Find All Healthcare Professionals and Their Patients

```sparql
PREFIX hc: <http://healthcare.semantic/ontology/>
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>

SELECT ?doctor ?doctorName ?specialty ?patient ?patientName
WHERE {
    ?doctor rdf:type hc:Doctor .
    ?doctor hc:name ?doctorName .
    ?doctor hc:specialty ?specialty .
    ?prescription hc:prescribedBy ?doctor .
    ?prescription hc:prescribedTo ?patient .
    ?patient hc:name ?patientName .
}
ORDER BY ?doctorName
```

### Example 5: Complex Query - Patient Risk Analysis

```sparql
PREFIX hc: <http://healthcare.semantic/ontology/>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>

SELECT ?patient ?patientName ?medication1 ?med1Label ?medication2 ?med2Label
WHERE {
    # Get patient with multiple medications
    ?patient rdf:type hc:Patient .
    ?patient hc:name ?patientName .
    
    # Get first prescription
    ?prescription1 hc:prescribedTo ?patient .
    ?prescription1 hc:prescriptionMedication ?medication1 .
    ?medication1 rdfs:label ?med1Label .
    
    # Get second prescription
    ?prescription2 hc:prescribedTo ?patient .
    ?prescription2 hc:prescriptionMedication ?medication2 .
    ?medication2 rdfs:label ?med2Label .
    
    # Check for drug interactions
    ?medication1 hc:hasContraindication ?medication2 .
}
ORDER BY ?patientName
```

## Running Queries in Java

To add a new query, edit `src/main/java/com/healthcare/semantic/query/HealthcareQueries.java`:

```java
public void myCustomQuery() {
    String queryStr = PREFIX +
            " SELECT ?result WHERE { " +
            "  ?result rdf:type hc:SomeClass . " +
            " }";
    executeQuery(queryStr);
}
```

Then call it from `Main.java`:

```java
queries.myCustomQuery();
```
