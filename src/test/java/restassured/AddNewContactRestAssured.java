package restassured;

import helpers.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.AuthenticationRequestModel;
import models.ContactModel;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AddNewContactRestAssured implements TestHelper {

    @Test
    public void addNewContactPositive(){
        RestAssured.baseURI = ADD_CONTACT_PATH;
        ContactModel contactModel = new ContactModel(NameAndLastNameGenerator.generateName(),
                NameAndLastNameGenerator.generateLastName(),
                EmailGenerator.generateEmail(5,3,2),
                PhoneNumberGenerator.generatePhoneNumber(),
                AddressGenerator.generateAddress(),
                "created with RestAssured");
        given()
                .header(AuthorizationHeader, PropertiesReader.getProperty("token"))
                .body(contactModel)
                .contentType(ContentType.JSON)
                .when()
                .post()  // если бы в бейз ури не было бы пути, передали бы здесь
                .then()
                .assertThat()
                .statusCode(200);

    }
}
