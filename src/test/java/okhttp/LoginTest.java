package okhttp;

import helpers.*;
import models.AuthenticationRequestModel;
import models.AuthenticationResponseModel;
import models.ErrorModel;
import okhttp3.Request; // класс Request из библиотеки okhttp3 используется для создания HTTP-запросов.
import okhttp3.RequestBody; //класс RequestBody из библиотеки okhttp3 представляет тело HTTP-запроса.
import okhttp3.Response; // класс Response из библиотеки okhttp3 представляет ответ на HTTP-запрос.
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

import static helpers.TestHelper.gson;

public class LoginTest implements TestHelper {

    @Test
    public void loginPositive() throws IOException {
        // AuthenticationRequestModel класс представляет модель запроса аутентификации.
        AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username(PropertiesReader.getProperty("existingUserEmail"))
                .password(PropertiesReader.getProperty("existingUserPassword"));

        // // Создается экземпляр класса AuthenticationRequestModel с заданными данными пользователя для аутентификации (email и пароль).
        RequestBody requestBody = RequestBody
                .create(TestConfig.gson.toJson(requestModel),
                        TestConfig.JSON);

        System.out.println(requestBody);

        // Создается объект RequestBody, содержащий данные запроса в формате JSON.
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(requestBody)
                .build(); // Создается объект Request, представляющий HTTP POST-запрос к указанному URL с телом,
        //.url(PropertiesReader.getProperty("urlLogin"))
        // содержащим данные аутентификации. Класс Request является частью библиотеки OkHttp,
        // которая предоставляет удобный способ для работы с сетевыми запросами. в итоге у вас получается объект Request,
        // который готов к отправке на сервер с заданными параметрами: методом POST, URL-адресом и телом запроса.
        //"https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
        //PropertiesReader.getProperty(url + "/v1/user/registration/usernamepassword"
      //        .username(PropertiesReader.getProperty("existingUserEmail"))
      //                .password(PropertiesReader.getProperty("existingUserPassword"));
        Response response = TestConfig.client.newCall(request).execute(); // Выполняется HTTP-запрос с помощью объекта
        // client из класса TestConfig, и возвращается объект Response т.е. какой-то ответ.

       // если ответ содержит код 2** , то все прошло успешно.
        System.out.println("Response code :" + response.code());
        if(response.isSuccessful()){
            AuthenticationResponseModel responseModel =
                    TestConfig.gson.fromJson(response.body().string(),
                            AuthenticationResponseModel.class);
            System.out.println(responseModel.getToken());
            PropertiesWriter.writeProperties("token", responseModel.getToken(), false); // сохраняем токен

            Assert.assertTrue(response.isSuccessful());
        }
        else {
            System.out.println("Status code : "+response.code());
            ErrorModel errorModel = gson.fromJson(response.body().string(), ErrorModel.class);
            System.out.println("\u001B[32mError status: "+errorModel.getStatus());
            Assert.assertTrue(response.isSuccessful());
        }

    }

    @Test
    public void loginPositiveWithPropertiesXML() throws IOException {
       AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username(PropertiesReader.getProperty("existingUserEmail"))
                .password(PropertiesReader.getProperty("existingUserPassword"));
       RequestBody requestBody = RequestBody
                .create(TestConfig.gson.toJson(requestModel),
                        TestConfig.JSON);
       Request request = new Request.Builder()
                .url(PropertiesReader.getProperty("urlLogin"))
                .post(requestBody)
                .build();
        //"https://contactapp-telran-backend.herokuapp.com/v1/user/registration/usernamepassword")
        //PropertiesReader.getProperty(url + "/v1/user/registration/usernamepassword"
       Response response = TestConfig.client.newCall(request).execute();

        if(response.isSuccessful()){
            AuthenticationRequestModel responseModel =
                    TestConfig.gson.fromJson(response.body().string(),
                            AuthenticationRequestModel.class);
        }
        else {
            System.out.println("Status code : "+response.code());
            ErrorModel errorModel = gson.fromJson(response.body().string(), ErrorModel.class);
            System.out.println("\u001B[32mError status: "+errorModel.getStatus());
            Assert.assertTrue(response.isSuccessful());
        }

    }
}
