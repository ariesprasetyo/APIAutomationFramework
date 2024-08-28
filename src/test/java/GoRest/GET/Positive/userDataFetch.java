package GoRest.GET.Positive;

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

public class userDataFetch {
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
        String schemaGetSingleUser = "src/test/resources/JSON/getFetchUser.json";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(jsonSchemaValidator.matchesJsonSchema(schemaGetSingleUser,res.body().prettyPrint()));
    }
    @Test
    public void validGetRequestWithQueryParametersGender() throws Exception {
        String schemaGetSingleUser = "src/test/resources/JSON/getFetchUser.json";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addQueryParam("gender","male");
        builder.setContentType("application/json");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(jsonSchemaValidator.matchesJsonSchema(schemaGetSingleUser,res.body().prettyPrint()));
    }
    @Test
    public void validGetRequestWithQueryParametersPage() throws Exception {
        String schemaGetSingleUser = "src/test/resources/JSON/getFetchUser.json";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.addQueryParam("page","2");
        builder.setContentType("application/json");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertTrue(jsonSchemaValidator.matchesJsonSchema(schemaGetSingleUser,res.body().prettyPrint()));
    }

}
