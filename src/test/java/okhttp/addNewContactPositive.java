package okhttp;

import helpers.*;
import models.ContactModel;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.sql.SQLException;

public class addNewContactPositive implements TestHelper {

    String idResult;
    @Test
            public void addNewContact() throws IOException, SQLException {
    ContactModel contactModel = new ContactModel(NameAndLastNameGenerator.generateName(),
                            NameAndLastNameGenerator.generateLastName(),
                            EmailGenerator.generateEmail(8,3,2),
                            PhoneNumberGenerator.generatePhoneNumber(),
                            AddressGenerator.generateAddress(),
                            "New contact");

    RequestBody requestBody = RequestBody.create(gson.toJson(contactModel), JSON );
    Request request = new Request.Builder()
            .url(ADD_CONTACT_PATH)
            .addHeader(AuthorizationHeader, PropertiesReader.getProperty("token"))
            .post(requestBody).build();

    //  .addHeader(AuthorizationHeader, PropertiesReaderXML.getProperty("token"))

    Response response = client.newCall(request).execute();

    ContactResponseModel contactResponseModel = gson.fromJson(response.body().string(), ContactResponseModel.class);

    String responseMsg = contactResponseModel.getMessage();
    idResult = IdExtractor.extactId(responseMsg);

    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    dataBaseConnection.contactDatabaseRecorder(idResult, contactModel);

        System.out.println("Message: " + idResult);
        Assert.assertTrue(response.isSuccessful());


    }

    @Test
   public void deleteContactByID() throws IOException, SQLException {
                addNewContact();
                Request request= new Request.Builder()
                        .url(ADD_CONTACT_PATH+"/"+idResult)
                        .addHeader(AuthorizationHeader,PropertiesReader.getProperty("token") )
                        .delete()
                        .build();
                Response response = client.newCall(request).execute();
                ContactResponseModel contactResponseModel = gson
                        .fromJson(response.body().string(), ContactResponseModel.class);
        System.out.println("Message: " + contactResponseModel.getMessage());
        Assert.assertTrue(response.isSuccessful());
    }

}
