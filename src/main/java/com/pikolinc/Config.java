package com.pikolinc;

public class Config {
    public String inputResearchers = "researcher_publications.json";
    public String inputDepartments = "department_statistics.json";
    public String inputJournals = "journal_impact_data.json";

    public String outputResearchers = "scientific_publications_report.csv";
    public String outputDepartments = "department_summary_report.csv";
    public String outputJournals = "journal_impact_analysis.csv";

    public char delimiter = ',';

    /**
     * Factory method to create a Config object from CLI args.
     *
     * @param args Command line arguments
     * @return Config instance populated with CLI options
     */
    public static Config fromArgs(String[] args) {
        Config config = new Config();

        for (String arg : args) {
            if (arg.startsWith("--delimiter=")) {
                config.delimiter = parseDelimiter(arg);
            } else if (arg.startsWith("--input-researchers=")) {
                config.inputResearchers = arg.substring("--input-researchers=".length());
            } else if (arg.startsWith("--input-departments=")) {
                config.inputDepartments = arg.substring("--input-departments=".length());
            } else if (arg.startsWith("--input-journals=")) {
                config.inputJournals = arg.substring("--input-journals=".length());
            } else if (arg.startsWith("--output-researchers=")) {
                config.outputResearchers = arg.substring("--output-researchers=".length());
            } else if (arg.startsWith("--output-departments=")) {
                config.outputDepartments = arg.substring("--output-departments=".length());
            } else if (arg.startsWith("--output-journals=")) {
                config.outputJournals = arg.substring("--output-journals=".length());
            } else if ("--help".equals(arg) || "-h".equals(arg)) {
                printUsageHelp();
                System.exit(0);
            }
        }

        // If no delimiter was explicitly set
        if (config.delimiter == ',') {
            System.out.println("📋 Using default delimiter: ',' (comma)");
        }

        return config;
    }

    /**
     * Parses delimiter from CLI arg (--delimiter=...).
     */
    private static char parseDelimiter(String arg) {
        String customDelim = arg.substring("--delimiter=".length());
        if ("\\t".equals(customDelim) || "tab".equalsIgnoreCase(customDelim)) {
            return '\t';
        } else if ("space".equalsIgnoreCase(customDelim)) {
            return ' ';
        } else if (customDelim.length() == 1) {
            return customDelim.charAt(0);
        } else if (customDelim.length() > 1) {
            System.err.println("⚠️  Warning: Delimiter must be a single character. Using first character: '" + customDelim.charAt(0) + "'");
            return customDelim.charAt(0);
        }

        return ','; // fallback
    }

    /**
     * Prints usage help information for command line arguments.
     */
    private static void printUsageHelp() {
        System.out.println("\n📖 USAGE:");
        System.out.println("java com.kaserola4.Main [OPTIONS]");
        System.out.println("\nOPTIONS:");
        System.out.println("  --delimiter=CHAR          Set custom delimiter for CSV files (default: comma)");
        System.out.println("                            Examples:");
        System.out.println("                              --delimiter=;     (semicolon)");
        System.out.println("                              --delimiter=|     (pipe)");
        System.out.println("                              --delimiter=\\t    (tab)");
        System.out.println("                              --delimiter=space (space)");
        System.out.println("  --input-researchers=FILE  Path to researchers JSON input");
        System.out.println("  --input-departments=FILE  Path to departments JSON input");
        System.out.println("  --input-journals=FILE     Path to journals JSON input");
        System.out.println("  --output-researchers=FILE Path to researchers CSV output");
        System.out.println("  --output-departments=FILE Path to departments CSV output");
        System.out.println("  --output-journals=FILE    Path to journals CSV output");
        System.out.println("  --help, -h                Show this help message");
        System.out.println("\nEXAMPLES:");
        System.out.println("  java com.kaserola4.Main");
        System.out.println("  java com.kaserola4.Main --delimiter=;");
        System.out.println("  java com.kaserola4.Main --input-researchers=data/r.json --output-researchers=out/r.csv");
    }
}
