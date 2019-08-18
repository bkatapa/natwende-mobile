/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.util;

import com.mweka.natwende.trip.vo.TripVO;
import com.mweka.natwende.types.Town;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author Bell
 */
public class Database implements Serializable {
    
    public static void addTrips(List<TripVO> tripList) {
        TripVO defaultTrip = new TripVO();
        defaultTrip.setId(1L);
        defaultTrip.setBusReg("ABL 949");
        defaultTrip.setOccupiedSeats(new HashSet<>());
        defaultTrip.setFrom(Town.LUSAKA);
        defaultTrip.setTo(Town.MONGU);
        defaultTrip.setTotalNumOfSeats(60);
        defaultTrip.setAvailableNumOfSeats(35);
        tripList.add(defaultTrip);
        
        TripVO trip1 = new TripVO();
        trip1.setId(2L);
        trip1.setBusReg("ABV 6687");
        trip1.setOccupiedSeats(new TreeSet<>(Arrays.asList(new String[]{"1_1", "2_2", "6_1"})));
        trip1.setFrom(Town.LUSAKA);
        trip1.setTo(Town.MONGU);
        trip1.setTotalNumOfSeats(52);
        trip1.setAvailableNumOfSeats(30);
        tripList.add(trip1);
        
        TripVO trip2 = new TripVO();
        trip2.setId(3L);
        trip2.setBusReg("ALK 2212");
        trip2.setOccupiedSeats(new TreeSet<>(Arrays.asList(new String[]{"1_4","1_5","2_1","2_2","2_4","2_5","3_1","3_2","5_5","7_2","10_1","10_4","12_1","12_2","13_2"})));
        trip2.setFrom(Town.LUSAKA);
        trip2.setTo(Town.MONGU);
        trip2.setTotalNumOfSeats(60);
        trip2.setAvailableNumOfSeats(35);
        tripList.add(trip2);
        
        Calendar c = Calendar.getInstance();
        c.set(Calendar.AM_PM, 24);
        
        c.set(Calendar.DAY_OF_MONTH, 14);
        c.set(Calendar.MONTH, Calendar.FEBRUARY);
        c.set(Calendar.YEAR, 2018);
        
        c.set(Calendar.HOUR_OF_DAY, 6);
        c.set(Calendar.MINUTE, 0);
        defaultTrip.setScheduledDepartureDate(c.getTime());
        
        c.set(Calendar.HOUR_OF_DAY, 10);
        c.set(Calendar.MINUTE, 30);
        trip1.setScheduledDepartureDate(c.getTime());
        
        c.set(Calendar.HOUR_OF_DAY, 16);
        c.set(Calendar.MINUTE, 00);
        trip1.setScheduledDepartureDate(c.getTime());
    }
    
}
