import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PathAndJsonPath {
    @Test
    public void  extractingPath(){

        //"post code": "90210",

      String postcode=  given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("'post code'")
        ;
        System.out.println("postcode = " + postcode);
    }
    @Test
    public void  extractingJsonPath(){

        //"post code": "90210",

        int postcode=  given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().jsonPath().getInt("'post code'")
                ;
        System.out.println("postcode = " + postcode);
    }
}
