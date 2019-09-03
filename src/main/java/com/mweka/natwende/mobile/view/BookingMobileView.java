/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mweka.natwende.mobile.view;

import com.mweka.natwende.mobile.util.MessageHelper;
import com.mweka.natwende.trip.vo.BookingVO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Bell
 */
@Named
@SessionScoped
public class BookingMobileView extends MessageHelper {
    
    private List<BookingVO> entityList;
    private BookingVO selectedEntity;
    private int activeIndex;
    
    @PostConstruct
    public void init() {
        selectedEntity = new BookingVO();
        entityList = new ArrayList<>();
        activeIndex = 0;
    }

    public List<BookingVO> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<BookingVO> entityList) {
        this.entityList = entityList;
    }

    public BookingVO getSelectedEntity() {
        return selectedEntity;
    }

    public void setSelectedEntity(BookingVO selectedEntity) {
        this.selectedEntity = selectedEntity;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }
    
    public void loadEntityList() {
        // web service call
    }
    
    public void checkout() { System.out.println("Navigating to [bookingCapture-step1]");
        RequestContext.getCurrentInstance().update("bookingCapture-step1");
        RequestContext.getCurrentInstance().execute("PrimeFaces.Mobile.navigate('#bookingCapture-step1', {reverse: false, transition: 'slideup'});");
    }
}
    


