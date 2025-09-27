import com.pikolinc.JsonParser;
import com.pikolinc.records.Publication;
import com.pikolinc.records.Researcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JsonParserTest {

    @Test
    public void testToResearcher() throws Exception {
        // Crear publicación de ejemplo
        JSONObject researcherJson = getJsonObject();

        // Parsear JSON
        Researcher researcher = JsonParser.toResearcher(researcherJson);

        // Verificaciones
        Assert.assertEquals("R001", researcher.researcherId());
        Assert.assertEquals("Alice", researcher.name());
        Assert.assertEquals("Computer Science", researcher.department());

        List<Publication> pubs = researcher.publications();
        Assert.assertEquals(1, pubs.size());

        Publication pub = pubs.get(0);
        Assert.assertEquals("Research Paper 1", pub.title());
        Assert.assertEquals("Journal A", pub.journal());
        Assert.assertNull(pub.conference());
        Assert.assertNull(pub.book());
        Assert.assertEquals(2024, pub.year());
        Assert.assertEquals(10, pub.citations());
        Assert.assertEquals(Double.valueOf(2.5), pub.impactFactor()); // <- Aquí
        Assert.assertEquals("journal", pub.type());
        Assert.assertEquals("10.1234/example", pub.doi());
        Assert.assertNull(pub.isbn());
    }

    private static JSONObject getJsonObject() throws JSONException {
        JSONObject pub1 = new JSONObject();
        pub1.put("title", "Research Paper 1");
        pub1.put("journal", "Journal A");
        pub1.put("year", 2024);
        pub1.put("citations", 10);
        pub1.put("impact_factor", 2.5);
        pub1.put("type", "journal");
        pub1.put("doi", "10.1234/example");

        JSONArray publications = new JSONArray();
        publications.put(pub1);

        // Crear JSON del investigador
        JSONObject researcherJson = new JSONObject();
        researcherJson.put("researcher_id", "R001");
        researcherJson.put("name", "Alice");
        researcherJson.put("department", "Computer Science");
        researcherJson.put("publications", publications);
        return researcherJson;
    }
}
