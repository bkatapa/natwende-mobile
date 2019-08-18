/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.service.ReservationMobileService;
import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.trip.vo.ReservationVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Bell
 */
@Named
@SessionScoped
public class ReservationMobileView extends MessageHelper {
    
    private List<ReservationVO> entityList;
    private ReservationVO selectedEntity;
    private int activeIndex;
    
    @Inject
    private BookingMobileView bookingMobileView;
    
    @Inject
    private TripMobileView tripMobileView;
    
    @Inject
    private ReservationMobileService reservationMobileService;
    
    @PostConstruct
    public void init() {
        selectedEntity = new ReservationVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
    }

    public List<ReservationVO> getEntityList() {
        return entityList;
    }

    public ReservationVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(ReservationVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }   
    
    public String bookNow() {
        selectedEntity.getBookingList().clear();
        selectedEntity.getBookingList().add(bookingMobileView.getSelectedEntity());
        try {
            return reservationMobileService.generatePayment(selectedEntity, tripMobileView.getSearchResult());
        }
        catch (Exception ex) {
            onMessage("error", ex.getMessage());
        }
        return StringUtils.EMPTY;
    }
}