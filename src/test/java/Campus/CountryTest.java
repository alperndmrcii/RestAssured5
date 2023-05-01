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

    String CountryID;
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
        Faker faker=new Faker();


        Map<String, String> country = new HashMap<>();
        country.put("name",faker.address().country());
        country.put("code",faker.address().countryCode());
       CountryID=  given()
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

        given()
                .when()

                .then()
        ;
    }

    @Test(dependsOnMethods = "CreateCountryNegative")
    public void UpdateCountry() {

        given()
                .when()

                .then()
        ;
    }

    @Test(dependsOnMethods = "UpdateCountry")
    public void DeleteCountry() {

        given()
                .when()

                .then()
        ;
    }

    @Test(dependsOnMethods = "DeleteCountry")
    public void DeleteCountryNegative() {

        given()
                .when()

                .then()
        ;
    }

}
