package GoRest.GETSingleData.Negative;

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

public class getUserData {
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
    public void requestWithInvalidEndpoint() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.setBaseUri(dotenv.get("BASE_URL")+"s");
        builder.addHeader("Authorization", "Bearer " + dotenv.get("ACCESS_TOKEN"));
        builder.addQueryParam("id","7363995");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 404);
    }
    @Test
    public void requestWithInvalidToken() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer test");
        builder.addQueryParam("id","7363995");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 401);
        Assert.assertEquals(jp.get("message"), "Invalid token");
    }
}
