package GoRest;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {
    @Test
    public void CreateUser() {
        // https://gorest.co.in/public/v2/users
        //  {"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}
        // a3595aa2d2f40a1af2fdc5aff7d7b5e5f6564955bcebc5c21ab3aba805dff801
        int userID=
        given()
                .header("Authorization","Bearer a3595aa2d2f40a1af2fdc5aff7d7b5e5f6564955bcebc5c21ab3aba805dff801")
                .contentType(ContentType.JSON) // gönderilecek data JSON
                .body("{\"name\":\"ismet temur3535\", \"gender\":\"male\", \"email\":\"ismet3535@gmail.com\", \"status\":\"active\"}")
                .log().uri()
                .log().body()
                .when()
                .post("https://gorest.co.in/public/v2/users")

                .then()
                .log().body()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .extract().path("id")
                ;

    }

    @Test
    public void GetUserByID() {

    }

    @Test
    public void UpdateUser() {

    }

    @Test
    public void DeleteUser() {

    }

    @Test
    public void DeleteUserNegative() {

    }
}
