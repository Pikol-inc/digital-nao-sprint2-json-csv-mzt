package com.pikolinc.records;

public record JournalImpact(
        String journalName,
        String issn,
        String publisher,
        double impactFactor2024,
        double impactFactor2023,
        String quartile,
        String subjectArea,
        int universityPublications,
        int totalCitationsReceived
) {}
