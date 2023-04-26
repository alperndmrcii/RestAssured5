import Model.Location;
import Model.Place;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

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
    public void bodyArrayHasSizeTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1))

        ;
    }

    @Test
    public void combiningTest() {
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places", hasSize(1))
                .body("places.state", hasItem("California"))
                .body("places[0].'place name'", equalTo("Beverly Hills"))
        ;
    }

    @Test
    public void pathParamTest() {
        given()
                .pathParam("ulke", "us")
                .pathParam("postakod", 90210)
                .log().uri() // request link


                .when()
                .get("http://api.zippopotam.us/{ulke}/{postakod}")


                .then()
                .statusCode(200)

        ;
    }

    @Test
    public void queryParamTest() {
        given()
                .param("page", 1)
                .log().uri() // request link


                .when()
                .get("https://gorest.co.in/public/v1/users")


                .then()
                .statusCode(200)
                .log().body()

        ;
    }

    @Test
    public void queryParamTest2() {
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.

        for (int i = 1; i < 10; i++) {
            given()
                    .param("page", i)
                    .log().uri()
                    .when()
                    .get("https://gorest.co.in/public/v1/users")
                    .then()
                    .statusCode(200)
                    .body("meta.pagination.page", equalTo(i));
        }
    }

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup() {
        baseURI = "https://gorest.co.in/public/v1";

        requestSpec = new RequestSpecBuilder()
                .log(LogDetail.URI)
                .setContentType(ContentType.JSON)
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .log(LogDetail.BODY)
                .build();

    }

    @Test
    public void requestResponseSpecification() {
        given()
                .param("page", 1)
                .spec(requestSpec)


                .when()
                .get("/users")


                .then()
                .spec(responseSpec)
        ;
    }

    @Test
    public void extractingJsonPath() {
        String countryName = given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().path("country");
        System.out.println("countryName = " + countryName);
        Assert.assertEquals(countryName, "United States");
    }

    @Test
    public void extractingJsonPath1() {
        String placeName = given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .extract().path("places[0].'place name'");
        System.out.println("placeName = " + placeName);
        Assert.assertEquals(placeName, "Beverly Hills");
    }

    @Test
    public void extractingJsonPath2() {
        // https://gorest.co.in/public/v1/users  dönen değerdeki limit bilgisini yazdırınız.
        int limit = given()

                .when()
                .get("/users")
                .then()
                // .log().body()
                .extract().path("meta.pagination.limit");
        System.out.println("limit = " + limit);

    }

    @Test
    public void extractingJsonPath3() {
        // https://gorest.co.in/public/v1/users  dönen değerdeki datadaki bütün id bilgilerini yazdırınız.

        List<Integer> idler =
                given()

                        .when()
                        .get("/users")

                        .then()
                        .extract().path("data.id");
        System.out.println("idler = " + idler);
    }

    @Test
    public void extractingJsonPath4() {
        // https://gorest.co.in/public/v1/users  dönen değerdeki datadaki bütün name bilgilerini yazdırınız.
        List<String> names = given()


                .when()
                .get("/users")

                .then()
                .extract().path("data.name");
        System.out.println("names = " + names);

    }

    @Test
    public void extractingJsonPathResponsAll() {
        // https://gorest.co.in/public/v1/users  dönen değerdeki bütün name lei yazdırınız.

        Response donenData =
                given()

                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .statusCode(200)
                        // .log().body()
                        .extract().response(); // dönen tüm datayı verir.
        ;

        List<Integer> idler = donenData.path("data.id");
        List<String> names = donenData.path("data.name");
        int limit = donenData.path("meta.pagination.limit");

        System.out.println("idler = " + idler);
        System.out.println("names = " + names);
        System.out.println("limit = " + limit);

        Assert.assertTrue(names.contains("Sanjay Mukhopadhyay"));
        Assert.assertTrue(idler.contains(1203758));
        Assert.assertEquals(limit, 10, "test sonucu hatalı");
    }

    @Test
    public void extractJsonAll_POJO() {
        // POJO : JSON nesnei : BU DURUMDA LOCATION SEKMESİ

        Location locationNesnesi =
                given()

                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        //.log().body()
                        .extract().body().as(Location.class);


        System.out.println("locationNesnesi = "
                + locationNesnesi.getCountry());


        for (Place p:locationNesnesi.getPlaces())
            System.out.println("p = " + p);


        System.out.println("locationNesnesi.getPlaces().get(0).getPlacename() = "
                + locationNesnesi.getPlaces().get(0).getPlacename());
    }
    @Test
    public void extractPOJO_Soru() {
        // aşağıdaki endpointte Dörtağaç köyüne ait diğer bilgileri yazdırınız
       Location locationNesnesi= given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .extract().body().as(Location.class)
        ;
      // System.out.println( locationNesnesi.getPlaces().get(2).getPlacename());
      // System.out.println( locationNesnesi.getPlaces().get(2).getLatitude());
      // System.out.println( locationNesnesi.getPlaces().get(2).getLongitude());
      // System.out.println( locationNesnesi.getPlaces().get(2).getState());
      // System.out.println( locationNesnesi.getPlaces().get(2).getStateabbreviation());

      //  System.out.println(locationNesnesi.getPlaces().get(2).toString());

        for (Place p: locationNesnesi.getPlaces())
            if (p.getPlacename().equalsIgnoreCase("Dörtağaç Köyü"))
            {
                System.out.println("p = " + p);
            }

    }

}
