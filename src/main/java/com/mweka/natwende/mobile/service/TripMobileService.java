/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
import com.mweka.natwende.trip.search.vo.TripSearchVO;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
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
            String json = fetchTripsJson(searchVO.getFromTown().name(), searchVO.getToTown().name(), searchVO.getTravelDate());
            resultList = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, TripSearchResultVO.class));
        }
        catch (IOException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
        return resultList;
    }
    
    public String fetchTripsJson(String fromTown, String toTown, Date date) throws Exception {
        Client client = ClientBuilder.newClient()/*.register(JacksonJsonProvider.class)*/;
        WebTarget target = client.target(BASE_URI);
        String travelDate = SDF.format(date);
        try {
            String jsonResponse = target.path("trips")
                    .path("from")
                    .path(fromTown)
                    .path("to")
                    .path(toTown)
                    .path("date")
                    .path(travelDate)
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            return jsonResponse;
        }
        catch (NotFoundException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
    }
    
    public String fetchBusTemplateJson(String busReg, BigDecimal busFare) throws Exception {
        Client client = ClientBuilder.newClient();
        try {
            String jsonResponse = client.target(BASE_URI)                    
                    .path("busReg")
                    .path(busReg)
                    .path("fareAmount")
                    .path(busFare.toPlainString())
                    .path("templateScript")
                    .path("isMobile")
                    .path(Boolean.TRUE.toString())
                    .request()
                    .accept(MediaType.APPLICATION_JSON)
                    .get(String.class);
            return jsonResponse;
        }
        catch (NotFoundException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
    }
    
    private static final String SOURCE_CLASS = TripMobileService.class.getName();
    private static final Logger LOGGER = Logger.getLogger(SOURCE_CLASS);
    private static final String BASE_URI = "http://localhost:8080/natwende/services/trips";
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd-MM-yyyy");
}
