import com.pikolinc.CsvWriter;


import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CsvWriterTest {
    private CsvWriter csvWriter;

    @Before
    public void setUp() {
        csvWriter = new CsvWriter();
    }

    @Test
    public void testWriteCSV() throws IOException {
        String[] headers = {"Name", "Age"};
        List<String[]> data = Arrays.asList(
                new String[]{"John", "30"},
                new String[]{"Jane", "25"}
        );

        csvWriter.writeCSV("test.csv", headers, data);

        // Verify file was created and has content
        Path path = Paths.get("test.csv");

        assertTrue(Files.exists(path));
        String content = new String(Files.readAllBytes(path));
        assertTrue(content.contains("Name,Age"));
        assertTrue(content.contains("John,30"));

        // Cleanup
        Files.deleteIfExists(path);
    }
}
