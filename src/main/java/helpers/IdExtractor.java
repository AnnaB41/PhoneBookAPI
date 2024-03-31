package helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IdExtractor {

//    public static void main(String[] args) {
//
//        System.out.println("Res : " + extactId("Contact was added! ID: ac452eef-546c-40da-8bab-47520374e461"));
//    }
    public static String extactId(String input){

        Pattern pattern = Pattern.compile("ID: (\\S+)"); // все символы до пробела
        Matcher matcher = pattern.matcher(input); // создаем объект мэтчер, со строкой, с применением шаблона, указанного выше
        if(matcher.find()){
          return   matcher.group(1);
        }
        else return null;
    }
}
