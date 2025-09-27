package com.pikolinc;

import com.pikolinc.records.Publication;
import com.pikolinc.records.Researcher;
import com.pikolinc.records.JournalImpact;
import com.pikolinc.records.Department;
import com.pikolinc.records.DepartmentReport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("🎓 SCIENTOMETRICS DEPARTMENT - AUTOMATION SYSTEM");
        System.out.println("================================================");
        System.out.println("Developer: Pikolinc");
        System.out.println("Purpose: Bi-monthly Scientific Production Report Generation\n");

        // Parse command line arguments for custom delimiter
        Config config = Config.fromArgs(args);

        JsonReader jsonReader = new JsonReader();
        CsvWriter csvWriter = new CsvWriter();

        try {
            // Phase 1: Researcher publications
            System.out.println("=== PHASE 1: Processing Researcher Publications ===");
            processResearcherPublications(jsonReader, csvWriter, config);

            // Phase 2: Department statistics
            System.out.println("\n=== PHASE 2: Processing Department Statistics ===");
            processDepartmentStatistics(jsonReader, csvWriter, config);

            // Phase 3: Journal impact factors
            System.out.println("\n=== PHASE 3: Processing Journal Impact Data ===");
            processJournalImpactData(jsonReader, csvWriter, config);

            System.out.println("\n✅ AUTOMATION COMPLETE!");
            System.out.println("📊 All scientific data has been successfully processed and integrated.");
            System.out.println("📋 Bi-monthly report files are ready for Director Érika's review.");

        } catch (IOException | JSONException e) {
            System.err.println("❌ SYSTEM ERROR: " + e.getMessage());
            System.err.println("🔧 Please check data integrity and file permissions.");
            e.printStackTrace();
        }
    }

    private static void processResearcherPublications(JsonReader jsonReader, CsvWriter csvWriter, Config config)
            throws IOException, JSONException {

        JSONArray researchersArray = jsonReader.readJsonArray(config.inputResearchers);
        //JSONArray researchersArray = jsonReader.readJsonArray("researcher_publications.json");
        System.out.println("📖 Processing " + researchersArray.length() + " researchers");

        String[] headers = {"Researcher_ID", "Researcher_Name", "Department", "Publication_Title",
                "Venue", "Year", "Citations", "Impact_Factor", "Type", "DOI/ISBN"};

        List<String[]> data = new ArrayList<>();

        // Using JsonParser to convert JSON objects to Researcher records
        for (int i = 0; i < researchersArray.length(); i++) {
            JSONObject researcherObj = researchersArray.getJSONObject(i);
            Researcher researcher = JsonParser.toResearcher(researcherObj);

            for (Publication pub : researcher.publications()) {
                String venue = Stream.of(pub.journal(), pub.conference(), pub.book())
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse("N/A");

                String identifier = Stream.of(pub.doi(), pub.isbn())
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElse("N/A");

                data.add(new String[]{
                        researcher.researcherId(),
                        researcher.name(),
                        researcher.department(),
                        pub.title(),
                        venue,
                        String.valueOf(pub.year()),
                        String.valueOf(pub.citations()),
                        Optional.ofNullable(pub.impactFactor())
                                .map(String::valueOf)
                                .orElse("N/A"),
                        pub.type(),
                        identifier
                });
            }
        }

//        csvWriter.writeCSV("scientific_publications_report.csv", headers, data, config.delimiter);
        csvWriter.writeCSV(config.outputResearchers, headers, data, config.delimiter);
        System.out.println("💾 Generated: " + config.outputResearchers);
        System.out.println("📊 Total publications processed: " + data.size());
    }

    private static void processJournalImpactData(JsonReader jsonReader, CsvWriter csvWriter, Config config)
            throws IOException, JSONException {

//        JSONArray journalsArray = jsonReader.readJsonArray("journal_impact_data.json");
        JSONArray journalsArray = jsonReader.readJsonArray(config.inputJournals);
        System.out.println("📖 Processing " + journalsArray.length() + " journals for impact analysis");

        List<JournalImpact> journals = new ArrayList<>();

        // Using JsonParser to convert JSON objects to JournalImpact records
        for (int i = 0; i < journalsArray.length(); i++) {
            JSONObject journalObj = journalsArray.getJSONObject(i);
            journals.add(JsonParser.toJournalImpact(journalObj));
        }

        String[] journalHeaders = {"Journal_Name", "ISSN", "Publisher", "Impact_Factor_2024",
                "Impact_Factor_2023", "Quartile", "Subject_Area",
                "University_Publications", "Citations_Received"};
        List<String[]> journalData = new ArrayList<>();

        for (JournalImpact journal : journals) {
            journalData.add(new String[]{
                    journal.journalName(),
                    journal.issn(),
                    journal.publisher(),
                    String.valueOf(journal.impactFactor2024()),
                    String.valueOf(journal.impactFactor2023()),
                    journal.quartile(),
                    journal.subjectArea(),
                    String.valueOf(journal.universityPublications()),
                    String.valueOf(journal.totalCitationsReceived())
            });
        }

//        csvWriter.writeCSV("journal_impact_analysis.csv", journalHeaders, journalData, config.delimiter);
        csvWriter.writeCSV(config.outputJournals, journalHeaders, journalData, config.delimiter);
        System.out.println("💾 Generated: " + config.outputJournals);
        System.out.println("📈 Journal impact analysis complete for Director Érika's review");
    }

    private static void processDepartmentStatistics(JsonReader jsonReader, CsvWriter csvWriter, Config config)
            throws IOException, JSONException {

//        JSONObject statsObj = jsonReader.readJsonObject("department_statistics.json");
        JSONObject statsObj = jsonReader.readJsonObject(config.inputDepartments);

        // Using JsonParser to convert JSON object to DepartmentReport record
        DepartmentReport departmentReport = JsonParser.toDepartmentReport(statsObj);

        System.out.println("📖 Processing department statistics for: " + departmentReport.reportPeriod());

        String[] deptHeaders = {"Department", "Researchers", "Publications", "Total_Citations",
                "H_Index", "Avg_Impact_Factor"};
        List<String[]> deptData = new ArrayList<>();

        for (Department dept : departmentReport.departments()) {
            deptData.add(new String[]{
                    dept.name(),
                    String.valueOf(dept.researchers()),
                    String.valueOf(dept.publications()),
                    String.valueOf(dept.totalCitations()),
                    String.valueOf(dept.hIndex()),
                    String.valueOf(dept.averageImpactFactor())
            });
        }

//        csvWriter.writeCSV("department_summary_report.csv", deptHeaders, deptData, config.delimiter);
        csvWriter.writeCSV(config.outputDepartments, deptHeaders, deptData, config.delimiter);
        System.out.println("💾 Generated: " + config.outputDepartments);
        System.out.println("⏱️ Time saved with automation: " + departmentReport.automationTimeSaved());
        System.out.println("👥 Total researchers across all departments: " + departmentReport.totalResearchers());
    }
}