package com.pikolinc.records;

import java.util.List;

public record Researcher(
        String researcherId,
        String name,
        String department,
        List<Publication> publications
) {}
