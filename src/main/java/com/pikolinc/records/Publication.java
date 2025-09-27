package com.pikolinc.records;

public record Publication(
        String title,
        String journal,
        String conference,
        String book,
        int year,
        int citations,
        Double impactFactor,
        String type,
        String doi,
        String isbn
) {}
