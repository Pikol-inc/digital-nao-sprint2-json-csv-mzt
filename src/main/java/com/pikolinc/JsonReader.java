package com.pikolinc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
    /**
     * Reads and parses a JSON file into a JSONObject.
     *
     * @param filePath the path to the JSON file
     * @return JSONObject containing the parsed JSON data
     * @throws IOException if file cannot be read
     */
    public JSONObject readJsonObject(String filePath) throws IOException {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONObject(content);
        } catch (IOException | JSONException e) {
            throw new IOException("Failed to read file: " + filePath, e);
        }
    }

    /**
     * Reads and parses a JSON file into a JSONArray.
     *
     * @param filePath the path to the JSON file containing an array
     * @return JSONArray containing the parsed JSON data
     * @throws IOException if file cannot be read
     */
    public JSONArray readJsonArray(String filePath) throws IOException {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            return new JSONArray(content);
        } catch (IOException | JSONException e) {
            throw new IOException("Failed to read file: " + filePath, e);
        }
    }
}