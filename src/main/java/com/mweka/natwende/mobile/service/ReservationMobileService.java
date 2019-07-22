/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mweka.natwende.payment.vo.PaymentVO;
import com.mweka.natwende.trip.search.vo.TripSearchResultVO;
import com.mweka.natwende.trip.vo.BookingVO;
import com.mweka.natwende.trip.vo.ReservationVO;
import com.mweka.natwende.types.BookingStatus;
import com.mweka.natwende.types.PaymentStatus;
import com.mweka.natwende.user.vo.UserVO;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

/**
 *
 * @author Bell
 */
@Named
public class ReservationMobileService implements Serializable {
    
    @Inject
    private PaymentMobileService paymentMobileService;
    
    @Inject
    private BookingMobileService bookingMobileService;
    
    @Inject
    private UserMobileService userMobileService;
    
    public String generatePayment(ReservationVO reservation, TripSearchResultVO searchResult) throws Exception {
        try {                        
            if (reservation.getBookingList() == null || reservation.getBookingList().isEmpty()) {
                throw new Exception("No booking found for reservation.");
            }
            BookingVO booking = reservation.getBookingList().get(0);
            PaymentVO payment = reservation.getPayment();
            if (reservation.getCustomer() == null) {
                reservation.setCustomer(userMobileService.getEntity("749865"));
            }
            populatePaymentFields(reservation, searchResult);
            payment = paymentMobileService.updateEntity(payment);            
            reservation.setPayment(payment);
            reservation = updateEntity(reservation);
            populateBookingFields(reservation, searchResult);
            bookingMobileService.updateEntity(booking);
            
            switch (payment.getPaymentOption()) {
                case BANK_APP :
                case MOBILE_MONEY :
                case ZOONA : // Save to DB for third party to pick up. Show message of pending payment.
                    reservation.setBookingStatus(BookingStatus.RESERVED);
                    break;
                case CASH :
                case SWIPE_MACHINE : // Log cashier details. Show success message.
                    payment.setPaymentStatus(PaymentStatus.AUTHORIZED);
                    paymentMobileService.updateEntity(payment);
                    reservation.setBookingStatus(BookingStatus.CONFIRMED);
                    updateEntity(reservation);
                    return "pm:bookingCaptureOutcome-confirmed?transition=fade";
                case VIA_CARD : // Connect to payment gateway. Show outcome of payment.
                    break;
                default : ;
            }
            updateEntity(reservation);
        }
        catch (Exception ex) {
            if (!ex.getMessage().contains("No booking found for reservation")) {
                LOGGER.info(bookingMobileService.deleteEntity(reservation.getBookingList().get(0)));
            }
            if (reservation.getId() > 0) {
                LOGGER.info(deleteEntity(reservation));
            }
            if (reservation.getPayment().getId() > 0) {
                LOGGER.info(paymentMobileService.deleteEntity(reservation.getPayment()));
            }
            LOGGER.debug(ex);
            throw ex;
        }
        return "pm:bookingCaptureOutcome-pending?transition=fade";
    }   
    
    public ReservationVO updateEntity(ReservationVO entity) throws Exception {
        Client client = ClientBuilder.newClient();
        Response response = client.target(BASE_URL)
                .path("update")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(entity, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 200) {
            LOGGER.debug(response);
            throw new Exception(response.getStatusInfo().toString());
        }
        try {
            String result = response.readEntity(String.class);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            entity = mapper.readValue(result, ReservationVO.class);
            return entity;
        }
        catch (IOException ex) {
            LOGGER.debug(ex);
            throw ex;
        }
    }
    
    public String deleteEntity(ReservationVO entity) throws Exception {
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
    
    private void populatePaymentFields(ReservationVO reservation, TripSearchResultVO searchResult) {
        PaymentVO payment = reservation.getPayment();
        UserVO customer = reservation.getCustomer();        
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setCustomerName(customer.getName());
        payment.setPhoneNumber(customer.getContactNumber());
        payment.setBeneficiary(searchResult.getTrip().getOperatorName().getDisplay());
        payment.setAmount(searchResult.getStretch().getFareAmount());
        payment.setDescription(new StringBuilder(payment.getBeneficiary())
            .append(StringUtils.SPACE)
            .append(searchResult.getFromTown().getDisplay())
            .append("-")
            .append(searchResult.getToTown().getDisplay())
            .append(",")
            .append(SDF.format(searchResult.getEstimatedJourneyStartDate()))
            .append(",")
            .append(payment.getCustomerName())
            .toString());
    }
    
    private void populateBookingFields(ReservationVO reservation, TripSearchResultVO searchResult) {
        BookingVO booking = reservation.getBookingList().get(0);
        UserVO customer = reservation.getCustomer();
        booking.setTrip(searchResult.getTrip());
        booking.setAccountUser(customer.getName());
        booking.setFrom(searchResult.getFromTown().getDisplay());
        booking.setTo(searchResult.getToTown().getDisplay());
        booking.setAccountUserEmail(customer.getEmail());
        booking.setReservation(reservation);
    }
    
    private static final String BASE_URL = "http://localhost:8080/natwende/services/reservations";
    private static final String CLASS_NAME = ReservationMobileService.class.getName();
    private static final Logger LOGGER = Logger.getLogger(CLASS_NAME);
    private static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
}
