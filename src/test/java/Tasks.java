import Model.Location;
import Model.ToDo;
import io.restassured.http.ContentType;
import org.testng.Assert;
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
    public void task2() {
        given()


                .when()
                .get("https://httpsat.us/203")


                .then()
                .log().all()

                .statusCode(203)
                .contentType(ContentType.TEXT)

        ;
    }

    /**
     * Task1
     * Create a request to https://jsonplaceholder.typicode.com/todos/2
     * Expect status 200
     * Converting into POJO
     */
    @Test
    public void Task1() {

        ToDo todo = given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")


                .then()
                .log().body()
                .statusCode(200)
                .extract().body().as(ToDo.class);
        System.out.println("todo = " + todo);
        System.out.println("todo.getTitle() = " + todo.getTitle());
    }

    /**
     * Task 3
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void Task3() {
      given()


                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")


                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title",equalTo("quis ut nam facilis et officia qui"))
                ;

    }
    /**
     Task 4
     create a request to https://jsonplaceholder.typicode.com/todos/2
     expect status 200
     expect content type JSON
     expect response completed status to be false
     extract completed field and testNG assertion
     */
    @Test
    public void Task4(){
        ToDo todo=given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .body("completed",equalTo(false))
                .contentType(ContentType.JSON)
                .extract().body().as(ToDo.class)
        ;
        Assert.assertTrue(todo.getCompleted().equals(false));
    }


}
