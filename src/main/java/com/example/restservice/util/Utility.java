package com.example.restservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.ResponseEntity;

/**
 * 
 */
public class Utility {
    /**
     * Returns a list of objects to a list of a passed TypeReference
     * 
     * @param <T>
     * @param fileName
     * @param typeReference
     * @return
     */
    public static <T> List<T> readFileToList(String fileName, TypeReference<List<T>> typeReference) {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = TypeReference.class.getResourceAsStream(fileName);
        Object resourceObject = null;
        List<T> resourceObjectsList = null;
        try {
            resourceObject = mapper.readValue(inputStream, typeReference);
            resourceObjectsList = objectsToList(resourceObject);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resourceObjectsList;
    }

    /**
     * Returns an object cast to a List.
     * 
     * @param <T>
     * @param objects
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends List<?>> T objectsToList(Object objects) {
        return (T) objects;
    }

    /**
     * Returns an object cast to a List.
     * 
     * @param <T>
     * @param objects
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends ResponseEntity<?>> T objectToResponseEntity(Object object) {
        return (T) object;
    }

    /**
     * Returns an HTML page as a String.
     * 
     * @param fileName
     * @param resource
     * @return
     */
    public static String getHtml(String fileName, Object resource) {
        InputStream htmlStream = resource.getClass().getResourceAsStream(fileName);
        Scanner scanner = null;
        String html = null;
        try {
            scanner = new Scanner(htmlStream, "UTF-8").useDelimiter("\\A");
            html = scanner.next();
        } catch (Error error) {
            error.printStackTrace();
        } finally {
            scanner.close();
        }

        return html;
    }

}
