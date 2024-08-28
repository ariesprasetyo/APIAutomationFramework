package GoRest.PUT.Positive;

import GoRest.GETSingleData.Positive.getUserData;
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
import utils.RandomEmailGenerator;

public class updateDataUser {
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
        String schemaGetSingleUser = "src/test/resources/JSON/putDataUser.json";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + dotenv.get("ACCESS_TOKEN"));
        builder.setBasePath("/"+System.getProperty("id"));
        String email = RandomEmailGenerator.generateRandomEmail();
        String name = "aries eko";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(jp.get("name"),name);
        Assert.assertEquals(jp.get("email"),email);
        System.setProperty("email", email);
        Assert.assertTrue(jsonSchemaValidator.matchesJsonSchema(schemaGetSingleUser,res.body().prettyPrint()));
    }
    @Test
    public void validateUpdateData() throws Exception {
        getUserData getUserData = new getUserData();
        getUserData.requestWithValidData();
    }
}
