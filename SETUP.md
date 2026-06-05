# Setup & Installation Guide

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- Git

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/emansahn/semantic-web-healthcare.git
cd semantic-web-healthcare
```

### 2. Build the Project

```bash
mvn clean install
```

This will download all dependencies and compile the project.

### 3. Run the Application

```bash
mvn exec:java
```

### Expected Output

```
╔════════════════════════════════════════════════════════════╗
║   Semantic Web - Healthcare Domain Project                 ║
║   Using RDF, RDFS, OWL and SPARQL                          ║
╚════════════════════════════════════════════════════════════╝

📂 Loading Healthcare Ontology...
✅ Ontology loaded successfully!

════════════════════════════════════════════════════════════
EXECUTING SPARQL QUERIES
════════════════════════════════════════════════════════════

📋 [Query 1] List all patients:
  1. patient=patient1 name=John Doe age=45
  2. patient=patient2 name=Jane Smith age=38

[... more queries ...]

✅ All queries executed successfully!
```

## Project Structure

```
semantic-web-healthcare/
├── README.md                    # Project overview
├── SETUP.md                     # This file
├── pom.xml                      # Maven configuration
├── .gitignore                   # Git ignore rules
│
├── src/
│   ├── main/java/com/healthcare/semantic/
│   │   ├── Main.java                           # Entry point
│   │   ├── ontology/
│   │   │   ├── HealthcareOntology.java         # Ontology loader
│   │   │   └── OntologyBuilder.java            # Programmatic builder
│   │   └── query/
│   │       └── HealthcareQueries.java          # SPARQL queries
│   │
│   └── test/java/                              # Test classes (optional)
│
├── ontology/
│   └── healthcare.rdf                          # RDF ontology file
│
└── docs/
    └── PROJECT_REPORT.md                       # Detailed report
```

## Troubleshooting

### Issue: "mvn command not found"

**Solution**: Install Maven

```bash
# On macOS (using Homebrew)
brew install maven

# On Ubuntu/Debian
sudo apt-get install maven
```

### Issue: "No Java compiler found"

**Solution**: Ensure Java is installed

```bash
java -version
```

### Issue: Memory error during execution

**Solution**: Increase heap size

```bash
mvn exec:java -Dexec.args="-Xmx512m"
```

## Customization

### Adding More Patients/Doctors

Edit `OntologyBuilder.java` in the `addInstances()` method.

### Adding New Queries

Add methods to `HealthcareQueries.java`.

## Running Tests

```bash
mvn test
```

## IDE Setup

### IntelliJ IDEA

1. Open project: `File > Open > select semantic-web-healthcare`
2. Mark `src/main/java` as Sources Root
3. Run `Main.java`

### Eclipse

1. Import: `File > Import > Maven > Existing Maven Projects`
2. Browse to project directory
3. Run `Main.java`

### VS Code

1. Install Extension: "Extension Pack for Java"
2. Run: `mvn exec:java`

---

**Happy Semantic Web Development! 🚀**