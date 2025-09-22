import com.pikolinc.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonReaderTest {
    private JsonReader jsonReader;

    @Before
    public void setUp() {
        jsonReader = new JsonReader();
    }

    @Test
    public void testReadJsonObject() throws IOException, JSONException {
        // Create test JSON file
        String testJson = "{\"name\":\"John\",\"age\":30}";

        Path path = Paths.get("test.json");

        Files.write(path, testJson.getBytes());

        JSONObject result = jsonReader.readJsonObject("test.json");
        Assert.assertEquals("John", result.getString("name"));
        Assert.assertEquals(30, result.getInt("age"));

        // Cleanup
        Files.deleteIfExists(path);
    }

    @Test(expected = IOException.class)
    public void testReadNonExistentFile() throws IOException {
        jsonReader.readJsonObject("nonexistent.json");
    }
}