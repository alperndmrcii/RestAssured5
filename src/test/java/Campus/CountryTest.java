package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CountryTest {
    Faker faker = new Faker();
    String CountryID;
    String CountryName;

    RequestSpecification reqSpec;

    @BeforeClass
    public void Login() {
        baseURI = "https://test.mersys.io";
        Map<String, String> userCredential = new HashMap<>();
        userCredential.put("username", "turkeyts");
        userCredential.put("password", "TechnoStudy123");
        userCredential.put("rememberMe", "true");
        Cookies cookies =
                given()
                        .contentType(ContentType.JSON)
                        .body(userCredential)
                        .when()
                        .post("/auth/login")

                        .then()
                        //  .log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();
        reqSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }

    @Test
    public void CreateCountry() {


        Map<String, String> country = new HashMap<>();
        CountryName = faker.address().country() + faker.number().digits(5);
        country.put("name", CountryName);
        country.put("code", faker.address().countryCode() + faker.number().digits(5));


        CountryID = given()
                .spec(reqSpec)
                .body(country)
                .log().body()
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;
        System.out.println("CountryID = " + CountryID);
    }

    @Test(dependsOnMethods = "CreateCountry")
    public void CreateCountryNegative() {
        Map<String, String> country = new HashMap<>();
        country.put("name", CountryName);
        country.put("code", faker.address().countryCode() + faker.number().digits(5));
        given()
                .spec(reqSpec)
                .body(country)
                .log().body()
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsString("already"))
        ;


    }

    @Test(dependsOnMethods = "CreateCountryNegative")
    public void UpdateCountry() {

        Map<String,String> country=new HashMap<>();
        country.put("id",CountryID);

        CountryName="Alperen ülkesi"+faker.number().digits(7);
        country.put("name",CountryName);
        country.put("code",faker.address().countryCode()+faker.number().digits(5));

        given()
                .spec(reqSpec)
                .body(country) // giden body
                //.log().body() // giden body yi log olarak göster

                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body() // gelen body yi log olarak göster
                .statusCode(200)
                .body("name", equalTo(CountryName))
        ;
    }

    @Test(dependsOnMethods = "UpdateCountry")
    public void DeleteCountry() {
        given()
                .spec(reqSpec)
                .pathParam("CountryID",CountryID)
                .when()
                .delete("/school-service/api/countries/{CountryID}")
                .then()
                .log().body()
                .statusCode(200)
        ;

    }

    @Test(dependsOnMethods = "DeleteCountry")
    public void DeleteCountryNegative() {
        given()
                .spec(reqSpec)
                .pathParam("CountryID",CountryID)
                .when()
                .delete("/school-service/api/countries/{CountryID}")
                .then()
                .log().body()
                .statusCode(400)
                .body("message",equalTo("Country not found"))
        ;

    }

}
