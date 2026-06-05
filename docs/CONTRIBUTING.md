# Contributing Guide

## How to Extend the Project

### 1. Adding New Medical Concepts

To add a new class to the ontology, edit `OntologyBuilder.java`:

```java
// In initializeClasses() method
Resource Hospital = model.createResource(NS + "Hospital");
Hospital.addProperty(RDF.type, RDFS.Class)
        .addProperty(RDFS.label, "Hospital");
```

### 2. Adding New Relationships

Add properties in `initializeProperties()`:

```java
Property locatedIn = model.createProperty(NS + "locatedIn");
locatedIn.addProperty(RDF.type, RDF.Property)
        .addProperty(RDFS.label, "locatedIn")
        .addProperty(RDFS.domain, Hospital);
```

### 3. Adding Sample Data

In `addInstances()` method:

```java
Resource hospital1 = model.createResource(NS + "hospital1");
hospital1.addProperty(RDF.type, Hospital)
        .addProperty(name, "City Medical Center")
        .addProperty(locatedIn, "New York");
```

### 4. Adding New SPARQL Queries

In `HealthcareQueries.java`:

```java
public void getHospitalsByCity(String city) {
    String queryStr = PREFIX +
            " SELECT ?hospital ?name WHERE { " +
            "  ?hospital rdf:type hc:Hospital . " +
            "  ?hospital hc:name ?name . " +
            "  ?hospital hc:locatedIn \"" + city + "\" . " +
            " }";
    executeQuery(queryStr);
}
```

### 5. Adding Tests

Create `src/test/java/com/healthcare/semantic/HealthcareTest.java`:

```java
import org.junit.Test;
import static org.junit.Assert.*;

public class HealthcareTest {
    @Test
    public void testOntologyLoads() {
        HealthcareOntology ontology = new HealthcareOntology();
        Model model = ontology.loadOntology();
        assertTrue(model.size() > 0);
    }
}
```

Run with: `mvn test`

## Project Structure Guidelines

```
src/main/java/com/healthcare/semantic/
├── Main.java                    # Entry point
├── ontology/
│   ├── HealthcareOntology.java  # Load RDF from file
│   └── OntologyBuilder.java     # Programmatic ontology
├── query/
│   └── HealthcareQueries.java   # All SPARQL queries
└── model/                        # (Optional) Data models
    ├── Patient.java
    ├── Doctor.java
    └── Disease.java
```

## Code Standards

1. **Comments**: Use JavaDoc for all public methods
2. **Naming**: Use camelCase for variables, PascalCase for classes
3. **SPARQL**: Use PREFIX definitions at the top
4. **Error Handling**: Catch and log exceptions properly

## Testing Checklist

Before committing:

- [ ] Code compiles: `mvn clean compile`
- [ ] Tests pass: `mvn test`
- [ ] Application runs: `mvn exec:java`
- [ ] No compiler warnings
- [ ] Documentation updated

## Common Issues

### Issue: Query returns no results

**Solution**: Check that:
1. Property names match exactly (case-sensitive)
2. URIs are correct
3. Data instances exist in ontology

### Issue: Memory error with large datasets

**Solution**: Increase heap size in pom.xml or command line:

```bash
mvn exec:java -Dexec.args="-Xmx1024m"
```

### Issue: Ontology file not found

**Solution**: Place `healthcare.rdf` in `src/main/resources/` folder.

## Performance Tips

1. **Use SPARQL FILTER** for complex conditions
2. **Add LIMIT** to large result sets
3. **Index frequent properties** in ontology
4. **Cache query results** for repeated queries

## Resources

- [Apache Jena Documentation](https://jena.apache.org/)
- [SPARQL Query Language](https://www.w3.org/TR/sparql11-query/)
- [RDF/OWL Primer](https://www.w3.org/TR/rdf-primer/)

---

**Happy Contributing! 🚀**
