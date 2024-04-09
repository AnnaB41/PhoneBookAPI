package restassured;

import helpers.PropertiesReader;
import helpers.TestHelper;
import models.ContactListModel;
import models.ContactModel;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GetAllContacts implements TestHelper {

    @Test
    public void getAllContactsPositive() throws IOException{

        File logFile = new File("src/logs/testresult.log");
        if(!logFile.exists()){
            logFile.getParentFile().mkdirs(); //создай путь директории
            logFile.createNewFile(); // создай новый файл
        }
        PrintStream printStream = new PrintStream(new FileOutputStream(logFile));// для вывода данных , которые в консоли, передаем, куда записать
        System.setOut(printStream);  // стандартный текст??
        System.setErr(printStream); // ошибки

        ContactListModel contactListModel = given()
                .header(AuthorizationHeader, PropertiesReader.getProperty("token"))
                .when()
                .get(PropertiesReader.getProperty("getAllContacts"))
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()// извлечь что-то
                .as(ContactListModel.class); // в качестве контактлистмодел

        for(ContactModel contact : contactListModel.getContacts()){
            System.out.println(contact.getEmail());
            System.out.println(contact.getId());
            System.out.println("=========================================");

        }
        printStream.close();
    }
}
