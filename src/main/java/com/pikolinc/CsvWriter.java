package com.pikolinc;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    /**
     * Writes data to a CSV file with default comma delimiter.
     *
     * @param filePath the path where the CSV file will be created
     * @param headers the column headers for the CSV
     * @param data the data rows to write
     * @throws IOException if file cannot be written
     */
    public void writeCSV(String filePath, String[] headers, List<String[]> data) throws IOException {
        writeCSV(filePath, headers, data, ',');
    }

    /**
     * Writes data to a CSV file with custom delimiter.
     *
     * @param filePath the path where the CSV file will be created
     * @param headers the column headers for the CSV
     * @param data the data rows to write
     * @param delimiter the delimiter character to use
     * @throws IOException if file cannot be written
     */
    public void writeCSV(String filePath, String[] headers, List<String[]> data, char delimiter) throws IOException {
        try (FileWriter writer = new FileWriter(filePath);
             CSVPrinter printer = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.builder()
                             .setDelimiter(delimiter)
                             .setHeader(headers)
                             .get()
             )) {

            for (String[] row : data) {
                printer.printRecord((Object[]) row);
            }
        } catch (IOException e) {
            throw new IOException("Failed to write CSV file: " + filePath, e);
        }
    }
}
