import com.pikolinc.CsvWriter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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

        Assert.assertTrue(Files.exists(path));
        String content = new String(Files.readAllBytes(path));
        Assert.assertTrue(content.contains("Name,Age"));
        Assert.assertTrue(content.contains("John,30"));

        // Cleanup
        Files.deleteIfExists(path);
    }

}