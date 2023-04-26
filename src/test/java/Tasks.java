import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Tasks {

    /**
     * Task 2
     * Create a request to https://httpsat.us/203
     * Expect status 203
     * Exptect content type TEXT
     */
    @Test
    public void task2()
    {
        given()


                .when()
                .get("https://httpsat.us/203")


                .then()
                .log().all()

                .statusCode(203)
                .contentType(ContentType.TEXT)

        ;
    }
}
