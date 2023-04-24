import org.testng.annotations.Test;

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
                ;
    }

}
