@echo off
REM Build and run the Healthcare Semantic Web project (Windows)

echo.
echo ===============================================================
echo   Healthcare Semantic Web - Build Script (Windows)
echo ===============================================================
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Maven not found. Please install Maven first.
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo X Java not found. Please install Java 11 or higher.
    exit /b 1
)

echo OK Prerequisites found
echo.

REM Clean and build
echo Package Building project...
mvn clean compile -q
echo OK Build successful
echo.

REM Run application
echo Rocket Running application...
echo.
mvn exec:java

echo.
echo ===============================================================
echo OK Execution completed
echo ===============================================================
