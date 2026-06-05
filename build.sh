#!/bin/bash
# Build and run the Healthcare Semantic Web project

set -e  # Exit on error

echo "═══════════════════════════════════════════════════════════"
echo "  Healthcare Semantic Web - Build Script"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven not found. Please install Maven first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 11 or higher."
    exit 1
fi

echo "✅ Prerequisites found"
echo ""

# Clean and build
echo "📦 Building project..."
mvn clean compile -q
echo "✅ Build successful"
echo ""

# Run tests (if any exist)
if [ -d "src/test" ]; then
    echo "🧪 Running tests..."
    mvn test -q
    echo "✅ Tests passed"
    echo ""
fi

# Run application
echo "🚀 Running application..."
echo ""
mvn exec:java

echo ""
echo "═══════════════════════════════════════════════════════════"
echo "✅ Execution completed"
echo "═══════════════════════════════════════════════════════════"
