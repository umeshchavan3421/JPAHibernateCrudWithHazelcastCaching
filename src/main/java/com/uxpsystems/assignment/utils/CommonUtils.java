package com.uxpsystems.assignment.utils;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtils {
	
	public static <T, E> E mapObject(Class<E> classType, String file) {
        E response = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            InputStream stream = CommonUtils.class.getResourceAsStream(file);
            response = mapper.readValue(stream, classType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

	public static <T,E> E mapStringToObject(String contentAsString, Class<E> class1) {
		E response = null;
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            response = mapper.readValue(contentAsString, class1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
	}


}
