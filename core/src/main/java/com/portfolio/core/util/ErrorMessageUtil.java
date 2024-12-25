package com.portfolio.core.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.core.helpers.validators.annotations.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ErrorMessageUtil {
    /**
     * A map that holds the error message strategies for different field types.
     * Each entry in the map associates a field type (such as String, Date, etc.)
     * with a function that generates the corresponding error message.
     */
    private static final Map<Class<?>, Function<Class<?>, String>> errorMessageMap = new HashMap<>();

    static {
        // Initialize the map with error messages for common field types.
        errorMessageMap.put(String.class, fieldType -> "Invalid String");
        errorMessageMap.put(Date.class, fieldType -> "Invalid Date, expected format YYYY-MM-DD");
        errorMessageMap.put(LocalDate.class, fieldType -> "Invalid Date, expected format YYYY-MM-DD");
        errorMessageMap.put(Integer.class, fieldType -> "Invalid number");
        errorMessageMap.put(int.class, fieldType -> "Invalid number");
        errorMessageMap.put(Double.class, fieldType -> "Invalid decimal value");
        errorMessageMap.put(double.class, fieldType -> "Invalid decimal value");
        errorMessageMap.put(Boolean.class, fieldType -> "Invalid boolean value: Expected either true or false");
        errorMessageMap.put(boolean.class, fieldType -> "Invalid boolean value: Expected either true or false");
    }

    /**
     * Returns a custom error message based on the field's annotation or field type.
     * If the field has an {@link ErrorMessage} annotation, the associated message is returned.
     * Otherwise, a type-specific message is generated based on the field type.
     *
     * @param ref The reference that provides the field name and the object it belongs to.
     * @return A custom error message for the field.
     */
    public static String getCustomErrorMessage(JsonMappingException.Reference ref) {
        try {
            Class<?> targetClass = ref.getFrom().getClass();
            Field field = targetClass.getDeclaredField(ref.getFieldName());

            if (field.isAnnotationPresent(ErrorMessage.class)) {
                ErrorMessage errorMessage = field.getAnnotation(ErrorMessage.class);

                if (errorMessage.message().isEmpty()) {
                    return getTypeSpecificMessage(field.getType());
                }

                return errorMessage.message();
            }else{
                return getTypeSpecificMessage(field.getType());
            }
        } catch (NoSuchFieldException e) {
            log.warn("Field '{}' not found in class '{}'.", ref.getFieldName(), ref.getFrom().getClass().getSimpleName());
        }
        return "Invalid value";
    }

    /**
     * Generates a type-specific error message based on the field's type.
     * This method uses a map of common field types to return a corresponding error message.
     * If the field type is an enum, a custom message with the valid enum options is generated.
     *
     * @param fieldType The class type of the field.
     * @return A type-specific error message.
     */
    private static String getTypeSpecificMessage(Class<?> fieldType) {
        if (errorMessageMap.containsKey(fieldType)) {
            return errorMessageMap.get(fieldType).apply(fieldType);
        }

        // If it's an enum type, check if the fieldType is assignable from Enum,
        //we should be specifically looking for classes that are assignable from Enum, not Enum.class itself which is why this message is not added on the map
        if (Enum.class.isAssignableFrom(fieldType)) {
            String enumValues = Arrays.toString(fieldType.getEnumConstants());
            return "Invalid value, expected one of the valid enum options: " + enumValues;
        }

        return "Invalid value for type: " + fieldType.getSimpleName();
    }

    /**
     * Utility class for extracting and replacing fully qualified class names in a string
     * with their corresponding simple class names.
     *
     * The method uses regular expressions to identify occurrences of fully qualified class names
     * (e.g., "java.util.LocalDate" or "java.io.File") and replaces them with their simple class names
     * (e.g., "LocalDate" or "File") within the provided input string.
     *
     * <p>
     * Example:
     * <pre>
     * String input = "Some text java.util.LocalDate and other append java.io.File";
     * String result = ClassNameExtractor.extractSimpleClassName(input);
     * System.out.println(result);  // Output: "Some text LocalDate and other append File"
     * </pre>
     * </p>
     *
     * <h3>Regex Explanation</h3>
     * The method uses the following regular expression to identify fully qualified class names:
     * <ul>
     *   <li><b>(?:[a-z]+\\.)+</b>: Matches one or more lowercase package names followed by a dot (e.g., "java." or "util.")</li>
     *   <li><b>[A-Za-z]+</b>: Matches the class name (e.g., "LocalDate" or "File")</li>
     * </ul>
     *
     * The replacement occurs by extracting the class name (the part after the last dot) and replacing
     * the matched fully qualified class name with just the class name in the input string.
     *
     * <h3>Important Notes</h3>
     * <ul>
     *   <li>The method replaces all occurrences of fully qualified class names in the input string.</li>
     *   <li>It does not modify the original input string but returns a new string with the replacements.</li>
     * </ul>
     *
     * @param input The input string containing potentially fully qualified class names.
     * @return A new string with all fully qualified class names replaced by their simple class names..
     */
    public static String simplifyMessage(String input) {
        if (input == null) { return null;}

        // Regex pattern to match the fully qualified class name
        String regex = "(?:[a-z]+\\.)+[A-Za-z]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Replace all occurrences of fully qualified class names with just the class name
        StringBuilder output = new StringBuilder();
        while (matcher.find()) {
            String fullClassName = matcher.group();
            String simpleClassName = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            matcher.appendReplacement(output, simpleClassName);
        }
        matcher.appendTail(output);

        return output.toString();
    }
}
