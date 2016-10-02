package utils;

/**
 * Created by Yossi on 01/10/2016.
 */
public class StringUtils {

    public StringUtils(){}

    public static int getWordCount(String str){
        if(str == null){
            return 0;
        }
        // Get rid of leading and trailing whitespaces.
        String trimmed = str.trim();
        // Split the original string to array of words(separated by whitespaces).
        return trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
    }
}
