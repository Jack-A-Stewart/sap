package nl.codegorilla.sap.utility;

import java.lang.reflect.Field;

public class Utility {

    /**
     * @param clazz to get fields for
     * @return String[] of fields of given class
     */
    public static String[] getAllFieldsAsStringArray(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }
}
