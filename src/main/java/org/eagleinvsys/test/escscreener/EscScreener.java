package org.eagleinvsys.test.escscreener;

import java.util.List;

public class EscScreener {

    /**
     * Converts {@link List<String>} elements to Csv adapted form
     *
     * @param strings collection to extract elements
     */

    public static List<String> screenCollection(List<String> strings){
        for(int i = 0; i < strings.size(); i++){
            if (strings.get(i).contains(",") || strings.get(i).contains("\"")){
                String tmp = "\"" + strings.get(i).replace("\"", "\"\"") + "\"";
                strings.set(i,tmp);
            }
        }
        return strings;
    }

    /**
     * Converts {@link String} a single string element to Csv adapted form
     */

    public static String screenString(String string){
        String screenedString;
        if (string.contains(",") || string.contains("\"") || string.equals("")){
            screenedString = "\"" + string.replace("\"", "\"\"") + "\"";
            return screenedString;
        }
        return string;
    }
}
