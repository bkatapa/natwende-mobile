/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.user.vo.UserVO;
import java.io.Serializable;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

/**
 *
 * @author Bell
 */
@Named
public class UserMobileService implements Serializable {
    
    public UserVO getEntity(String nrc) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL)
                .path("nrc")
                .path(nrc)
                .request(MediaType.APPLICATION_JSON)
                .get();
        if (response.getStatus() != 200) {
            LOGGER.debug(response);
            throw new Exception(response.getStatusInfo().toString());
        }
        String result = response.readEntity(String.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        UserVO entity = mapper.readValue(result, UserVO.class);
        return entity;
    }
    
    private static final String BASE_URL = "http://localhost:8080/natwende/services/users";
    private static final String CLASS_NAME = UserMobileService.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
}
