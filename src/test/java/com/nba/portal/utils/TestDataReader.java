package com.nba.portal.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestDataReader {
    private static final String TEST_DATA_PATH = "src/test/resources/testdata.json";

    private final JsonNode rootNode;

    public TestDataReader() {
        try {
            rootNode = new ObjectMapper().readTree(new File(TEST_DATA_PATH));
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read test data from " + TEST_DATA_PATH, exception);
        }
    }

    public List<String> getSixersExpectedSlideTitles() {
        List<String> titles = new ArrayList<>();
        JsonNode titleNodes = rootNode.path("sixers").path("expectedSlideTitles");

        for (JsonNode titleNode : titleNodes) {
            titles.add(titleNode.asText());
        }

        return titles;
    }

    public long getSixersExpectedDuration() {
        return rootNode.path("sixers").path("expectedDuration").asLong(5);
    }
}
