import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class ZippoTest {
    @Test
    public void Test() {
        given()
                // Hazırlık işlemleri : (token,send body, parameters)

                .when()
                // endpoint (url), method

                .then()

        // Assertion, test, data işlemleri
        ;
    }

    @Test
    public void StatusCodeTest() {

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body() // dönen body json datası log.all (her şeyi gösterir)
                .statusCode(200) // dönüş kodu 200 mü ?
                .contentType(ContentType.JSON) // dönen sonuc json mı ?
                .body("country", equalTo("United States"))
        ;
    }

    @Test
    public void StateTest() {
        given()
                .when().
                get("http://api.zippopotam.us/us/90210")
                .then()
                //.log().body()
                //.statusCode(200) // dönüş kodu 200 mü ?
                //.contentType(ContentType.JSON) // dönen sonuc json mı ?
                .body("places[0].state", equalTo("California"))
        ;
    }

    @Test
    public void checkHasItemy() {
        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .log().body()
                .statusCode(200)
                .body("places.'place name'", hasItem("Dörtağaç Köyü"))
                ;
    }

    @Test
    public void bodyArrayHasSizeTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places",hasSize(1))

                ;
    }
    @Test
    public void combiningTest(){
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places",hasSize(1))
                .body("places.state",hasItem("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                ;
    }
    @Test
    public void pathParamTest(){
        given()
                .pathParam("ulke","us")
                .pathParam("postakod",90210)
                .log().uri() // request link


                .when()
                .get("http://api.zippopotam.us/{ulke}/{postakod}")


                .then()
                .statusCode(200)

                ;
    }
}
