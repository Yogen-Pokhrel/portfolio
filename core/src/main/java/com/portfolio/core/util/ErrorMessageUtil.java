package com.portfolio.core.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.portfolio.core.helpers.validators.annotations.ErrorMessage;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.*;
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
        errorMessageMap.put(Long.class, fieldType -> "Invalid number");
        errorMessageMap.put(long.class, fieldType -> "Invalid number");
        errorMessageMap.put(Float.class, fieldType -> "Invalid number");
        errorMessageMap.put(float.class, fieldType -> "Invalid number");
        errorMessageMap.put(Short.class, fieldType -> "Invalid number");
        errorMessageMap.put(short.class, fieldType -> "Invalid number");
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
                    return getTypeSpecificMessage(field);
                }

                return errorMessage.message();
            } else {
                return getTypeSpecificMessage(field);
            }
        } catch (Exception e) {
            log.warn("Field '{}' not found in class '{}'.", ref.getFieldName(), ref.getFrom().getClass().getSimpleName());
        }
        return "Invalid value";
    }

    /**
     * Generates a type-specific error message based on the field's type.
     * This method uses a map of common field types to return a corresponding error message.
     * If the field type is an enum, a custom message with the valid enum options is generated.
     *
     * @param field The field to generate an error message for.
     * @return A type-specific error message.
     */
    private static String getTypeSpecificMessage(Field field) {
        Class<?> fieldType = field.getType();

        if (errorMessageMap.containsKey(fieldType)) {
            return errorMessageMap.get(fieldType).apply(fieldType);
        }

        if (Collection.class.isAssignableFrom(fieldType)) {
            if (List.class.isAssignableFrom(fieldType)) {
                return "Invalid list, expected elements of type " + getGenericType(field);
            } else if (Set.class.isAssignableFrom(fieldType)) {
                return "Invalid set, expected elements of type " + getGenericType(field);
            } else if (Map.class.isAssignableFrom(fieldType)) {
                return "Invalid map, expected key-value pairs with types: " + getGenericType(field) + " -> " + getMapValueType(field);
            }
        }

        if (Enum.class.isAssignableFrom(fieldType)) {
            String enumValues = Arrays.toString(fieldType.getEnumConstants());
            return "Invalid value, expected one of the valid enum options: " + enumValues;
        }

        return "Invalid value provided";
    }

    /**
     * Helper method to get the generic type of a collection (List or Set) dynamically based on the field.
     *
     * @param field The field in the class that represents the collection.
     * @return The simple name of the generic type (if available).
     */
    private static String getGenericType(Field field) {
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) field.getGenericType();
            return ((Class<?>) paramType.getActualTypeArguments()[0]).getSimpleName();
        }
        return "Unknown type";
    }

    /**
     * Helper method to get the generic value type of a Map dynamically based on the field.
     *
     * @param field The field in the class that represents the map.
     * @return The simple name of the value type in the map.
     */
    private static String getMapValueType(Field field) {
        if (field.getGenericType() instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) field.getGenericType();
            return ((Class<?>) paramType.getActualTypeArguments()[1]).getSimpleName();
        }
        return "Unknown type";
    }

    /**
     * Utility method for extracting and replacing fully qualified class names in a string
     * with their corresponding simple class names.
     *
     * @param input The input string containing potentially fully qualified class names.
     * @return A new string with all fully qualified class names replaced by their simple class names.
     */
    public static String simplifyMessage(String input) {
        if (input == null) {
            return null;
        }

        String regex = "(?:[a-z]+\\.)+[A-Za-z]+";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

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
