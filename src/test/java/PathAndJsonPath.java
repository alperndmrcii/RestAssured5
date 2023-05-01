import GoRest.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PathAndJsonPath {
    @Test
    public void extractingPath() {

        //"post code": "90210",

        String postcode = given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("'post code'");
        System.out.println("postcode = " + postcode);
    }

    @Test
    public void extractingJsonPath() {

        //"post code": "90210",

        int postcode = given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().jsonPath().getInt("'post code'");
        System.out.println("postcode = " + postcode);
    }

    @Test
    public void getUsers() {
        Response response =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v2/users")

                        .then()
                        //.log().body()
                        .extract().response();
        int idPath = response.path("[2].id");
        int idJsonPath = response.jsonPath().getInt("[2].id");
        System.out.println("idPath = " + idPath);
        System.out.println("idJsonPath = " + idJsonPath);

        User[] userPath = response.as(User[].class); // as nesne dönüşümünde (POJO) dizi destekli
        List<User> userJsonPath = response.jsonPath().getList("", User.class); // JsonPath ise list olarak verebiliyor
        System.out.println("userPath = " + Arrays.toString(userPath));
        System.out.println("userJsonPath = " + userJsonPath);
    }

    @Test
    public void getUsersV1() {
        Response body=
        given()

                .when()
                .get("https://gorest.co.in/public/v1/users")

                .then()
               // .log().body()
                .extract().response()
        ;
        List<User>dataUsers=body.jsonPath().getList("data", User.class);
        //Jsonpath bir response içindeki bir parçayı nesneye dönüştürebilir.
        System.out.println("dataUsers = " + dataUsers);
        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.

        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.

    }

}
