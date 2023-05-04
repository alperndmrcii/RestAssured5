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

public class BankAccountTest {
    Faker faker = new Faker();
    String BankAccountID;
    String IBAN = faker.number().digits(10);
    String BankAccountName;
    String SchooldID="6390f3207a3bcb6a7ac977f9";
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
    public void AddBankAccount() {

        Map<String, String> BankAccount = new HashMap<>();
        BankAccountName = faker.name().firstName() + faker.number().digits(3);
        BankAccount.put("iban", IBAN);
        BankAccount.put("name", BankAccountName);
        BankAccount.put("currency","EUR");
        BankAccount.put("schoolId", SchooldID);

        BankAccountID = given()
                .spec(reqSpec)
                .body(BankAccount)
                .log().body()

                .when()
                .post("/school-service/api/bank-accounts")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
        ;
        System.out.println("BankAccountID = " + BankAccountID);
    }

    @Test(dependsOnMethods = "AddBankAccount")
    public void AddBankAccountNegative(){
        Map<String, String> BankAccount = new HashMap<>();
        BankAccount.put("iban", IBAN);
        BankAccount.put("name", BankAccountName);
        BankAccount.put("currency","EUR");
        BankAccount.put("schoolId", SchooldID);



        given()
                .spec(reqSpec)
                .body(BankAccount)
                .log().body()

                .when()
                .post("/school-service/api/bank-accounts")

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsString("already"))
        ;
    }
}