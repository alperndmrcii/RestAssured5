package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GoRestUsersTests {
    Faker randomUretici = new Faker();
    int userID;

    RequestSpecification  requestSpecification;
    @BeforeClass
    public void setup(){
        requestSpecification=new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer a3595aa2d2f40a1af2fdc5aff7d7b5e5f6564955bcebc5c21ab3aba805dff801")
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test(enabled = false)
    public void CreateUserJson() {
        // https://gorest.co.in/public/v2/users
        //  {"name":"{{$randomFullName}}", "gender":"male", "email":"{{$randomEmail}}", "status":"active"}
        // a3595aa2d2f40a1af2fdc5aff7d7b5e5f6564955bcebc5c21ab3aba805dff801

        String rndFullname = randomUretici.name().fullName();
        String rndEmail = randomUretici.internet().emailAddress();

        userID =
                given()
                        .spec(requestSpecification)
                        .body("{\"name\":\"" + rndFullname + "\", \"gender\":\"male\", \"email\":\"" + rndEmail + "\", \"status\":\"active\"}")
                        //.log().uri()
                        //.log().body()
                        .when()
                        .post("https://gorest.co.in/public/v2/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;

    }

    @Test(dependsOnMethods = "CreateUserMap")
    public void GetUserByID() {
        given()
                .spec(requestSpecification)

                .when()
                .get("https://gorest.co.in/public/v2/users/"+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))


                ;

    }
    @Test
    public void CreateUserMap() {

        String rndFullname = randomUretici.name().fullName();
        String rndEmail = randomUretici.internet().emailAddress();

        Map<String,Object> newUser=new HashMap<>();
        newUser.put("name",rndFullname);
        newUser.put("gender","male");
        newUser.put("email",rndEmail);
        newUser.put("status","active");
        userID =
                given()
                        .spec(requestSpecification)
                        .body(newUser)
                        //.log().uri()
                        //.log().body()
                        .when()
                        .post("https://gorest.co.in/public/v2/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;

    }
    @Test(enabled = false)
    public void CreateUserClass() {

        String rndFullname = randomUretici.name().fullName();
        String rndEmail = randomUretici.internet().emailAddress();
        User newUser=new User();
        newUser.name=rndFullname;
        newUser.gender="male";
        newUser.email=rndEmail;
        newUser.status="active";
        userID =
                given()
                        .spec(requestSpecification)
                        .body(newUser)
                        //.log().uri()
                        //.log().body()
                        .when()
                        .post("https://gorest.co.in/public/v2/users")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .contentType(ContentType.JSON)
                        .extract().path("id")
        ;
    }

    @Test(dependsOnMethods = "GetUserByID")
    public void UpdateUser() {

        Map<String,Object> updateUser=new HashMap<>();
        updateUser.put("name","Alperen Demirci");

        given()
                .spec(requestSpecification)
                .body(updateUser)

                .when()
                .put("https://gorest.co.in/public/v2/users"+userID)

                .then()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo("Alperen Demirci"))

        ;

    }

    @Test
    public void DeleteUser() {

    }

    @Test
    public void DeleteUserNegative() {

    }
}
