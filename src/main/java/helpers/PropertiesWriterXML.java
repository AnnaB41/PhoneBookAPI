package helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesWriterXML {
    public PropertiesWriterXML(String filePath) {
        this.filePath = filePath;
    }
    private static String filePath; // путь
    Properties properties = new Properties();

    public PropertiesWriterXML(Properties properties) {
        this.properties = properties;
    }

    public PropertiesWriterXML() {

    }

    public void setProperties(String key, String value, boolean clearFile){ // метод для записи параметр 3-   для очищения файла
        if(!clearFile){
            try( FileInputStream inputStream = new FileInputStream(filePath)){
                properties.loadFromXML(inputStream);  // загрузи из xml
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        properties.setProperty(key, value);
        try(FileOutputStream outputStream = new FileOutputStream(filePath)){
            properties.storeToXML(outputStream, null); // сохраняем в XML
        }
        catch (IOException e){e.printStackTrace();}
    }



    }


