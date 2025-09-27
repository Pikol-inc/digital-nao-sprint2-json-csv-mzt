package com.pikolinc;

import com.pikolinc.records.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    public static Researcher toResearcher(JSONObject obj) throws JSONException {
        String researcherId = obj.getString("researcher_id");
        String name = obj.getString("name");
        String department = obj.getString("department");

        JSONArray pubsArr = obj.getJSONArray("publications");

        List<Publication> pubs = new ArrayList<>();

        for (int i = 0; i < pubsArr.length(); i++) {
            pubs.add(toPublication(pubsArr.getJSONObject(i)));
        }

        return new Researcher(researcherId, name, department, pubs);
    }

    private static Publication toPublication(JSONObject obj) throws JSONException {
        return new Publication(
                obj.getString("title"),
                obj.has("journal") ? obj.getString("journal") : null,
                obj.has("conference") ? obj.getString("conference") : null,
                obj.has("book") ? obj.getString("book") : null,
                obj.getInt("year"),
                obj.getInt("citations"),
                obj.has("impact_factor") ? obj.getDouble("impact_factor") : null, // <-- corregido
                obj.getString("type"),
                obj.has("doi") ? obj.getString("doi") : null,
                obj.has("isbn") ? obj.getString("isbn") : null
        );
    }

    // ---- DepartmentReport ----
    public static DepartmentReport toDepartmentReport(JSONObject obj) throws JSONException {
        String reportPeriod = obj.getString("report_period");
        int totalResearchers = obj.getInt("total_researchers");
        String generatedBy = obj.getString("report_generated_by");
        String automationTime = obj.getString("automation_time_saved");
        String manualTime = obj.getString("previous_manual_process_time");

        JSONArray deptsArr = obj.getJSONArray("departments");
        List<Department> depts = new ArrayList<>();
        for (int i = 0; i < deptsArr.length(); i++) {
            JSONObject d = deptsArr.getJSONObject(i);
            depts.add(new Department(
                    d.getString("name"),
                    d.getInt("researchers"),
                    d.getInt("publications"),
                    d.getInt("total_citations"),
                    d.getInt("h_index"),
                    d.getDouble("average_impact_factor")
            ));
        }

        return new DepartmentReport(reportPeriod, totalResearchers, depts, generatedBy, automationTime, manualTime);
    }

    // ---- JournalImpact ----
    public static JournalImpact toJournalImpact(JSONObject obj) throws JSONException {
        return new JournalImpact(
                obj.getString("journal_name"),
                obj.getString("issn"),
                obj.getString("publisher"),
                obj.getDouble("impact_factor_2024"),
                obj.getDouble("impact_factor_2023"),
                obj.getString("quartile"),
                obj.getString("subject_area"),
                obj.getInt("university_publications"),
                obj.getInt("total_citations_received")
        );
    }
}
