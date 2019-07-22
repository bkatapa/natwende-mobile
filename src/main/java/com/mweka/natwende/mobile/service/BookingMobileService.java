/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.trip.vo.BookingVO;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
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
@Named
public class BookingMobileService implements Serializable {
    
    public BookingVO updateEntity(BookingVO booking) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL)
                .path("create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(booking, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            LOGGER.debug(response);
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            BookingVO entity = mapper.readValue(result, BookingVO.class);
            return entity;
        }
        catch (IOException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
    }
    
    public String deleteEntity(BookingVO entity) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL)
                .path("delete")
                .path("" + entity.getId())
                .request(MediaType.TEXT_PLAIN)
                .delete();
        String result = response.readEntity(String.class);
        if (response.getStatus() == 200) {
            response.close();
        }
        else {
            response.close();
            throw new Exception(response.getStatusInfo().toString() + " " + result);
        }
        return result;
    }
    
    private static final String BASE_URL = "http://localhost:8080/natwende/services/bookings";
    private static final String CLASS_NAME = UserMobileService.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    
}
