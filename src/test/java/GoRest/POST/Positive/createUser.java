package GoRest.POST.Positive;

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

public class createUser {
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
        String schemaGetSingleUser = "src/test/resources/JSON/postNewUser.json";
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + dotenv.get("ACCESS_TOKEN"));
        String email = RandomEmailGenerator.generateRandomEmail();
        System.setProperty("email", email);
        String name = "aries";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().post();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 201);
        Assert.assertEquals(jp.get("name"),name);
        System.setProperty("id", jp.getString("id"));
        System.out.println(System.getProperty("id"));
        Assert.assertTrue(jsonSchemaValidator.matchesJsonSchema(schemaGetSingleUser,res.body().prettyPrint()));
    }
    @Test
    public void verifyUserExists(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer " + dotenv.get("ACCESS_TOKEN"));
        builder.addQueryParam("id",System.getProperty("id"));
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        jp = res.jsonPath();
        System.out.println("Respon Body\n"+res.body().prettyPrint());
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(jp.get("[0].id").toString(),System.getProperty("id"));
    }
}
