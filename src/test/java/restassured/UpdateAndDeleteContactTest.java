package restassured;

import helpers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.ContactModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateAndDeleteContactTest implements TestHelper {
   String id;
   ContactModel contactModel;
    @BeforeMethod
    public void precondition(){
        RestAssured.baseURI = ADD_CONTACT_PATH;
        contactModel = new ContactModel(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                EmailGenerator.generateEmail(5,3,2),
                PhoneNumberGenerator.generatePhoneNumber(),
                AddressGenerator.generateAddress(),
                "created with RestAssured for updating");

        String message = given()
                .header(AuthorizationHeader, PropertiesReader.getProperty("token"))
                .body(contactModel)
                .contentType(ContentType.JSON)
                .when().post().then()
                .assertThat().statusCode(200)
                .extract().path("message");

        System.out.println("STRING " + message);
        id = IdExtractor.extactId(message);
      //  String firstEmail = contactModel.getEmail();

    }

    @Test
    public void updateContactTest(){
        contactModel.setId(id);
        contactModel.setEmail(EmailGenerator.generateEmail(5, 3, 2));
        given()
                .header(AuthorizationHeader, PropertiesReader.getProperty("token"))
                .body(contactModel)
                .contentType(ContentType.JSON)
                .when().put().then()
                .assertThat().statusCode(200)
                .assertThat().body("message", containsString("updated"));

        String secondEmail = contactModel.getEmail();

       // Assert.assertNotEquals();

    }

    @Test
    public void deleteContactById(){
        given().header(AuthorizationHeader, PropertiesReader.getProperty("token"))
                .when()
                .delete(id)
                .then().assertThat().statusCode(200)
                .assertThat().body("message", containsString("deleted"));
    }

}
