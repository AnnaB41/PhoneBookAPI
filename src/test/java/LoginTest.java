import helpers.PropertiesReader;
import helpers.TestConfig;
import models.AuthenticationRequestModel;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {

    @Test
    public void loginPositive() throws IOException {
        // AuthenticationRequestModel класс представляет модель запроса аутентификации.
        AuthenticationRequestModel requestModel = AuthenticationRequestModel
                .username("myemail5@mail.com")
                .password("Tt123456$");
        // // Создается экземпляр класса AuthenticationRequestModel с заданными данными пользователя для аутентификации (email и пароль).
        RequestBody requestBody = RequestBody
                .create(TestConfig.gson.toJson(requestModel),
                        TestConfig.JSON);
        // Создается объект RequestBody, содержащий данные запроса в формате JSON.
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(requestBody)
                .build(); // Создается объект Request, представляющий HTTP POST-запрос к указанному URL с телом,
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

        if(response.isSuccessful()){
            AuthenticationRequestModel responseModel =
                    TestConfig.gson.fromJson(response.body().string(),
                            AuthenticationRequestModel.class);
        }
        else {
            System.out.println("Error");
        }

    }

    @Test
    public void loginPositiveWithProperties() throws IOException {
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
            System.out.println("Error");
        }

    }
}
