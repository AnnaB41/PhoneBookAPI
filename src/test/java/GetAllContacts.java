import helpers.PropertiesReader;
import helpers.TestConfig;
import models.ContactListModel;
import models.ContactModel;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContacts {

    @Test
    public void getAllContactsPositive() throws IOException {


        Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("getAllContacts"))
                .addHeader("Authorization", PropertiesReader.getProperty("token"))
                .build();  // это запрос

        Response response = TestConfig.client.newCall(request).execute(); //ответ получить из запроса

        String responseBody = response.body().string(); //вернули тело ответа в стринге
        System.out.println("Response body : " + responseBody.toString());

        Assert.assertTrue(response.isSuccessful());

        ContactListModel contacts = TestConfig.gson.fromJson(responseBody, ContactListModel.class);

       for(ContactModel contactModel : contacts.getContacts()){
           System.out.println(contactModel.getName());
           System.out.println(contactModel.getEmail());
           System.out.println("+++++++++++++++++++++++++++++++");
       }

    }
}
