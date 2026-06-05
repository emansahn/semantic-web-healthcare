# API & Integration Guide

## Using HealthcareQueries Programmatically

### Basic Usage

```java
// Load ontology
HealthcareOntology ontology = new HealthcareOntology();
Model model = ontology.loadOntology();

// Create query executor
HealthcareQueries queries = new HealthcareQueries(model);

// Execute queries
queries.getAllPatients();
queries.getAllDoctors();
queries.getPatientDiagnosis("patient1");
```

## Embedding in Other Applications

### Maven Dependency

If you package this as a library:

```xml
<dependency>
    <groupId>com.healthcare</groupId>
    <artifactId>semantic-web-healthcare</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

### Usage Example

```java
import com.healthcare.semantic.ontology.HealthcareOntology;
import com.healthcare.semantic.query.HealthcareQueries;
import org.apache.jena.rdf.model.Model;

public class MyHealthcareApp {
    
    private HealthcareQueries queries;
    
    public MyHealthcareApp() {
        HealthcareOntology ontology = new HealthcareOntology();
        Model model = ontology.loadOntology();
        this.queries = new HealthcareQueries(model);
    }
    
    public void findPatient(String patientId) {
        queries.getPatientPrescriptions(patientId);
    }
}
```

## REST API Example

### Spring Boot Integration

```java
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/healthcare")
public class HealthcareController {
    
    private HealthcareQueries queries;
    
    @GetMapping("/patients")
    public void getAllPatients() {
        queries.getAllPatients();
    }
    
    @GetMapping("/doctors")
    public void getAllDoctors() {
        queries.getAllDoctors();
    }
    
    @GetMapping("/patient/{id}/diagnosis")
    public void getPatientDiagnosis(@PathVariable String id) {
        queries.getPatientDiagnosis(id);
    }
}
```

## Data Import/Export

### Export to RDF

```java
HealthcareOntology ontology = new HealthcareOntology();
Model model = ontology.loadOntology();

// Write to file
try (OutputStream out = new FileOutputStream("export.rdf")) {
    model.write(out, "RDF/XML");
}
```

### Export to JSON-LD

```java
try (OutputStream out = new FileOutputStream("export.jsonld")) {
    model.write(out, "JSON-LD");
}
```

### Export to Turtle

```java
try (OutputStream out = new FileOutputStream("export.ttl")) {
    model.write(out, "TURTLE");
}
```

## Custom Query Builder

### Helper Method for Dynamic Queries

```java
public void customQuery(String sparqlQuery) {
    Query query = QueryFactory.create(sparqlQuery);
    QueryExecution exec = QueryExecutionFactory.create(query, model);
    
    if (query.isSelectType()) {
        ResultSet results = exec.execSelect();
        while (results.hasNext()) {
            QuerySolution solution = results.nextSolution();
            // Process solution
        }
    }
    
    exec.close();
}
```

## Event Listeners

```java
public interface QueryListener {
    void onQueryStart(String queryName);
    void onQueryComplete(String queryName, int resultCount);
    void onQueryError(String queryName, Exception e);
}
```

## Caching Results

```java
public class CachedHealthcareQueries {
    private Map<String, ResultSet> cache = new HashMap<>();
    private HealthcareQueries queries;
    
    public ResultSet getPatients() {
        String key = "all_patients";
        if (!cache.containsKey(key)) {
            // Execute and cache
            cache.put(key, executePatientQuery());
        }
        return cache.get(key);
    }
}
```

## Performance Optimization

### Use SPARQL LIMIT for Large Results

```sparql
SELECT ?patient ?name LIMIT 100
WHERE { ?patient rdf:type hc:Patient . }
```

### Use FILTER for Conditions

```sparql
SELECT ?patient ?name ?age
WHERE {
    ?patient rdf:type hc:Patient .
    ?patient hc:age ?age .
    FILTER (?age > 50)
}
```

### Use ASK for Boolean Queries

```sparql
ASK WHERE {
    hc:patient1 hc:hasDiagnosis ?diagnosis .
}
```

## Error Handling Best Practices

```java
try {
    queries.getAllPatients();
} catch (QueryException e) {
    logger.error("Query execution failed", e);
    // Retry or fallback
} catch (Exception e) {
    logger.error("Unexpected error", e);
    // Handle gracefully
}
```

---

**API Version**: 1.0
