package okhttp;

import helpers.PropertiesReader;
import helpers.TestConfig;
import helpers.TestHelper;
import models.ContactListModel;
import models.ContactModel;
import models.ContactResponseModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteAllContacts implements TestHelper {


    @Test
    public void deleteAllContacts() throws IOException {
        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("getAllContacts"))
                .addHeader(AuthorizationHeader, PropertiesReader.getProperty("token"))
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string(); //вернули тело ответа в стринге
        ContactListModel contacts = TestConfig.gson.fromJson(responseBody, ContactListModel.class);
        System.out.println(" Count " + contacts.countOfContact());

        if(contacts.countOfContact() != 0){
            Request request1 = new Request.Builder()
                    .url(PropertiesReader.getProperty("deleteAllContacts"))
                    .delete()
                    .addHeader(AuthorizationHeader, PropertiesReader.getProperty("token"))
                    .build();

            Response response1 = client.newCall(request1).execute();
            String responseBody1 = response1.body().string();

            System.out.println("ResponseBody = " + responseBody1);

    } else
            System.out.println("No contacts to delete");









//
//
//        Assert.assertTrue(response.isSuccessful());



    }

}
