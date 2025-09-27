package com.kaserola4.records;

import java.util.List;

public record DepartmentReport(
        String reportPeriod,
        int totalResearchers,
        List<Department> departments,
        String reportGeneratedBy,
        String automationTimeSaved,
        String previousManualProcessTime
) {}

