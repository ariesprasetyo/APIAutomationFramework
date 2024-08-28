package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.File;

public class JsonSchemaValidator {
    public static boolean matchesJsonSchema(String schemaString, String jsonData) throws Exception {
        JsonNode schemaNode = JsonLoader.fromFile(new File(schemaString));
        JsonNode dataNode = JsonLoader.fromString(jsonData);
        JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
        JsonSchema schema = factory.getJsonSchema(schemaNode);
        ProcessingReport report = schema.validate(dataNode);
        return report.isSuccess();
    }
}
