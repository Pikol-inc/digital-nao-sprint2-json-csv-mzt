Sprint 2: Back End in Java for Information Processing

NAO IDs: 
3320
3316
3304
3318

## HOW TO USE
Specify the args such as
--delimiter=","
--input-researchers="input/researcher_publications.json"
--input-departments="input/department_statistics.json"
--input-journals="input/journal_impact_data.json"
--output-researchers="output/researcher_publications_output.csv"
--output-departments="output/department_statistics_output.csv"
--output-journals="output/journal_impact_output.csv"

Example run command:
```
mvn compile exec:java

// with flags

mvn compile exec:java '-Dexec.args="--delimiter=;, --input-researchers=input/researcher_publications.json --input-departments=input/department_statistics.json --input-journals=input/journal_impact_data.json --output-researchers=output/scientific_publications_report.csv --output-departments=output/department_summary_report.csv --output-journals=output/journal_impact_analysis.csv"'
```

## HOW TO EXECUTE TESTS:
### Requirement: 
- Maven installed

## Execute command
> mvn test
