/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.mobile.util.JsonUtils;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import com.mweka.natwende.types.Town;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
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
public class TripMobileService implements Serializable {
    
    public List<TripSearchResultVO> searchTrips(TripSearchVO searchVO) throws Exception {
        List<TripSearchResultVO> resultList = Collections.emptyList();
        try {
            ObjectMapper mapper = new ObjectMapper();            
            JsonUtils.beforeDeserialization(mapper);
            String json = fetchTripsJson(searchVO.getFromTown().name(), searchVO.getToTown().name(), searchVO.getTravelDate());
            //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
            resultList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, TripSearchResultVO.class));
        }
        catch (IOException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
        return resultList;
    }
    
    public static String fetchTripsJson(String fromTown, String toTown, Date date) throws Exception {
        String travelDate = SDF.format(date);
        Client client = ClientBuilder.newClient()/*.register(JacksonJsonProvider.class)*/;
        //System.out.println("Calling server");
        Response res = client.target(BASE_URI)
                .path("from")
                .path(fromTown)
                .path("to")
                .path(toTown)
                .path("date")
                .path(travelDate)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        String result = res.readEntity(String.class);
        System.out.println(result);
        if (res.getStatus() == 200) {
            return result;
        }
        String errMsg = res.getStatus() + ", " + res.getStatusInfo() + ", " + result;
        throw new Exception(errMsg);
    }
    
    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2019);
        c.set(Calendar.MONTH, Calendar.NOVEMBER);
        c.set(Calendar.DAY_OF_MONTH, 30);
        Date date = c.getTime();
        String fromTown = Town.LUSAKA.getDisplay();
        String toTown = Town.NDOLA.getDisplay();
        try {
            System.out.println("About to make a call");
            String json = fetchTripsJson(fromTown, toTown, date);
            System.out.println(json);
        }
        catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
        } 
    }
    
    private static final String SOURCE_CLASS = TripMobileService.class.getName();
    private static final Logger LOGGER = Logger.getLogger(SOURCE_CLASS);
    private static final String BASE_URI = "http://localhost:8080/natwende/services/trips";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
}
