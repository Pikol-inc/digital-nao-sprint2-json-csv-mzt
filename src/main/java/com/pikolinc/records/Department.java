package com.pikolinc.records;

public record Department(
        String name,
        int researchers,
        int publications,
        int totalCitations,
        int hIndex,
        double averageImpactFactor
) {}
