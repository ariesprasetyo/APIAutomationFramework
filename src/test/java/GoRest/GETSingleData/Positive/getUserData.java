package GoRest.GETSingleData.Positive;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.JsonSchemaValidator;

public class getUserData {
    JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();
    Dotenv dotenv = Dotenv.load();
    private Response res = null;
    private JsonPath jp = null;
    private RequestSpecification requestSpec;
    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = dotenv.get("BASE_URL");
    }
    @AfterTest
    public void tearDown() {RestAssured.reset();}

    @Test
    public void requestWithValidData() throws Exception {
        String schema = "src/test/resources/JSON/getSingleUser.json";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + dotenv.get("ACCESS_TOKEN"));
        builder.addQueryParam("id",System.getProperty("id"));
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(jp.get("[0].id").toString(),System.getProperty("id"));
        Assert.assertEquals(jp.get("[0].email").toString(),System.getProperty("email"));
        Assert.assertTrue(jsonSchemaValidator.matchesJsonSchema(schema,res.body().prettyPrint()));
    }
}
