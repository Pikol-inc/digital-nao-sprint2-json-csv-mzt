package com.pikolinc;

import com.pikolinc.records.Publication;
import com.pikolinc.records.Researcher;
import com.pikolinc.records.JournalImpact;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
}