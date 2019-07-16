/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.trip.vo.ReservationVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

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
    
}
