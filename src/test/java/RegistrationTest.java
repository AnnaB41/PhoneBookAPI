import helpers.*;
import models.AuthenticationRequestModel;
import models.AuthenticationResponseModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTest {

 @Test
 public void registrationPositiveTest() throws IOException {
     AuthenticationRequestModel requestModel = AuthenticationRequestModel
             .username(EmailGenerator.generateEmail(8,3,2))
             .password(PasswordStringGenerator.generateString());
     RequestBody requestBody = RequestBody
             .create(TestConfig.gson.toJson(requestModel),
                     TestConfig.JSON);
     Request request = new Request.Builder()
             .url(PropertiesReader.getProperty("urlRegistration"))
             .post(requestBody)
             .build();
//"https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
     Response response = TestConfig.client.newCall(request).execute();
     System.out.println("Response code :" + response.code());
     if(response.isSuccessful()){
         AuthenticationResponseModel responseModel =
                 TestConfig.gson.fromJson(response.body().string(),
                         AuthenticationResponseModel.class);
         System.out.println(responseModel.getToken());
         PropertiesWriter.writeProperties("tokenNew", responseModel.getToken(), false); // сохраняем токен

         Assert.assertTrue(response.isSuccessful());
     }
     else {
         System.out.println("Error");
     }

 }
 }

