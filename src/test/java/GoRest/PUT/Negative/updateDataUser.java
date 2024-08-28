package GoRest.PUT.Negative;

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
import utils.RandomEmailGenerator;

public class updateDataUser {
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
    public void updateUserWithoutToken(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.setBasePath("/"+System.getProperty("id"));
        String email = RandomEmailGenerator.generateRandomEmail();
        String name = "aries";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 404);
        Assert.assertEquals(jp.get("message"),"Resource not found");
    }
    @Test
    public void updateUserWithInvalidToken(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer Test");
        builder.setBasePath("/"+System.getProperty("id"));
        String email = System.getProperty("email");
        String name = "aries";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 401);
        Assert.assertEquals(jp.get("message"),"Invalid token");
    }
    @Test
    public void updateUserWithInvalidEndpoint(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(dotenv.get("BASE_URL")+"s");
        builder.setBasePath("/"+System.getProperty("id"));
        builder.setContentType("application/json");
        builder.addHeader("Authorization", "Bearer "+dotenv.get("ACCESS_TOKEN"));
        String email = RandomEmailGenerator.generateRandomEmail();
        String name = "aries";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 404);
    }
    @Test
    public void createUserWithDuplicateUniqueKey(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.setBasePath("/"+System.getProperty("id"));
        builder.addHeader("Authorization", "Bearer "+dotenv.get("ACCESS_TOKEN"));
        String name = "aries";
        String email = "delameta@gmail.com";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 422);
    }
    @Test
    public void createUserWithInvalidFormat(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.setBasePath("/"+System.getProperty("id"));
        builder.addHeader("Authorization", "Bearer "+dotenv.get("ACCESS_TOKEN"));
        String name = "aries";
        String email = "emailTest";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\"male\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 422);
    }
    @Test
    public void createUserWithUnsupportedValue(){
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setContentType("application/json");
        builder.setBasePath("/"+System.getProperty("id"));
        builder.addHeader("Authorization", "Bearer "+dotenv.get("ACCESS_TOKEN"));
        String name = "aries";
        String email = "emailTest@gmail.com";
        String gender = "Test";
        builder.setBody("{\"name\":\""+name+"\",\"gender\":\""+gender+"\",\"email\":\""+email+"\",\"status\":\"active\"}");
        requestSpec = builder.build();
        requestSpec = RestAssured.given().spec(requestSpec);
        requestSpec.log().all();
        res = requestSpec.when().put();
        jp = res.jsonPath();
        Assert.assertEquals(res.statusCode(), 422);
    }
}
