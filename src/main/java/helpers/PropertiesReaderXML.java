package helpers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesReaderXML { // для чтения файла
    private  static final String PROPERTIES_PATH = "src/main/resources/myfile.xml";

    public static String getProperty(String key){// метод для чтения из файла xml
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(PROPERTIES_PATH)){
            properties.loadFromXML(fis);
            return properties.getProperty(key);
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }


    }

}