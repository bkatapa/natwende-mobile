/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.BusVO;
import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

/**
 *
 * @author Bell
 */
public class BusMobileService {
    
    public BusVO updateEntity(BusVO entity) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL)
                .path("create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            LOGGER.debug(response);
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            entity = new ObjectMapper().readValue(result, BusVO.class);
            return entity;
        }
        catch (IOException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
    }
    
    private static final String BASE_URL = "http://localhost:8080/natwende/services/buses";
    private static final String CLASS_NAME = BusMobileService.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    
}
