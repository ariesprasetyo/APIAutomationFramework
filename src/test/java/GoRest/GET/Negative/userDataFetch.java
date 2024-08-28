package GoRest.GET.Negative;

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

public class userDataFetch {
    Dotenv dotenv = Dotenv.load();
    private Response res = null;
    private JsonPath jp = null;
    private RequestSpecification requestSpec;
    @BeforeTest
    public void setUp() {
        RestAssured.baseURI = dotenv.get("BASE_URL")+ "s";
    }
    @AfterTest
    public void tearDown() {RestAssured.reset();}

    @Test
    public void getRequestWithInvalidEndpoint(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().get();
        Assert.assertEquals(res.statusCode(), 404);
    }
}
