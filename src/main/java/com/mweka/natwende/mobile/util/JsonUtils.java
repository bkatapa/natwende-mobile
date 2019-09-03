/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.util.JacksonIgnoreInheritedProperties;
import java.io.Serializable;

/**
 *
 * @author bellk
 */
public class JsonUtils implements Serializable {
    
    public static void beforeDeserialization(ObjectMapper mapper) {
        mapper.setAnnotationIntrospector(JacksonIgnoreInheritedProperties.getIntrospetor());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
    
}
